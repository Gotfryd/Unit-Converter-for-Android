package pl.pwr.unitconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //navi menu
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private Spinner inputSpinner;
    private Spinner outputSpinner;
    private EditText valueToConvertEditText;
    private Button convertButton;
    private TextView resultTextView;
    private String currentCategory = "length"; //inicjalizuje teraz poniewaz bedzie to domyslna kategoria od razu wyswietlona po wlaczeniu aplikacji
    private TextView categoryTextView;

    Unit unitToConvert;
    Unit unitConverted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //tworzenie obiektow jednostek
        List<Unit> units = new ArrayList<Unit>();
        //jednostki dlugosci
        units.add(new Unit("milimetr [mm]", (float) 1000, "length"));
        units.add(new Unit("centymetr [cm]", (float) 100, "length"));
        units.add(new Unit("decymetr [dm]", (float) 10, "length"));
        units.add(new Unit("metr [m]", (float) 1, "length"));
        units.add(new Unit("kilometr [km]", (float) 0.001, "length"));
        units.add(new Unit("cal [in]", (float) 39.3700787, "length"));
        units.add(new Unit("stopa [ft]", (float) 3.28084, "length"));
        units.add(new Unit("jard [yd]", (float) 1.093613, "length"));
        units.add(new Unit("mila [mi]", (float) 0.00062137, "length"));
        //jednostki masy
        units.add(new Unit("miligram [mg]", (float) 1000000, "mass"));
        units.add(new Unit("gram [g]", (float) 1000, "mass"));
        units.add(new Unit("dekagram [dkg]", (float) 100, "mass"));
        units.add(new Unit("kilogram [kg]", (float) 1, "mass"));
        units.add(new Unit("tona [T]", (float) 0.001, "mass"));
        units.add(new Unit("uncja [oz]", (float) 35.273962, "mass"));
        units.add(new Unit("funt [lb]", (float) 2.204623, "mass"));
        units.add(new Unit("kwintal [q]", (float) 0.01, "mass"));
        //jednostki szybkosci
        units.add(new Unit("Metr na sekundę [m/s]", (float) 1, "speed"));
        units.add(new Unit("Kilometr na sekundę [km/s]", (float) 0.001, "speed"));
        units.add(new Unit("Kilometr na godzinę [km/h]", (float) 3.6, "speed"));
        units.add(new Unit("Mila na sekundę [mps]", (float) 0.000621, "speed"));
        units.add(new Unit("Mila na godzinę [mph]", (float) 2.236936, "speed"));
        units.add(new Unit("Stopa na sekundę [fps]", (float) 3.28084, "speed"));
        units.add(new Unit("Węzeł (knot)[kn][kt]", (float) 1.943844, "speed"));

        //input spinner
        ArrayAdapter adapterSpinner = new ArrayAdapter(this, R.layout.spinner, availableSpinnerUnits(units, currentCategory));
        inputSpinner = (Spinner) findViewById(R.id.spinnerInput);
        inputSpinner.setAdapter(adapterSpinner);
        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitToConvert = (Unit) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //output spinner
        outputSpinner = (Spinner) findViewById(R.id.spinnerOutput);
        outputSpinner.setAdapter(adapterSpinner);
        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitConverted = (Unit) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //navi menu
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //result box
        resultTextView = (TextView) findViewById(R.id.textViewResult);

        //convert button
        convertButton = (Button) findViewById(R.id.convertButton);

        //value to convert field
        valueToConvertEditText = (EditText) findViewById(R.id.editTextNumberDecimal2);

        //box with category name
        categoryTextView = (TextView) findViewById(R.id.categoryName);

        //on change number field makes button disabled
        valueToConvertEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!valueToConvertEditText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "filled", Toast.LENGTH_SHORT).show();
                    convertButton.setEnabled(true);
                }
                else if(valueToConvertEditText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "empty", Toast.LENGTH_SHORT).show();
                    convertButton.setEnabled(false);
                }
                else{
                    Toast.makeText(MainActivity.this, "unknown field content", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               float numberToConvert = Float.parseFloat(valueToConvertEditText.getText().toString());
               float result = unitConverted.convertFromSiUnit(unitToConvert.convertToSiUnit(numberToConvert));
               resultTextView.setText(String.valueOf(result));

            }
        });

        //navi menu - pola do wyboru
        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.categoryLength){
                    currentCategory = "length";
                    categoryTextView.setText(currentCategory.toUpperCase());
                    availableSpinnerUnits(units, currentCategory); //
                    drawerLayout.closeDrawers();
                    Toast.makeText(MainActivity.this, currentCategory, Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.categoryMass){
                    currentCategory = "mass";
                    categoryTextView.setText(currentCategory.toUpperCase());
                    availableSpinnerUnits(units, currentCategory);
                    drawerLayout.closeDrawers();
                    Toast.makeText(MainActivity.this, currentCategory, Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.categorySpeed){
                    currentCategory = "speed";
                    categoryTextView.setText(currentCategory.toUpperCase());
                    availableSpinnerUnits(units, currentCategory);
                    drawerLayout.closeDrawers();
                    Toast.makeText(MainActivity.this, currentCategory, Toast.LENGTH_SHORT).show();
                }
                adapterSpinner.clear();
                adapterSpinner.addAll(availableSpinnerUnits(units, currentCategory));
                adapterSpinner.notifyDataSetChanged();
                return true;
            }
        });

    }

    //funkcja tworzaca nowa liste z dostepnymi jednostkami podajac z ktorej maja byc kategorii zeby potem wyswietlil je spinner
    public List availableSpinnerUnits( List<Unit> units, String currentCategory){ //(LISTA OBIEKTOW, WYBRANA KATEGORIA Z MENU) //NIE WIEM CZY TYP FUNKCJI JEST DOBRZE //zmienic nazwe funkcji

        List<Unit> availableUnits = new ArrayList<Unit>();
        for(Unit x : units){ //ITERACJA PO LISCIE OBIEKTOW
            if(x.getUnitCategory().equals(currentCategory)) { //SPRAWDZANIE CZY OBIEkT Z LISTY units MA DANA KATEGORIE // equals() raczej dziala poprawnie
                availableUnits.add(new Unit(x.getName(), x.getSiFactor(), x.getUnitCategory())); //JESLI TAK TO DODAJ DO NOWEJ LISTY (SKOPIOWAC Z TAMTEJ LISTY JAKOS?)
            }
        }
        return availableUnits; //NA KONCU ZWROCIC NOWA LISTE, SPINNER WIDZI NOWA LISTE
    }

    //navi menu i header options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //navigation button (hamburger)
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(this, "Navi menu opened", Toast.LENGTH_SHORT).show();
            return true;
        }

        //header options menu
        switch (item.getItemId()){
            //history icon when pressed
            case R.id.historyIcon:
                Toast.makeText(this, "History icon selected", Toast.LENGTH_SHORT).show();
                //go to History Activity
                Intent intentHistory = new Intent(this, HistoryActivity.class);
                startActivity(intentHistory);
                return true;
            //settings icon when pressed
            case R.id.settingsIcon:
                Toast.makeText(this, "Settings icon selected", Toast.LENGTH_SHORT).show();
                //go to Settings Activity
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    //spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


}
