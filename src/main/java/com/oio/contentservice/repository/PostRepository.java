package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {


}
