package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Duda on 22/09/15.
 */
public class SeguradoraDAO {
    private Realm realm = null;
    private Context context;

    public SeguradoraDAO(Context context){
        this.context = context;
    }

    public Realm getRealm(){
        if(realm==null){
            realm = Realm.getInstance(this.context);
        }
        return realm;
    }

    public RealmResults<Seguradora> findAll(){return getRealm().where(Seguradora.class).findAll();}

    public Seguradora findById(long id){
        return getRealm().where(Seguradora.class)
                .equalTo("id", id)
                .findFirst();
    }

    public Seguradora findById(int id){
        return getRealm().where(Seguradora.class)
                .equalTo("id", id)
                .findFirst();
    }


    public Seguradora findByNome(String nome){
        return getRealm().where(Seguradora.class)
                .equalTo("nomeSeguradora", nome)
                .findFirst();
    }


    public void create(Seguradora seguradora){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(seguradora);
        getRealm().commitTransaction();
    }

    public void init(){
        int resultado = findAll().size();
        if (resultado <= 0) {
            getRealm().beginTransaction();
            for(Seguradora seguradora: getSeguradoras()){
                getRealm().copyToRealm(seguradora);
            }
            getRealm().commitTransaction();
        }
    }

    private List<Seguradora> getSeguradoras(){
        List<Seguradora> seguradoras = new ArrayList<Seguradora>();

        Seguradora allianz = new Seguradora();
        allianz.setNomeSeguradora("Allianz");
        allianz.setDataCadastro(new Date());
        allianz.setStatus("A");
        seguradoras.add(allianz);

        Seguradora azul = new Seguradora();
        azul.setNomeSeguradora("Azul");
        azul.setDataCadastro(new Date());
        azul.setStatus("A");
        seguradoras.add(azul);

        Seguradora bradesco = new Seguradora();
        bradesco.setNomeSeguradora("Bradesco");
        bradesco.setDataCadastro(new Date());
        bradesco.setStatus("A");
        seguradoras.add(bradesco);

        Seguradora caixa = new Seguradora();
        caixa.setNomeSeguradora("Caixa");
        caixa.setDataCadastro(new Date());
        caixa.setStatus("A");
        seguradoras.add(caixa);

        Seguradora hdi = new Seguradora();
        hdi.setNomeSeguradora("Hdi");
        hdi.setDataCadastro(new Date());
        hdi.setStatus("A");
        seguradoras.add(hdi);

        Seguradora itau = new Seguradora();
        itau.setNomeSeguradora("Itau");
        itau.setDataCadastro(new Date());
        itau.setStatus("A");
        seguradoras.add(itau);

        Seguradora sulamerica = new Seguradora();
        sulamerica.setNomeSeguradora("Sulamerica");
        sulamerica.setDataCadastro(new Date());
        sulamerica.setStatus("A");
        seguradoras.add(sulamerica);

        Seguradora maritima = new Seguradora();
        maritima.setNomeSeguradora("Maritima");
        maritima.setDataCadastro(new Date());
        maritima.setStatus("A");
        seguradoras.add(maritima);

        Seguradora porto = new Seguradora();
        porto.setNomeSeguradora("Porto");
        porto.setDataCadastro(new Date());
        porto.setStatus("A");
        seguradoras.add(porto);

        Seguradora tokio = new Seguradora();
        tokio.setNomeSeguradora("Tokio Marine");
        tokio.setDataCadastro(new Date());
        tokio.setStatus("A");
        seguradoras.add(tokio);

        return seguradoras;
    }

}
