package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Alert;
import com.sptrans.mobilidade_urbana.entities.Profile;

public interface AlertRepository extends JpaRepository<Alert, Long> {

	List<Alert> findByProfile(Profile profile);

}
