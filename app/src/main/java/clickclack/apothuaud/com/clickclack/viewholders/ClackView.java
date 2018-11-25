package clickclack.apothuaud.com.clickclack.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import clickclack.apothuaud.com.clickclack.activities.ClackDetailsActivity;
import clickclack.apothuaud.com.clickclack.activities.ClackUpdateActivity;
import clickclack.apothuaud.com.clickclack.activities.ClacksListActivity;
import clickclack.apothuaud.com.clickclack.utils.API;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ClackView{

    private Context context;

    public TextView clack_id_label, clack_id_value, clack_attrs_label, clack_attrs_value;
    private Button btn_update_clack, btn_delete_clack, btn_details_clack;

    private View view;

    public ClackView(Context context) {
        this.context = context;
        init();
    }

    public View getView() {
        return view;
    }

    @SuppressLint("InflateParams")
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.row_clack_item, null);

        this.clack_id_label = view.findViewById(R.id.clack_id_label);
        this.clack_id_value = view.findViewById(R.id.clack_id_value);
        this.clack_attrs_label = view.findViewById(R.id.clack_attrs_label);
        this.clack_attrs_value = view.findViewById(R.id.clack_attrs_value);
        this.btn_details_clack = view.findViewById(R.id.clack_details_button);
        this.btn_update_clack = view.findViewById(R.id.clack_btn_update);
        this.btn_delete_clack = view.findViewById(R.id.clack_btn_delete);

        this.btn_details_clack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("clackId", clack_id_value.getText().toString());
                Intent intent = new Intent(view.getContext(), ClackDetailsActivity.class);
                intent.putExtras(b);
                view.getContext().startActivity(intent);
            }
        });

        this.btn_update_clack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("clackId", clack_id_value.getText().toString());
                Intent intent = new Intent(view.getContext(), ClackUpdateActivity.class);
                intent.putExtras(b);
                view.getContext().startActivity(intent);
            }
        });

        this.btn_delete_clack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Delete Clack Button", "Button clicked");
                String id = clack_id_value.getText().toString();
                Log.i("Delete Clack Button", "Clack ID: " + id);

                RequestQueue queue = Volley.newRequestQueue(view.getContext());

                String url = API.getUri() + "/clacks/" + id;

                StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Intent intent = new Intent(view.getContext(), ClacksListActivity.class);
                                view.getContext().startActivity(intent);
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
