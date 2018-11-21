package clickclack.apothuaud.com.clickclack.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import clickclack.apothuaud.com.clickclack.R;

public class ClackUpdateActivity extends AppCompatActivity {

    private static final String TAG = "Clack Update Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clack_update);

        Log.i(TAG, "Create Activity");
    }
}
