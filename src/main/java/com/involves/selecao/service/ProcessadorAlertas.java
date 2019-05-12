package com.involves.selecao.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class ProcessadorAlertas {

	@Autowired
	private AlertaGateway gateway;
	
	public void processa() throws IOException {
		URL url = new URL("https://selecao-involves.agilepromoter.com/pesquisas");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		  new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();

		Gson gson = new Gson();
        Pesquisa[] pesquisas =  gson.fromJson(content.toString(), Pesquisa[].class);
        for (Pesquisa pesquisa : pesquisas) {
            for (Resposta resposta : pesquisa.getRespostas()) {
                switch (resposta.getPergunta()) {
                    case "Qual a situação do produto?":
                        if(resposta.getResposta().equals("Produto ausente na gondola")){
                            salvarParticipacao(pesquisa, resposta, "Ruptura detectada!", false);
                        }
                        break;
                    case "Qual o preço do produto?":
                        if(verificarColetadoMaior(pesquisa.getPreco_estipulado(), resposta.getResposta())){
                            salvarParticipacao(pesquisa, resposta, "Preço acima do estipulado!", true);
                        } else {
                            salvarParticipacao(pesquisa, resposta, "Preço abaixo do estipulado!", true);
                        }
                        break;
                    case "%Share":
                        if(verificarColetadoMaior(pesquisa.getParticipacao_estipulada(), resposta.getResposta())){
                            salvarParticipacao(pesquisa, resposta, "Participação superior ao estipulado!", true);
                        } else {
                            salvarParticipacao(pesquisa, resposta, "Participação inferior ao estipulado!", true);
                        }
                        break;
                    default:
                        System.out.println("Alerta ainda não implementado!");
                }
            }
        }
	}

	protected Alerta salvarParticipacao(Pesquisa pesquisa, Resposta resposta, String descricao, Boolean verificar_margem) {
        Alerta alerta = new Alerta();
        if (verificar_margem) {
            int margem = 0;
            if (pesquisa.getPreco_estipulado() != null) {
                margem = calcularMargem(pesquisa.getPreco_estipulado(), resposta.getResposta());
            } else if (pesquisa.getParticipacao_estipulada() != null) {
                margem = calcularMargem(pesquisa.getParticipacao_estipulada(), resposta.getResposta());
            }
            if (margem == 0) {
                return null;
            }
            alerta.setMargem(margem);
        }
		alerta.setDescricao(descricao);
		alerta.setProduto(pesquisa.getProduto());
		alerta.setPontoDeVenda(pesquisa.getPonto_de_venda());
		alerta.setCategoria(pesquisa.getCategoria());
		alerta.setFlTipo(getFlTipo(descricao));
		alerta.setDataRegistro(new Date());
		gateway.salvar(alerta);
		return alerta;
	}

	protected int getFlTipo(String descricao_resposta) {
        int flTipo;
		switch (descricao_resposta) {
			case "Ruptura detectada!":
				flTipo =  1;
				break;
			case "Preço acima do estipulado!":
				flTipo =  2;
				break;
			case "Preço abaixo do estipulado!":
				flTipo =  3;
				break;
			case "Participação superior ao estipulado!":
				flTipo =  4;
				break;
			case "Participação inferior ao estipulado!":
				flTipo =  5;
				break;
			default:
				flTipo =  0;
	    }

		return flTipo;
    }

    protected int calcularMargem(String estipulado, String coletado) {
		int integer_coletado = Integer.parseInt(coletado);
		int integer_estipulado = Integer.parseInt(estipulado);
		return integer_estipulado - integer_coletado;
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