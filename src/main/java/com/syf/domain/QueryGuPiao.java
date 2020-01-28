package com.syf.domain;

import lombok.Data;

/**
 * 原实体因数据库字段限制，无法构建额外属性
 */
@Data
public class QueryGuPiao extends GuPiao implements Comparable<QueryGuPiao>{
    private int count;

    @Override
    public int compareTo(QueryGuPiao o) {
        return o.getCount()-count;
    }
}
