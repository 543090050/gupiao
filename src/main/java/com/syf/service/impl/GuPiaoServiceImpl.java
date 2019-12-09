package com.syf.service.impl;

import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GuPiaoServiceImpl implements IGuPiaoService {

    private JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    @Override
    public void create(GuPiao guPiao) {
        jdbcTemplate.update("insert into USER(NAME, AGE) values(?, ?)",
                guPiao.getId(), guPiao.getName());
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("delete from USER where id = ?", id);
    }

    @Override
    public GuPiao find(GuPiao guPiao) {
        return null;
    }
}
