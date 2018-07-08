package br.com.knaak.projetoagendador.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "app.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;


    private final String CPF_USUARIO_LOGADO = "cpf_usuario_logado";
    private final String EMAIL_USUARIO_LOGADO = "email_usuario_logado";
    private final String SENHA_USUARIO_LOGADO = "senha_usuario_logado";
    private final String DATA_CONSULTA = "data_consulta";

    public Preferencias(Context contextoParametro) {
        context = contextoParametro;

        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);

        //associar o nosso preferencees.edit()
        editor = preferences.edit();
    }


    public void salvarUsuarioPreferencias(String email, String senha, String cpf) {


        //salvar dentro do nosso arquivo de preferencias o email e senha do usuario
        editor.putString(CPF_USUARIO_LOGADO, cpf);
        editor.putString(EMAIL_USUARIO_LOGADO, email);
        editor.putString(SENHA_USUARIO_LOGADO, senha);
        editor.commit();

    }
    public String getCpfUsuarioLogado() {

        return preferences.getString(CPF_USUARIO_LOGADO, null);
    }


    public String getEmailUsuarioLogado() {
        return preferences.getString(EMAIL_USUARIO_LOGADO, null);
    }

    public String getSenhaUsuarioLogado() {

        return preferences.getString(SENHA_USUARIO_LOGADO, null);
    }

    public void salvarDataConsulta(String data) {
        editor.putString(DATA_CONSULTA, data);
        editor.commit();
    }

    public String getDataConsulta()  {

        return preferences.getString(DATA_CONSULTA, null);
    }

}
