package com.mamamoney.ussdrestservice;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionStepsRepository extends JpaRepository<SessionStepsModel, Integer> {
    
}