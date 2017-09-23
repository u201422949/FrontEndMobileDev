package pe.edu.upc.homeassistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Skill;

public class CompetenciaListAdapter extends BaseAdapter{

    private List<Skill> list;
    private Context context;
    private LayoutInflater layoutInflater;


    public CompetenciaListAdapter(Context context, List<Skill> list ) {
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
    public Skill getItem(int position) {
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

        Skill competencia = getItem(position);

        if (competencia != null){
            holder.checkBox.setText(competencia.getCompetencia());
        }

        return convertView;
    }


    public class CompetenciaViewHolder {

        private CheckBox checkBox;

        public CompetenciaViewHolder(View view) {
            this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

}
