package com.involves.selecao.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class BuscarAlertasAPIService {

	public StringBuffer getContentAPI() {
        try{
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

            return content;
        }catch(MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }catch(IOException ex) {
            ex.printStackTrace();
            return null;
        }        
    }
}