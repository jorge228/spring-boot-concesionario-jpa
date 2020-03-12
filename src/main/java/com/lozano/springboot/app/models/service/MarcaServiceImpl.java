package com.lozano.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lozano.springboot.app.models.dao.IMarcaDao;
import com.lozano.springboot.app.models.entity.Marca;

@Service
public class MarcaServiceImpl implements IMarcaService{

	@Autowired
	private IMarcaDao marcaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Marca> findAll() {
		return (List<Marca>) marcaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Marca marca) {
		marcaDao.save(marca);
	}

	@Override
	@Transactional(readOnly = true)
	public Marca findOne(int id) {
		return marcaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(int id) {
		marcaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Marca> findAll(Pageable pageable) {
		return marcaDao.findAll(pageable);
	}

}
