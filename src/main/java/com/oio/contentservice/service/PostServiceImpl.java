package com.oio.contentservice.service;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.PostRepository;
import com.oio.contentservice.vo.ResponseModify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<PostDto> getPostByAll() {

        List<PostEntity> list = postRepository.findAll();

        List<PostDto> resultPost = new ArrayList<>();

        list.forEach(p->{
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
    public ResponseModify modifyPost(PostDto postDto) {

        Optional<PostEntity> result = postRepository.findById(postDto.getPno());

        PostEntity postEntity = result.orElseThrow();

        postEntity.change(postDto.getTitle(), postDto.getContent());

        postRepository.save(postEntity);

        ResponseModify responseModify = modelMapper.map(postEntity, ResponseModify.class);

        return responseModify;
    }

    @Override
    public void removePost(Long pno) {

        postRepository.deleteById(pno);
    }


}
