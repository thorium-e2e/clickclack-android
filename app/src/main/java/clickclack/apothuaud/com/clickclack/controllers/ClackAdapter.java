package clickclack.apothuaud.com.clickclack.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.models.Clack;

public class ClackAdapter extends ArrayAdapter<Clack> {

    public ClackAdapter(Context context, ArrayList<Clack> clacks) {
        super(context, 0, clacks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Clack clack = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_clack_item, parent, false);
        }

        TextView clack_attrs_value = convertView.findViewById(R.id.textView_clack_attrs);
        TextView clack_id_value = convertView.findViewById(R.id.textView_clack_id);

        assert clack != null;
        clack_id_value.setText(clack.getId());
        clack_attrs_value.setText(clack.getAttributes());

        return convertView;
    }
}
