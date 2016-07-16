package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.adapters;

import android.content.Context;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by turbiani on 30/07/15.
 */
public class RealmSeguradoListAdapter extends RealmBaseAdapter<Segurado> implements ListAdapter {

    private static class ViewHolder {
        TextView student;
    }

    public RealmSeguradoListAdapter(Context context, int resId,
                                    RealmResults<Segurado> realmResults,
                                    boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.student = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Segurado item = realmResults.get(position);
        viewHolder.student.setText(item.getNome() + "==" + item.getEmail());
        return convertView;
    }

    public RealmResults<Segurado> getRealmResults() {
        return realmResults;
    }
}
