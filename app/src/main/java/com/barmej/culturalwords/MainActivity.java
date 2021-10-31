package com.barmej.culturalwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // constants
    public static final String  APP_PREF_SAVE_LANGUAGE = "APP_PREF_SAVE_LANGUAGE";
    public static final String  APP_PREF_SAVE_WHEN_CHANGE_LANGUAGE = "APP_PREF_SAVE_WHEN_CHANGE_LANGUAGE";
    //
    private ImageView imageView;

    private int numberIndex = 1;
    private int isLanguageChange = 0;
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
        int number;
        SharedPreferences sharedPreferencesSaveLanguage = getSharedPreferences("APP_PREF_SAVE_LANGUAGE", MODE_PRIVATE);
        String language = sharedPreferencesSaveLanguage.getString("app_lang", "");
        if (!language.isEmpty()) {
            LoacaleHelper.setLocale(MainActivity.this, language);
        }
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view_question);
        isLanguageChange = getIntent().getIntExtra("younes", 0);
        if (isLanguageChange == 0){
            changeImageDrawable();
            isLanguageChange++;
        } else if (isLanguageChange > 0){
            numberIndex = sharedPreferencesSaveLanguage.getInt("1",0);
            changeImageDrawable();
        }
    }



    public void onButtonOpenAnswerClicked(View view){
        Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
        intent.putExtra("number_index", numberIndex);
        startActivity(intent);
    }
    public void onButtonChangeQuestionClicked(View view){
        Random random = new Random();
        numberIndex = random.nextInt(idDrawable.length);
        changeImageDrawable();

    }

    public void changeImageDrawable(){
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
                        intent.putExtra("younes", 1);
                        startActivity(intent);

                    }
                }).create();
        alertDialog.show();
    }
    private void saveLang(String lang){
        SharedPreferences sharedPreferencesSaveLanguage = getSharedPreferences(APP_PREF_SAVE_LANGUAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSaveLanguage.edit();
        editor.putString("app_lang", lang);
        editor.putInt("1", numberIndex);
        editor.apply();
    }
    public void onButtonShareQuestion(View view){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("share_image_drawable", idDrawable[numberIndex]);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("save_number_index", numberIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        numberIndex = savedInstanceState.getInt("save_number_index");
        changeImageDrawable();
    }
}
