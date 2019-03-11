package com.luisassulfi.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisassulfi.cursomc.domain.Cliente;
import com.luisassulfi.cursomc.repositories.ClienteRepository;
import com.luisassulfi.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente findById(Integer id) {

		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + Cliente.class.getName()));
	}
}
