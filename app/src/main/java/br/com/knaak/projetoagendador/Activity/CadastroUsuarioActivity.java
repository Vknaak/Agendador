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
import br.com.knaak.projetoagendador.R;

import static br.com.knaak.projetoagendador.DAO.ConfiguracaoFirebase.getFirebase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private BootstrapEditText email;
    private BootstrapEditText senha1;
    private BootstrapEditText senha2;
    private BootstrapEditText nome;
    private BootstrapEditText cpf;
    private BootstrapEditText rua;
    private BootstrapEditText numero;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private BootstrapButton btnCadastrar;
    private BootstrapButton btnCancelar;
    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        email = (BootstrapEditText) findViewById(R.id.edtCadEmail);
        cpf = (BootstrapEditText) findViewById(R.id.edtCadCPF);
        rua = (BootstrapEditText) findViewById(R.id.edtCadRua);
        numero = (BootstrapEditText) findViewById(R.id.edtCadNumero);
        senha1 = (BootstrapEditText) findViewById(R.id.edtCadSenha1);
        senha2 = (BootstrapEditText) findViewById(R.id.edtCadSenha2);
        nome = (BootstrapEditText) findViewById(R.id.edtCadNome);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        btnCadastrar = (BootstrapButton) findViewById(R.id.btnCadastrar);
        btnCancelar = (BootstrapButton) findViewById(R.id.btnCancelar);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (senha1.getText().toString().equals(senha2.getText().toString())) {
                    usuario = new Usuario();

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    usuario.setNome(nome.getText().toString());
                    usuario.setCpf(cpf.getText().toString());
                    usuario.setRua(rua.getText().toString());
                    usuario.setNumero(numero.getText().toString());
                    usuario.setTipoUsuario("Comum");

                    if (rbFeminino.isChecked()) {
                        usuario.setSexo("Feminino");
                    } else if (rbMasculino.isChecked()) {
                        usuario.setSexo("Masculino");
                    }

                    //chamada de método para cadastro de usuários
                    cadastrarUsuario();

                } else {
                    Toast.makeText(CadastroUsuarioActivity.this, getResources().getString(R.string.senhaDiferentes), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    insereUsuario(usuario);


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

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean insereUsuario(Usuario usuario) {

        try {


            reference = getFirebase().child("usuarios");
            String key = reference.push().getKey();
            usuario.setKeyUsuario(key);
            reference.child(key).setValue(usuario);
            Toast.makeText(CadastroUsuarioActivity.this, getResources().getString(R.string.cadastroSucesso), Toast.LENGTH_LONG).show();
            abreLoginUsuario();
            return true;

        } catch (Exception e) {
            Toast.makeText(CadastroUsuarioActivity.this, getResources().getString(R.string.cadastroErro), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private void abreLoginUsuario() {

        Intent intent = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}