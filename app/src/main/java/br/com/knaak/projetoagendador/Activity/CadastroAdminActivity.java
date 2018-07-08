package br.com.knaak.projetoagendador.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.knaak.projetoagendador.Classes.Usuario;
import br.com.knaak.projetoagendador.DAO.ConfiguracaoFirebase;
import br.com.knaak.projetoagendador.Helper.Preferencias;
import br.com.knaak.projetoagendador.R;

public class CadastroAdminActivity extends AppCompatActivity {

    private BootstrapEditText email;
    private BootstrapEditText senha1;
    private BootstrapEditText senha2;
    private BootstrapEditText nome;
    private RadioButton rbAdmin;
    private RadioButton rbAtend;
    private BootstrapButton btnCadastrar;
    private BootstrapButton btnCancelar;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_admin);

        email = (BootstrapEditText) findViewById(R.id.edtCadEmail);
        senha1 = (BootstrapEditText) findViewById(R.id.edtCadSenha1);
        senha2 = (BootstrapEditText) findViewById(R.id.edtCadSenha2);
        nome = (BootstrapEditText) findViewById(R.id.edtCadNome);
        rbAdmin = (RadioButton) findViewById(R.id.rbAdmin);
        rbAtend = (RadioButton) findViewById(R.id.rbAtend);
        btnCadastrar = (BootstrapButton) findViewById(R.id.btnCadastrar);
        btnCancelar = (BootstrapButton) findViewById(R.id.btnCancelar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (senha1.getText().toString().equals(senha2.getText().toString())) {
                    usuario = new Usuario();

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    usuario.setNome(nome.getText().toString());

                    if (rbAdmin.isChecked()) {
                        usuario.setTipoUsuario("Administrador");
                    } else if (rbAtend.isChecked()) {
                        usuario.setTipoUsuario("Atendente");
                    }

                    //chamada de método para cadastro de usuários
                    cadastrarUsuario();

                } else {
                    Toast.makeText(CadastroAdminActivity.this, getResources().getString(R.string.senhaDiferentes), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroAdminActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    insereUsuario(usuario);

                    finish();

                    //deslogar ao adicionar o usuário
                    autenticacao.signOut();

                    //para abrir a nossa tela principal após a re-autenticação
                    abreTelaPrincipal();


                } else {

                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e que contenha letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é invalido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está cadastro!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroAdminActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean insereUsuario(Usuario usuario) {

        try {

            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
            reference.push().setValue(usuario);
            Toast.makeText(CadastroAdminActivity.this, getResources().getString(R.string.cadastroSucesso), Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(CadastroAdminActivity.this, getResources().getString(R.string.cadastroErro), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private void abreTelaPrincipal() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

        Preferencias preferencias = new Preferencias(CadastroAdminActivity.this);

        autenticacao.signInWithEmailAndPassword(preferencias.getEmailUsuarioLogado(), preferencias.getSenhaUsuarioLogado()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(CadastroAdminActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(CadastroAdminActivity.this, getResources().getString(R.string.falhou), Toast.LENGTH_LONG).show();
                    autenticacao.signOut();
                    Intent intent = new Intent(CadastroAdminActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });

    }
}