package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by turbiani on 31/08/15.
 */
public class SeguradoDAO {
    private Realm realm = null;
    private Context context;

    public SeguradoDAO(Context context){
        this.context = context;
    }

    public Realm getRealm(){
        if(realm==null){
            realm = Realm.getInstance(this.context);
        }
        return realm;
    }

    public RealmResults<Segurado> findAll(){
        return getRealm().where(Segurado.class).equalTo("status", "A").findAll();
    }

    public Segurado findById(long id){
        Segurado segurado =  getRealm().where(Segurado.class)
                .equalTo("id", id)
                .findFirst();
        return segurado;
    }

    public Segurado findById(long id, Realm r){
        Segurado segurado =  r.where(Segurado.class)
                .equalTo("id", id)
                .findFirst();
        return segurado;
    }

    public void create(Segurado segurado){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(segurado);
        getRealm().commitTransaction();
        close();
    }

    public Segurado remove(Segurado segurado){
        Segurado entidade;
        getRealm().beginTransaction();
        entidade = findById(segurado.getId(), getRealm());
        if(entidade != null){
            entidade.removeFromRealm();
        }
        getRealm().commitTransaction();
        close();
        return segurado;
    }

    public void remove(long idSegurado){
        Segurado entidade;
        getRealm().beginTransaction();
        entidade = findById(idSegurado, getRealm());
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
}
