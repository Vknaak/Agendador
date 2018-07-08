package br.com.knaak.projetoagendador.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.knaak.projetoagendador.Classes.Agendamento;
import br.com.knaak.projetoagendador.Classes.Usuario;
import br.com.knaak.projetoagendador.Helper.Preferencias;
import br.com.knaak.projetoagendador.R;

import static br.com.knaak.projetoagendador.DAO.ConfiguracaoFirebase.getFirebase;


public class AgendamentoActivity extends AppCompatActivity {

    private BootstrapButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private TextView tvagendamento;
    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private DatabaseReference reference;
    private Agendamento agendamento = new Agendamento();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        autenticacao = FirebaseAuth.getInstance();


        btn1 = (BootstrapButton) findViewById(R.id.btn1);
        btn2 = (BootstrapButton) findViewById(R.id.btn2);
        btn3 = (BootstrapButton) findViewById(R.id.btn3);
        btn4 = (BootstrapButton) findViewById(R.id.btn4);
        btn5 = (BootstrapButton) findViewById(R.id.btn5);
        btn6 = (BootstrapButton) findViewById(R.id.btn6);
        btn7 = (BootstrapButton) findViewById(R.id.btn7);
        btn8 = (BootstrapButton) findViewById(R.id.btn8);
        btn9 = (BootstrapButton) findViewById(R.id.btn9);

        tvagendamento = (TextView) findViewById(R.id.tvagendamento);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn1.isPressed()) {

                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time1));
                    agendamento.setHorario("08:00");
                    btn1.setClickable(false);
                    btn1.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }

            }


        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn2.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time2));
                    agendamento.setHorario("09:00");
                    btn2.setClickable(false);
                    btn2.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));


                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn3.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time3));
                    agendamento.setHorario("10:00");
                    btn3.setClickable(false);
                    btn3.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn4.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time4));
                    agendamento.setHorario("11:00");
                    btn4.setClickable(false);
                    btn4.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn5.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time5));
                    agendamento.setHorario("14:00");
                    btn5.setClickable(false);
                    btn5.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                if (btn6.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time6));
                    agendamento.setHorario("15:00");
                    btn6.setClickable(false);
                    btn6.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);


                if (btn7.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time7));
                    agendamento.setHorario("16:00");
                    btn7.setClickable(false);
                    btn7.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn8.setClickable(true);
                    btn8.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

                btn9.setClickable(true);
                if (btn8.isPressed()) {
                    tvagendamento.setText("Confirme seu agendamento para " + preferencias.getDataConsulta() + " das " + getResources().getText(R.string.time8));
                    agendamento.setHorario("17:00");
                    btn8.setClickable(false);
                    btn8.setBackgroundColor(Color.parseColor("#B5B5B5"));
                    btn1.setClickable(true);
                    btn1.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn2.setClickable(true);
                    btn2.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn3.setClickable(true);
                    btn3.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn4.setClickable(true);
                    btn4.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn5.setClickable(true);
                    btn5.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn6.setClickable(true);
                    btn6.setBackgroundColor(Color.parseColor("#FF4D4D"));
                    btn7.setClickable(true);
                    btn7.setBackgroundColor(Color.parseColor("#FF4D4D"));
                }
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (agendamento.getHorario() != null && !agendamento.getHorario().isEmpty() && btn9.isPressed()) {

                    salvarAgendamento();

                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.agendamentoSucesso), Toast.LENGTH_LONG).show();
                    tvagendamento.setText("");
                    btn9.setClickable(false);
                    btn9.setBackgroundColor(Color.parseColor("#B5B5B5"));
                }else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.selecioneHorario), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void salvarAgendamento() {


        autenticacao = FirebaseAuth.getInstance();
        reference = getFirebase().child("agendamento");
        Preferencias preferencias = new Preferencias(AgendamentoActivity.this);

        // Monta o Usuário
        Usuario user = new Usuario();
        user.setEmail(preferencias.getEmailUsuarioLogado().toString());
        // VERIFICAR SE PRECISA, ATUALMENTE ESTÁ MANDANDO O UID DO USUÁRIO
        // user.setCpf(preferencias.getCpfUsuarioLogado());

        // Monta o Agendamento
        agendamento.setUsuario(user);

        agendamento.setData(preferencias.getDataConsulta());

        // Salva Tudo
        reference.push().setValue(agendamento);

        Intent intent = new Intent(AgendamentoActivity.this, ComumActivity.class);
        startActivity(intent);
        finish();
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
        }

        return super.onOptionsItemSelected(item);

    }

    private void deslogarUsuario() {

        autenticacao.signOut();

        Intent intent = new Intent(AgendamentoActivity.this, MainActivity.class);
        startActivity(intent);

        finish();

    }

}