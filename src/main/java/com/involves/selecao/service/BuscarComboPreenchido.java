package com.involves.selecao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.gateway.ComboGateway;

@Service
public class BuscarComboPreenchido {
	
	@Autowired
	private ComboGateway combo;
	
	public List<String> buscarTipo() {
		return combo.buscarTipo();
	}
	
	public List<String> buscarPontoDeVenda() {
		return combo.buscarPontoDeVenda();
	}
	
}
