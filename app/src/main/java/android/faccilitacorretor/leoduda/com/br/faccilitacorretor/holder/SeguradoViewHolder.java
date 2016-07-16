package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.AddApoliceFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.AddSeguradoFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.SeguradosFragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by turbiani on 12/09/15.
 */
public class SeguradoViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    public TextView seguradoNomeTextView;
    public TextView seguradoTelCeularOuOutrosTextView;
    private List<Segurado> segurados;
    private FragmentActivity activity;

    public SeguradoViewHolder(Context context, View itemView, final List<Segurado> segurados, FragmentActivity activity) {
        super(itemView);
        this.context = context;
        this.segurados = segurados;
        this.activity  = activity;


        seguradoNomeTextView                = (TextView) itemView.findViewById(R.id.seguradoNomeTextView);
        seguradoTelCeularOuOutrosTextView   = (TextView) itemView.findViewById(R.id.seguradoTelCeularOuOutrosTextView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Segurado seguradoSelecionado = segurados.get(getPosition());
                if (seguradoSelecionado!=null) {
                    alteraSegurado(seguradoSelecionado);
                } else {
                    Snackbar.make(v, "Erro de sistema", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Segurado seguradoSelecionado = segurados.get(getPosition());
                if (seguradoSelecionado!=null) {
                   //CRIA ALERTDIALOG
                    criaAlertDialog(seguradoSelecionado, v,getPosition()).show();
                } else {
                    Snackbar.make(v, "Erro de sistema", Snackbar.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private AlertDialog criaAlertDialog(final Segurado segurado, final View v,  int position){
        CharSequence[] opcoes = {"Inativar segurado", "Criar Apólice"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder
            .setTitle("Ações")
            .setItems(opcoes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    if (position==0) {
                        if (segurado != null) {
                            SeguradoDAO dao = new SeguradoDAO(v.getContext());
                            Snackbar.make(v, "Segurado " + segurado.getNome() + " inativado", Snackbar.LENGTH_SHORT).show();
                            dao.remove(segurado.getId());
                            //segurados.remove(position);
                            listaSegurados();
                        }

                    }else if (position==1){
                        //chamar a tela de apólice mandando os dados deste segurado via bundle;
                        adicionaApolice(segurado);
                    }
                }
            });
        return builder.create();
    }

    private void listaSegurados(){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        SeguradosFragment seguradoFragment = new SeguradosFragment();
        ft.replace(R.id.container_main, seguradoFragment, "fragSegurados");
        ft.commit();
    }

    private void alteraSegurado(Segurado segurado){
        Bundle bundle = new Bundle();
        bundle.putLong("seguradoID", segurado.getId());
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        AddSeguradoFragment fragAddSegurado = new AddSeguradoFragment();
        fragAddSegurado.setArguments(bundle);
        ft.replace(R.id.container_main, fragAddSegurado, "fragAddSegurados");
        ft.commit();
    }

    private void adicionaApolice(Segurado segurado){
        Bundle bundle = new Bundle();
        bundle.putLong("seguradoID", segurado.getId());
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        AddApoliceFragment fragAddApolice = new AddApoliceFragment();
        fragAddApolice.setArguments(bundle);
        ft.replace(R.id.container_main, fragAddApolice, "fragAddApolice");
        ft.commit();
    }
}
