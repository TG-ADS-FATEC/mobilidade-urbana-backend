package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Stop;

public interface StopRepository extends JpaRepository<Stop, String> {

}
