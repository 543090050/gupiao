package com.syf.service;

import com.querydsl.jpa.impl.JPAQueryFactory;

public interface IBaseService {
    /**
     * 获取查询工厂
     * @return JPAQueryFactory
     */
    public JPAQueryFactory getQueryFactory();
}
