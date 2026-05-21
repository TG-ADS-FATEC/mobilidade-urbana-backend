package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Shape;

public interface ShapeRepository extends JpaRepository<Shape, String> {

}
