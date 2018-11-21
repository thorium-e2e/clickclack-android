package clickclack.apothuaud.com.clickclack.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
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
import clickclack.apothuaud.com.clickclack.utils.API;

public class ClacksListActivity extends AppCompatActivity {

    private static final String TAG = "ClacksListActivity";
    private static final int DIALOG_DELAY = 1200;

    private List<Clack> clackList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_list);

        Log.i(TAG, "Create Activity");

        // get recycler view
        RecyclerView cList = findViewById(R.id.clack_list);
        // initiate clacks list
        clackList = new ArrayList<>();
        // get clack view adapter
        adapter = new ClackAdapter(getApplicationContext(), clackList);

        // get linear layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cList.getContext(), linearLayoutManager.getOrientation());

        // clacks list view settings
        cList.setHasFixedSize(true);
        cList.setLayoutManager(linearLayoutManager);
        cList.addItemDecoration(dividerItemDecoration);
        cList.setAdapter(adapter);

        // get data from api
        getData();
    }

    private void getData() {
        Log.i(TAG, "Request data from API");

        // show progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Request clacks...");
        progressDialog.show();

        String url = API.getUri() + "/clacks";
        Log.d(TAG, "URL: " + url);

        // build request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Response received");

                // iterate on response
                for (int i = 0; i < response.length(); i++) {
                    try {

                        // build json object (clack)
                        JSONObject jsonObject = response.getJSONObject(i);

                        // build clack object
                        Clack clack = new Clack();
                        clack.setId(jsonObject.getString("_id"));
                        clack.setAttributes(jsonObject.toString());

                        // add clack to list
                        clackList.add(clack);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // close dialog
                        progressDialog.setMessage("ERROR getting data");

                        progressDialog.dismiss();
                    }
                }

                // notify adapter of a change in the list
                adapter.notifyDataSetChanged();
                // close dialog
                progressDialog.setMessage("Data received");

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

                progressDialog.setMessage("Error");

                progressDialog.dismiss();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // add request to request queue
        requestQueue.add(jsonArrayRequest);
    }

    public void onAddNew(View view) {
        Log.i(TAG, "Add button");

        // start Create Clack Activity
        Intent intent = new Intent(this, ClacksCreateActivity.class);
        startActivity(intent);
    }
}
