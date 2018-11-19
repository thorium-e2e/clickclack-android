package clickclack.apothuaud.com.clickclack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import clickclack.apothuaud.com.clickclack.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClacks(View view) {
        Intent intent = new Intent(this, ClacksListActivity.class);
        startActivity(intent);
    }
}
