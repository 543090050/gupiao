package com.syf.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data//lombok
@Entity
@Table(name = "xiangxi")
public class XiangXi {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "time")
    private String time;
    @Column(name = "tougu")
    private String tougu;
    @Column(name = "gongsi_id")
    private String gongsi_id;
}
