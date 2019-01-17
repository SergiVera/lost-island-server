package dsa.eetac.upc.edu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    private List<GameObject> data;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        // public Button btn;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(android.R.id.text1);
            // btn = v.findViewById(android.R.id.button1);
        }
    }

    public AdapterRecycler(List<GameObject> data) {
        this.data = data;
    }

    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_selectable_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolder holder, int position) {
        GameObject obj = data.get(position);
        holder.text.setText(obj.getName());
        holder.itemView.setTag(obj.getCost());
        // holder.btn.setText("edit");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}


