package com.mamamoney.ussdrestservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionModel, SessionModelId> {}