package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.CorretorDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoraDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.ApolicesFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.CorretorFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.GraficosFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.HomeFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.SeguradorasFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.SeguradosFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private ImageView perfilImageViewHeader;
    private ProgressBar pbProfile;
    private TextView emailPerfil;
    private TextView nomePerfil;
    private String idUsuarioLogado;
    private String urlImagem;
    private boolean isPrimeiroAcesso;
    private boolean isLogado;

    //Fragments
    HomeFragment fragHome = (HomeFragment) getSupportFragmentManager().findFragmentByTag("fragHome");
    ApolicesFragment fragApolices = (ApolicesFragment) getSupportFragmentManager().findFragmentByTag("fragApolices");
    SeguradosFragment fragSegurados = (SeguradosFragment) getSupportFragmentManager().findFragmentByTag("fragSegurados");
    SeguradorasFragment fragSeguradoras = (SeguradorasFragment) getSupportFragmentManager().findFragmentByTag("fragSeguradoras");
    CorretorFragment fragCorretor = (CorretorFragment) getSupportFragmentManager().findFragmentByTag("fragCorretor");
    GraficosFragment fragGraficos = (GraficosFragment) getSupportFragmentManager().findFragmentByTag("fragGraficos");
    FragmentTransaction ft;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.ic_launcher_faccilita_logo);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(toolbar);

        emailPerfil = (TextView) findViewById(R.id.emailHeaderTextView);
        nomePerfil = (TextView) findViewById(R.id.nomeHeaderTextView);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        //CARREGANDO O PROGREES  BAR E A IMAGEVIEW PARA MANIPULACAO VIA API
        perfilImageViewHeader = (ImageView) findViewById(R.id.perfilImageViewHeader);
        pbProfile = (ProgressBar) findViewById(R.id.progresBarContainer);

        //VERIFICANDO USUARIO
        verificaUsuarioLogado();

        //CARREGA SEGURADORAS
        SeguradoraDAO dao = new SeguradoraDAO(getApplicationContext());
        dao.init();

    }//Fim onCreate

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_drawer_home:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.app_name);
                                fragHome = new HomeFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragHome, "fragHome");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_apolice:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_apolices);
                                fragApolices = new ApolicesFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragApolices, "fragApolice");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_segurado:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_segurados);
                                fragSegurados = new SeguradosFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragSegurados, "fragSegurados");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_seguradora:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_seguradoras);
                                fragSeguradoras = new SeguradorasFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragSeguradoras, "fragSeguradoras");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_corretor:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_corretor);
                                fragCorretor = new CorretorFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragCorretor, "fragCorretor");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_grafico:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_grafico);
                                fragGraficos = new GraficosFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container_main, fragGraficos, "fragGrafico");
                                ft.commit();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_ajuste:
                                menuItem.setChecked(true);
                                //toolbar.setTitle(R.string.drawer_ajustes);
                                Toast.makeText(getApplicationContext(),
                                        "Entre em contato com os desenvolvedores!" +
                                                "\nEmail:help@faccilitacorretor.com.br",
                                        Toast.LENGTH_LONG).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the
        // menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        carregaDadosUsuarioLogado();
        if (isLogado) {
            //Buscando dados do usuario logado, no caso um corretor
            if (idUsuarioLogado != null) {
                Corretor corretor = null;
                CorretorDAO dao = new CorretorDAO(getApplicationContext());
                try {
                    corretor = dao.findById(idUsuarioLogado);
                    if (corretor != null) {
                        //Preenchendo valores dos headers
                        emailPerfil.setText(corretor.getEmail());
                        nomePerfil.setText(corretor.getNome());
                        loadImage(perfilImageViewHeader, pbProfile, urlImagem);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void verificaUsuarioLogado() {
        carregaDadosUsuarioLogado();
        //VERIFICANDO USUARIO
        if (isLogado && !isPrimeiroAcesso) {
            //Fragment Home
            fragHome = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container_main, fragHome, "fragHome");
            ft.commit();
        } else if (isLogado && isPrimeiroAcesso) {
            //Fragment Corretor
            fragCorretor = new CorretorFragment();
            fragCorretor.setIdUsuarioLogado(idUsuarioLogado);
            fragCorretor.setUrlImagem(urlImagem);

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_main, fragCorretor, "fragCorretor");
            ft.commit();
        } else {
            startActivity(new Intent(MainActivity.this, SocialLoginActivity.class));
            finish();
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

    private void loadImage(final ImageView ivImg
            , final ProgressBar pbImg
            , final String urlImg) {
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        ImageLoader il = new ImageLoader(rq, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                pbImg.setVisibility(View.GONE);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        pbImg.setVisibility(View.INVISIBLE);
        il.get(urlImg, il.getImageListener(ivImg, pbImg.getId(), pbImg.getId()));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Gostaria de sair do Faccilita Corretor?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}