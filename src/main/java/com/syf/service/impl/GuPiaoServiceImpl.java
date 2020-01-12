package com.syf.service.impl;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.syf.domain.GuPiao;
import com.syf.domain.QGuPiao;
import com.syf.domain.QXiangXi;
import com.syf.jpa.GuPiaoJPA;
import com.syf.service.IGuPiaoService;
import com.syf.service.IXiangXiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class GuPiaoServiceImpl extends BaseServiceImpl implements IGuPiaoService {
    @Autowired
    private IXiangXiService xiangXiService;

    @Autowired
    private GuPiaoJPA guPiaoJPA;

    @Transactional//涉及修改删除时需要增加事务
    @Modifying
    @Override
    public GuPiao apply(GuPiao guPiao) {
        log.debug("GuPiaoServiceImpl apply start");
        GuPiao oldGuPiao = find(guPiao);
        if (null == oldGuPiao) {
            create(guPiao);
        } else {
            update(guPiao);
        }
        log.debug("GuPiaoServiceImpl apply end");
        return guPiao;
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    public void create(GuPiao obj) {
        log.debug("GuPiaoServiceImpl create start");
        guPiaoJPA.save(obj);
        log.debug("GuPiaoServiceImpl create end");
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    public void delete(GuPiao obj) {
        log.debug("GuPiaoServiceImpl delete start");
        xiangXiService.batchDeleteByGuPiao(obj);

        QGuPiao qGuPiao = QGuPiao.guPiao;
        getQueryFactory().delete(qGuPiao).where(qGuPiao.id.eq(obj.getId())).execute();
        log.debug("GuPiaoServiceImpl delete end");
    }

    @Override
    public GuPiao find(GuPiao obj) {
        log.debug("GuPiaoServiceImpl find start");
        QGuPiao qGuPiao = QGuPiao.guPiao;
        obj = getQueryFactory()
                .selectFrom(qGuPiao)//查询源
                .where(qGuPiao.id.eq(obj.getId()))
                .fetchOne();
        log.debug("GuPiaoServiceImpl find end");
        return obj;
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    @Modifying
    public GuPiao update(GuPiao obj) {
        log.debug("GuPiaoServiceImpl update start");
        QGuPiao qGuPiao = QGuPiao.guPiao;
        getQueryFactory().update(qGuPiao)
                .set(qGuPiao.name, obj.getName())
                .where(qGuPiao.id.eq(obj.getId()))
                .execute();
        log.debug("GuPiaoServiceImpl update end");
        return obj;
    }

    @Override
    public List<GuPiao> queryForList(GuPiao obj) {
        log.debug("GuPiaoServiceImpl queryForList start");
        String id = obj.getId();
        String name = obj.getName();

        QGuPiao qGuPiao = QGuPiao.guPiao;
        JPAQuery query = getQueryFactory().selectFrom(qGuPiao);

        if (!StringUtils.isEmpty(name)) {
            query.where(qGuPiao.name.like(name));
        }
        if (!StringUtils.isEmpty(id)) {
            query.where(qGuPiao.id.like(id));
        }
        List list = query.fetch();
        log.debug("GuPiaoServiceImpl queryForList end");
        return list;
    }

    @Override
    public QueryResults pageQuery(Predicate predicate, Pageable pageable) {
        if (predicate == null) {
            predicate = new BooleanBuilder();
        }
//        Page<GuPiao> result = guPiaoJPA.findAll(predicate, pageable);
        QGuPiao qGuPiao = QGuPiao.guPiao;
        QueryResults<GuPiao> result =  getQueryFactory().selectFrom(qGuPiao)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return result;
    }

    @Override
    public QueryResults pageQueryByTime(Predicate predicate, Pageable pageable,String time ) {
        if (predicate == null) {
            predicate = new BooleanBuilder();
        }
        QGuPiao qGuPiao = QGuPiao.guPiao;
        QueryResults<GuPiao> result =  getQueryFactory().selectFrom(qGuPiao)
                .where(qGuPiao.id.in(
                        JPAExpressions.select(QXiangXi.xiangXi.gongsi_id).from(QXiangXi.xiangXi)
                        .where(QXiangXi.xiangXi.time.like(time+"%"))
                        .groupBy(QXiangXi.xiangXi.gongsi_id)
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return result;
    }

    @Override
    public Page multiPageQuery(Predicate predicate, Pageable pageable) {
        if (predicate == null) {
            predicate = new BooleanBuilder();
        }
        QGuPiao qGuPiao = QGuPiao.guPiao;
        QXiangXi qXiangXi = QXiangXi.xiangXi;
        QueryResults<Tuple> result = getQueryFactory().select(qXiangXi.time, qGuPiao.name)
                .from(qXiangXi)
                .leftJoin(qGuPiao)
                .on(qXiangXi.gongsi_id.eq(qGuPiao.id))
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        System.out.println(JSON.toJSONString(result));
        for(Tuple tuple:result.getResults()){
            String time = tuple.get(qXiangXi.time);
            String name = tuple.get(qGuPiao.name);
            System.out.println(time+" "+name);
        }
        return null;
    }
}
