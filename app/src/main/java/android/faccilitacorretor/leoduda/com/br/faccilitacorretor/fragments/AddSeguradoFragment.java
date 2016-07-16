package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
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

import java.util.Date;

/**
 * Created by Duda on 15/08/2015.
 */
public class AddSeguradoFragment extends Fragment{
    //VIEW ELEMENTS
    private EditText nome;
    private EditText email;
    private EditText telefoneCelular;
    private EditText telefoneOutro;
    private Button   btnOk;
    private Segurado segurado;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_segurado, container, false);
        nome  = (EditText) view.findViewById(R.id.seguradoNomeEditText);
        email = (EditText) view.findViewById(R.id.seguradoEmailEditText);
        telefoneCelular  = (EditText) view.findViewById(R.id.seguradoTelCelularEditText);
        telefoneOutro  = (EditText) view.findViewById(R.id.seguradoTelOutroEditText);
        btnOk = (Button) view.findViewById(R.id.cadastraSeguradoButton);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(v);
            }
        });

        //CARREGANDO SEGURADO QUANDO ID FOR INFORMADO
        if(getArguments()!=null) {
            long seguradoID = getArguments().getLong("seguradoID");
            if (seguradoID != 0L) {
                carregaSegurado(seguradoID);
            }
        }

        return view;
    }

    private void carregaSegurado(long seguradoID){
        SeguradoDAO dao = new SeguradoDAO(getActivity().getApplicationContext());
        segurado = dao.findById(seguradoID);
        if(segurado!=null){
            nome.setText(segurado.getNome());
            email.setText(segurado.getEmail());
            telefoneCelular.setText(segurado.getTelefoneCelular());
            telefoneOutro.setText(segurado.getTelefoneOutro());
        }
    }

    public void onClickButton(View v){
        //if(segurado==null) segurado =  new Segurado();
        Segurado newSegurado = new Segurado();
        newSegurado.setDataCadastro(new Date());
        newSegurado.setEmail(email.getEditableText().toString());
        newSegurado.setNome(nome.getEditableText().toString());
        newSegurado.setTelefoneCelular(telefoneCelular.getEditableText().toString());
        newSegurado.setTelefoneOutro(telefoneOutro.getEditableText().toString());
        newSegurado.setStatus("A");
        if(segurado!=null) {
            newSegurado.setId(segurado.getId());
            newSegurado.setStatus(segurado.getStatus());
        }

        SeguradoDAO dao = new SeguradoDAO(getActivity().getApplicationContext());
        dao.create(newSegurado);
        clearFields();
        Snackbar.make(getView(), "Operação realizada com sucesso!", Snackbar.LENGTH_SHORT).show();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        SeguradosFragment  fragSegurados = new SeguradosFragment();
        ft.remove(this);
        ft.replace(R.id.container_main, fragSegurados, "fragSegurados");
        ft.commit();
    }

    private void clearFields(){
        nome.setText("");
        email.setText("");
        telefoneCelular.setText("");
        telefoneOutro.setText("");
    }

}


