package com.oio.contentservice.service;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.vo.ResponseModify;

import java.util.List;

public interface PostService {

    Long register(PostDto postDto);

    List<PostDto> getPostByAll();

    PostDto getPostById(Long pno);

    ResponseModify modifyPost(PostDto postDto);

    void removePost(Long pno);
}
