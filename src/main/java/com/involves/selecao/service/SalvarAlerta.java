package com.involves.selecao.service;

import java.util.Date;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvarAlerta {
	
	@Autowired
    private AlertaGateway gateway;

    public Alerta salvarParticipacao(Pesquisa pesquisa, Resposta resposta, String descricao, Boolean verificar_margem) {
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
		Date date = new Date();
		alerta.setDataRegistro(date);
		gateway.salvar(alerta);
		return alerta;
	}

    protected int calcularMargem(String estipulado, String coletado) {
		int integer_coletado = Integer.parseInt(coletado);
		int integer_estipulado = Integer.parseInt(estipulado);
		return integer_estipulado - integer_coletado;
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
}