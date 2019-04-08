package com.luisassulfi.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisassulfi.cursomc.domain.Estado;
import com.luisassulfi.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAllByOrderByNome(){
		
		return repo.findAllByOrderByNome();
	}
}
