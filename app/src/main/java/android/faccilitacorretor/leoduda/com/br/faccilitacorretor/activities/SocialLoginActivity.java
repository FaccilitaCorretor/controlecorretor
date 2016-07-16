package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.CorretorDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.enums.EntityStatus;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.CorretorFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.HomeFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.utils.JsonParserUtil;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.utils.MiddleWareUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import static com.squareup.okhttp.Request.Builder;

public class SocialLoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_CODE = 56465;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;
    private boolean isConsentScreenOpened;
    private boolean isSignInButtonClicked;
    private AlertDialog alertDialog;
    //PROD
    private final String urlMiddleWare = "https://middleware-faccilita.herokuapp.com";
    //private final String urlMiddleWare = "http://192.168.8.91:4567";
    private Corretor corretor;
    // VIEWS
    private LinearLayout llContainerAll;
    private LinearLayout llLoginForm;
    private SignInButton btSignInDefault;
    private LinearLayout llConnected;
    private Button btSignOut;
    private Button btRevokeAccess;
    //dados from google
    private Person p;
    private FragmentTransaction ft;
    private SharedPreferences preferences;
    private String idUsuarioLogado;
    private String urlImagem;
    private boolean isPrimeiroAcesso;
    private boolean isLogado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //BUSCANDO AS PREFERENCIAS
        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getApplicationContext().MODE_PRIVATE);

        boolean primeiroAcesso = preferences.getBoolean("primeiro-acesso", true);
        if(!primeiroAcesso){
            Intent it = new Intent(SocialLoginActivity.this, MainActivity.class);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        accessViews();
        // Initialize the Google API Client
        googleApiClient = new GoogleApiClient.Builder(SocialLoginActivity.this)
                .addConnectionCallbacks(SocialLoginActivity.this)
                .addOnConnectionFailedListener(SocialLoginActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        //VERIFICANDO USUARIO
        verificaUsuarioLogado();
    }


    @Override
    public void onStart(){
        super.onStart();

        if(googleApiClient != null){
            googleApiClient.connect();
        }
    }


    @Override
    public void onStop(){
        super.onStop();

        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SIGN_IN_CODE){
            isConsentScreenOpened = false;

            if(resultCode != RESULT_OK){
                isSignInButtonClicked = false;
            }

            if(!googleApiClient.isConnecting()){
                googleApiClient.connect();
            }
        }
    }


    // LISTENERS
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btSignInDefault ){
            if(!googleApiClient.isConnecting()){
                isSignInButtonClicked = true;
                showUi(false, true);
                resolveSignIn();
            }
        }
        else if(v.getId() == R.id.btSignOut){
            if(googleApiClient.isConnected()){
                Plus.AccountApi.clearDefaultAccount(googleApiClient);
                googleApiClient.disconnect();
                googleApiClient.connect();
                showUi(false, false);
            }
        }
        else if(v.getId() == R.id.btRevokeAccess){
            if(googleApiClient.isConnected()){
                Plus.AccountApi.clearDefaultAccount(googleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient).setResultCallback(new ResultCallback<Status>(){
                    @Override
                    public void onResult(Status result) {
                        finish();
                    }
                });
            }
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        gravaDados();
    }


    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
        showUi(false, false);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!result.hasResolution()){
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), SocialLoginActivity.this, 0).show();
            return;
        }

        if(!isConsentScreenOpened){
            connectionResult = result;

            if(isSignInButtonClicked){
                resolveSignIn();
            }
        }
    }

    // UTIL
    public void accessViews(){
        llContainerAll = (LinearLayout) findViewById(R.id.llContainerAll);

        // NOT CONNECTED
        llLoginForm = (LinearLayout) findViewById(R.id.llLoginForm);
        btSignInDefault = (SignInButton) findViewById(R.id.btSignInDefault);

        // CONNECTED
        llConnected = (LinearLayout) findViewById(R.id.llConnected);
        btSignOut = (Button) findViewById(R.id.btSignOut);
        btRevokeAccess = (Button) findViewById(R.id.btRevokeAccess);

        // LISTENER
        btSignInDefault.setOnClickListener(SocialLoginActivity.this);
        btSignOut.setOnClickListener(SocialLoginActivity.this);
        btRevokeAccess.setOnClickListener(SocialLoginActivity.this);

    }

    public void showUi(boolean status, boolean statusProgressBar){
        if(!statusProgressBar){
            llContainerAll.setVisibility(View.VISIBLE);

            llLoginForm.setVisibility(status ? View.GONE : View.VISIBLE);
            llConnected.setVisibility(!status ? View.GONE : View.VISIBLE);
        }
        else{
            llContainerAll.setVisibility(View.GONE);
        }
    }


    public void resolveSignIn(){
        if(connectionResult != null && connectionResult.hasResolution()){
            try {
                isConsentScreenOpened = true;
                connectionResult.startResolutionForResult(SocialLoginActivity.this, SIGN_IN_CODE);
            }
            catch(IntentSender.SendIntentException e) {
                isConsentScreenOpened = false;
                googleApiClient.connect();
            }
        }
    }

    private void gravaDados(){
        p = Plus.PeopleApi.getCurrentPerson(googleApiClient);

        if(p != null){
            //gravando no banco de dados
            CorretorDAO dao = new CorretorDAO(getApplicationContext());
            corretor = new Corretor();
            corretor.setId(p.getId());
            corretor.setNome(p.getDisplayName());
            corretor.setEmail(Plus.AccountApi.getAccountName(googleApiClient));
            corretor.setDataCadastro(new Date());
            corretor.setStatus(EntityStatus.ATIVA.toString());
            corretor.setNomeImagem(p.getId());
            dao.create(corretor);

            //AQUI PRECISO VERIFICAR SE TEM INTERNET, SE SIM FAÇO A GRAVACAO NO DYNAMO - POR HORA GRAVO NA MARRA
            //SE TIVER INTERNET, EU EU BUSCO NO MIDDLEWARE, SE O CARA EXISTIR EU GRAVO AS PREFERENCIAS E DEPOIS EXIBO POPUP
            //SE ELE NAO EXISITR, APENAS GRAVO OS DADOS DELE NO MIDDLEWARE E GRAVO AS PREFERENCIAS
            //SEM INTERNET EU GRAVO DIRETO AS PREFERENCIAS
            if(isConnected()) {
                verifyUserInMiddleware(corretor);
            }else{
                gravaPreferencias();
            }
        }
    }


    private void verifyUserInMiddleware(final Corretor corretor){
        /*AsyncTask task = new AsyncTask(){
            protected Object doInBackground(Object... params) {
                final MediaType TEXT
                        = MediaType.parse("text/plain; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                try {
                    RequestBody body = RequestBody.create(TEXT, corretor.getEmail());
                    Request request = new Request.Builder()
                            .url(urlMiddleWare + "/faccilitacorretor/exists")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String retorno = response.body().string();
                    JSONObject jsonReturned = new JSONObject(retorno);
                    if(jsonReturned.getBoolean("item")){
                        //createPopup(); - nao posso criar um componente de dentro de uma outra thread
                        gravaPreferencias();
                    }else{
                        postNewUserToMiddleware(corretor);
                        gravaPreferencias();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException j){
                    j.printStackTrace();
                }

                return null;
            }
        };
        task.execute();*/
        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject objectRequest = new JSONObject();
            objectRequest.put("email", corretor.getEmail());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, urlMiddleWare + "/faccilitacorretor/exists"
                        , objectRequest, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("item")){
                                createPopup();
                            }else{
                                postNewUserToMiddleware(corretor);
                                gravaPreferencias();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });

            queue.add(jsonObjectRequest);
        }catch (JSONException js){
            js.printStackTrace();
        }
    }

    private void postNewUserToMiddleware(final Corretor corretor){
        AsyncTask task = new AsyncTask(){
            protected Object doInBackground(Object... params) {
                final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");
                String json = JsonParserUtil.parseCorretorToJson(corretor).toString();
                OkHttpClient client = new OkHttpClient();
                try {
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Builder()
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
    }

    private void restauraDadosFromMiddleWare(Corretor corretor){
        //TOD0 - BUSCAR DADOS NO MIDDLEWARE E POPULAR NO BANCO
        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject objectRequest = new JSONObject();
            objectRequest.put("email", corretor.getEmail());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (com.android.volley.Request.Method.POST, urlMiddleWare + "/faccilitacorretor/busca"
                            , objectRequest, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            MiddleWareUtil util = new MiddleWareUtil(getApplicationContext());
                            util.restoreDataFromMiddleware(response);
                            gravaPreferencias();
                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            error.printStackTrace();
                        }
                    });

            queue.add(jsonObjectRequest);
        }catch (JSONException js){
            js.printStackTrace();
        }
    }

    private void createPopup(){
        new AlertDialog.Builder(this)
                .setMessage("Você possui um backup, deseja restaurar os dados ?")
                .setNegativeButton("Não", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1) {
                        gravaPreferencias();
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        restauraDadosFromMiddleWare(corretor);
                    }
                }).create().show();
    }

    private void gravaPreferencias(){
        String urlImagem;
        String idUsuario;

        if(p != null){
            String imageUrl = p.getImage().getUrl();

            urlImagem = imageUrl.substring(0, imageUrl.length() - 2)+"200";
            idUsuario = p.getId();

            preferences = getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("idUsuario", idUsuario);
            editor.putString("urlImagem", urlImagem);
            editor.putBoolean("primeiro-acesso", true);
            editor.putBoolean("isLogado", true);
            editor.commit();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));

            finish();
        }
        else{
            Toast.makeText(SocialLoginActivity.this, "Dados não liberados", Toast.LENGTH_SHORT).show();
        }
    }

    private void verificaUsuarioLogado() {
        carregaDadosUsuarioLogado();
        //VERIFICANDO USUARIO
        if (isLogado && !isPrimeiroAcesso) {
            //Fragment Home
            HomeFragment fragHome = new HomeFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container_main, fragHome, "fragHome");
            ft.commit();
        } else if (isLogado && isPrimeiroAcesso) {
            //Fragment Corretor
            CorretorFragment fragCorretor = new CorretorFragment();
            fragCorretor.setIdUsuarioLogado(idUsuarioLogado);
            fragCorretor.setUrlImagem(urlImagem);

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_main, fragCorretor, "fragCorretor");
            ft.commit();
        }
    }

    private void carregaDadosUsuarioLogado() {
        //BUSCANDO AS PREFERENCIAS
        preferences = getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getApplicationContext().MODE_PRIVATE);
        idUsuarioLogado = preferences.getString("idUsuario", null);
        urlImagem = preferences.getString("urlImagem", null);
        isPrimeiroAcesso = preferences.getBoolean("primeiro-acesso", true);
        isLogado = preferences.getBoolean("isLogado", false);
    }

    private boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private boolean isConnectedOnWifi(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

}