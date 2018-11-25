package clickclack.apothuaud.com.clickclack.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
import clickclack.apothuaud.com.clickclack.models.Clack;
import clickclack.apothuaud.com.clickclack.utils.API;
import clickclack.apothuaud.com.clickclack.viewholders.ClackView;

public class ClacksListActivity extends AppCompatActivity {

    private static final String TAG = "ClacksListActivity";

    private List<Clack> clackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_list);

        Log.i(TAG, "Create Activity");

        // initiate clacks list
        clackList = new ArrayList<>();

        // get data from api
        getData();
    }

    private void setView() {
        for (int i = 0; i < clackList.size(); i++) {
            Log.i(TAG, "Adding new clack item view");
            LinearLayout cList = findViewById(R.id.clack_list);
            // init clack view
            ClackView newView = new ClackView(this);
            // set clack view
            newView.clack_id_value.setText(clackList.get(i).getId());
            newView.clack_attrs_value.setText(clackList.get(i).getAttributes());
            // add to scroll view
            cList.addView(newView.getView());
        }
    }

    private void getData() {
        Log.i(TAG, "Request data from API");

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
                    }
                }

                // add clack items in view
                setView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
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

    public Context getContext() {
        return this;
    }
}
