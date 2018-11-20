package clickclack.apothuaud.com.clickclack.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import clickclack.apothuaud.com.clickclack.R;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ClackViewHolder extends RecyclerView.ViewHolder {

    public TextView clack_id_label, clack_id_value, clack_attrs_label, clack_attrs_value;
    private Button btn_update_clack, btn_delete_clack;

    public ClackViewHolder(View itemView) {
        super(itemView);

        clack_id_label = itemView.findViewById(R.id.clack_id_label);
        clack_id_value = itemView.findViewById(R.id.clack_id_value);
        clack_attrs_label = itemView.findViewById(R.id.clack_attrs_label);
        clack_attrs_value = itemView.findViewById(R.id.clack_attrs_value);
        btn_update_clack = itemView.findViewById(R.id.clack_btn_update);
        btn_delete_clack = itemView.findViewById(R.id.clack_btn_delete);
    }
}
