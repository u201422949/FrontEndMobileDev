package pe.edu.upc.homeassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

/**
 * Created by paul.cabrera on 08/09/2017.
 */

public class CompetenciaListAdapter extends BaseAdapter{

    private List<Competencia> list;
    Context context;
    LayoutInflater layoutInflater;


    public CompetenciaListAdapter(Context context, List<Competencia> list ) {
        super();
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Competencia getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CompetenciaViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_competencia, parent, false);
            holder = new CompetenciaViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (CompetenciaViewHolder) convertView.getTag();
        }

        Competencia competencia = getItem(position);

        if (competencia != null){
            holder.checkBox.setText(competencia.getCompetencia());
        }

        return convertView;
    }


    class CompetenciaViewHolder {

        public  CheckBox checkBox;

        public CompetenciaViewHolder(View view) {
            this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

}
