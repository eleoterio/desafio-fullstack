package com.involves.selecao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.service.ProcessadorAlertas;
import com.involves.selecao.service.SalvarAlerta;

public class SalvarAlertasTest {
	SalvarAlerta salvarAlerta = new SalvarAlerta();
	@Test
	void testCalcularMargem() {
		int resultado;
		SalvarAlerta salvarAlerta = new SalvarAlerta();
				
		resultado = salvarAlerta.calcularMargem("10", "15");
		assertEquals(resultado, -5);
		
		resultado = salvarAlerta.calcularMargem("10", "10");
		assertEquals(resultado, 0);
		
		resultado = salvarAlerta.calcularMargem("15", "10");
		assertEquals(resultado, 5);
	}
	
	@Test
	void testGetFlTipo() {
		int resultado;
		
		SalvarAlerta salvarAlerta = new SalvarAlerta();
		
		resultado = salvarAlerta.getFlTipo("Ruptura detectada!");
		assertEquals(resultado, 1);
		
		resultado = salvarAlerta.getFlTipo("Preço acima do estipulado!");
		assertEquals(resultado, 2);
		
		resultado = salvarAlerta.getFlTipo("Preço abaixo do estipulado!");
		assertEquals(resultado, 3);
		
		resultado = salvarAlerta.getFlTipo("Participação superior ao estipulado!");
		assertEquals(resultado, 4);
		
		resultado = salvarAlerta.getFlTipo("Participação inferior ao estipulado!");
		assertEquals(resultado, 5);
		
		resultado = salvarAlerta.getFlTipo("Não encontrar!");
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
		
		resultado = salvarAlerta.salvarParticipacao(pesquisa, resposta, "Ruptura detectada!", true);
		assertEquals(resultado, null);
	}
}
