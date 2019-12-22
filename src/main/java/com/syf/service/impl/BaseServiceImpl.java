package com.syf.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.syf.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class BaseServiceImpl implements IBaseService {
    //实体管理者
    @Autowired
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    /**
     * 获取查询工厂
     *
     * @return JPAQueryFactory
     */
    @Override
    public JPAQueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = new JPAQueryFactory(entityManager);
        }
        return queryFactory;
    }
}
