package com.barmej.culturalwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    private int numberIndex = 1;
    private int []idDrawable = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String language = sharedPreferences.getString("app_lang", "");
        if (language != "") {
            LoacaleHelper.setLocale(MainActivity.this, language);
        }
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view_question);
    }



    public void onButtonOpenAnswerClicked(View view){
        Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
        intent.putExtra("number_index", numberIndex);
        startActivity(intent);
    }
    public void onButtonChangeQuestionClicked(View view){
        Random random = new Random();
        numberIndex = random.nextInt(12);
        Drawable drawable = ContextCompat.getDrawable(this,idDrawable[numberIndex]);
        imageView.setImageDrawable(drawable);

    }

    public void  showLanguageDialog(View view){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        saveLang(language);
                        LoacaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }).create();
        alertDialog.show();
    }
    private void saveLang(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang", lang);
        editor.apply();
    }
    public void onButtonShareQuestion(View view){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("share_image_drawable", idDrawable[numberIndex]);
        startActivity(intent);
    }



}
