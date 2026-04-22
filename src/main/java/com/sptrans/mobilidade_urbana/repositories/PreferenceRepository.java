package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

}
