package com.example.mobiledb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

// UI 세팅
public class MainActivity extends AppCompatActivity {

    final int CREATE_DIET = 0;
    int diet_order = 1;

    TextView textview1Breakfast = findViewById(R.id.textview1Breakfast);
    TextView textview1Lunch = findViewById(R.id.textview1Lunch);
    TextView textview1Dinner = findViewById(R.id.textview1Dinner);

    TextView textview2Breakfast = findViewById(R.id.textview2Breakfast);
    TextView textview2Lunch = findViewById(R.id.textview2Lunch);
    TextView textview2Dinner = findViewById(R.id.textview2Dinner);

    TextView textview3Breakfast = findViewById(R.id.textview3Breakfast);
    TextView textview3Lunch = findViewById(R.id.textview3Lunch);
    TextView textview3Dinner = findViewById(R.id.textview3Dinner);

    TextView textview4Breakfast = findViewById(R.id.textview4Breakfast);
    TextView textview4Lunch = findViewById(R.id.textview4Lunch);
    TextView textview4Dinner = findViewById(R.id.textview4Dinner);

    TextView textview5Breakfast = findViewById(R.id.textview5Breakfast);
    TextView textview5Lunch = findViewById(R.id.textview5Lunch);
    TextView textview5Dinner = findViewById(R.id.textview5Dinner);

    TextView textview6Breakfast = findViewById(R.id.textview6Breakfast);
    TextView textview6Lunch = findViewById(R.id.textview6Lunch);
    TextView textview6Dinner = findViewById(R.id.textview6Dinner);

    TextView textview7Breakfast = findViewById(R.id.textview7Breakfast);
    TextView textview7Lunch = findViewById(R.id.textview7Lunch);
    TextView textview7Dinner = findViewById(R.id.textview7Dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ControlDB control = new ControlDB();
        control.SetAllToNotUsed("밥");
        control.SetAllToNotUsed("반찬(메인)");
        control.SetAllToNotUsed("반찬(사이드)");
        Intent intent = new Intent(this, CreateDiet.class);
        startActivityForResult(intent, CREATE_DIET);

    }

    // 식단정보 받아와서 GridLayout에 세팅
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_DIET) {
            if (resultCode == RESULT_OK) {
                // 데이터 받아오기
                String received_breakfast_info = data.getStringExtra("BREAKFAST_INFO");
                String received_lunch_info = data.getStringExtra("LUNCH_INFO");
                String received_dinner_info = data.getStringExtra("DINNER_INFO");
                String received_breakfast_nutritions = data.getStringExtra("BREAKFAST_NUTRITIONS");
                String received_lunch_nutritions = data.getStringExtra("LUNCH_NUTRITIONS");
                String received_dinner_nutritions = data.getStringExtra("DINNER_NUTRITIONS");

                Log.d("test", "작업완료");
//                Log.d("test", "received_breakfast_info: " + received_breakfast_info);
//                Log.d("test", "received_lunch_info: " + received_breakfast_info);
//                Log.d("test", "received_dinner_info: " + received_breakfast_info);
//                Log.d("test", "received_breakfast_nutritions: " + received_breakfast_nutritions);
//                Log.d("test", "received_lunch_nutritions: " + received_lunch_nutritions);
//                Log.d("test", "received_dinner_nutritions: " + received_dinner_nutritions);

                // 데이터 UI에 세팅하기
                switch(diet_order){
                    case 1:
                        textview1Breakfast.setText(received_breakfast_info);
                        textview1Lunch.setText(received_lunch_info);
                        textview1Dinner.setText(received_dinner_info);
                        break;

                    case 2:
                        textview2Breakfast.setText(received_breakfast_info);
                        textview2Lunch.setText(received_lunch_info);
                        textview2Dinner.setText(received_dinner_info);
                        break;

                    case 3:
                        textview3Breakfast.setText(received_breakfast_info);
                        textview3Lunch.setText(received_lunch_info);
                        textview3Dinner.setText(received_dinner_info);
                        break;

                    case 4:
                        textview4Breakfast.setText(received_breakfast_info);
                        textview4Lunch.setText(received_lunch_info);
                        textview4Dinner.setText(received_dinner_info);
                        break;

                    case 5:
                        textview5Breakfast.setText(received_breakfast_info);
                        textview5Lunch.setText(received_lunch_info);
                        textview5Dinner.setText(received_dinner_info);
                        break;

                    case 6:
                        textview6Breakfast.setText(received_breakfast_info);
                        textview6Lunch.setText(received_lunch_info);
                        textview6Dinner.setText(received_dinner_info);
                        break;

                    case 7:
                        textview7Breakfast.setText(received_breakfast_info);
                        textview7Lunch.setText(received_lunch_info);
                        textview7Dinner.setText(received_dinner_info);
                        break;
                }

                if(diet_order == 7)
                    diet_order = 1;
                else
                    diet_order = diet_order + 1;
            }
        }
    }
}

