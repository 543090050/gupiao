package com.syf.service.impl;

import com.syf.domain.GuPiao;
import com.syf.domain.XiangXi;
import com.syf.service.IGuPiaoService;
import com.syf.service.IXiangXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuPiaoServiceImpl implements IGuPiaoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IXiangXiService xiangXiService;

    @Override
    public GuPiao apply(GuPiao guPiao) {
        GuPiao oldGuPiao = find(guPiao);
        if (null == oldGuPiao) {
            create(guPiao);
        } else {
            update(guPiao);
        }
        return null;
    }

    @Override
    public void create(GuPiao obj) {
        jdbcTemplate.update("insert into gongsi(id, name) values(?, ?)",
                obj.getId(), obj.getName());
    }

    @Override
    public void delete(GuPiao obj) {
        String gongsiId = obj.getId();
        jdbcTemplate.update("delete from gongsi where id = ?", gongsiId);
        XiangXi xiangXi = new XiangXi();
        xiangXi.setGongsi_id(gongsiId);
        List<XiangXi> xiangXiList = xiangXiService.queryForList(xiangXi);
        if (null != xiangXiList) {
            for (XiangXi xx : xiangXiList) {
                xiangXiService.delete(xx);
            }
        }
    }

    @Override
    public GuPiao find(GuPiao obj) {
        String id = obj.getId();
        String sql = "select * from gongsi where id =?";
        List<GuPiao> result = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper(GuPiao.class));
        if (result == null || result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public GuPiao update(GuPiao obj) {
        String slq = "update gongsi set name ='" + obj.getName() + "'   where id =" + obj.getId();
        jdbcTemplate.update(slq);
        return obj;
    }

    @Override
    public List<GuPiao> queryForList(GuPiao obj) {
        String id = obj.getId();
        String name = obj.getName();
        String sql = "select * from gongsi where 1=1";
        if (!"".equals(id) && null != id) {
            sql = sql + " and id = " + id;
        }
        if (!"".equals(name) && null != name) {
            sql = sql + " and name ='" + name + "'";
        }
        List<GuPiao> result = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper(GuPiao.class));
        return result;
    }
}
