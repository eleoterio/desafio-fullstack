package com.involves.selecao.gateway;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.mongo.MongoDbFactory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Component
public class ComboMongoGateway implements ComboGateway{
	
	@Autowired
	private MongoDbFactory mongoFactory;
	
	@Override
	public List<String> buscarTipo() {
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		FindIterable<Document> db = collection.find();
		List<String> combo = new ArrayList<>();
		for (Document document : db) {
			Integer tipo_integer = document.getInteger("tipo");
			String tipo_string = Integer.toString(tipo_integer);
			if (combo.contains(tipo_string)) {
				combo.add(tipo_string);				
			}
		}
		return combo;
	}
	
	@Override
	public List<String> buscarPontoDeVenda() {
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		FindIterable<Document> db = collection.find();
		List<String> combo = new ArrayList<>();
		for (Document document : db) {
			String ponto_de_venda = document.getString("ponto_de_venda");
			if (!combo.contains(ponto_de_venda)) {
				combo.add(ponto_de_venda);				
			}
		}
		return combo;
	}
}
