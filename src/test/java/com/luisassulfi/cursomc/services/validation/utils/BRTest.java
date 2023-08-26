package com.luisassulfi.cursomc.services.validation.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BRTest {

    private String cpf;

    private String cnpj;

    private boolean result;

    @Test
    public void givenAValidCPF_whenCallsBRIsCPFValid_thenReturnTrue() {
        givenAValidCPF();
        whenCallBRIsCPFValid();
        thenReturnTrue();
    }

    @Test
    public void givenAnInvalidCPF_whenCallsBRIsCPFValid_thenReturnFalse() {
        givenAnInvalidCPF();
        whenCallBRIsCPFValid();
        thenReturnFalse();
    }

    @Test
    public void givenANullCPF_whenCallsBRIsCPFValid_thenReturnFalse() {
        givenANullCPF();
        whenCallBRIsCPFValid();
        thenReturnFalse();
    }

    @Test
    public void givenACPFWithLengthDifferentThenEleven_whenCallsBRIsCPFValid_thenReturnFalse() {
        givenACPFWithLengthDifferentThenEleven();
        whenCallBRIsCPFValid();
        thenReturnFalse();
    }

    @Test
    public void givenAValidCNPJ_whenCallBRIsValidCNPJ_thenReturnTrue() {
        givenAValidCNPJ();
        whenCallBRIsValidCNPJ();
        thenReturnTrue();
    }

    @Test
    public void givenANInvalidCNPJ_whenCallBRIsValidCNPJ_thenReturnFalse() {
        givenAnInvalidCNPJ();
        whenCallBRIsValidCNPJ();
        thenReturnFalse();
    }

    @Test
    public void givenANullCNPJ_whenCallBRIsValidCNPJ_thenReturnFalse() {
        givenANullCNPJ();
        whenCallBRIsValidCNPJ();
        thenReturnFalse();
    }

    @Test
    public void givenACNPJWithLengthDifferentThenFourteen_whenCallBRIsValidCNPJ_thenReturnFalse() {
        givenACNPJWithLengthDifferentThenFourteen();
        whenCallBRIsValidCNPJ();
        thenReturnFalse();
    }

    // GIVEN METHODS

    private void givenAValidCPF() {
        this.cpf = "31851082050";
    }

    private void givenAnInvalidCPF() {
        this.cpf = "11111111111";
    }

    private void givenANullCPF() {
        this.cpf = null;
    }

    private void givenACPFWithLengthDifferentThenEleven() {
        this.cpf = "3185108205";
    }

    private void givenAValidCNPJ() {
        this.cnpj = "75703195000135";
    }

    private void givenAnInvalidCNPJ() {
        this.cnpj = "11111111111111";
    }

    private void givenANullCNPJ() {
        this.cnpj = null;
    }

    private void givenACNPJWithLengthDifferentThenFourteen() {
        this.cnpj = "7570319500013";
    }

    // WHEN METHODS

    private void whenCallBRIsCPFValid() {
        this.result = BR.isValidCPF(this.cpf);
    }

    private void whenCallBRIsValidCNPJ() {
        this.result = BR.isValidCNPJ(this.cnpj);
    }

    // THEN METHODS

    private void thenReturnTrue() {
        assertTrue(this.result);
    }

    private void thenReturnFalse() {
        assertFalse(this.result);
    }
}
