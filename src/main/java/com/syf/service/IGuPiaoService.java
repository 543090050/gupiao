package com.syf.service;

import com.syf.domain.GuPiao;

public interface IGuPiaoService {
    void create(GuPiao guPiao);

    void delete(String id);

    GuPiao find(GuPiao guPiao);
}
