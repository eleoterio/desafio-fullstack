package com.involves.selecao.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProcessadorAlertasTest {	
	
	@Test
	void testVerificarColetadoMaior() {
		boolean resultado;
		ProcessadorAlertas processador = new ProcessadorAlertas();
		
		resultado = processador.verificarColetadoMaior("10", "05");		
		assertEquals(resultado, false);
		
		resultado = processador.verificarColetadoMaior("05", "10");		
		assertEquals(resultado, true);
	}
}
