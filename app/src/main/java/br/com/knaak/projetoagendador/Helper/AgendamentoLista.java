package br.com.knaak.projetoagendador.Helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.knaak.projetoagendador.Classes.Agendamento;
import br.com.knaak.projetoagendador.R;

public class AgendamentoLista extends ArrayAdapter{

    private Activity context;
    private List<Agendamento> listaAgendamento;

    public AgendamentoLista(Activity context, List<Agendamento> listaAgendamento){
        super(context, R.layout.lista_agendamento, listaAgendamento);
        this.context = context;
        this.listaAgendamento = listaAgendamento;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.lista_agendamento, null, true);

        TextView data = (TextView) listViewItem.findViewById(R.id.Txtage1);
        TextView hora = (TextView) listViewItem.findViewById(R.id.Txtage2);
        TextView email = (TextView) listViewItem.findViewById(R.id.Txtage3);


        Agendamento agendamento = listaAgendamento.get(position);
        data.setText("Data: " + agendamento.getData());
        hora.setText("Hora: " + agendamento.getHorario());
        email.setText("E-mail: "+ agendamento.getUsuario().getEmail());
        return listViewItem;

    }
}

