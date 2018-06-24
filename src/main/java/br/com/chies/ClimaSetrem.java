package br.com.chies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClimaSetrem
{

    private String temperatura;

    private String humidade;
    private String precipitacao;
    private String vento;
    private String ultimaleitura;
    private String data;
    private String hora;

    public String getClimaSetrem() throws Exception
    {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://estacao.setrem.com.br/api/temperatura");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null)
        {
            result.append(line);
        }
        rd.close();
        return result.toString().replace("(", "").replace(");", "");
    }

    public void processaClimaSetrem(String dados) throws Exception
    {
//        JSONObject json = new JSONObject(dados);
//        boolean status = (boolean) json.get("status");
//        if (status)
//        {
//            JSONObject response = new JSONObject(json.get("response").toString());
//
//            temperatura = response.get("temperatura").toString();
//            humidade = response.get("humidade").toString();
//            precipitacao = response.get("precipitacao").toString();
//            vento = response.get("vento").toString();
//            ultimaleitura = response.get("ultimaleitura").toString();
//            data = ultimaleitura.split(" ")[0];
//            hora = ultimaleitura.split(" ")[1];
//        }

        temperatura = "13.4";
        humidade = "94";
        precipitacao = "29.2";
        vento = "9.7";
        ultimaleitura = "24/06/2017 14:36:00";
        data = ultimaleitura.split(" ")[0];
        hora = ultimaleitura.split(" ")[1];
    }

    public String getTemperatura()
    {
        return temperatura;
    }

    public void setTemperatura(String temperatura)
    {
        this.temperatura = temperatura;
    }

    public String getHumidade()
    {
        return humidade;
    }

    public void setHumidade(String humidade)
    {
        this.humidade = humidade;
    }

    public String getPrecipitacao()
    {
        return precipitacao;
    }

    public void setPrecipitacao(String precipitacao)
    {
        this.precipitacao = precipitacao;
    }

    public String getVento()
    {
        return vento;
    }

    public void setVento(String vento)
    {
        this.vento = vento;
    }

    public String getUltimaleitura()
    {
        return ultimaleitura;
    }

    public void setUltimaleitura(String ultimaleitura)
    {
        this.ultimaleitura = ultimaleitura;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getHora()
    {
        return hora;
    }

    public void setHora(String hora)
    {
        this.hora = hora;
    }
}
