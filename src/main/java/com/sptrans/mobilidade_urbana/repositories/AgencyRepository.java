package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Agency;

public interface AgencyRepository extends JpaRepository<Agency, String> {

}
