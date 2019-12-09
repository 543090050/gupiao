package com.syf.service.impl;

import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GuPiaoServiceImpl implements IGuPiaoService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void create(GuPiao guPiao) {
        String sql = "insert into t_author(id,real_name,nick_name) " +
                "values(:id,:realName,:nickName)";
        Map<String, Object> param = new HashMap<>();
        param.put("id",guPiao.getId());
        param.put("name", guPiao.getName());
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public GuPiao find(GuPiao guPiao) {
        return null;
    }
}
