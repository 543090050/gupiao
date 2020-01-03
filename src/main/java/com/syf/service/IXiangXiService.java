package com.syf.service;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.syf.domain.GuPiao;
import com.syf.domain.XiangXi;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IXiangXiService {
    void apply(XiangXi obj);

    void create(XiangXi obj);

    void delete(XiangXi obj);

    void batchDeleteByGuPiao(GuPiao guPiao);

    XiangXi find(XiangXi obj);

    XiangXi update(XiangXi obj);

    List<XiangXi> queryForList(XiangXi obj);

    public QueryResults pageQuery(Predicate predicate, Pageable pageable);
}
