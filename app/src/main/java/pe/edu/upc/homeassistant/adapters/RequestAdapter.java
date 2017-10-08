package pe.edu.upc.homeassistant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.upc.homeassistant.R;
import pe.edu.upc.homeassistant.model.Request;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<Request> requestList;
    private RecyclerViewClickListener listener;

    public RequestAdapter(List<Request> requestList, RecyclerViewClickListener listener) {
        this.requestList = requestList;
        this.listener = listener;
    }


    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.ViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.txtDescription.setText(request.getDescription());
        holder.txtTitle.setText(request.getSubject());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txtTitle, txtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.recyclerViewListClicked(view, getLayoutPosition());
        }
    }
}
