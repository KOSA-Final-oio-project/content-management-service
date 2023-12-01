package com.oio.contentservice.service;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.PostRepository;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.ResponsePostModify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(PostDto postDto) {

        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);

        Long pno = postRepository.save(postEntity).getPno();

        return pno;
    }

    @Override
    public List<PostDto> getPostAll() {

        List<PostEntity> list = postRepository.findAll();

        List<PostDto> resultPost = new ArrayList<>();

        list.forEach(p -> {
            PostDto postDto = modelMapper.map(p, PostDto.class);
            resultPost.add(postDto);
        });

        return resultPost;
    }

    @Override
    public PostDto getPostById(Long pno) {

        Optional<PostEntity> result = postRepository.findById(pno);

        PostEntity postEntity = result.orElseThrow();

        PostDto postDto = modelMapper.map(postEntity, PostDto.class);

        return postDto;
    }

    @Override
    public ResponsePostModify modifyPost(RequestPostModify RequestPostModify) {

        Optional<PostEntity> result = postRepository.findById(RequestPostModify.getPno());

        PostEntity postEntity = result.orElseThrow();

        postEntity.change(RequestPostModify.getTitle(), RequestPostModify.getContent());

        postRepository.save(postEntity);

        ResponsePostModify responseModify = modelMapper.map(postEntity, ResponsePostModify.class);

        return responseModify;
    }

    @Override
    public void removePost(Long pno) {

        postRepository.deleteById(pno);
    }

    @Override
    public PageResponseDto<PostDto> getPosts(PageRequestDto pageRequestDto) {

        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("pno");

        Page<PostEntity> result = postRepository.searchAll(types, keyword, pageable);

        List<PostDto> dtoList = result.getContent().stream()
                .map(p -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        PageResponseDto<PostDto> pageResponseDto= PageResponseDto.<PostDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

        return pageResponseDto;
    }
}
