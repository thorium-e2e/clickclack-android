package clickclack.apothuaud.com.clickclack.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import clickclack.apothuaud.com.clickclack.viewholders.ClackFieldsView;

public class ClacksCreateActivity extends AppCompatActivity {

    private static final String TAG = "ClacksCreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_create);
    }

    public void onReset(View view) {

    }

    public void onAddFields(View view) {
        LinearLayout fields = findViewById(R.id.create_clack_fields_layout);
        ClackFieldsView newFields = new ClackFieldsView(this);
        fields.addView(newFields.getView());
    }

    public void onSubmit(View view) {
        Log.i(TAG, "Submit the form...");

        Map<String, String> formParams = new HashMap<>();

        LinearLayout fields = findViewById(R.id.create_clack_fields_layout);
        int fieldsChildsNb = fields.getChildCount();
        Log.i(TAG, "Found " + fieldsChildsNb + " fields pairs");

        for (int i = 0; i < fieldsChildsNb; i++) {
            View childFields = fields.getChildAt(i);
            EditText key = childFields.findViewById(R.id.create_clack_key);
            EditText value = childFields.findViewById(R.id.create_clack_value);
            formParams.put(key.getText().toString(), value.getText().toString());
        }

        Log.i(TAG, "formParams:");
        Log.i(TAG, formParams.toString()); // OK

        // post request to api
        postData(formParams);

        // open List Activity
        Intent intent = new Intent(this, ClacksListActivity.class);
        startActivity(intent);
    }

    private void postData(final Map<String, String> params) {

        JSONObject jsonBody = new JSONObject(params);
        final String mRequestBody = jsonBody.toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        String url = "https://rec-clickclack-api.herokuapp.com/clacks";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
