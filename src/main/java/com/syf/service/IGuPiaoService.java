package com.syf.service;

import com.syf.domain.GuPiao;

import java.util.List;

public interface IGuPiaoService {
    GuPiao apply(GuPiao guPiao);

    void create(GuPiao guPiao);

    void delete(GuPiao guPiao);

    GuPiao find(GuPiao guPiao);

    GuPiao update(GuPiao guPiao);

    List<GuPiao> queryForList(GuPiao guPiao);
}
