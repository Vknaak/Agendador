package br.com.knaak.projetoagendador.Activity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.knaak.projetoagendador.Classes.Usuario;
import br.com.knaak.projetoagendador.DAO.ConfiguracaoFirebase;
import br.com.knaak.projetoagendador.Helper.Preferencias;
import br.com.knaak.projetoagendador.R;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth autenticacao;
    private BootstrapEditText edtEmailLogin;
    private BootstrapEditText edtSenhaLogin;
    private BootstrapButton btnLogin;
    private Usuario usuario;
    private TextView txtAbreCadastro;
    private TextView txtRecuperarSenha;
    private AlertDialog alerta;
    private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = (BootstrapEditText) findViewById(R.id.edtEmail);
        edtSenhaLogin = (BootstrapEditText) findViewById(R.id.edtSenha);
        txtAbreCadastro = (TextView) findViewById(R.id.txtAbreCadastro);
        txtRecuperarSenha = (TextView) findViewById(R.id.txtRecuperarSenha);
        btnLogin = (BootstrapButton) findViewById(R.id.btnLogin);

        final EditText editTextEmail = new EditText(MainActivity.this);
        editTextEmail.setHint("exemplo@exemplo.com");

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        autenticacao = FirebaseAuth.getInstance();



        if (usuarioLogado()) {

            String email = autenticacao.getCurrentUser().getEmail().toString();

            abrirTelaPrincipal(email);
            finish();

        } else {

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!edtEmailLogin.getText().toString().equals("") && !edtSenhaLogin.getText().toString().equals("")) {

                        usuario = new Usuario();

                        usuario.setEmail(edtEmailLogin.getText().toString());
                        usuario.setSenha(edtSenhaLogin.getText().toString());

                        validarLogin();


                    } else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.camposObrigatorios), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        txtAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });


        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(false);

                builder.setTitle("Recuperar senhar");

                builder.setMessage("Informe o seu e-mail");

                builder.setView(editTextEmail);

                if (!editTextEmail.getText().equals("")) {

                    builder.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            autenticacao = FirebaseAuth.getInstance();

                            String emailRecuperar = editTextEmail.getText().toString();


                            autenticacao.sendPasswordResetEmail(emailRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.alertaEmail), Toast.LENGTH_LONG).show();

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);

                                    } else {

                                        Toast.makeText(MainActivity.this,getResources().getString(R.string.alertaFalhaEmail), Toast.LENGTH_LONG).show();

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.emailVazio), Toast.LENGTH_LONG).show();
                }


                alerta = builder.create();

                alerta.show();

            }
        });
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    abrirTelaPrincipal(usuario.getEmail());

                    Preferencias preferencias = new Preferencias(MainActivity.this);
                    preferencias.salvarUsuarioPreferencias(usuario.getEmail(), usuario.getSenha(), autenticacao.getCurrentUser().getUid());
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.loginSucesso), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.erroLogin), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void abrirTelaPrincipal(String emailUsuario) {


        String email = autenticacao.getCurrentUser().getEmail().toString();


        referenciaFirebase.child("usuarios").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String tipoUsuarioEmail = postSnapshot.child("tipoUsuario").getValue().toString();

                    if (tipoUsuarioEmail.equals("Administrador")) {

                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();


                    } else if (tipoUsuarioEmail.equals("Comum")) {

                        Intent intent = new Intent(MainActivity.this, ComumActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public Boolean usuarioLogado() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }



}

