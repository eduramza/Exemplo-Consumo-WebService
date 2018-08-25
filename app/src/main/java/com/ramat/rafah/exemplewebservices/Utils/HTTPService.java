package com.ramat.rafah.exemplewebservices.Utils;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ramat.rafah.exemplewebservices.Model.CEP;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by rafah on 12/01/2018.
 */

public class HTTPService extends AsyncTask<Void, Void, CEP> {
    private final String cep;

    public HTTPService(String cep) {
        this.cep = cep;
    }

    //realizando a requisição em background
    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        //Validando o CEP
        if (this.cep != null && this.cep.length() == 8){
            try {
                //URL á ser consumida
                URL url = new URL("http://ws.matheuscastiglioni.com.br/ws/cep/find/" + this.cep + "/json/");

                //*********** ABRINDO A CONEXÃO ************
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setRequestProperty("Content-type","application/json");
                conexao.setRequestProperty("Accept", "application/json");
                conexao.setDoOutput(true);
                conexao.setConnectTimeout(5000);
                conexao.connect();

                //********* LENDO AS INFORMAÇÕES *********
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    resposta.append(scanner.next());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //********* CONVERTENDO DADOS DO JSON **************
        //compile 'com.google.code.gson:gson:2.8.2' Adicionado para auxiliar na conversão de JSON
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
