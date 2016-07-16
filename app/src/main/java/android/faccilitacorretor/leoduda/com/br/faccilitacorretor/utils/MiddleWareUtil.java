package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.utils;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.AlarmeDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.ApoliceDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.CorretorDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Alarme;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by turbiani on 07/12/15.
 */
public class MiddleWareUtil {
    private Context context;
    //PROD
    private final String urlMiddleWare = "https://middleware-faccilita.herokuapp.com";
    //private final String urlMiddleWare = "http://192.168.8.91:4567";

    public MiddleWareUtil(Context context){
        this.context = context;
    }

    public void restoreDataFromMiddleware(String data){

    }

    public void restoreDataFromMiddleware(JSONObject data){
        try{
            SeguradoDAO seguradoDAO = new SeguradoDAO(context);
            ApoliceDAO apoliceDAO = new ApoliceDAO(context);
            AlarmeDAO alarmeDAO = new AlarmeDAO(context);
            CorretorDAO corretorDAO = new CorretorDAO(context);

            Corretor corretor = JsonParserUtil.parseJsonToCorretor(data);
                corretorDAO.create(corretor);

            for(Segurado segurado : getSeguradosFromJsonArray(data.optJSONArray("segurados"))){
               seguradoDAO.create(segurado);
            }

            for(Alarme alarme : getAlarmsFromJsonArray(data.optJSONArray("alarmes"))){
               alarmeDAO.create(alarme);
            }

            for(Apolice apolice : getApolicesFromJsonArray(data.optJSONArray("apolices"))){
               apoliceDAO.create(apolice);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pushDataToMiddleware(Apolice apolice){
        try {
            //Montando futuro CorretorDTO que possui todos os objetos appendados nele
           final JSONObject corretorRoot = JsonParserUtil.parseCorretorToJson(apolice.getCorretor());
            corretorRoot.put("segurados", getSeguradosAsJsonArray());
            corretorRoot.put("alarmes", getAlarmsAsJsonArray());
            corretorRoot.put("apolices", getApolicesAsJsonArray());
            AsyncTask task = new AsyncTask(){
                protected Object doInBackground(Object... params) {
                    final MediaType JSON
                            = MediaType.parse("application/json; charset=utf-8");
                    OkHttpClient client = new OkHttpClient();
                    try {
                        RequestBody body = RequestBody.create(JSON, corretorRoot.toString());
                        Request request = new Request.Builder()
                                .url(urlMiddleWare + "/faccilitacorretor")
                                .post(body)
                                .build();
                        Response response = client.newCall(request).execute();
                        String retorno = response.body().string();
                        Log.e("faccilitacorretor-post", retorno);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    return null;
                }
            };
            task.execute();
        }catch (JSONException j){
            j.printStackTrace();
        }
    }

    private JSONArray getSeguradosAsJsonArray(){
        JSONArray  seguradosJson = new JSONArray();
        SeguradoDAO dao = new SeguradoDAO(context);
        List<Segurado> segurados = dao.findAll();
        for(Segurado segurado : segurados){
            seguradosJson.put(JsonParserUtil.parseSeguradoToJson(segurado));
        }
        return seguradosJson;
    }

    private JSONArray getAlarmsAsJsonArray(){
        JSONArray  alarmesJson = new JSONArray();
        AlarmeDAO dao = new AlarmeDAO(context);
        List<Alarme> alarmes = dao.findAll();
        for(Alarme alarme : alarmes){
            alarmesJson.put(JsonParserUtil.parseAlarmeToJson(alarme));
        }
        return alarmesJson;
    }

    private JSONArray getApolicesAsJsonArray(){
        JSONArray  apolicesJson = new JSONArray();
        ApoliceDAO dao = new ApoliceDAO(context);
        List<Apolice> apolices = dao.findAll();
        for(Apolice apolice : apolices){
            apolicesJson.put(JsonParserUtil.parseApoliceToJson(apolice));
        }
        return apolicesJson;
    }

    private List<Segurado> getSeguradosFromJsonArray(JSONArray data){
        if(data==null) return new ArrayList<Segurado>();
        List<Segurado> segurados = new ArrayList<Segurado>();
        try {
            for (int i = 0; i <= data.length() - 1; i++) {
                segurados.add(JsonParserUtil.parseJsonToSegurado(data.getJSONObject(i)));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return segurados;
        }
    }

    private List<Alarme> getAlarmsFromJsonArray(JSONArray data){
        if(data==null) return new ArrayList<Alarme>();
        List<Alarme> alarmes = new ArrayList<Alarme>();
        try {
            for (int i = 0; i <= data.length() - 1; i++) {
                alarmes.add(JsonParserUtil.parseJsonToAlarme(data.getJSONObject(i)));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return alarmes;
        }
    }

    private List<Apolice> getApolicesFromJsonArray(JSONArray data){
        if(data==null) return new ArrayList<Apolice>();
        List<Apolice> apolices = new ArrayList<Apolice>();
        try {
            for (int i = 0; i <= data.length() - 1; i++) {
                apolices.add(JsonParserUtil.parseJsonToApolice(data.getJSONObject(i)));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return apolices;
        }
    }

}
