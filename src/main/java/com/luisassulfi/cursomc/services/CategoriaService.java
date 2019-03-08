package com.luisassulfi.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisassulfi.cursomc.domain.Categoria;
import com.luisassulfi.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarPorId(Integer id) {
	
		Optional<Categoria> obj = categoriaRepository.findById(id);
		
		return obj.orElse(null);
	}
}
