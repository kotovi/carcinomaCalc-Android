package ru.geekbase.carcinomacalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;

import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



import ru.geekbase.carcinomacalc.filters.DecimalDigitsInputFilter;

public class MainActivity extends AppCompatActivity {
    Button calculateButton;
    Button clearDataButton;
    TextView pacientAge;
    TextView pacientEritrocites;
    TextView pacientALT;
    TextView pacientAFP;
    TextView pacientOP;
    TextView pacientGender;
    Boolean ageFault = false;
    Boolean erFault = false;
    Boolean altFault = false;
    Boolean afpFault = false;
    Boolean opFalse  = false;
    Integer gender = 2;

    SharedPreferences sPref;
    final String SAVED_PACIENT_AGE = "pacientAge";
    final String SAVED_PACIENT_ERITROCITES = "pacientEritrocites";
    final String SAVED_PACIENT_ALT = "pacientALT";
    final String SAVED_PACIENT_AFP = "pacientAFP";
    final String SAVED_PACIENT_OP = "pacientOP";
    final String SAVED_PACIENT_GENDER = "gender";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
            case R.id.action_user_agreement:
                Intent intentUserAgreement = new Intent(this, UserAgreementActivity.class);
                this.startActivity(intentUserAgreement);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pacientAge = findViewById(R.id.PacientAge);
        pacientEritrocites = findViewById(R.id.PacientEritrocites);
        pacientALT = findViewById(R.id.PacientALT);
        pacientAFP = findViewById(R.id.PacientAFP);
        pacientOP = findViewById(R.id.PacientOP);
        pacientGender=findViewById(R.id.pacient_gender);
        calculateButton = findViewById(R.id.calc_button);
        clearDataButton = findViewById(R.id.clear_button);

        Switch genderSwitch = (Switch) findViewById(R.id.pacient_gender_switch);
        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pacientGender.setText(getResources().getString(R.string.gender_male));
                    gender=1;
                } else {
                    pacientGender.setText(getResources().getString(R.string.gender_female));
                    gender=2;
                }
            }
        });

        loadPacientData();



        pacientAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0) {
                    if (Integer.valueOf(String.valueOf(s)) > 150) {
                        pacientAge.setTextColor(getResources().getColor(R.color.colorRed));
                        pacientAge.setError(getResources().getString(R.string.age_error));
                        ageFault = true;
                    } else {
                        if (Integer.valueOf(String.valueOf(s))>0) {
                            ageFault = false;
                            pacientAge.setTextColor(getResources().getColor(R.color.colorDefault));
                        }
                        else {
                            ageFault = true;
                            pacientAge.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    }
                }
            }
        });

        pacientEritrocites.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,1)});
        pacientEritrocites.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()!=0){
                    if ((s.charAt(0))==".".charAt(0)) {
                        s  = new SpannableStringBuilder("0");
                        pacientEritrocites.setText("");
                        pacientEritrocites.append(s);
                        pacientEritrocites.append(".");
                    }
                    if(Float.valueOf(String.valueOf(s))>7 ){
                        pacientEritrocites.setTextColor(getResources().getColor(R.color.colorRed));
                        pacientEritrocites.setError(getResources().getString(R.string.eritrocites_error));
                        erFault=true;
                    } else {
                        if (Float.valueOf(String.valueOf(s))>0){
                            pacientEritrocites.setTextColor(getResources().getColor(R.color.colorDefault));
                            erFault=false;
                        } else{
                            pacientEritrocites.setTextColor(getResources().getColor(R.color.colorRed));
                            erFault=true;
                        }
                    }
                }
            }
        });

        pacientALT.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});

        pacientALT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0) {
                    if ((s.charAt(0))==".".charAt(0)) {
                        s  = new SpannableStringBuilder("0");
                        pacientALT.setText("");
                        pacientALT.append(s);
                        pacientALT.append(".");
                    }

                       if (Float.valueOf(String.valueOf(s)) > 1000) {
                           pacientALT.setTextColor(getResources().getColor(R.color.colorRed));
                           pacientALT.setError(getResources().getString(R.string.alt_error));
                           altFault=true;
                       } else {
                           if (Float.valueOf(String.valueOf(s)) > 0) {
                               pacientALT.setTextColor(getResources().getColor(R.color.colorDefault));
                               altFault=false;
                           } else {
                               pacientALT.setTextColor(getResources().getColor(R.color.colorRed));
                               altFault=true;
                           }
                       }
                   }
            }
        });

        pacientAFP.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});
        pacientAFP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0) {
                    if ((s.charAt(0))==".".charAt(0)) {
                        s  = new SpannableStringBuilder("0");
                        pacientAFP.setText("");
                        pacientAFP.append(s);
                        pacientAFP.append(".");
                    }
                    if (Float.valueOf(String.valueOf(s)) > 10000) {
                        pacientAFP.setTextColor(getResources().getColor(R.color.colorRed));
                        pacientAFP.setError(getResources().getString(R.string.afp_error));
                        afpFault=true;
                    } else {
                        if(Float.valueOf(String.valueOf(s)) > 0) {
                            pacientAFP.setTextColor(getResources().getColor(R.color.colorDefault));
                            afpFault=false;
                        }else{
                            pacientAFP.setTextColor(getResources().getColor(R.color.colorRed));
                            afpFault=true;
                        }
                    }
                }
            }
        });

        pacientOP.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)});

        pacientOP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()!=0){
                    if ((s.charAt(0))==".".charAt(0)) {
                        s  = new SpannableStringBuilder("0");
                        pacientOP.setText("");
                        pacientOP.append(s);
                        pacientOP.append(".");
                    }
                    if(Float.valueOf(String.valueOf(s))>10000 ){
                        pacientOP.setTextColor(getResources().getColor(R.color.colorRed));
                        pacientOP.setError(getResources().getString(R.string.op_error));
                        opFalse=true;
                    } else {
                        if(Float.valueOf(String.valueOf(s))>0 ){
                            pacientOP.setTextColor(getResources().getColor(R.color.colorDefault));
                            opFalse=false;
                        } else {
                            pacientOP.setTextColor(getResources().getColor(R.color.colorRed));
                            opFalse=true;
                        }

                    }
                }
            }
        });

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pacientAge.setText("");
                pacientAge.setError(null);
                pacientEritrocites.setText("");
                pacientEritrocites.setError(null);
                pacientALT.setText("");
                pacientALT.setError(null);
                pacientAFP.setText("");
                pacientAFP.setError(null);
                pacientOP.setText("");
                pacientOP.setError(null);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean epicFail = false;

                if (pacientAge.getText().toString().trim().length() > 0) {

                } else {
                    epicFail = true;
                    pacientAge.setError(getResources().getString(R.string.error_empty));

                }
                if (pacientEritrocites.getText().toString().trim().length() > 0) {
                } else {
                    epicFail = true;
                    pacientEritrocites.setError(getResources().getString(R.string.error_empty));
                }

                if (pacientALT.getText().toString().trim().length() > 0) {
                } else {
                    epicFail = true;
                    pacientALT.setError(getResources().getString(R.string.error_empty));
                }

                if (pacientAFP.getText().toString().trim().length() > 0) {
                } else {
                    epicFail = true;
                    pacientAFP.setError(getResources().getString(R.string.error_empty));
                }

                if (pacientOP.getText().toString().trim().length() > 0) {
                } else {
                    epicFail = true;
                    pacientOP.setError(getResources().getString(R.string.error_empty));
                }

                if(
                   (!afpFault)&
                   (!erFault)&
                   (!altFault)&
                   (!afpFault)&
                   (!opFalse)&
                   (!epicFail)
                ) {
                    Double f1 = -73.1676 +
                            (0.4846 * Integer.valueOf(String.valueOf(pacientAge.getText()))) +
                            (6.5932 * gender) +
                            (22.3483 * Float.valueOf(String.valueOf(pacientEritrocites.getText()))) +
                            (0.024 * Float.valueOf(String.valueOf(pacientALT.getText()))) +
                            (0.0015 * Float.valueOf(String.valueOf(pacientAFP.getText()))) +
                            (0.056 * Float.valueOf(String.valueOf(pacientOP.getText())));

                    Double f2 = -72.7678 +
                            (0.2393 * Integer.valueOf(String.valueOf(pacientAge.getText()))) +
                            (9.9954 * gender) +
                            (24.3663 * Float.valueOf(String.valueOf(pacientEritrocites.getText()))) +
                            (0.0018 * Float.valueOf(String.valueOf(pacientALT.getText()))) +
                            (0.0012 * Float.valueOf(String.valueOf(pacientAFP.getText()))) -
                            (0.0009 * Float.valueOf(String.valueOf(pacientOP.getText())));

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    if (f1 > f2) {
                        // Указываем Title
                        alertDialog.setTitle(getResources().getString(R.string.high_risk_title));
                        // Указываем текст сообщение
                        alertDialog.setMessage(getResources().getString(R.string.high_risk_body));

                    } else {
                        alertDialog.setTitle(getResources().getString(R.string.low_risk_title));
                        alertDialog.setMessage(getResources().getString(R.string.low_risk_body));
                    }

                    alertDialog.setButton(getResources().getString(R.string.close_dialog_button),
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            pacientAge.setText("");
                            pacientEritrocites.setText("");
                            pacientALT.setText("");
                            pacientAFP.setText("");
                            pacientOP.setText("");
                        }
                    });
                    alertDialog.show();
                }
                else {
                        Toast alarmToast = Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_incorrect_data),
                                Toast.LENGTH_SHORT);
                        alarmToast.setGravity(Gravity.CENTER,0,0);
                        alarmToast.show();
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    void savePacientData(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_PACIENT_AGE, pacientAge.getText().toString());
        ed.putString(SAVED_PACIENT_ERITROCITES, pacientEritrocites.getText().toString());
        ed.putString(SAVED_PACIENT_AFP, pacientAFP.getText().toString());
        ed.putString(SAVED_PACIENT_ALT, pacientALT.getText().toString());
        ed.putString(SAVED_PACIENT_OP, pacientOP.getText().toString());
        ed.putString(SAVED_PACIENT_GENDER, gender.toString());
        ed.commit();
    }
    void loadPacientData() {
        sPref = getPreferences(MODE_PRIVATE);
        pacientAge.setText(sPref.getString(SAVED_PACIENT_AGE, ""));
        pacientEritrocites.setText(sPref.getString(SAVED_PACIENT_ERITROCITES, ""));
        pacientAFP.setText(sPref.getString(SAVED_PACIENT_AFP, ""));
        pacientALT.setText(sPref.getString(SAVED_PACIENT_ALT, ""));
        pacientOP.setText(sPref.getString(SAVED_PACIENT_OP, ""));
        String genderString=sPref.getString(SAVED_PACIENT_GENDER, "");
        if(genderString.length()>0){
            gender=Integer.valueOf(genderString);
            if(gender==2){
                pacientGender.setText(getResources().getString(R.string.gender_female));
                pacientGender.setPressed(false);
            } else {
                pacientGender.setText(getResources().getString(R.string.gender_male));
                pacientGender.setPressed(true);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePacientData();
    }
}