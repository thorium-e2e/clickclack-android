package clickclack.apothuaud.com.clickclack.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import clickclack.apothuaud.com.clickclack.R;
import clickclack.apothuaud.com.clickclack.controllers.ClackAdapter;
import clickclack.apothuaud.com.clickclack.models.Clack;

public class ClacksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clacks_list);

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
                            ListView listView = findViewById(R.id.clacks_list_view);
                            ArrayList<Clack> clacks = new ArrayList<>();
                            ClackAdapter arrayAdapter = new ClackAdapter(
                                    getContext(),
                                    clacks );
                            listView.setAdapter(arrayAdapter);
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject rclack = response.getJSONObject(i);
                                // Get the current clack (json object) data
                                String id = rclack.getString("_id");
                                String attributes = rclack.toString();
                                Clack clack = new Clack(id, attributes);
                                arrayAdapter.add(clack);
                            }
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
