package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Duda on 22/09/15.
 */
public class ApoliceDAO {
    private Realm realm = null;
    private Context context;

    public ApoliceDAO(Context context){
        this.context = context;
    }

    public Realm getRealm(){
        if(realm==null){
            realm = Realm.getInstance(this.context);
        }
        return realm;
    }

    public RealmResults<Apolice> findAll(){return getRealm().where(Apolice.class).findAll();}

    public Apolice findById(long id){
        return getRealm().where(Apolice.class)
                .equalTo("id", id)
                .equalTo("status", "A")
                .findFirst();
    }

    public Apolice findById(long id, Realm r){
        Apolice apolice =  r.where(Apolice.class)
                .equalTo("id", id)
                .findFirst();
        return apolice;
    }

    public void create(Apolice apolice){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(apolice);
        getRealm().commitTransaction();
    }

    public Apolice remove(Apolice apolice){
        Apolice entidade;
        getRealm().beginTransaction();
        entidade = findById(apolice.getId(), getRealm());
        if(entidade != null){
            entidade.removeFromRealm();
        }
        getRealm().commitTransaction();
        close();
        return apolice;
    }

    public void remove(long idApolice){
        Apolice entidade;
        getRealm().beginTransaction();
        entidade = findById(idApolice, getRealm());
        if(entidade != null){
            //entidade.removeFromRealm();
            entidade.setStatus("E");
        }
        getRealm().commitTransaction();
        close();
    }

    private void close(){
        if(realm!=null){
            realm.close();
            realm = null;
        }
    }

    public float[] getTopVendasSeguradoras(){
        float[] valores = {5, 1, 3, 4, 6};
        return valores;
    }

    public String [] getTopSeguradoras(){
        String[] seguradoras = {"Porto", "Tokio", "Azul", "Maritima","Outras"};
        return seguradoras;
    }

}
