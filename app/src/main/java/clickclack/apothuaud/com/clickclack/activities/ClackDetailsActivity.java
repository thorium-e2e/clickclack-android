package clickclack.apothuaud.com.clickclack.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.models.Clack;
import clickclack.apothuaud.com.clickclack.utils.API;

public class ClackDetailsActivity extends AppCompatActivity {

    private static final String TAG = "Clack Details Activity";
    private static final int DIALOG_DELAY = 1200;

    private Bundle b;
    private Clack clack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clack_details);

        Log.i(TAG, "Open Activity");

        // get extras
        b = getIntent().getExtras();
        assert b != null;

        // get clack data from api
        getData();

        // set clack id from extra
        TextView clack_id = findViewById(R.id.clack_details_id_value);
        clack_id.setText(b.getString("clackId"));

        // add on click listeners to buttons
        Button updateButton = findViewById(R.id.clack_details_btn_update);
        Button deleteButton = findViewById(R.id.clack_details_delete_btn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdate(v);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete(v);
            }
        });
    }

    private void getData() {

        Log.i(TAG, "Getting clack data from API");

        // progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Clack data...");
        progressDialog.show();

        // build request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // get url
        String url = API.getUri() + "/clacks/" + b.getString("clackId");
        Log.d(TAG, "API url: " + url);

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Response received");

                        // close progress dialog
                        progressDialog.setMessage("Clack data received");

                        progressDialog.dismiss();

                        // build clack object
                        clack = new Clack();
                        clack.setId(b.getString("clackId"));
                        clack.setAttributes(response.toString());

                        // fill clack attrs view
                        TextView clackAttrs = findViewById(R.id.clack_details_attrs_value);
                        clackAttrs.setText(clack.getAttributes());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // close dialog
                        progressDialog.setMessage("ERROR getting data");

                        progressDialog.dismiss();

                        // log error
                        Log.e(TAG, error.toString());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void onUpdate(View view) {
        Log.i(TAG, "Update Clack");

        // Start Update Clack activity
        Intent intent = new Intent(view.getContext(), ClackUpdateActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void onDelete(View view) {
        Log.i(TAG, "Delete Clack");

        // progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Delete Clack...");
        progressDialog.show();

        // get clack id
        String id = b.getString("clackId");
        Log.i(TAG, "Clack ID: " + id);

        // build request queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        // build url
        String url = API.getUri() + "/clacks/" + id;
        Log.d(TAG, "URL: " + url);

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response received after delete request");
                        // close dialog
                        progressDialog.setMessage("Clack deleted");

                        progressDialog.dismiss();
                        // start List Activity
                        final Intent intent = new Intent(getContext(), ClacksListActivity.class);

                        Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, DIALOG_DELAY);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        // close dialog
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setMessage("ERROR sending delete request");
                            }
                        }, DIALOG_DELAY);

                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            public void run() {
                                //your code here
                                progressDialog.dismiss();
                            }
                        }, DIALOG_DELAY);  // 3000 milliseconds
                    }
                }
        );

        // add request to queue
        queue.add(dr);
    }

    // context helper
    private Context getContext() {
        return this;
    }
}
