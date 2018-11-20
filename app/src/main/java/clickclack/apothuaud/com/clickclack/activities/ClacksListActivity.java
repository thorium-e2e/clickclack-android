package clickclack.apothuaud.com.clickclack.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.adapters.ClackAdapter;
import clickclack.apothuaud.com.clickclack.models.Clack;

public class ClacksListActivity extends AppCompatActivity {

    private List<Clack> clackList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_list);

        RecyclerView cList = findViewById(R.id.clack_list);
        clackList = new ArrayList<>();
        adapter = new ClackAdapter(getApplicationContext(), clackList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cList.getContext(), linearLayoutManager.getOrientation());

        cList.setHasFixedSize(true);
        cList.setLayoutManager(linearLayoutManager);
        cList.addItemDecoration(dividerItemDecoration);
        cList.setAdapter(adapter);

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "https://rec-clickclack-api.herokuapp.com/clacks";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Clack clack = new Clack();
                        clack.setId(jsonObject.getString("_id"));
                        clack.setAttributes(jsonObject.toString());

                        clackList.add(clack);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void onAddNew(View view) {
        Intent intent = new Intent(this, ClacksCreateActivity.class);
        startActivity(intent);
    }
}
