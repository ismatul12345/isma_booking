package com.ismatul.booking_futsal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Febi on 5/8/2018.
 */

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_second);

        String nm = getIntent().getStringExtra("nama");
        String lap = getIntent().getStringExtra("lapangan");
        String tgl = getIntent().getStringExtra("tanggal");
        String jam = getIntent().getStringExtra("jam");

        TextView inama = (TextView)findViewById(R.id.t_nama);
        TextView itanggal = (TextView)findViewById(R.id.t_tgl);
        TextView ijam = (TextView)findViewById(R.id.t_jam);
        TextView ilpg = (TextView)findViewById(R.id.t_lap);

        inama.setText(""+nm);
        itanggal.setText(""+tgl);
        ijam.setText(""+jam);
        ilpg.setText(""+lap);
    }
}
