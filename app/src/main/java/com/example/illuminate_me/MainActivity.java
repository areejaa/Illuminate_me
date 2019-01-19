package com.example.illuminate_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private  Button takePic;
   private Button uploadPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to remove top bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //initiate buttons
        setbtnviews ();
        //button listeners
        //(1)
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call user.takePic or write the code directly
              takePic.setText("hi");
            }
        });
        //(2)
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call user.uploadPic or write the code directly
                uploadPic.setText("hi again");
            }
        });
    }

    private void setbtnviews (){

        takePic = (Button) findViewById(R.id.takePic_btn);
        uploadPic = (Button) findViewById(R.id.uploadPic_btn);


    }
}
