package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.adapters.SeguradoraAdapter;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoraDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

/**
 * Created by Duda on 15/08/2015.
 */
public class SeguradorasFragment extends Fragment {
    private RecyclerView seguradoraRecyclerView;
    private SeguradoraAdapter seguradoraAdapter;
    private List<Seguradora> seguradoras;

    private FloatingActionMenu fab;

    AddSeguradoFragment fragAddSegurado = new AddSeguradoFragment();
    AddSeguradoraFragment fragAddSeguradora = new AddSeguradoraFragment();
    AddApoliceFragment fragAddApolice = new AddApoliceFragment();
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SeguradoraDAO dao = new SeguradoraDAO(getActivity().getApplicationContext());
        seguradoras = dao.findAll();

        View view = inflater.inflate(R.layout.fragment_seguradoras, container, false);
        seguradoraAdapter = new SeguradoraAdapter(this.getActivity().getApplicationContext(), seguradoras);
        seguradoraRecyclerView = (RecyclerView) view.findViewById(R.id.seguradoraListRecyclerView);
        seguradoraRecyclerView.setAdapter(seguradoraAdapter);
        seguradoraRecyclerView.setItemAnimator(new DefaultItemAnimator());
        seguradoraRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));

        //Floating Action Button com menu
        FloatingActionButton fabSegurado = (FloatingActionButton) view.findViewById(R.id.fab2);
        FloatingActionButton fabSeguradora = (FloatingActionButton) view.findViewById(R.id.fab3);
        FloatingActionButton fabApolice = (FloatingActionButton) view.findViewById(R.id.fab4);

        fab = (FloatingActionMenu) view.findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
            }
        });

        //Sub Menu - FloatingAction Button - addSegurado
        fabSegurado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddSegurado, "fragAddSegurado");
                ft.commit();
            }
        });

        //Sub Menu - FloatingAction Button - addSeguradora
        fabSeguradora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddSeguradora, "fragAddSeguradora");
                ft.commit();
            }
        });

        //Sub Menu - FloatingAction Button - addApolice
        fabApolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_main, fragAddApolice, "fragAddApolice");
                //ft.addToBackStack("backStack");
                ft.commit();
            }
        });

        fab.close(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
