package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by turbiani on 04/08/15.
 */
public class FaccilitaCorretor extends Application {
    private RealmConfiguration config;
    //private CognitoCachingCredentialsProvider credentialsProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        config = new RealmConfiguration.Builder(getApplicationContext())
                .name("faccilita")
                .build();
        Realm.setDefaultConfiguration(config);

        //credentialsProvider = new CognitoCachingCredentialsProvider(
        //        getApplicationContext(),    /* get the context for the application */
        //        "us-east-1:4e0e1585-00ce-42c2-8f45-20feab523afa",    /* Identity Pool ID */
        //        Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        //);

    }
}
