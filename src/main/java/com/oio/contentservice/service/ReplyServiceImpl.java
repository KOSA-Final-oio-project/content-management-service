package com.oio.contentservice.service;

import com.oio.contentservice.dto.ReplyDto;
import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.jpa.ReplyEntity;
import com.oio.contentservice.repository.PostRepository;
import com.oio.contentservice.repository.ReplyRepository;
import com.oio.contentservice.vo.RequestReplyModify;
import com.oio.contentservice.vo.ResponseReplyModify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDto replyDto) {

        ReplyEntity replyEntity = modelMapper.map(replyDto, ReplyEntity.class);

        Long rno = replyRepository.save(replyEntity).getRno();

        Optional<PostEntity> result = postRepository.findById(replyDto.getPno());

        PostEntity postEntity = result.orElseThrow();

        postEntity.changeStatus(1);

        postRepository.save(postEntity);

        return rno;
    }

    @Override
    public ResponseReplyModify modify(RequestReplyModify RequestReplyModify) {

        Optional<ReplyEntity> result = replyRepository.findById(RequestReplyModify.getRno());

        ReplyEntity replyEntity = result.orElseThrow();

        replyEntity.changeText(RequestReplyModify.getReplyText());

        replyRepository.save(replyEntity);

        ResponseReplyModify responseReplyModify = modelMapper.map(replyEntity, ResponseReplyModify.class);

        return responseReplyModify;
    }

    @Override
    public Long remove(Long rno, Long pno) {

        replyRepository.deleteById(rno);

        Optional<PostEntity> result = postRepository.findById(pno);

        PostEntity postEntity = result.orElseThrow();

        postEntity.changeStatus(0);

        postRepository.save(postEntity);

        return rno;
    }

    @Override
    public Map<String, Object> list(Long pno, String nickName) {
        Map<String, Object> resultMap = new HashMap<>();

        boolean isEquals = false;

        List<ReplyEntity> entityList = replyRepository.listOfPost(pno);

        if (nickName.equals("관리자")) {
            isEquals = true;
        }

        List<ReplyDto> resultList = new ArrayList<>();

        entityList.forEach(replyEntity -> {
            ReplyDto replyDto = modelMapper.map(replyEntity, ReplyDto.class);
            resultList.add(replyDto);
        });

        resultMap.put("resultList", resultList);
        resultMap.put("isEquals", isEquals);

        return resultMap;
    }

}
