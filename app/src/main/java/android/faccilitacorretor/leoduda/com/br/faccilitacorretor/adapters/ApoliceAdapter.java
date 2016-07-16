package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.adapters;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder.ApoliceViewHolder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Duda on 15/08/2015.
 */
public class ApoliceAdapter extends RecyclerView.Adapter<ApoliceViewHolder> {

    private Context context;
    private List<Apolice> apolices;
    private FragmentActivity activity;

    public ApoliceAdapter(Context context, List<Apolice> apolices, FragmentActivity activity) {
        this.context = context;
        this.apolices = apolices;
        this.activity = activity;
    }

    @Override
    public ApoliceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_apolice, parent, false);
        ApoliceViewHolder viewHolder = new ApoliceViewHolder(context, view, apolices, activity);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ApoliceViewHolder holder, int position) {
        Apolice apolice = apolices.get(position);
        //TENTAR EXIBIR DE UM JEITO AMIGAVEL O FINAL DA VIGENCIA
        holder.vigenciaApoliceTextView.setText(apolice.getFinalVigencia().toString());
        holder.tipoApoliceTextView.setText(apolice.getTipoApolice());
        carregaLogoSeguradora(
                apolice.getSeguradora()==null ? "xxx" : apolice.getSeguradora().getNomeSeguradora()
                , holder);
    }

    @Override
    public int getItemCount() {
        return apolices.size();
    }

    private void carregaLogoSeguradora(String nomeSeguradora, ApoliceViewHolder holder){

        if(nomeSeguradora!=null) {
            if (nomeSeguradora.equalsIgnoreCase("allianz")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.allianz);
            } else if (nomeSeguradora.equalsIgnoreCase("azul")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.azul);
            } else if (nomeSeguradora.equalsIgnoreCase("bradesco")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.bradesco);
            } else if (nomeSeguradora.equalsIgnoreCase("caixa")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.caixa);
            } else if (nomeSeguradora.equalsIgnoreCase("hdi")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.hdi);
            } else if (nomeSeguradora.equalsIgnoreCase("itau")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.itau);
            } else if (nomeSeguradora.equalsIgnoreCase("sulamerica")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.sulamerica);
            } else if (nomeSeguradora.equalsIgnoreCase("maritima")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.maritima);
            } else if (nomeSeguradora.equalsIgnoreCase("porto")) {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.porto);
            } else {
                holder.seguradoraApoliceImageView.setImageResource(R.drawable.tokio);
            }
        }
    }
}