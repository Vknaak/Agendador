package br.com.knaak.projetoagendador.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.knaak.projetoagendador.Classes.Agendamento;
import br.com.knaak.projetoagendador.Helper.AgendamentoLista;
import br.com.knaak.projetoagendador.R;

public class AdminActivity extends AppCompatActivity {


    private ListView lista;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase referenciaFirebase;
    private DatabaseReference reference;
    private List<Agendamento> agendamento = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lista = (ListView) findViewById(R.id.LvPesquisa);
        autenticacao = FirebaseAuth.getInstance();

        referenciaFirebase = FirebaseDatabase.getInstance();
        reference = referenciaFirebase.getReference("agendamento");



    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                agendamento.clear();
                for(DataSnapshot obj:dataSnapshot.getChildren()){
                    Agendamento agenda = obj.getValue(Agendamento.class);

                    try {
                        if (agenda.getDataFull().compareTo(Calendar.getInstance().getTime()) >= 0) {
                            agendamento.add(agenda);
                        }
                    } catch (Exception e) {

                    }

                }

                Collections.sort(agendamento, new Comparator<Agendamento>() {
                    @Override
                    public int compare(final Agendamento local1, final Agendamento local2) {
                        try {
                            return local1.getDataFull().compareTo(local2.getDataFull());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });

                adapter = new AgendamentoLista(AdminActivity.this, agendamento);
                lista.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_usuario) {
            abrirTelaCadastroUsuario();
        } else if (id == R.id.action_sair_admin) {
            deslogarUsuario();
        }
        return super.onOptionsItemSelected(item);

    }

    private void abrirTelaCadastroUsuario() {
        Intent intent = new Intent(AdminActivity.this, CadastroAdminActivity.class);

        startActivity(intent);
    }

    private void deslogarUsuario() {

        autenticacao.signOut();

        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}
