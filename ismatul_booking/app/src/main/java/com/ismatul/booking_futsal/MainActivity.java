package com.ismatul.booking_futsal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

//import java.text.DateFormat;
import android.text.format.DateFormat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.ismatul.booking_futsal.app.AppController;
import com.ismatul.booking_futsal.util.Server;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView tvnama,lapangan,tvtanggal,tvjam,enama;
    Button btnTanggal,btnJam, btnPesan;
    SimpleDateFormat dateFormat;
    String[] lapang;
    int index;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        pd = new ProgressDialog(this);

        tvnama = (TextView) findViewById(R.id.txt_nama);
        enama = (TextView) findViewById(R.id.tx_nama);
        tvtanggal = (TextView) findViewById(R.id.txt_tgl);
        tvjam = (TextView) findViewById(R.id.txt_Jam);
        lapangan = (TextView) findViewById(R.id.lap);
        btnTanggal = (Button) findViewById(R.id.btn_Tanggal);
        btnJam = (Button) findViewById(R.id.btn_Jam);
        btnPesan =(Button) findViewById(R.id.btn_Pesan);
        Spinner s1 = (Spinner) findViewById(R.id.spinner);

        lapang = getResources().getStringArray(R.array.lapangan_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lapang);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strnama = enama.getText().toString().trim();
                String strtanggal = tvtanggal.getText().toString().trim();
                String strjam = tvjam.getText().toString().trim();
                String strlapangan = lapang[index].toString().trim();
                if (strtanggal.matches("Tanggal") || strjam.matches("Jam")) {
                    Toast.makeText(MainActivity.this, "harap di isi semua", Toast.LENGTH_SHORT).show();
                } else {
                    if (!strnama.isEmpty() && !strtanggal.isEmpty() &&!strjam.isEmpty() && !strlapangan.isEmpty()) {
                        simpanData(strnama, strtanggal, strjam, strlapangan);
                       Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                    }

//                    pesan.putExtra("nama", strNama);
//                    pesan.putExtra("lapangan", lapang[index]);
//                    pesan.putExtra("tanggal", strTanggal);
//                    pesan.putExtra("jam", strJam);

                }
            }

        });
    }

    public void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth );
                tvtanggal.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    public void showTimeDialog(){
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvjam.setText(hourOfDay+":"+minute);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    private void simpanData(final String strnama, final String strtanggal, final String strjam, final String strlapangan ) {
        String url_simpan = Server.URL +"simpan.php";

        String tag_json = "tag_json";

        pd.setCancelable(false);
        pd.setMessage("Menyimpan...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response.toString());
                //hideDialog();

                try {
                    JSONObject jObject = new JSONObject(response);
                    String cetak = jObject.getString("cetak");
                    String hasil = jObject.getString("result");
                    if (hasil.equalsIgnoreCase("true")) {
                        Toast.makeText(MainActivity.this, cetak, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, cetak, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("nama", strnama);
                param.put("tanggal", strtanggal);
                param.put("jam", strjam);
                param.put("lapangan", strlapangan);
                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json);
    }

    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        finish();
        startActivity(intent);
    }
}
