package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.ApoliceDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.AddApoliceFragment;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments.ApolicesFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

/**
 * Created by turbiani on 12/09/15.
 */
public class ApoliceViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    public TextView vigenciaApoliceTextView;
    public TextView tipoApoliceTextView;
    public ImageView seguradoraApoliceImageView;
    private List<Apolice> apolices;
    private FragmentActivity activity;

    public ApoliceViewHolder(Context context, View itemView, final List<Apolice> apolices, FragmentActivity activity) {
        super(itemView);
        this.context = context;
        this.apolices = apolices;
        this.activity  = activity;


        vigenciaApoliceTextView = (TextView) itemView.findViewById(R.id.vigenciaApoliceTextView);
        tipoApoliceTextView     = (TextView) itemView.findViewById(R.id.tipoApoliceTextView);
        seguradoraApoliceImageView = (ImageView) itemView.findViewById(R.id.seguradoraApoliceImageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apolice apoliceSelecionada = apolices.get(getPosition());
                if (apoliceSelecionada!=null) {
                    alteraApolice(apoliceSelecionada);
                } else {
                    Snackbar.make(v, "Erro de sistema", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Apolice apoliceSelecionada = apolices.get(getPosition());
                if (apoliceSelecionada!=null) {
                   //CRIA ALERTDIALOG
                    criaAlertDialog(apoliceSelecionada, v,getPosition()).show();
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

    private AlertDialog criaAlertDialog(final Apolice apolice, final View v,  int position){
        CharSequence[] opcoes = {"Inativar apólice", "Ligar para o segurado"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder
            .setTitle("Ações")
            .setItems(opcoes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    String numero = apolice.getSegurado().getTelefoneCelular();
                    String outroNumero = apolice.getSegurado().getTelefoneOutro();

                    if (position==0) {
                        if (apolice != null) {
                            ApoliceDAO dao = new ApoliceDAO(v.getContext());
                            Snackbar.make(v, "Apolice " + apolice.getNumeroApolice() + " inativada", Snackbar.LENGTH_SHORT).show();
                            dao.remove(apolice.getId());
                            listaApolices();
                        }

                    }else if (position==1){
                        if (numero != null){
                            Uri uri = Uri.parse("tel:" + numero);
                            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
                            activity.startActivity(callIntent);

                        }else if (outroNumero != null){
                            Uri uri = Uri.parse("tel:" + numero);
                            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
                            activity.startActivity(callIntent);
                        }
                        else {
                            Snackbar.make(itemView, "Não foi possível efetuar a ligação", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        return builder.create();
    }

    private void listaApolices(){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ApolicesFragment fragApolices = new ApolicesFragment();
        ft.replace(R.id.container_main, fragApolices, "fragApolice");
        ft.commit();
    }

    private void alteraApolice(Apolice apolice){
        Bundle bundle = new Bundle();
        bundle.putLong("apoliceID", apolice.getId());
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        AddApoliceFragment fragAddApolice = new AddApoliceFragment();
        fragAddApolice.setArguments(bundle);
        ft.replace(R.id.container_main, fragAddApolice, "fragAddApolice");
        ft.commit();
    }

}
