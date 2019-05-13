package com.involves.selecao.gateway;

import java.util.List;

import com.involves.selecao.alerta.Alerta;

public interface AlertaGateway {
	
	void salvar(Alerta alerta);

	List<Alerta> buscarTodos();
	
	List<Alerta> buscarPorTipo(String valor);
	
	List<Alerta> buscarPorPontoDeVenda(String valor);
	
	List<Alerta> buscarPorPontoDeVendaETipo(String ponto_de_venda, String tipo);
}
