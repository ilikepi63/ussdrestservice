package com.mamamoney.ussdrestservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<ConversionModel, ConversionModelId> {}