package br.com.chies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuncoesUteis
{
    public SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    public SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
    
    public String getData(String vs)
    {
        return dateToStr(new Date());
    }
    
    public String getHora(String vs)
    {
        return timeToStr(new Date());
    }
    
    public String getDataHora(String vs)
    {
        return dateToStr(new Date()) + ";" + timeToStr(new Date());
    }
    
    public String dateToStr(Date date)
    {
        String data = formatDate.format(date);
        return data;
    }
    
    public String timeToStr(Date v)
    {
        String hora = formatHora.format(v);
        return hora;
    }
}
