package com.lozano.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lozano.springboot.app.models.dao.ICocheDao;
import com.lozano.springboot.app.models.entity.Coche;

@Service
public class CocheServiceImpl implements ICocheService {

	@Autowired
	private ICocheDao cocheDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Coche> findAll() {
		return (List<Coche>) cocheDao.findAll();
	}

	@Override
	@Transactional
	public void save(Coche coche) {
		cocheDao.save(coche);		
	}

	@Override
	@Transactional(readOnly = true)
	public Coche findOne(int id) {
		return cocheDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(int id) {
		cocheDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Coche> findAll(Pageable pageable) {
		return cocheDao.findAll(pageable);
	}

	/*@Override
	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM Coches WHERE id_marca = ?1", nativeQuery = true)
	public List<Coche> findByIdMarca(int marcaId) {
		return (List<Coche>) cocheDao.findAll();
	}

	@Override
	public List<Coche> findByMarca(Marca marca) {
		// TODO Auto-generated method stub
		return (List<Coche>) cocheDao.findAllById(ids)
	}
*/





	
}
