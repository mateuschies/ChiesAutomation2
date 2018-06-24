package br.com.chies;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal
{

    public static void main(String[] args)
    {
        try
        {
            System.out.println(new FuncoesUteis().getDataHora("") + " : Inicio");
            ClimaSetrem climaSetrem = new ClimaSetrem();
            String result = climaSetrem.getClimaSetrem();
            System.out.println("result:" + result);
            System.out.println(new FuncoesUteis().getDataHora("") + " : End GET Setrem");

            climaSetrem.processaClimaSetrem(result);
            
            System.out.println(new FuncoesUteis().getDataHora("") + " : End Process Setrem");

            FirebaseComunication.Clima item;
            item = new FirebaseComunication.Clima(climaSetrem.getData(), climaSetrem.getHora(), "temperatura", climaSetrem.getTemperatura());
            new FirebaseComunication().saveUsingPush("clima", item);
            item = new FirebaseComunication.Clima(climaSetrem.getData(), climaSetrem.getHora(), "umidade", climaSetrem.getHumidade());
            new FirebaseComunication().saveUsingPush("clima", item);
            item = new FirebaseComunication.Clima(climaSetrem.getData(), climaSetrem.getHora(), "precipitacao", climaSetrem.getPrecipitacao());
            new FirebaseComunication().saveUsingPush("clima", item);
            item = new FirebaseComunication.Clima(climaSetrem.getData(), climaSetrem.getHora(), "vento", climaSetrem.getVento());
            new FirebaseComunication().saveUsingPush("clima", item);
            System.out.println(new FuncoesUteis().getDataHora("") + " : Clima Atualizado com Sucesso");
        } catch (Exception ex)
        {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
