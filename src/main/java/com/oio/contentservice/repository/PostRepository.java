package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.search.PostSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostSearch {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select p from PostEntity p where p.pno = :pno")
    Optional<PostEntity> findByIdWithImages(Long pno);

    @Query("select p from PostEntity p where p.nickName = :nickName and p.category = 'Q&A'")
    List<PostEntity> findByNickName(String nickName);




}
