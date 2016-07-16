package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.adapters;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder.SeguradoViewHolder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Duda on 15/08/2015.
 */
public class SeguradoAdapter extends RecyclerView.Adapter<SeguradoViewHolder> {

    private Context context;
    private List<Segurado> segurados;
    private FragmentActivity activity;

    public SeguradoAdapter(Context context, List<Segurado> segurados, FragmentActivity activity) {
        this.context = context;
        this.segurados = segurados;
        this.activity = activity;
    }

    @Override
    public SeguradoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_segurado, parent, false);
        SeguradoViewHolder viewHolder = new SeguradoViewHolder(context, view, segurados, activity);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeguradoViewHolder holder, int position) {
        Segurado segurado = segurados.get(position);
        holder.seguradoNomeTextView.setText(segurado.getNome());
        holder.seguradoTelCeularOuOutrosTextView.setText(segurado.getTelefoneCelular()!="" ? segurado.getTelefoneCelular() : segurado.getTelefoneOutro());
    }

    @Override
    public int getItemCount() {
        return segurados.size();
    }
}
