package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.activities;

import android.content.Intent;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Duda on 26/01/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
