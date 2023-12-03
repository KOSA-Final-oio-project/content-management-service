package com.oio.contentservice.service;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.ResponsePostModify;

import java.util.List;

public interface PostService {

    Long register(PostDto postDto);

    List<PostDto> getPostAll();

    PostDto getPostById(Long pno);

    ResponsePostModify modifyPost(RequestPostModify RequestPostModify);

    void removePost(Long pno);

    // 게시글 페이징
    PageResponseDto<PostDto> getPosts(PageRequestDto pageRequestDto);
    
}
