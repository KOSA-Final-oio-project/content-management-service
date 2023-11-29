package com.oio.contentservice.service;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
