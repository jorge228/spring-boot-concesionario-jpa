package com.lozano.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lozano.springboot.app.models.entity.Marca;

public interface IMarcaDao extends PagingAndSortingRepository<Marca, Integer>{	
	
}
 