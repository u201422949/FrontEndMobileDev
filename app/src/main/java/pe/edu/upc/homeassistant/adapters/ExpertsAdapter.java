package pe.edu.upc.homeassistant.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Expert;
import pe.edu.upc.homeassistant.model.Request;

public class ExpertsAdapter extends RecyclerView.Adapter<ExpertsAdapter.ViewHolder> {

    private List<Expert> experts;
    private List<Expert> selectedExperts;

    public ExpertsAdapter(List<Expert> experts, List<Expert> selectedExperts) {
        this.experts = experts;
        this.selectedExperts = selectedExperts;
    }

    @Override
    public ExpertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_experts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpertsAdapter.ViewHolder holder, int position) {
        holder.txtDescription.setText(experts.get(position).getMail());
        holder.txtName.setText(experts.get(position).getMail());

        if(selectedExperts.contains(experts.get(position)))
            holder.cardView.setBackgroundColor(ContextCompat.getColor(
                    holder.cardView.getContext(), R.color.list_item_selected_state));
        else
            holder.cardView.setBackgroundColor(ContextCompat.getColor(
                    holder.cardView.getContext(), R.color.list_item_normal_state));

    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setSelectedExperts(List<Expert> selectedExperts){
        this.selectedExperts = selectedExperts;
    }

    public void setExperts(List<Expert> experts){
        this.experts = experts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName, txtDescription;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
