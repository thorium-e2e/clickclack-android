package clickclack.apothuaud.com.clickclack.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.activities.ClacksListActivity;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ClackViewHolder extends RecyclerView.ViewHolder {

    public TextView clack_id_label, clack_id_value, clack_attrs_label, clack_attrs_value;
    private Button btn_update_clack, btn_delete_clack;

    public ClackViewHolder(final View itemView) {
        super(itemView);

        clack_id_label = itemView.findViewById(R.id.clack_id_label);
        clack_id_value = itemView.findViewById(R.id.clack_id_value);
        clack_attrs_label = itemView.findViewById(R.id.clack_attrs_label);
        clack_attrs_value = itemView.findViewById(R.id.clack_attrs_value);
        btn_update_clack = itemView.findViewById(R.id.clack_btn_update);
        btn_delete_clack = itemView.findViewById(R.id.clack_btn_delete);

        btn_delete_clack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Delete Clack Button", "Button clicked");
                String id = clack_id_value.getText().toString();
                Log.i("Delete Clack Button", "Clack ID: " + id);

                RequestQueue queue = Volley.newRequestQueue(itemView.getContext());

                String url = "https://rec-clickclack-api.herokuapp.com/clacks/" + id;

                StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Intent intent = new Intent(itemView.getContext(), ClacksListActivity.class);
                                itemView.getContext().startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error.

                            }
                        }
                );
                queue.add(dr);
            }
        });
    }
}
