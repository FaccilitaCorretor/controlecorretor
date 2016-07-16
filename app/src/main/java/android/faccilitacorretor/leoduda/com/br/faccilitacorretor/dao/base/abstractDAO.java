package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.base;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by turbiani on 31/08/15.
 */
public abstract class abstractDAO<T extends RealmObject> {
    private static Realm  realm    = Realm.getDefaultInstance();

    /*public RealmResults<T> findAll(T entidade){
        return realm.where(entidade.getClass()).findAll();
    }*/

    public void createOrUpdate(T entidade){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(entidade);
        realm.commitTransaction();
    }

    public void create(T entidade){
        realm.beginTransaction();
        realm.copyToRealm(entidade);
        realm.commitTransaction();
    }

    public RealmObject findById(Class<T> entidade, int id){
        return realm.where((Class)entidade.getClass())
                .equalTo("id", id)
                .findFirst();
    }


}
