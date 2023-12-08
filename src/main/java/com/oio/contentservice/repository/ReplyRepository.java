package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    @Query("select r from ReplyEntity r where r.post.pno = :pno")
    List<ReplyEntity> listOfPost(Long pno);

    void deleteByPost_Pno(Long pno);
}
