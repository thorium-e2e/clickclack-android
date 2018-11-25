package clickclack.apothuaud.com.clickclack.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.utils.API;
import clickclack.apothuaud.com.clickclack.viewholders.ClackFieldsView;

public class ClacksCreateActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "ClacksCreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_create);

        Log.i(TAG, "Create Activity");
    }

    // Button Reset => reset all fields
    public void onReset(View view) {

        Log.i(TAG, "Reset fields");

        LinearLayout fields = findViewById(R.id.create_clack_fields_layout);
        int fieldsChildsNb = fields.getChildCount();
        Log.i(TAG, "Found " + fieldsChildsNb + " fields pairs");

        // iterate on fields and reset
        for (int i = 0; i < fieldsChildsNb; i++) {
            // get fields
            View childFields = fields.getChildAt(i);
            EditText key = childFields.findViewById(R.id.create_clack_key);
            EditText value = childFields.findViewById(R.id.create_clack_value);

            // empty text values
            key.setText("");
            value.setText("");
        }

        Log.i(TAG, "Fields reset complete");
    }

    // Button Add fields => Append key/value fields to LinearLayout
    public void onAddFields(View view) {
        Log.i(TAG, "Add fields");

        LinearLayout fields = findViewById(R.id.create_clack_fields_layout);
        ClackFieldsView newFields = new ClackFieldsView(this);
        fields.addView(newFields.getView());

        Log.d(TAG, "fields added to view");
    }

    // Button Submit => post request to api
    public void onSubmit(View view) {

        Log.i(TAG, "Submit the form...");

        // initiate parameters as a Map
        Map<String, String> formParams = new HashMap<>();

        // get layout
        LinearLayout fields = findViewById(R.id.create_clack_fields_layout);
        int fieldsChildsNb = fields.getChildCount();
        Log.i(TAG, "Found " + fieldsChildsNb + " fields pairs");

        // iterate on fields
        for (int i = 0; i < fieldsChildsNb; i++) {
            // get child view
            View childFields = fields.getChildAt(i);
            // get text fields
            EditText key = childFields.findViewById(R.id.create_clack_key);
            EditText value = childFields.findViewById(R.id.create_clack_value);
            // add to parameters map
            formParams.put(key.getText().toString(), value.getText().toString());
        }

        Log.i(TAG, "formParams built:");
        Log.i(TAG, formParams.toString()); // OK

        // post request to api
        postData(formParams);

        // open List Activity
        Intent intent = new Intent(this, ClacksListActivity.class);

        startActivity(intent);
    }

    private void postData(final Map<String, String> params) {

        Log.i(TAG, "post form data");

        // get parameters as string
        JSONObject jsonBody = new JSONObject(params);
        final String mRequestBody = jsonBody.toString();

        // get url
        String url = API.getUri() + "/clacks";
        Log.d(TAG, "Url: " + url);

        // build request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // prepare request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG, "response received after post data");
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e(TAG, error.toString());
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

        // add request to request queue
        requestQueue.add(postRequest);
    }
}
