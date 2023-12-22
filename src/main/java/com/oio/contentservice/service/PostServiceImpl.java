package com.oio.contentservice.service;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.PostRepository;
import com.oio.contentservice.repository.ReplyRepository;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.RequestPostRemove;
import com.oio.contentservice.vo.ResponsePostModify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Value("${com.oio.upload.path}")
    private String uploadPath;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    private final S3FileService s3FileService;

    @Override
    public Long register(PostDto postDto) {

//        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
        PostEntity postEntity = dtoToEntity(postDto);

        Long pno = postRepository.save(postEntity).getPno();

        return pno;
    }

    @Override
    public List<PostDto> getPostAll(String nickName) {

        List<PostEntity> list = postRepository.findByNickName(nickName);

        List<PostDto> resultPost = new ArrayList<>();

        list.forEach(p -> {
            PostDto postDto = entityToDTO(p);
            resultPost.add(postDto);
        });

        return resultPost;
    }

    @Override
    public Map<String, Object> getPostById(Long pno, String nickName) {

        Map<String, Object> resultMap = new HashMap<>();
        boolean isEquals = false;

        Optional<PostEntity> result = postRepository.findByIdWithImages(pno);

        PostEntity postEntity = result.orElseThrow();

//        PostDto postDto = modelMapper.map(postEntity, PostDto.class);
        if(postEntity.getNickName().equals(nickName) || nickName.equals("관리자")){
            isEquals = true;
        }

        PostDto postDto = entityToDTO(postEntity);

        resultMap.put("postDto", postDto);
        resultMap.put("isEquals", isEquals);

        return resultMap;
    }

    @Override
    public ResponsePostModify modifyPost(RequestPostModify requestPostModify) {

        Optional<PostEntity> result = postRepository.findById(requestPostModify.getPno());

        PostEntity postEntity = result.orElseThrow();

        postEntity.change(requestPostModify.getTitle(), requestPostModify.getContent());

        postEntity.clearImages();

        if(requestPostModify.getFileNames() != null) {
            for(String fileName : requestPostModify.getFileNames()) {
                String[] arr = fileName.split("_");
                postEntity.addImage(arr[0], arr[1]);
            }
        }

        postRepository.save(postEntity);

        ResponsePostModify responseModify = modelMapper.map(postEntity, ResponsePostModify.class);

        return responseModify;
    }

    @Override
    public void removePost(RequestPostRemove requestPostRemove) {

        replyRepository.deleteByPost_Pno(requestPostRemove.getPno());

        postRepository.deleteById(requestPostRemove.getPno());

        List<String> fileNames = requestPostRemove.getFileNames();
        if(fileNames != null && fileNames.size() > 0) {
            fileNames.forEach(fileName->{
                System.out.println(fileName);
                s3FileService.removeS3File(fileName);
            });
//            removeFiles(fileNames);
        }
    }

    private void removeFiles(List<String> files) {

        for(String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

                if(contentType.startsWith("image")) {
                    File thumnailFile = new File(uploadPath +  File.separator + "s_"+ fileName);

                    thumnailFile.delete();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    @Override
    public PageResponseDto<PostDto> getPosts(PageRequestDto pageRequestDto, String category) {

        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("pno");

        Page<PostEntity> result = postRepository.searchAll(types, keyword, pageable, category);

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
