package com.syf.jpa;

import com.syf.domain.GuPiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface GuPiaoJPA extends JpaRepository<GuPiao, String> {
}
