package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.holder;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Duda on 21/09/15.
 */
public class SeguradoraViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    public TextView nomeSeguradoraTextView;
    public ImageView logoSeguradoraImageView;

    public SeguradoraViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        nomeSeguradoraTextView = (TextView) itemView.findViewById(R.id.nomeSeguradoraTextView);
        logoSeguradoraImageView = (ImageView) itemView.findViewById(R.id.logoSeguradoraImageView);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
