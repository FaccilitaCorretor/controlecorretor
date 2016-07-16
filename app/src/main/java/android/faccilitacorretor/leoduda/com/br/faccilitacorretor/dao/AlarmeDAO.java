package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Alarme;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Duda on 22/09/15.
 */
public class AlarmeDAO {
    private Realm realm = null;
    private Context context;

    public AlarmeDAO(Context context){
        this.context = context;
    }

    public Realm getRealm(){
        if(realm==null){
            realm = Realm.getInstance(this.context);
        }
        return realm;
    }

    public RealmResults<Alarme> findAll(){return getRealm().where(Alarme.class).findAll();}

    public Alarme findById(int id){
        return getRealm().where(Alarme.class)
                .equalTo("id", id)
                .findFirst();
    }

    public Alarme findById(long id){
        return getRealm().where(Alarme.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void create(Alarme alarme){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(alarme);
        getRealm().commitTransaction();
    }

}
