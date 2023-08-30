package com.luisassulfi.cursomc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.luisassulfi.cursomc.domain.Categoria;
import com.luisassulfi.cursomc.dto.CategoriaDTO;
import com.luisassulfi.cursomc.repositories.CategoriaRepository;
import com.luisassulfi.cursomc.services.exceptions.DataIntegrityException;
import com.luisassulfi.cursomc.services.exceptions.ObjectNotFoundException;

public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    private Integer anId;

    private Categoria actualCategoria;

    private List<Categoria> actualCategorias;

    private Categoria aCategoria;

    private CategoriaDTO aCategoriaDTO;

    private Integer aPage;

    private Integer aLinesPerPage;

    private String anOrderBy;

    private String aDirection;

    private Page<Categoria> aPageableCategoria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAValidCategoriaId_whenCallCategoriaServiceFindById_thenShouldReturnACategory() {
        givenAValidCategoriaId();
        givenCategoriaRepositoryFindByIdReturnsAValidCategoria();
        whenCallCategoriaServiceFindById();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectAValidCategoria();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenAnInvalidId_whenCallCategoriaServiceFindById_thenShouldThrowObjectNotFoundException() {
        givenAnInvalidCategoriaId();
        givenCategoriaRepositoryFindByIdReturnsEmptyResult();
        whenCallCategoriaServiceFindById();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectANullCategoria();
    }

    @Test
    public void givenAValidListOfCategories_whenCallCategoriaServiceFindAll_thenShouldReturnAListOfCategories() {
        givenCategoriaRepositoryFindAllReturnsAListOfCategorias();
        whenCallCategoriaServiceFindAll();
        thenExpectCategoriaRepositoryFindAllCalledOnce();
        thenExpectAValidListOfCategorias();
    }

    @Test
    public void givenAValidPageRequest_whenCallCategoriaServiceFindPage_thenShouldReturnAValidPageResponse() {
        givenAValidPageRequest();
        givenCategoriaRepositoryFindPageReturnsAPaginatedList();
        whenCallCategoriaServiceFindPage();
        thenExpectAValidPageResponse();
    }

    @Test
    public void givenAValidCategoria_whenCallCategoriaServiceInsert_shouldReturnTheInsertedCategoria() {
        givenAValidCategoria();
        givenCategoriaRepositorySaveReturnsACategoriaWithId();
        whenCallCategoriaServiceInsert();
        thenExpectCategoriaRepositorySaveCalledOnce();
        thenExpectAValidCategoria();
    }

    @Test
    public void givenAValidCategoria_whenCallCategoriaServiceUpdate_shouldReturnTheUpdatedCategoria() {
        givenAValidCategoriaWithUpdatedName();
        givenCategoriaRepositoryFindByIdReturnsAValidCategoria();
        givenCategoriaRepositorySaveReturnsACategoriaWithId();
        whenCallCategoriaServiceUpdate();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectCategoriaRepositorySaveCalledOnce();
        thenExpectAnUpdatedCategoria();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenAnInvalidCategoria_whenCallCategoriaServiceUpdate_ThenShouldThrowObjectNotFoundException() {
        givenAnInvalidCategoriaWithUpdatedName();
        givenCategoriaRepositoryFindByIdReturnsEmptyResult();
        whenCallCategoriaServiceUpdate();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectCategoriaRepositorySaveNeverCalled();
        thenExpectANullCategoria();
    }

    @Test
    public void givenAnValidId_whenCallCategoriaServiceDelete_thenExpectNoReturn() {
        givenAValidCategoriaId();
        givenCategoriaRepositoryFindByIdReturnsAValidCategoria();
        givenCategoriaRepositoryDeleteByIdDoesNothing();
        whenCallCategoriaServiceDelete();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectCategoriaRepositoryDeleteByIdCalledOnce();
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenAnInvalidId_whenCallCategoriaServiceDelete_thenShouldThrowObjectNotFoundException() {
        givenAnInvalidCategoriaId();
        givenCategoriaRepositoryFindByIdReturnsEmptyResult();
        whenCallCategoriaServiceDelete();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectCategoriaRepositoryDeleteByIdNeverCalled();
    }

    @Test(expected = DataIntegrityException.class)
    public void givenAValidId_givenRepositoryThrowsException_whenCallCategoriaServiceDelete_thenShouldThrowDataIntegrityException() {
        givenAValidCategoriaId();
        givenCategoriaRepositoryFindByIdReturnsAValidCategoria();
        givenCategoriaRepositoryDeleteByIdThrowsDataIntegrityViolationException();
        whenCallCategoriaServiceDelete();
        thenExpectCategoriaRepositoryFindByIdCalledOnce();
        thenExpectCategoriaRepositoryDeleteByIdCalledOnce();
    }

    @Test
    public void givenAValidDto_whenCallFromDto_thenShouldReturnACategoria() {
        givenAValidDto();
        whenCallFromDto();
        thenExpectAValidCategoria();
    }

    // GIVEN METHODS

    private void givenAValidCategoriaId() {
        this.anId = 1;
    }

    private void givenAnInvalidCategoriaId() {
        this.anId = 2;
    }

    private void givenAValidPageRequest() {
        this.aPage = 1;
        this.aLinesPerPage = 2;
        this.anOrderBy = "nome";
        this.aDirection = "DESC";
    }

    private void givenAValidCategoria() {
        this.aCategoria = new Categoria(1, "Eletronicos");
    }

    private void givenAValidCategoriaWithUpdatedName() {
        givenAValidCategoria();
        this.aCategoria.setNome("Eletronicos Usados");
        this.anId = this.aCategoria.getId();
    }

    private void givenAnInvalidCategoriaWithUpdatedName() {
        givenAValidCategoriaWithUpdatedName();
        this.anId = 2;
    }

    private void givenAValidDto() {
        this.aCategoriaDTO = new CategoriaDTO();
        this.aCategoriaDTO.setId(1);
        this.aCategoriaDTO.setNome("Eletronicos");
    }

    private void givenCategoriaRepositoryFindByIdReturnsAValidCategoria() {
        final Categoria aCategoria = new Categoria(1, "Eletronicos");
        when(this.repository.findById(this.anId)).thenReturn(Optional.of(aCategoria));
    }

    private void givenCategoriaRepositoryFindByIdReturnsEmptyResult() {
        when(this.repository.findById(this.anId)).thenReturn(Optional.empty());
    }

    private void givenCategoriaRepositoryFindPageReturnsAPaginatedList() {
        final List<Categoria> categorias = Arrays.asList(
                new Categoria(1, "Eletronicos"),
                new Categoria(2, "Eletrodoméstricos"));
        final Page<Categoria> page = new PageImpl<>(categorias);

        Pageable pageable = PageRequest.of(aPage, aLinesPerPage, Direction.fromString(aDirection), this.anOrderBy);

        when(this.repository.findAll(pageable)).thenReturn(page);
    }

    private void givenCategoriaRepositoryFindAllReturnsAListOfCategorias() {
        final List<Categoria> categorias = Arrays.asList(
                new Categoria(1, "Eletronicos"));
        when(this.repository.findAll()).thenReturn(categorias);
    }

    private void givenCategoriaRepositorySaveReturnsACategoriaWithId() {
        final Categoria categoria = new Categoria(1, this.aCategoria.getNome());
        when(this.repository.save(this.aCategoria)).thenReturn(categoria);
    }

    private void givenCategoriaRepositoryDeleteByIdDoesNothing() {
        doNothing().when(this.repository).deleteById(this.anId);
    }

    private void givenCategoriaRepositoryDeleteByIdThrowsDataIntegrityViolationException() {
        doThrow(new DataIntegrityViolationException("Data integrity violation exception"))
                .when(this.repository)
                .deleteById(this.anId);
    }

    // WHEN METHODS

    private void whenCallCategoriaServiceFindById() {
        this.actualCategoria = this.service.findById(this.anId);
    }

    private void whenCallCategoriaServiceFindPage() {
        this.aPageableCategoria = this.service.findPage(aPage, aLinesPerPage, anOrderBy, aDirection);
    }

    private void whenCallCategoriaServiceFindAll() {
        this.actualCategorias = this.service.findAll();
    }

    private void whenCallCategoriaServiceInsert() {
        this.actualCategoria = this.service.insert(this.aCategoria);
    }

    private void whenCallCategoriaServiceUpdate() {
        this.actualCategoria = this.service.update(this.aCategoria);
    }

    private void whenCallCategoriaServiceDelete() {
        this.service.delete(this.anId);
    }

    private void whenCallFromDto() {
        this.actualCategoria = this.service.fromDTO(aCategoriaDTO);
    }

    // THEN METHODS

    private void thenExpectCategoriaRepositoryFindByIdCalledOnce() {
        verify(this.repository, times(1)).findById(anyInt());
    }

    private void thenExpectCategoriaRepositoryFindAllCalledOnce() {
        verify(this.repository, times(1)).findAll();
    }

    private void thenExpectCategoriaRepositorySaveCalledOnce() {
        verify(this.repository, times(1)).save(any(Categoria.class));
    }

    private void thenExpectCategoriaRepositorySaveNeverCalled() {
        verify(this.repository, never()).save(any(Categoria.class));
    }

    private void thenExpectCategoriaRepositoryDeleteByIdCalledOnce() {
        verify(this.repository, times(1)).deleteById(anyInt());
    }

    private void thenExpectCategoriaRepositoryDeleteByIdNeverCalled() {
        verify(this.repository, never()).deleteById(anyInt());
    }

    private void thenExpectAValidCategoria() {
        final Integer expectedCategoriaId = 1;
        final String expectedCategoriaName = "Eletronicos";

        assertNotNull(this.actualCategoria);
        assertEquals(expectedCategoriaId, this.actualCategoria.getId());
        assertEquals(expectedCategoriaName, this.actualCategoria.getNome());
        assertNotNull(this.actualCategoria.getProdutos());
    }

    private void thenExpectANullCategoria() {
        assertNull(this.actualCategoria);
    }

    private void thenExpectAValidPageResponse() {
        final int expectedContentSize = 2;

        assertNotNull(this.aPageableCategoria);
        assertNotNull(this.aPageableCategoria.getContent());
        assertEquals(expectedContentSize, this.aPageableCategoria.getContent().size());
    }

    private void thenExpectAValidListOfCategorias() {
        final int expectedItemCount = 1;
        final Integer expectedCategoriaId = 1;
        final String expectedCategoriaName = "Eletronicos";

        assertNotNull(this.actualCategorias);
        assertEquals(expectedItemCount, this.actualCategorias.size());
        assertEquals(expectedCategoriaId, this.actualCategorias.get(0).getId());
        assertEquals(expectedCategoriaName, this.actualCategorias.get(0).getNome());
        assertNotNull(this.actualCategorias.get(0).getProdutos());
    }

    private void thenExpectAnUpdatedCategoria() {
        assertNotNull(this.actualCategoria);
        assertEquals(this.aCategoria.getId(), this.actualCategoria.getId());
        assertEquals(this.aCategoria.getNome(), this.actualCategoria.getNome());
        assertEquals(this.aCategoria.getProdutos(), this.actualCategoria.getProdutos());
    }
}
