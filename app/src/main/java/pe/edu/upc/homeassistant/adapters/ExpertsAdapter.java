package pe.edu.upc.homeassistant.adapters;

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
    private RecyclerViewClickListener listener;

    public ExpertsAdapter(List<Expert> experts, RecyclerViewClickListener listener) {
        this.experts = experts;
        this.listener = listener;
    }

    @Override
    public ExpertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_experts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpertsAdapter.ViewHolder holder, int position) {
        holder.imgThumbnail.setImageResource(R.mipmap.ic_launcher);
        holder.txtDescription.setText(experts.get(position).getMail());
        holder.txtTitle.setText(experts.get(position).getMail());
    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgThumbnail;
        private TextView txtTitle, txtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.recyclerViewListClicked(view, getLayoutPosition());
        }
    }
}
