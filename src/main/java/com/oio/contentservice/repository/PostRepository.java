package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.repository.search.PostSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostSearch {


}
