package com.syf.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.syf.domain.GuPiao;
import com.syf.domain.QGuPiao;
import com.syf.jpa.GuPiaoJPA;
import com.syf.service.IGuPiaoService;
import com.syf.service.IXiangXiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.info("GuPiaoServiceImpl apply start");
        GuPiao oldGuPiao = find(guPiao);
        if (null == oldGuPiao) {
            create(guPiao);
        } else {
            update(guPiao);
        }
        log.info("GuPiaoServiceImpl apply end");
        return guPiao;
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    public void create(GuPiao obj) {
        log.info("GuPiaoServiceImpl create start");
        guPiaoJPA.save(obj);
        log.info("GuPiaoServiceImpl create end");
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    public void delete(GuPiao obj) {
        log.info("GuPiaoServiceImpl delete start");
        xiangXiService.batchDeleteByGuPiao(obj);

        QGuPiao qGuPiao = QGuPiao.guPiao;
        getQueryFactory().delete(qGuPiao).where(qGuPiao.id.eq(obj.getId())).execute();
        log.info("GuPiaoServiceImpl delete end");
    }

    @Override
    public GuPiao find(GuPiao obj) {
        log.info("GuPiaoServiceImpl find start");
        QGuPiao qGuPiao = QGuPiao.guPiao;
        obj = getQueryFactory()
                .selectFrom(qGuPiao)//查询源
                .where(qGuPiao.id.eq(obj.getId()))
                .fetchOne();
        log.info("GuPiaoServiceImpl find end");
        return obj;
    }

    @Override
    @Transactional//涉及修改删除时需要增加事务
    @Modifying
    public GuPiao update(GuPiao obj) {
        log.info("GuPiaoServiceImpl update start");
        QGuPiao qGuPiao = QGuPiao.guPiao;
        getQueryFactory().update(qGuPiao)
                .set(qGuPiao.name, obj.getName())
                .where(qGuPiao.id.eq(obj.getId()))
                .execute();
        log.info("GuPiaoServiceImpl update end");
        return obj;
    }

    @Override
    public List<GuPiao> queryForList(GuPiao obj) {
        log.info("GuPiaoServiceImpl queryForList start");
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
       List list =  query.fetch();
        log.info("GuPiaoServiceImpl queryForList end");
        return list;
    }
}
