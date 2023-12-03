package com.oio.contentservice.repository.search;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.jpa.QPostEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostSearchImpl extends QuerydslRepositorySupport implements PostSearch {

    public PostSearchImpl() {
        super(PostEntity.class);
    }

    @Override
    public Page<PostEntity> searchAll(String[] types, String keyword, Pageable pageable) {

        QPostEntity post = QPostEntity.postEntity;
        JPQLQuery<PostEntity> query = from(post);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(post.title.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(post.nickName.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(post.pno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<PostEntity> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
