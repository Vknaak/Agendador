package br.com.knaak.projetoagendador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import br.com.knaak.projetoagendador.Helper.Preferencias;
import br.com.knaak.projetoagendador.R;

import static android.icu.util.Calendar.getInstance;
import static java.util.Calendar.DAY_OF_WEEK;

public class ComumActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener  {

    private FirebaseAuth autenticacao;
    private BootstrapButton agendamento;
    private TextView tvagendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comum);

        autenticacao = FirebaseAuth.getInstance();

        agendamento = (BootstrapButton) findViewById(R.id.btnAgendar);


        agendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendar();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        menu.clear();

        getMenuInflater().inflate(R.menu.menu_comum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sair_comum) {
            deslogarUsuario();
        }else if (id == R.id.action_Agendamento){
            agendar();
        }

        return super.onOptionsItemSelected(item);

    }

    private void deslogarUsuario() {

        autenticacao.signOut();

        Intent intent = new Intent(ComumActivity.this, MainActivity.class);
        startActivity(intent);

        finish();

    }


    private int year, month, day, hour, minute;
    public void  agendar(){

        iniDataTime();

        Calendar calendar = getInstance();
        calendar.set(year, month, day);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        java.util.Calendar cMin = java.util.Calendar.getInstance();
        java.util.Calendar cMax = java.util.Calendar.getInstance();
        cMax.set(cMax.get(java.util.Calendar.YEAR), 11,31);
        datePickerDialog.setMinDate(cMin);
        datePickerDialog.setMaxDate(cMax);

        List<java.util.Calendar> daysList =  new LinkedList<>();
        java.util.Calendar[] daysArray;
        java.util.Calendar cAux =  java.util.Calendar.getInstance();

        while(cAux.getTimeInMillis()<= cMax.getTimeInMillis()){
            if(cAux.get(DAY_OF_WEEK)!= 1 && cAux.get(DAY_OF_WEEK)!= 7){
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());
                daysList.add(c);
            }
            cAux.setTimeInMillis(cAux.getTimeInMillis()+(24 * 60 * 60 * 1000));
        }
        daysArray = new java.util.Calendar[daysList.size()];
        for (int i = 0; i <daysArray.length; i++){
            daysArray[i] = daysList.get(i);
        }
        datePickerDialog.setSelectableDays(daysArray);
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(),"DatePickerDialog");
        datePickerDialog.setThemeDark(true);


    }


    private void iniDataTime() {
        if(year == 0){
            java.util.Calendar c = java.util.Calendar.getInstance();
            year = c.get(java.util.Calendar.YEAR);
            month = c.get(java.util.Calendar.MONTH);
            day = c.get(java.util.Calendar.DAY_OF_MONTH);
            hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            minute = c.get(java.util.Calendar.MINUTE);
        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        year = month = day = hour = minute = 0;
        tvagendamento.setText("");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int i, int i1, int i2) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        year =i;
        month = i1;
        day = i2;
        calendar.set(year, month, day, 0, 0, 0);

        Preferencias preferencias = new Preferencias(ComumActivity.this);
        preferencias.salvarDataConsulta(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

        Intent intent = new Intent(ComumActivity.this, AgendamentoActivity.class);
        startActivity(intent);


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int i, int i1) {
        if(i< 8 || i>18){
            onDateSet(null, year , month, day);
            Toast.makeText(ComumActivity.this,  getResources().getString(R.string.atendimentoHorario), Toast.LENGTH_LONG).show();
            return;
        }
        hour = i;
        minute = i1;


    }


}