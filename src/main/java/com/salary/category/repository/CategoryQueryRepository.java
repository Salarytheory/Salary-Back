package com.salary.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.salary.category.entity.QCategory.category;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Category getCategory(String name){
        return queryFactory.select(category)
                .from(category)
                .where(category.name.eq(name)
                        .and(category.isDefault.eq(true))
                )
                .fetchOne();
    }
}
