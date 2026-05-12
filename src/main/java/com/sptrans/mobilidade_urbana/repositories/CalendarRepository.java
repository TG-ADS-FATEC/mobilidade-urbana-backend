package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, String> {

}
