package clickclack.apothuaud.com.clickclack.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.models.Clack;
import clickclack.apothuaud.com.clickclack.viewholders.ClackViewHolder;

public class ClackAdapter extends RecyclerView.Adapter<ClackViewHolder> {

    private Context context;
    private List<Clack> list;

    public ClackAdapter(Context context, List<Clack> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ClackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_clack_item, parent, false);
        return new ClackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClackViewHolder holder, int position) {
        Clack clack = list.get(position);

        holder.clack_id_value.setText(clack.getId());
        holder.clack_attrs_value.setText(clack.getAttributes());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
