package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.entities.StopTimeId;

public interface StopTimeRepository extends JpaRepository<StopTime, Long> {

}
