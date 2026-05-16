package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Trip;

public interface TripRepository extends JpaRepository<Trip, String> {

}
