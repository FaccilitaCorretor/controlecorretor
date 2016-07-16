package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.content.SharedPreferences;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.CorretorDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Duda on 15/08/2015.
 */
public class CorretorFragment extends Fragment {
    //VIEW ELEMENTS
    private ImageView fotoPerfilImageView;
    private EditText nomeEditext;
    private EditText emailEditText;
    private EditText telefoneEditText;
    private EditText celularEditText;
    private Button confirmarButton;
    private SharedPreferences preferences;
    private ProgressBar progresBarCorretor;

    private CorretorDAO corretorDAO;
    private String idUsuarioLogado;
    private String urlImagem;

    HomeFragment homeFrag = new HomeFragment();
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_corretor, container, false);

        fotoPerfilImageView = (ImageView) view.findViewById(R.id.fotoPerfilImageView);
        nomeEditext         = (EditText) view.findViewById(R.id.nomeCorretorEditText);
        emailEditText       = (EditText) view.findViewById(R.id.emailCorretorEditText);
        telefoneEditText    = (EditText) view.findViewById(R.id.telefoneCorretorEditText);
        celularEditText     = (EditText) view.findViewById(R.id.celularCorretorEditText);
        confirmarButton     = (Button) view.findViewById(R.id.confirmarCorretorButton);
        progresBarCorretor  = (ProgressBar) view.findViewById(R.id.progresBarCorretor);

        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravaCorretor();
            }
        });

        if(urlImagem==null){
            carregaDadosUsuarioLogado();
        }

        loadImage(fotoPerfilImageView, progresBarCorretor, urlImagem);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaCorretor();
    }

    public String getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(String idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    private void carregaCorretor(){
        Corretor corretor;
        corretorDAO = new CorretorDAO(getActivity().getApplicationContext());
        corretor = corretorDAO.findById(idUsuarioLogado);
        nomeEditext.setText(corretor.getNome());
        emailEditText.setText(corretor.getEmail());
        if(corretor.getTelefoneCelular()==null || corretor.getTelefoneOutro()==null) {
            celularEditText.requestFocus();
        }else{
            celularEditText.setText(corretor.getTelefoneCelular());
            telefoneEditText.setText(corretor.getTelefoneOutro());
        }
    }

    private void gravaCorretor(){
        try{
            Corretor corretor = new Corretor();
            corretorDAO = new CorretorDAO(getActivity().getApplicationContext());
            corretor.setId(idUsuarioLogado);
            corretor.setNome(nomeEditext.getEditableText().toString());
            corretor.setEmail(emailEditText.getEditableText().toString());
            corretor.setTelefoneCelular(celularEditText.getEditableText().toString());
            corretor.setTelefoneOutro(telefoneEditText.getEditableText().toString());
            corretorDAO.create(corretor);
            gravaPreferencia();

            Toast.makeText(getActivity(), "Cadastrado atualizado!", Toast.LENGTH_LONG).show();

            //fragment home
            ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_main, homeFrag, "homeFrag");
            ft.commit();

        }catch (Exception e){
            Toast.makeText(getActivity(), "Erro", Toast.LENGTH_LONG).show();
        }
    }

    private void loadImage(final ImageView ivImg
            , final ProgressBar pbImg
            , final String urlImg){
        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
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
        pbImg.setVisibility(View.VISIBLE);
        il.get(urlImg, il.getImageListener(ivImg, pbImg.getId(), pbImg.getId()));
    }

    private void gravaPreferencia(){
        SharedPreferences preferences = getActivity().getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getActivity().getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("primeiro-acesso", false);
        editor.putBoolean("isLogado", true);
        editor.commit();
    }

    private void carregaDadosUsuarioLogado(){
        //BUSCANDO AS PREFERENCIAS
        preferences = getActivity().getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getActivity().getApplicationContext().MODE_PRIVATE);
        idUsuarioLogado = preferences.getString("idUsuario", null);
        urlImagem = preferences.getString("urlImagem", null);
    }

}