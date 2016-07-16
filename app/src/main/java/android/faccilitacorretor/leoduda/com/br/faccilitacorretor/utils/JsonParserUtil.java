package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.utils;

import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Alarme;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by turbiani on 05/12/15.
 */
public class JsonParserUtil {

    //CONVERT DOMAIN OBJECTS TO JSON
    public static JSONObject parseCorretorToJson(Corretor corretor){
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("id", corretor.getId()==null || corretor.getId()=="" ? JSONObject.NULL : corretor.getId());
            object.put("nome", corretor.getNome()==null || corretor.getNome()=="" ? JSONObject.NULL : corretor.getNome());
            object.put("email", corretor.getEmail()==null || corretor.getEmail()=="" ? JSONObject.NULL : corretor.getEmail());
            object.put("susep", corretor.getSusep()==null || corretor.getSusep()=="" ? JSONObject.NULL : corretor.getSusep());
            object.put("nomeImagem", corretor.getNomeImagem()==null || corretor.getNomeImagem()=="" ? JSONObject.NULL : corretor.getNomeImagem());
            object.put("telefoneCelular", corretor.getTelefoneCelular()==null || corretor.getTelefoneCelular()=="" ? JSONObject.NULL : corretor.getTelefoneCelular());
            object.put("telefoneOutro", corretor.getTelefoneOutro()==null || corretor.getTelefoneOutro()=="" ? JSONObject.NULL : corretor.getTelefoneOutro());
            object.put("status", corretor.getStatus()==null || corretor.getStatus()=="" ? JSONObject.NULL : corretor.getStatus());
            object.put("dataCadastro", corretor.getDataCadastro()==null ? JSONObject.NULL : corretor.getDataCadastro());
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return object;
        }
    }

    public static JSONObject parseSeguradoToJson(Segurado segurado){
        JSONObject object = null;
        try {
            object  = new JSONObject();
            object.put("id", segurado.getId());
            object.put("nome", segurado.getNome()==null || segurado.getNome()=="" ? JSONObject.NULL : segurado.getNome());
            object.put("email", segurado.getEmail()==null || segurado.getEmail()=="" ? JSONObject.NULL : segurado.getEmail());
            object.put("telefoneCelular", segurado.getTelefoneCelular()==null || segurado.getTelefoneOutro()=="" ? JSONObject.NULL : segurado.getTelefoneOutro());
            object.put("telefoneOutro", segurado.getTelefoneOutro()==null || segurado.getTelefoneOutro()=="" ? JSONObject.NULL : segurado.getTelefoneOutro());
            object.put("status", segurado.getStatus()==null || segurado.getStatus()=="" ? JSONObject.NULL : segurado.getStatus());
            object.put("dataCadastro", segurado.getDataCadastro()==null ? JSONObject.NULL : segurado.getDataCadastro());
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return object;
        }

    }

    public static JSONObject parseSeguradoraToJson(Seguradora seguradora){
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("id", seguradora.getId());
            object.put("nomeSeguradora", seguradora.getNomeSeguradora() == null || seguradora.getNomeSeguradora() == "" ? JSONObject.NULL : seguradora.getNomeSeguradora());
            object.put("status", seguradora.getStatus()==null || seguradora.getStatus()=="" ? JSONObject.NULL : seguradora.getStatus());
            object.put("dataCadastro", seguradora.getDataCadastro()==null ? JSONObject.NULL : seguradora.getDataCadastro());
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return object;
        }

    }

    public static JSONObject parseAlarmeToJson(Alarme alarme) {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("id", alarme.getId());
            object.put("mensagem", alarme.getMensagem()==null || alarme.getMensagem()=="" ? JSONObject.NULL : alarme.getMensagem());
            object.put("diaAlerta", alarme.getDiaAlerta());
            object.put("mesAlerta", alarme.getMesAlerta());
            object.put("anoAlerta", alarme.getAnoAlerta());
            object.put("horaAlerta", alarme.getHoraAlerta());
            object.put("minutoAlerta", alarme.getMinutoAlerta());
            object.put("status", alarme.getStatus()==null || alarme.getStatus()=="" ? JSONObject.NULL : alarme.getStatus());
            object.put("dataCadastro", alarme.getDataCadastro()==null ? JSONObject.NULL : alarme.getDataCadastro());
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return object;
        }
    }


    public static JSONObject parseApoliceToJson(Apolice apolice) {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("id", apolice.getId());
            object.put("numeroApolice", apolice.getNumeroApolice()==null || apolice.getNumeroApolice()=="" ? JSONObject.NULL : apolice.getNumeroApolice());
            object.put("inicioVigencia", apolice.getInicioVigencia()==null || apolice.getInicioVigencia()=="" ? JSONObject.NULL : apolice.getInicioVigencia());
            object.put("finalVigencia", apolice.getFinalVigencia()==null || apolice.getFinalVigencia()=="" ? JSONObject.NULL : apolice.getFinalVigencia());
            object.put("valorPremio", apolice.getValorPremio()==null || apolice.getValorPremio()=="" ? JSONObject.NULL : apolice.getValorPremio());
            object.put("corretor", parseCorretorToJson(apolice.getCorretor())==null  ? JSONObject.NULL : parseCorretorToJson(apolice.getCorretor()));
            object.put("seguradora", parseSeguradoraToJson(apolice.getSeguradora())==null ? JSONObject.NULL : parseSeguradoraToJson(apolice.getSeguradora()));
            object.put("segurado", parseSeguradoToJson(apolice.getSegurado())==null ? JSONObject.NULL : parseSeguradoToJson(apolice.getSegurado()));
            object.put("tipoApolice", apolice.getTipoApolice()==null || apolice.getTipoApolice()=="" ? JSONObject.NULL : apolice.getTipoApolice());
            object.put("idAlarme", apolice.getIdAlarme());

            object.put("status", apolice.getStatus()==null || apolice.getStatus()=="" ? JSONObject.NULL : apolice.getStatus());
            object.put("dataCadastro", apolice.getDataCadastro()==null ? JSONObject.NULL : apolice.getDataCadastro());
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return object;
        }
    }

    //CONVERTO JSON TO DOMAIN OBJECTS
    public static Corretor parseJsonToCorretor(JSONObject json){
        Corretor entidade = new Corretor();
        entidade.setId(json.optString("id"));
        entidade.setNome(json.optString("nome"));
        entidade.setEmail(json.optString("email"));
        entidade.setSusep(json.optString("susep"));
        entidade.setNomeImagem(json.optString("nomeImagem"));
        entidade.setTelefoneCelular(json.optString("telefoneCelular"));
        entidade.setTelefoneOutro(json.optString("telefoneOutro"));
        entidade.setStatus(json.optString("status"));
        entidade.setDataCadastro(new Date());
        return entidade;
    }

    public static Corretor parseJsonToCorretor(String data){
        try{
            JSONObject corretorJson = new JSONObject(data);
            return parseJsonToCorretor(corretorJson);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new Corretor();
    }

    public static Segurado parseJsonToSegurado(JSONObject json){
        Segurado entidade = new Segurado();
        entidade.setId(json.optInt("id"));
        entidade.setNome(json.optString("nome"));
        entidade.setEmail(json.optString("email"));
        entidade.setTelefoneCelular(json.optString("telefoneCelular"));
        entidade.setTelefoneOutro(json.optString("telefoneOutro"));
        entidade.setStatus(json.optString("status"));
        entidade.setDataCadastro(new Date());
        return entidade;
    }

    public static Segurado parseJsonToSegurado(String data){
        try{
            JSONObject seguradoJson = new JSONObject(data);
            return parseJsonToSegurado(seguradoJson);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new Segurado();
    }

    public static Seguradora parseJsonToSeguradora(JSONObject json){
        Seguradora entidade = new Seguradora();
        entidade.setId(json.optInt("id"));
        entidade.setNomeSeguradora(json.optString("nomeSeguradora"));
        entidade.setStatus(json.optString("status"));
        entidade.setDataCadastro(new Date());
        return entidade;
    }

    public static Seguradora parseJsonToSeguradora(String data){
        try{
            JSONObject seguradoraJson = new JSONObject(data);
            return parseJsonToSeguradora(seguradoraJson);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new Seguradora();
    }

    public static Alarme parseJsonToAlarme(JSONObject json) {
        Alarme entidade = new Alarme();
        entidade.setId(json.optLong("id"));
        entidade.setMensagem(json.optString("mensagem"));
        entidade.setDiaAlerta(json.optInt("diaAlerta"));
        entidade.setMesAlerta(json.optInt("mesAlerta"));
        entidade.setAnoAlerta(json.optInt("anoAlerta"));
        entidade.setHoraAlerta(json.optInt("horaAlerta"));
        entidade.setMinutoAlerta(json.optInt("minutoAlerta"));
        entidade.setStatus(json.optString("status"));
        entidade.setDataCadastro(new Date());
        return entidade;
    }

    public static Apolice parseJsonToApolice(JSONObject json) {
        Apolice entidade = new Apolice();
        try {
            entidade.setId(json.getLong("id"));
            entidade.setNumeroApolice(json.optString("numeroApolice"));
            entidade.setInicioVigencia(json.optString("inicioVigencia"));
            entidade.setFinalVigencia(json.optString("finalVigencia"));
            entidade.setValorPremio(json.optString("valorPremio"));
            entidade.setCorretor(parseJsonToCorretor(json.optString("corretor")));
            entidade.setSeguradora(parseJsonToSeguradora(json.optString("seguradora")));
            entidade.setSegurado(parseJsonToSegurado(json.optString("segurado")));
            entidade.setIdAlarme(json.optLong("idAlarme"));
            entidade.setTipoApolice(json.optString("tipoApolice"));
            entidade.setStatus(json.optString("status"));
            entidade.setDataCadastro(new Date());
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return entidade;
        }
    }

}
