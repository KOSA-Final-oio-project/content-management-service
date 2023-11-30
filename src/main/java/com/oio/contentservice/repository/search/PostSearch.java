package com.oio.contentservice.repository.search;

import com.oio.contentservice.jpa.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearch {

    Page<PostEntity> searchAll(String[] types, String keyword, Pageable pageable);
}
