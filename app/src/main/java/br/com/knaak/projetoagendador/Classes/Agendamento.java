package br.com.knaak.projetoagendador.Classes;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Agendamento {

    private Usuario usuario;

    private String data;

    private String horario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    @SuppressLint("SimpleDateFormat")
    public Date getDataFull() throws ParseException {
        Date data = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(this.getData() + " " + this.getHorario());
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
