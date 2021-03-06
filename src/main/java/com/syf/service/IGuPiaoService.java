package com.syf.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.syf.domain.GuPiao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGuPiaoService {
    GuPiao apply(GuPiao guPiao);

    void create(GuPiao guPiao);

    void delete(GuPiao guPiao);

    GuPiao find(GuPiao guPiao);

    GuPiao update(GuPiao guPiao);

    List<GuPiao> queryForList(GuPiao guPiao);

    /**
     * 单表查询
     * @param predicate
     * @param pageable
     * @return
     */
    public QueryResults pageQuery(Predicate predicate, Pageable pageable);

    public QueryResults pageQueryByTime(Predicate predicate, Pageable pageable, String time);

    /**
     * 多表查询
     * @param predicate
     * @param pageable
     * @return
     */
    public Page multiPageQuery(Predicate predicate, Pageable pageable);
}
