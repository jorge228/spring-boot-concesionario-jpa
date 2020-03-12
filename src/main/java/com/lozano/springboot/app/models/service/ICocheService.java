package com.lozano.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lozano.springboot.app.models.entity.Coche;

public interface ICocheService {

	public List<Coche> findAll();

	public Page<Coche> findAll(Pageable pageable);

	public void save(Coche coche);

	public Coche findOne(int id);

	public void delete(int id);

	//public List<Coche> findByEmailAddress(String marcaId);
	
	//public List<Coche> findByMarca(Marca marca);

}
