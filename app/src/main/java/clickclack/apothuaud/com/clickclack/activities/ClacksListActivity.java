package clickclack.apothuaud.com.clickclack.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clickclack.apothuaud.com.clickclack.R;

public class ClacksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_list);

        final List<String> your_array_list = new ArrayList<String>();

        final ListView listView = findViewById(R.id.clacks_list_view);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://rec-clickclack-api.herokuapp.com/clacks";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject clack = response.getJSONObject(i);
                                // Get the current clack (json object) data
                                String id = clack.getString("_id");
                                your_array_list.add(id);
                            }

                            // This is the array adapter, it takes the context of the activity as a
                            // first parameter, the type of list view as a second parameter and your
                            // array as a third parameter.
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                    getContext(),
                                    android.R.layout.simple_list_item_1,
                                    your_array_list );

                            listView.setAdapter(arrayAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );


        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    private Context getContext() {
        return this;
    }
}
