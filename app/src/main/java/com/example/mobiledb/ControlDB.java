package com.example.mobiledb;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// DB-foods 내 데이터 관리
// 반찬(사이드) - 358개, 반찬(메인) - 457개, 밥 - 25개
public class ControlDB {
    private FirebaseDatabase instance;
    private DatabaseReference ref;

    ControlDB(){
        instance = FirebaseDatabase.getInstance();
        ref = instance.getReference("foods");
    }

    // 카테고리까지 reference로 받아야함
    public void SetAllToNotUsed(String category){
        int count = 0;
        if(category == "밥")
            count = 25;
        else if(category == "반찬(메인)")
            count = 457;
        else if(category == "반찬(사이드)")
            count = 358;

        int finalCount = count;
        for(int i=0; i<finalCount; i++){
            ref.child(category).child(String.valueOf(i)).child("사용여부").setValue("0");
        }
        ref.child(category).child("CalledTimes").setValue("0");
    }
}
