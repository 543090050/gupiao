package com.syf.service.impl;

import com.syf.domain.XiangXi;
import com.syf.service.IXiangXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class XiangXiSerivceImpl implements IXiangXiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void apply(XiangXi obj) {
        XiangXi oldObj = find(obj);
        if (null == oldObj) {
            create(obj);
        }else {
            update(obj);
        }
    }

    @Override
    public void create(XiangXi obj) {
        obj.setId(UUID.randomUUID().toString());
        jdbcTemplate.update("insert into xiangxi(id, time, tougu, gongsi_id) values(?, ?, ?, ?)",
                obj.getId(), obj.getTime(), obj.getTougu(), obj.getGongsi_id());
    }

    @Override
    public void delete(XiangXi obj) {
        jdbcTemplate.update("delete from xiangxi where id = ?", obj.getId());
    }

    @Override
    public XiangXi find(XiangXi obj) {
        String id = obj.getId();
        String sql = "select * from xiangxi where id =?";
        List<XiangXi> result = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper(XiangXi.class));
        if (result == null||result.size()==0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public XiangXi update(XiangXi obj) {
        obj = find(obj);
        String slq = "update xiangxi set time ='" + obj.getTime() + "', tougu='" + obj.getTougu() + "'   where id =" + obj.getId();
        jdbcTemplate.update(slq);
        return obj;
    }

    @Override
    public List<XiangXi> queryForList(XiangXi obj) {
        String parentId = obj.getGongsi_id();
        String sql = "select * from xiangxi where 1=1";
        if (!"".equals(parentId) && null != parentId) {
            sql = sql + " and gongsi_id = " + parentId;
        }
        List<XiangXi> result = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper(XiangXi.class));
        return result;
    }
}
