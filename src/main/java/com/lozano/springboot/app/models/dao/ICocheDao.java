package com.lozano.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lozano.springboot.app.models.entity.Coche;

public interface ICocheDao extends PagingAndSortingRepository<Coche, Integer> {

	
}
