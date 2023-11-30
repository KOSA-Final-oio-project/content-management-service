package com.oio.contentservice.service;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.vo.ResponseModify;

import java.util.List;

public interface PostService {

    Long register(PostDto postDto);

    List<PostDto> getPostAll();

    PostDto getPostById(Long pno);

    ResponseModify modifyPost(PostDto postDto);

    void removePost(Long pno);

    // 게시글 페이징
    PageResponseDto<PostDto> getPosts(PageRequestDto pageRequestDto);
    
}
