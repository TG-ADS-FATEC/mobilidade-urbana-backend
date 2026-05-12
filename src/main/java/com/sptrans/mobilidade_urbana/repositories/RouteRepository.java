package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Route;

public interface RouteRepository extends JpaRepository<Route, String> {

}
