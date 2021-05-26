package com.example.mobiledb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

// 하루치 식단 만듬
// CreateDiet부르는 Activity에서 따로 밥, 반찬들 count 세주고 사용여부 초기화해줘야함
public class CreateDiet extends AppCompatActivity{

    private int what_time_rice = 0;
    private int what_time_maindish = 0;
    private int what_time_sidedish = 0;
    private int flag_protein = 10;
    private int flag_carbo = 10;
    private int flag_fat = 10;
    private double target_protein = 0;
    private double target_carbo = 0;
    private double target_fat = 0;

    TextView breakfast;
    TextView lunch;
    TextView dinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_creatediet);
        // 하루치 식단을 GridLayout에 표시, 이는 상위 Activity에서 LinearLayout의 요소로 사용될 것임.

        Log.d("test","CreateDiet 액티비티 불러오기 성공");


        ControlDB controlDB = new ControlDB();

        // $하루 식단생성기 Start
        OneDiet[] daydiet = new OneDiet[3];

        // Callback을 하나로 했더니 밥, 반찬(메인), 반찬(사이드)의 각 Food 사이에 동기화가 안되서 오류가 나서 그냥 각각 불러오는 index를 따로 줬음
        Callback callback_rice = new Callback(){
            @Override
            public void success(String str, Food food){
                Log.d("callback","Callback result: " + str);
                Log.d("callback", "parameter_what_time: "+what_time_rice);
                daydiet[what_time_rice].plusFood(food);
                increaseTarget(food);
                Log.d("callback", "daydiet[0]: "+daydiet[0].returnAll());
                Log.d("callback", "daydiet[1]: "+daydiet[1].returnAll());
                Log.d("callback", "daydiet[2]: "+daydiet[2].returnAll());

                if(what_time_rice >= 2) {
                    what_time_rice = 0;
                } else {
                    what_time_rice = what_time_rice+1;
                }
            }
            @Override
            public void fail(String message){
                Log.d("test", message);
            }
        };

        Callback callback_maindish = new Callback(){
            @Override
            public void success(String str, Food food){
                Log.d("callback", "Callback result: " + str);
                Log.d("callback", "parameter_what_time: " + what_time_maindish);
                daydiet[what_time_maindish].plusFood(food);
                increaseTarget(food);
                Log.d("callback", "daydiet[0]: " + daydiet[0].returnAll());
                Log.d("callback", "daydiet[1]: " + daydiet[1].returnAll());
                Log.d("callback", "daydiet[2]: " + daydiet[2].returnAll());
                if (what_time_maindish >= 2) {
                    what_time_maindish = 0;
                } else {
                    what_time_maindish = what_time_maindish + 1;
                }
            }
            @Override
            public void fail(String message){
                Log.d("test", message);
            }
        };

        Callback callback_sidedish = new Callback(){
            @Override
            public void success(String str, Food food){
                if((target_carbo < flag_carbo) && (target_protein < flag_protein) && (target_fat < flag_fat)) {
                    Log.d("callback","Callback result: " + str);
                    Log.d("callback", "parameter_what_time: "+what_time_sidedish);
                    daydiet[what_time_sidedish].plusFood(food);
                    increaseTarget(food);
                    Log.d("callback", "daydiet[0]: "+daydiet[0].returnAll());
                    Log.d("callback", "daydiet[1]: "+daydiet[1].returnAll());
                    Log.d("callback", "daydiet[2]: "+daydiet[2].returnAll());

                    if(what_time_sidedish >= 2) {
                        what_time_sidedish = 0;
                    } else {
                        what_time_sidedish = what_time_sidedish+1;
                    }
                }
                else{
                    Intent intent = getIntent();
                    intent.putExtra("BREAKFAST_INFO", daydiet[0].returnDietToString());
                    intent.putExtra("BREAKFAST_NUTRITIONS", daydiet[0].returnNutritions());
                    intent.putExtra("LUNCH_INFO", daydiet[1].returnDietToString());
                    intent.putExtra("LUNCH_NUTRITIONS", daydiet[1].returnNutritions());
                    intent.putExtra("DINNER_INFO", daydiet[2].returnDietToString());
                    intent.putExtra("DINNER_NUTRITIONS", daydiet[2].returnNutritions());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
            @Override
            public void fail(String message){
                Log.d("test", message);
            }
        };

        // 아침, 점심, 저녁 음식 채우기
        for(int meals=0; meals<3; meals++) {
            // 세 영양성분 중 하나라도 권장섭취량을 넘어가면 out
            daydiet[meals] = new OneDiet();
            Food food = new Food("밥", callback_rice);
            Food main_dish = new Food("반찬(메인)", callback_maindish);
            Food side_dish = new Food("반찬(사이드)", callback_sidedish);
            // callback_sidedish에 권장섭취량 초과하면 그만 추가하는 기능 추가하기
            // 추가할 음식(밥) firebase에서 받아와서 위 변수에 저장
        }

        // 아침, 점심, 저녁 나머지 권장섭취량만큼 반찬(사이드) 채우기
//        while(true){
//            Food side_dish = new Food("반찬(사이드)", callback_maindish);
//        }
    }

    public void increaseTarget(Food food){
        this.target_carbo = this.target_carbo + food.getCarbo();
        this.target_protein = this.target_protein + food.getProtein();
        this.target_fat = this.target_fat + food.getFat();
    }

}

