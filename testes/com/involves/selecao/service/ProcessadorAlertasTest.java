package com.involves.selecao.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;

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
	
	@Test
	void testCalcularMargem() {
		int resultado;
		ProcessadorAlertas processador = new ProcessadorAlertas();
		
		resultado = processador.calcularMargem("10", "15");
		assertEquals(resultado, -5);
		
		resultado = processador.calcularMargem("10", "10");
		assertEquals(resultado, 0);
		
		resultado = processador.calcularMargem("15", "10");
		assertEquals(resultado, 5);
	}
	
	@Test
	void testGetFlTipo() {
		int resultado;
		ProcessadorAlertas processador = new ProcessadorAlertas();
		
		resultado = processador.getFlTipo("Ruptura detectada!");
		assertEquals(resultado, 1);
		
		resultado = processador.getFlTipo("Preço acima do estipulado!");
		assertEquals(resultado, 2);
		
		resultado = processador.getFlTipo("Preço abaixo do estipulado!");
		assertEquals(resultado, 3);
		
		resultado = processador.getFlTipo("Participação superior ao estipulado!");
		assertEquals(resultado, 4);
		
		resultado = processador.getFlTipo("Participação inferior ao estipulado!");
		assertEquals(resultado, 5);
		
		resultado = processador.getFlTipo("Não encontrar!");
		assertEquals(resultado, 0);
	}
	
	@Test
	void testSalvarParticipacao() {
		Alerta resultado = new Alerta();
		ProcessadorAlertas processador = new ProcessadorAlertas();
		
		Pesquisa pesquisa = new Pesquisa();
		
		pesquisa.setId(2);
		pesquisa.setNotificante("teste");
		pesquisa.setPonto_de_venda("Teste comercio");
		pesquisa.setProduto("Produto teste");
		
		Resposta resposta = new Resposta();
		resposta.setPergunta("Qual a situação do produto?");
		resposta.setResposta("Produto ausente na gondola");	
		
		resultado = processador.salvarParticipacao(pesquisa, resposta, "Ruptura detectada!", true);
		assertEquals(resultado, null);
	}
}
