package com.lozano.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lozano.springboot.app.models.entity.Marca;

public interface IMarcaService {

	public List<Marca> findAll();
	
	public Page<Marca> findAll(Pageable pageable);

	public void save(Marca marca);

	public Marca findOne(int id);

	public void delete(int id);
}
