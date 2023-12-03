package com.oio.contentservice.service;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.RequestPostRemove;
import com.oio.contentservice.vo.ResponsePostModify;

import java.util.List;
import java.util.stream.Collectors;

public interface PostService {

    Long register(PostDto postDto);

    List<PostDto> getPostAll();

    PostDto getPostById(Long pno);

    ResponsePostModify modifyPost(RequestPostModify RequestPostModify);

    void removePost(RequestPostRemove requestPostRemove);

    // 게시글 페이징
    PageResponseDto<PostDto> getPosts(PageRequestDto pageRequestDto);

    default PostEntity dtoToEntity(PostDto postDto){

        PostEntity postEntity = PostEntity.builder()
                .pno(postDto.getPno())
                .nickName(postDto.getNickName())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .key(postDto.getKey())
                .password(postDto.getPassword())
                .status(postDto.getStatus())
                .build();

        if(postDto.getFileNames() != null) {
            postDto.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                postEntity.addImage(arr[0], arr[1]);
            });
        }

        return postEntity;
    }

    default  PostDto entityToDTO(PostEntity postEntity){

        PostDto postDto = PostDto.builder()
                .pno(postEntity.getPno())
                .nickName(postEntity.getNickName())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .category(postEntity.getCategory())
                .key(postEntity.getKey())
                .password(postEntity.getPassword())
                .status(postEntity.getStatus())
                .createdAt(postEntity.getCreatedAt())
                .build();

        List<String> fileNames =
                postEntity.getImageSet().stream().sorted().map(postImage ->
                    postImage.getUuid()+"_"+postImage.getFileName()).collect(Collectors.toList());

        postDto.setFileNames(fileNames);

        return postDto;
    }



    
}
