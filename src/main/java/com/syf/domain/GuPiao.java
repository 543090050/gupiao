package com.syf.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data//lombok
@Entity
@Table(name = "gongsi")
public class GuPiao {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
//    private String count;
}
