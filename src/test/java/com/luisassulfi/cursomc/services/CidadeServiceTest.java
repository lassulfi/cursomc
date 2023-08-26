package com.luisassulfi.cursomc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luisassulfi.cursomc.domain.Cidade;
import com.luisassulfi.cursomc.domain.Estado;
import com.luisassulfi.cursomc.repositories.CidadeRepository;

public class CidadeServiceTest {

    @InjectMocks
    private CidadeService service;

    @Mock
    private CidadeRepository repository;

    private Integer actualEstadoId;

    private final List<Cidade> CIDADES = Arrays.asList(
            new Cidade(1, "Campinas", new Estado(1, "São Paulo")));

    private List<Cidade> actualCidades;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAValidEstadoId_whenCallCidadeServiceFindByEstado_thenShouldReturnAListOfCidades() {
        givenAValidEstadoId();
        givenCidadeRepositoryFindCidadesReturnsAListOfCidades();
        whenCallCidadeServiceFindByEstado();
        thenExpectCidadeRepositoryFindCidadesCalledOnce();
        thenExpectAValidListOfCidades();
    }

    @Test
    public void givenAnInvalidEstadoId_whenCallCidadeServiceFindByEstado_thenShouldAnEmptyList() {
        givenAnInvalidEstadoId();
        givenCidadeRepositoryFindCidadesReturnsAnEmptyList();
        whenCallCidadeServiceFindByEstado();
        thenExpectCidadeRepositoryFindCidadesCalledOnce();
        thenExpectAnEmptyListOfCidades();
    }

    // GIVEN METHODS

    private void givenAValidEstadoId() {
        this.actualEstadoId = 1;
    }

    private void givenAnInvalidEstadoId() {
        this.actualEstadoId = 2;
    }

    private void givenCidadeRepositoryFindCidadesReturnsAListOfCidades() {
        when(this.repository.findCidades(this.actualEstadoId)).thenReturn(CIDADES);
    }

    private void givenCidadeRepositoryFindCidadesReturnsAnEmptyList() {
        when(this.repository.findCidades(this.actualEstadoId)).thenReturn(new ArrayList<Cidade>());
    }

    // WHEN METHODS

    private void whenCallCidadeServiceFindByEstado() {
        this.actualCidades = this.service.findByEstado(this.actualEstadoId);
    }

    // THEN METHODS

    private void thenExpectCidadeRepositoryFindCidadesCalledOnce() {
        verify(this.repository, times(1)).findCidades(anyInt());
    }

    private void thenExpectAValidListOfCidades() {
        final int expectedItemsCount = 1;
        final Integer expectedCidadeId = 1;
        final String expectedCidadeName = "Campinas";
        final Integer expectedEstadoId = 1;
        final String expectedEstadoName = "São Paulo";

        assertNotNull(this.actualCidades);
        assertEquals(expectedItemsCount, this.actualCidades.size());
        assertEquals(expectedCidadeId, this.actualCidades.get(0).getId());
        assertEquals(expectedCidadeName, this.actualCidades.get(0).getNome());
        assertEquals(expectedEstadoId, this.actualCidades.get(0).getEstado().getId());
        assertEquals(expectedEstadoName, this.actualCidades.get(0).getEstado().getNome());
    }

    private void thenExpectAnEmptyListOfCidades() {
        assertNotNull(this.actualCidades);
        assertTrue(this.actualCidades.isEmpty());
    }
}
