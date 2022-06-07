package pl.pwr.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;
/*
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadLocale();
        addPreferencesFromResource(R.xml.preferences);

        //change language button
        Preference button = findPreference(getString(R.string.changeLanguage));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(SettingsActivity.this, "language change button works", Toast.LENGTH_SHORT).show();
                showChangeLanguageDialog();
                return true;
            }
        });


    }

    private void showChangeLanguageDialog(){
        final String[] listItems = {"English", "Polski", "Deutsch", "Français"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
       // mBuilder.setTitle("Change Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //English
                    setLocale("en");
                    recreate();
                }
                else if(i == 1){
                    //Polish
                    setLocale("pl");
                    recreate();
                }
                else if(i == 2){
                    //German
                    setLocale("de");
                    recreate();
                }
                else if(i == 3){
                    //French
                    setLocale("fr");
                    recreate();
                }

                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}

 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        //change language button
        Preference button = findPreference(getString(R.string.changeLanguage));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(SettingsActivity.this, "language change button works", Toast.LENGTH_SHORT).show();
                showChangeLanguageDialog();
                return true;
            }
        });


    }

    private void showChangeLanguageDialog(){
        final String[] listItems = {"English", "Polski", "Deutsch", "Français"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        // mBuilder.setTitle("Change Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //English
                    setAppLocale("en");
                recreate();

                }
                else if(i == 1){
                    //Polish
                    setAppLocale("pl");
                    recreate();
                }
                else if(i == 2){
                    //German
                    setAppLocale("de");
                    recreate();
                }
                else if(i == 3){
                    //French
                    setAppLocale("fr");
                    recreate();
                }

                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setAppLocale(String localeCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        }
        else{
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }
}