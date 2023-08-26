package com.luisassulfi.cursomc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luisassulfi.cursomc.domain.Estado;
import com.luisassulfi.cursomc.repositories.EstadoRepository;

public class EstadoServiceTest {

    @Mock
    private EstadoRepository repository;

    @InjectMocks
    private EstadoService service;

    private Estado estado;

    private List<Estado> estados;

    private List<Estado> actualEstados;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAValidEstado_whenCallFindAllByOrderNome_shouldReturnAList() {
        givenAValidEstado();
        givenAValidListOfEstados();
        givenEstadoRepositoryFindAllByOrderNameReturnsAListOfEstados();
        whenCallEstadoServiceFindAllByOrderName();
        thenExpectAListOfEstados();
    }

    // GIVEN METHODS

    private void givenAValidEstado() {
        this.estado = new Estado(1, "São Paulo");
    }

    private void givenAValidListOfEstados() {
        this.estados = Arrays.asList(this.estado);
    }

    private void givenEstadoRepositoryFindAllByOrderNameReturnsAListOfEstados() {
        when(this.repository.findAllByOrderByNome()).thenReturn(this.estados);
    }

    // WHEN METHODS

    private void whenCallEstadoServiceFindAllByOrderName() {
        this.actualEstados = this.service.findAllByOrderByNome();
    }

    // THEN METHODS

    private void thenExpectAListOfEstados() {
        final int expectedListSize = 1;
        
        assertNotNull(this.actualEstados);
        assertEquals(expectedListSize, this.actualEstados.size());
        assertEquals(this.estado, this.actualEstados.get(0));
    }
}
