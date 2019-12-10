package com.syf.service;


import com.syf.domain.XiangXi;

import java.util.List;

public interface IXiangXiService {
    void apply(XiangXi obj);

    void create(XiangXi obj);

    void delete(XiangXi obj);

    XiangXi find(XiangXi obj);

    XiangXi update(XiangXi obj);

    List<XiangXi> queryForList(XiangXi obj);
}
