package com.syf.jpa;

import com.syf.domain.GuPiao;
import com.syf.domain.XiangXi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface XiangXiJPA extends JpaRepository<XiangXi, String> {
}
