package com.syf.service.impl;

import com.syf.domain.GuPiao;
import com.syf.domain.QXiangXi;
import com.syf.domain.XiangXi;
import com.syf.jpa.XiangXiJPA;
import com.syf.service.IXiangXiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class XiangXiSerivceImpl extends BaseServiceImpl implements IXiangXiService {

    @Autowired
    private XiangXiJPA xiangXiJPA;

    @Transactional
    @Override
    public void apply(XiangXi obj) {
        log.info("XiangXiSerivceImpl apply start");
        XiangXi oldObj = find(obj);
        if (null == oldObj) {
            create(obj);
        } else {
            update(obj);
        }
        log.info("XiangXiSerivceImpl apply end");
    }

    @Override
    public void create(XiangXi obj) {
        log.info("XiangXiSerivceImpl create start");
        xiangXiJPA.save(obj);
        log.info("XiangXiSerivceImpl create end");
    }

    @Override
    @Transactional
    public void delete(XiangXi obj) {
        log.info("XiangXiSerivceImpl delete start");
        QXiangXi qXiangXi = QXiangXi.xiangXi;

        getQueryFactory().delete(qXiangXi).where(qXiangXi.id.eq(obj.getId())).execute();
        log.info("XiangXiSerivceImpl delete end");
    }

    @Transactional
    @Override
    public void batchDeleteByGuPiao(GuPiao guPiao) {
        log.info("XiangXiSerivceImpl batchDeleteByGuPiao start");
        XiangXi xiangXi = new XiangXi();
        xiangXi.setGongsi_id(guPiao.getId());
        List<XiangXi> xiangXiList = queryForList(xiangXi);
        if (null != xiangXiList) {
            for (XiangXi xx : xiangXiList) {
                delete(xx);
            }
        }
        log.info("XiangXiSerivceImpl batchDeleteByGuPiao end");
    }

    @Override
    public XiangXi find(XiangXi obj) {
        log.info("XiangXiSerivceImpl find start");
        String id = obj.getId();
        QXiangXi qXiangXi = QXiangXi.xiangXi;
        obj = getQueryFactory().selectFrom(qXiangXi).where(qXiangXi.id.eq(id)).fetchOne();
        log.info("XiangXiSerivceImpl find end");
        return obj;
    }

    @Transactional
    @Override
    public XiangXi update(XiangXi obj) {
        log.info("XiangXiSerivceImpl update start");
        QXiangXi qXiangXi = QXiangXi.xiangXi;
        getQueryFactory().update(qXiangXi)
                .set(qXiangXi.time, obj.getTime())
                .set(qXiangXi.tougu, obj.getTougu())
                .where(qXiangXi.id.eq(obj.getId()))
                .execute();
        log.info("XiangXiSerivceImpl update end");
        return obj;
    }

    @Override
    public List<XiangXi> queryForList(XiangXi obj) {
        log.info("XiangXiSerivceImpl queryForList start");
        if (obj.getTime() == null) {
            obj.setTime("");
        }
        QXiangXi qXiangXi = QXiangXi.xiangXi;
        List list = getQueryFactory().selectFrom(qXiangXi)
                .where(qXiangXi.gongsi_id.eq(obj.getGongsi_id())
                .and(qXiangXi.time.like(obj.getTime()+"%")))
                .fetch();
        log.info("XiangXiSerivceImpl queryForList end");
        return list;
    }
}
