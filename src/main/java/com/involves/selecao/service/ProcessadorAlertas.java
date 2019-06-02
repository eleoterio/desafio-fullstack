package com.involves.selecao.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;

@Service
public class ProcessadorAlertas {

	@Autowired
	private BuscarAlertasAPIService buscarAPI;

	@Autowired
	private SalvarAlerta salvarAlerta;

	public void processa() throws IOException {

		StringBuffer content = buscarAPI.getContentAPI();

		Gson gson = new Gson();
        Pesquisa[] pesquisas =  gson.fromJson(content.toString(), Pesquisa[].class);
        for (Pesquisa pesquisa : pesquisas) {
            for (Resposta resposta : pesquisa.getRespostas()) {
                setAlerta(resposta, pesquisa);
            }
        }
	}

	private void setAlerta(Resposta resposta, Pesquisa pesquisa) {
		String mensagem = "Mensagem padrão";
		boolean verificarMargem = true;
		switch (resposta.getPergunta()) {
			case "Qual a situação do produto?":
				if(resposta.getResposta().equals("Produto ausente na gondola")){
					mensagem = "Ruptura detectada!";
					verificarMargem = false;
				}
				break;
			case "Qual o preço do produto?":
				if(verificarColetadoMaior(pesquisa.getPreco_estipulado(), resposta.getResposta())){
					mensagem = "Preço acima do estipulado!";
				} else {
					mensagem = "Preço abaixo do estipulado!";
				}
				break;
			case "%Share":
				if(verificarColetadoMaior(pesquisa.getParticipacao_estipulada(), resposta.getResposta())){
					mensagem = "Participação superior ao estipulado!";
				} else {
					mensagem = "Participação inferior ao estipulado!";
				}
				break;
			default:
				System.out.println("Alerta ainda não implementado!");
		}
		salvarAlerta.salvarParticipacao(pesquisa, resposta, mensagem, verificarMargem);
	}
    
    protected boolean verificarColetadoMaior(String estipulado, String coletado) {
		int integer_coletado = Integer.parseInt(coletado);
		int integer_estipulado = Integer.parseInt(estipulado);
        boolean coletado_maior = true;
        if (integer_coletado < integer_estipulado) {
            coletado_maior = false;
        } 
        return coletado_maior;
	}
	
}