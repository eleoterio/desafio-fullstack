package com.involves.selecao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class BuscaAlertasService {
	
	@Autowired
	private AlertaGateway gateway;
	
	public List<Alerta> buscarTodos() {
		return gateway.buscarTodos();
	}
	
	public List<Alerta> buscarPorTipo(String valor) {
		return gateway.buscarPorTipo(valor);
	}
	
	public List<Alerta> buscarPorPontoDeVenda(String valor) {
		return gateway.buscarPorPontoDeVenda(valor);
	}

}
