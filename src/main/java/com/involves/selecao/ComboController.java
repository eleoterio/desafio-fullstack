package com.involves.selecao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.service.BuscarComboPreenchido;
import com.involves.selecao.service.ProcessadorAlertas;

@RestController
@RequestMapping("/combos")
public class ComboController {
	@Autowired
	private BuscarComboPreenchido buscar_combo;
	
	@GetMapping("/tipo")
    public List<String> comboTipo() {
		List<String> lista_combo = null;
		lista_combo = buscar_combo.buscarTipo();
		return lista_combo;
	}
	
	@GetMapping("/ponto-de-venda")
    public List<String> comboPontoDeVenda() {
		List<String> lista_combo = null;
		lista_combo = buscar_combo.buscarPontoDeVenda();
		return lista_combo;
	}
    
}
