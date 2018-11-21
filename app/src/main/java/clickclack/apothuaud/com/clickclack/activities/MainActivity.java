package clickclack.apothuaud.com.clickclack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import clickclack.apothuaud.com.clickclack.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Create Activity");
    }

    public void onClacks(View view) {

        Log.i(TAG, "Clacks button");

        // Start Clacks List Activity
        Intent intent = new Intent(this, ClacksListActivity.class);
        startActivity(intent);
    }
}
