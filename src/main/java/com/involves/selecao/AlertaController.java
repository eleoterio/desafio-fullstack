package com.involves.selecao;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.service.BuscaAlertasService;
import com.involves.selecao.service.ProcessadorAlertas;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

	@Autowired
	private BuscaAlertasService buscaAlertasService;
	
	@Autowired
	private ProcessadorAlertas processador;
	
	@GetMapping
    public List<Alerta> alertas(
	    		@RequestParam("pesquisa") String pesquisa, 
	    		@RequestParam(value="valor", required=false) String valor
    		) {
		List<Alerta> lista_alerta_retorno = null;
		switch (pesquisa) {
			case "tipo":
				lista_alerta_retorno = buscaAlertasService.buscarPorTipo(valor);
				break;
			case "local":
				lista_alerta_retorno = buscaAlertasService.buscarPorPontoDeVenda(valor);
				break;
			case "todos":
				lista_alerta_retorno = buscaAlertasService.buscarTodos();
				break;
		}
		return lista_alerta_retorno;
    }
	
	@GetMapping("/processar")
    public void processar() {
        try {
            processador.processa();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
