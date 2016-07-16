package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoraDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by Duda on 15/08/2015.
 */
public class AddSeguradoraFragment extends Fragment {
    //VIEW ELEMENTS
    private ImageView logo;
    private EditText nomeFantasia;
    private Button btnOk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_seguradora, container, false);
        logo = (ImageView) view.findViewById(R.id.cadastrarLogoSeguradoraImageView);
        nomeFantasia = (EditText) view.findViewById(R.id.cadastrarSeguradoraNomeEditText);
        btnOk = (Button) view.findViewById(R.id.cadastrarSeguradoraButton);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(v);
            }
        });
        return view;
    }

    public void onClickButton(View v) {
        Seguradora seguradora = new Seguradora();
        seguradora.setDataCadastro(new Date());
        seguradora.setNomeSeguradora(nomeFantasia.getEditableText().toString());

        SeguradoraDAO dao = new SeguradoraDAO(getActivity().getApplicationContext());
        dao.create(seguradora);
        Toast.makeText(getActivity(), "Seguradora Cadastrada", Toast.LENGTH_SHORT).show();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        SeguradorasFragment  fragSeguradoras = new SeguradorasFragment();
        ft.replace(R.id.container_main, fragSeguradoras, "fragSeguradoras");
        ft.commit();
    }
}


