package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Frequency;

public interface FrequencyRepository extends JpaRepository<Frequency, Long> {

}
