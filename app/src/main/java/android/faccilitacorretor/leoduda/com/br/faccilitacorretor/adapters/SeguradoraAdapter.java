package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.adapters;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder.SeguradoraViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Duda on 21/09/2015.
 */
public class SeguradoraAdapter extends RecyclerView.Adapter<SeguradoraViewHolder> {

    private Context context;
    private List<Seguradora> seguradoras;
    private View view;

    public SeguradoraAdapter(Context context, List<Seguradora> seguradoras) {
        this.context = context;
        this.seguradoras = seguradoras;
    }

    @Override
    public SeguradoraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(context).inflate(R.layout.item_seguradora, parent, false);
        SeguradoraViewHolder viewHolder = new SeguradoraViewHolder(context, this.view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeguradoraViewHolder holder, int position) {
        Seguradora seguradora = seguradoras.get(position);
        holder.nomeSeguradoraTextView.setText(seguradora.getNomeSeguradora());
        //holder.logoSeguradoraImageView.setImageDrawable(getLogoSeguradora(seguradora.getNomeSeguradora()));
        carregaLogoSeguradora(seguradora.getNomeSeguradora(), holder);
    }

    @Override
    public int getItemCount() {
        return seguradoras.size();
    }

    private void carregaLogoSeguradora(String nomeSeguradora, SeguradoraViewHolder holder){

        if(nomeSeguradora!=null) {
            if (nomeSeguradora.equalsIgnoreCase("allianz")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.allianz);
            } else if (nomeSeguradora.equalsIgnoreCase("azul")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.azul);
            } else if (nomeSeguradora.equalsIgnoreCase("bradesco")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.bradesco);
            } else if (nomeSeguradora.equalsIgnoreCase("caixa")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.caixa);
            } else if (nomeSeguradora.equalsIgnoreCase("hdi")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.hdi);
            } else if (nomeSeguradora.equalsIgnoreCase("itau")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.itau);
            } else if (nomeSeguradora.equalsIgnoreCase("sulamerica")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.sulamerica);
            } else if (nomeSeguradora.equalsIgnoreCase("maritima")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.maritima);
            } else if (nomeSeguradora.equalsIgnoreCase("porto")) {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.porto);
            } else {
                holder.logoSeguradoraImageView.setImageResource(R.drawable.tokio);
            }
        }
    }
}
