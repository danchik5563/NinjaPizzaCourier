package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danilwelter.ninjapizzacourier.R;

public class BugReportActivity extends AppCompatActivity {

    EditText et;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_report);

        et = findViewById(R.id.activity_bug_report_et_message);
        bt = findViewById(R.id.activity_bug_report_bt_send);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et.getText().toString();
                String address = "danchik5563@gmail.com";

                if (message.isEmpty()) {
                    //Toast.makeText(this,"Вы ничего не написали...", Toast.LENGTH_SHORT).show();
                } else {
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[]{address});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            "BugReport Ninja Pizza Courier");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            message);

                    startActivity(Intent.createChooser(emailIntent,
                            "Отправка письма..."));
                }
            }
        });
    }

}
