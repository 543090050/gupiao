package com.syf.domain;

import java.util.List;

public class GuPiao {
    private String id;
    private String name;
    private List<XiangXi> xiangXiList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<XiangXi> getXiangXiList() {
        return xiangXiList;
    }

    public void setXiangXiList(List<XiangXi> xiangXiList) {
        this.xiangXiList = xiangXiList;
    }
}
