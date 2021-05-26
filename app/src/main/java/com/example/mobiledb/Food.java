package com.example.mobiledb;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food {
    private double protein;
    private double carbo;
    private double fat;
    private String name;
    private boolean is_used = true;
    private int num = 0;
    // category 따라서 랜덤으로 Firebase에서 각 영양성분 받아와서 변수에 할당
    public Food(String category, final Callback callback) {
        // 랜덤넘버 생성 , 사용여부가 1이면 랜덤넘버 다시 생성
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("foods").child(category);
//        Log.d("test", String.valueOf(ref));
//        callback.success("Food 생성");

//            Log.d("test", "여기까지 진행됨2 " + String.valueOf(finalNum));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                callback.success("DB 진입");
                while(is_used) {
                    if (category == "밥")
                        setNum((int) (Math.random() * 26));
                    else if (category == "반찬(메인)")
                        setNum((int) (Math.random() * 458));
                    else if (category == "반찬(사이드)")
                        setNum((int) (Math.random() * 359));
                    else{
                        Log.d("error", "Food_category_error");
                        return;
                    }
                    int finalNum = num;
                    DatabaseReference numRef = ref.child(String.valueOf(finalNum)).child("사용여부");
                    if(snapshot.child(String.valueOf(finalNum)).child("사용여부").getValue().toString().equals("0")) {
                        setIs_used();
                        numRef.setValue("1");
                    }
                }
//                    Log.d("test", "Food_여기까지 진행됨3, is_used: " + String.valueOf(is_used));
//                callback.success("Food_num: " + String.valueOf(num));
                // 영양성분 불러와서 변수에 할당해주기
                ref.child(String.valueOf(num)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        callback.success("내부DB 진입");
                        setCarbo(Double.parseDouble(snapshot.child("탄수화물(g)").getValue().toString()));
                        setProtein(Double.parseDouble(snapshot.child("단백질(g)").getValue().toString()));
                        setFat(Double.parseDouble(snapshot.child("지방(g)").getValue().toString()));
                        setName(snapshot.child("식품명").getValue().toString());
//                Log.d("test", "예시음식 이름: " + String.valueOf(getName()));
//                Log.d("test", "예시음식 탄수화물: " + String.valueOf(getCarbo()));
//                Log.d("test", "예시음식 단백질: " + String.valueOf(getProtein()));
//                Log.d("test", "예시음식 지방: " + String.valueOf(getFat()));
                        String text = "이름: " + getName()
                                + ", 탄수화물: "+String.valueOf(getCarbo())
                                +", 단백질: "+String.valueOf(getProtein())
                                +", 지방: "+String.valueOf(getFat());
                        callback.success(text, returnThis());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
        });



    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbo(double carbo) {
        this.carbo = carbo;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_used() {
        this.is_used = false;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbo() {
        return carbo;
    }

    public String getName() {
        return name;
    }

    public Food returnThis(){
        return this;
    }
}

