package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by turbiani on 31/08/15.
 */
public class CorretorDAO {
    private Realm realm = null;
    private Context context;


    public CorretorDAO(Context context){
        this.context = context;
    }

    public Realm getRealm(){
        if(realm==null){
            realm = Realm.getInstance(this.context);
        }

        return realm;
    }

    public RealmResults<Corretor> findAll(){
        return getRealm().where(Corretor.class).findAll();
    }

    public Corretor findById(String id){
        return getRealm().where(Corretor.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void create(Corretor corretor){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(corretor);
        getRealm().commitTransaction();
    }
}
