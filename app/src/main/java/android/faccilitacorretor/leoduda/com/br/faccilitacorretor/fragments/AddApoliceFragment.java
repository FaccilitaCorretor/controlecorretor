package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.R;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.AlarmeDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.ApoliceDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.CorretorDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dao.SeguradoraDAO;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Alarme;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Apolice;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Corretor;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Segurado;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.Seguradora;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.services.AlarmReceiver;
import android.faccilitacorretor.leoduda.com.br.faccilitacorretor.utils.MiddleWareUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.RealmList;

/**
 * Created by Duda on 15/08/2015.
 */
public class AddApoliceFragment extends Fragment {

    private EditText seguradoEditText;
    private EditText seguradoraEditText;
    private EditText numeroApoliceEditText;
    private EditText tipoApoliceEditText;
    private EditText inicioVigenciaEditText;
    private EditText fimVigenciaEditText;
    private EditText valorPremioEditText;
    private EditText dataAlertaEditText;
    private EditText horaAlertaEditText;
    private Button cadastrarApoliceButton;
    private AlertDialog alertDialog;
    private AlertDialog alertDialogSeguradoras;
    private int diaInicio = 0, mesInicio = 0, anoInicio = 0;
    private int diaFim = 0, mesFim = 0, anoFim = 0;
    private Segurado segurado;
    private List<Seguradora> seguradoras;
    private Seguradora seguradoraSelecionada;
    private Apolice apolice;
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    Calendar cal = Calendar.getInstance();
    private SharedPreferences preferences;
    private String idUsuarioLogado;
    private String urlImagem;
    private boolean isPrimeiroAcesso;
    private boolean isLogado;

    int diaAlerta;
    int mesAlerta;
    int anoAlerta;
    int horaAlerta;
    int minutoAlerta;
    int segundoAlerta = 0;

    final static int RQS_1 = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_apolice, container, false);

        seguradoEditText = (EditText) view.findViewById(R.id.escolhaSeguradoEditText);
        numeroApoliceEditText = (EditText) view.findViewById(R.id.numeroApoliceEditText);
        inicioVigenciaEditText = (EditText) view.findViewById(R.id.inicioVigenciaEditText);
        fimVigenciaEditText = (EditText) view.findViewById(R.id.fimVigenciaEditText);
        valorPremioEditText = (EditText) view.findViewById(R.id.valorPremioEditText);
        dataAlertaEditText = (EditText) view.findViewById(R.id.dataAlertaEditText);
        horaAlertaEditText = (EditText) view.findViewById(R.id.horaAlertaEditText);
        cadastrarApoliceButton = (Button) view.findViewById(R.id.cadastrarApoliceButton);

        this.alertDialog = criaAlertDialog();
        tipoApoliceEditText = (EditText) view.findViewById(R.id.tipoApoliceEditText);
        tipoApoliceEditText.setFocusable(false);
        tipoApoliceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        this.alertDialogSeguradoras = criaAlertDialogSeguradoras();
        seguradoraEditText = (EditText) view.findViewById(R.id.escolhaSeguradoraEditText);
        seguradoraEditText.setFocusable(false);
        seguradoraEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSeguradoras.show();
            }
        });

        inicioVigenciaEditText.setFocusable(false);
        inicioVigenciaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), listenerInicio, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fimVigenciaEditText.setFocusable(false);
        fimVigenciaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), listenerFim, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dataAlertaEditText.setFocusable(false);
        dataAlertaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), listenerData, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        horaAlertaEditText.setFocusable(false);
        horaAlertaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), listenerHora, calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE), false).show();

            }
        });

        cadastrarApoliceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarme alarme = new Alarme();
                alarme.setDataCadastro(new Date());
                alarme.setStatus("A");
                alarme.setAnoAlerta(anoAlerta);
                alarme.setMesAlerta(mesAlerta);
                alarme.setDiaAlerta(diaAlerta);
                alarme.setHoraAlerta(horaAlerta);
                alarme.setMinutoAlerta(minutoAlerta);

                RealmList<Alarme> alarmes = new RealmList<Alarme>();
                alarmes.add(alarme);

                Apolice newApolice = new Apolice();
                newApolice.setStatus("A");
                newApolice.setDataCadastro(new Date());
                newApolice.setIdAlarme(alarme.getId());
                newApolice.setCorretor(carregaDadosCorretorLogado());
                newApolice.setAlarmes(alarmes);
                newApolice.setSegurado(segurado);
                newApolice.setSeguradora(seguradoraSelecionada);
                newApolice.setFinalVigencia(fimVigenciaEditText.getText().toString());
                newApolice.setInicioVigencia(inicioVigenciaEditText.getText().toString());
                newApolice.setTipoApolice(tipoApoliceEditText.getText().toString());
                newApolice.setNumeroApolice(numeroApoliceEditText.getText().toString());
                newApolice.setValorPremio(valorPremioEditText.getText().toString());

                if(apolice!=null){
                    newApolice.setId(apolice.getId());
                    newApolice.setDataCadastro(apolice.getDataCadastro());
                }

                ApoliceDAO dao = new ApoliceDAO(v.getContext());
                dao.create(newApolice);

                //DADOS PARA O BUNDLE
//                String nomeSegurado = newApolice.getSegurado().getNome();
//                String telefoneSegurado = newApolice.getSegurado().getTelefoneCelular();
//                String nomeSeguradora   = newApolice.getSeguradora().getNomeSeguradora();
//                String tipoApolice = newApolice.getTipoApolice();
//                String vencimentoApolice = newApolice.getFinalVigencia();

                //Passagem de parametros do AlarmManager para AlarmReceiver

                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//                intent.putExtra("nomeSegurado", nomeSegurado);
//                intent.putExtra("telefoneSegurado",telefoneSegurado);
//                intent.putExtra("nomeSeguradora", nomeSeguradora);
//                intent.putExtra("tipoApolice",tipoApolice);
//                intent.putExtra("vencimentoApolice",vencimentoApolice);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getActivity(), RQS_1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                cal.set(anoAlerta, mesAlerta - 1, diaAlerta, horaAlerta, minutoAlerta, segundoAlerta);

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                Snackbar.make(view, "Alerta programado para: " + cal.getTime(), Snackbar.LENGTH_LONG).show();

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ApolicesFragment fragApolices = new ApolicesFragment();
                ft.replace(R.id.container_main, fragApolices, "fragApolice");
                ft.commit();

                //GRAVANDO BACKUP NA NUVEM
                if(isConnected()) {
                    MiddleWareUtil middleWareUtil = new MiddleWareUtil(getActivity().getApplicationContext());
                    middleWareUtil.pushDataToMiddleware(newApolice);
                }
            }
        });

        //CARREGANDO SEGURADO QUANDO ID FOR INFORMADO
        if (getArguments() != null) {
            long seguradoID = getArguments().getLong("seguradoID");
            if (seguradoID != 0L) {
                carregaSegurado(seguradoID);
            }
        }

        //CARREGANDO APOLICE QUANDO ID FOR INFORMADO
        if(getArguments()!=null) {
            long apoliceID = getArguments().getLong("apoliceID");
            if (apoliceID != 0L) {
                carregaApolice(apoliceID);
            }
        }

        return view;

    }//fim onCreate

    private void    carregaApolice(long apoliceID) {
        ApoliceDAO dao = new ApoliceDAO(getActivity().getApplicationContext());
        Apolice apolice = dao.findById(apoliceID);
        if (apolice != null) {
            fimVigenciaEditText.setText(apolice.getFinalVigencia().toString());
            inicioVigenciaEditText.setText(apolice.getInicioVigencia().toString());
            tipoApoliceEditText.setText(apolice.getTipoApolice());
            numeroApoliceEditText.setText(apolice.getNumeroApolice());
            valorPremioEditText.setText(apolice.getValorPremio());
            seguradoEditText.setText(apolice.getSegurado()==null? "" : apolice.getSegurado().getNome());
            seguradoraEditText.setText(apolice.getSeguradora()==null? "" : apolice.getSeguradora().getNomeSeguradora());
            if(apolice.getAlarmes()!=null){
                if(apolice.getAlarmes().size() > 0){
                    anoAlerta = apolice.getAlarmes().get(0).getAnoAlerta();
                    diaAlerta = apolice.getAlarmes().get(0).getDiaAlerta();
                    horaAlerta = apolice.getAlarmes().get(0).getHoraAlerta();
                    minutoAlerta  = apolice.getAlarmes().get(0).getMinutoAlerta();

                    dataAlertaEditText.setText(diaAlerta + "/" + mesAlerta + "/" + anoAlerta);
                    horaAlertaEditText.setText(horaAlerta + ":" + minutoAlerta);
                }
            }

            if(apolice.getSeguradora()!=null){
                /*SeguradoraDAO daos  = new SeguradoraDAO(getActivity().getApplicationContext());
                Seguradora seguradoraBuscada = daos.findById(apolice.getSeguradora().getId());
                seguradoraSelecionada = seguradoraBuscada;
                seguradoraEditText.setText(seguradoraBuscada.getNomeSeguradora());
                apolice.setSeguradora(seguradoraBuscada);*/
                seguradoraSelecionada = apolice.getSeguradora();
            }

            if(apolice.getSegurado()!=null){
                segurado = apolice.getSegurado();
            }

            if(apolice.getIdAlarme()!=0){
                AlarmeDAO daoAlarme = new AlarmeDAO(getActivity().getApplicationContext());
                Alarme alarmes = daoAlarme.findById(apolice.getIdAlarme());

                anoAlerta = alarmes.getAnoAlerta();
                diaAlerta = alarmes.getDiaAlerta();
                horaAlerta = alarmes.getHoraAlerta();
                minutoAlerta  = alarmes.getMinutoAlerta();
                mesAlerta = alarmes.getMesAlerta();

                dataAlertaEditText.setText(diaAlerta + "/" + mesAlerta + "/" + anoAlerta);
                horaAlertaEditText.setText(horaAlerta + ":" + minutoAlerta);
            }

            this.apolice = apolice;
        }
    }

    private void carregaSegurado(long seguradoID) {
        SeguradoDAO dao = new SeguradoDAO(getActivity().getApplicationContext());
        Segurado segurado = dao.findById(seguradoID);
        if (segurado != null) {
            seguradoEditText.setText(segurado.getNome());
            this.segurado = segurado;
        }
    }

    //AlertDialog para tipoApolice
    private String[] tipo = {"Vida", "Automóvel", "Ramos Elementares"};

    public AlertDialog criaAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tipos de Apólice")
                .setItems(tipo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        tipoApoliceEditText.setText(tipo[position]);
                    }
                });
        return builder.create();
    }

    public AlertDialog criaAlertDialogSeguradoras() {
        final List<String> nomeSeguradoras = new ArrayList<String>();
        SeguradoraDAO dao = new SeguradoraDAO(getActivity().getApplicationContext());
        seguradoras = dao.findAll();

        for (Seguradora seguradora : seguradoras) {
            nomeSeguradoras.add(seguradora.getNomeSeguradora());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seguradoras")
                .setItems(nomeSeguradoras.toArray(new CharSequence[nomeSeguradoras.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        seguradoraEditText.setText(nomeSeguradoras.get(position));
                        seguradoraSelecionada = seguradoras.get(position);
                    }
                });
        return builder.create();
    }

    //DatePicker para dataInicio
    DatePickerDialog.OnDateSetListener listenerInicio = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            diaInicio = dayOfMonth;
            mesInicio = monthOfYear + 1;
            anoInicio = year;
            inicioVigenciaEditText.setText(diaInicio + "/" + mesInicio + "/" + anoInicio);
        }
    };

    //DatePicker para dataFinal
    DatePickerDialog.OnDateSetListener listenerFim = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            diaFim = dayOfMonth;
            mesFim = monthOfYear + 1;
            anoFim = year;

            if (anoInicio == 0 | mesInicio == 0 | diaInicio == 0) {
                Snackbar.make(getView(), "Preencha a data de início da vigência primeiro!", Snackbar.LENGTH_SHORT).show();
            } else if (anoFim <= anoInicio && mesFim <= mesInicio && diaFim <= diaInicio) {
                Snackbar.make(getView(), "A data deverá ser maior que o início da vigência!", Snackbar.LENGTH_SHORT).show();
            } else {
                fimVigenciaEditText.setText(diaFim + "/" + mesFim + "/" + anoFim);
            }
        }
    };

    //DatePicker para dataAlerta
    DatePickerDialog.OnDateSetListener listenerData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            diaAlerta = dayOfMonth;
            mesAlerta = monthOfYear + 1;
            anoAlerta = year;

            if (anoInicio == 0 | mesInicio == 0 | diaInicio == 0) {
                Snackbar.make(getView(), "Preencha a data de início da vigência primeiro!", Snackbar.LENGTH_SHORT).show();
            } else if (anoAlerta <= anoInicio && mesAlerta <= mesInicio && diaAlerta <= diaInicio) {
                Snackbar.make(getView(), "A data deverá ser maior que o início da vigência!", Snackbar.LENGTH_SHORT).show();
            } else if (anoAlerta >= anoFim && mesAlerta >= mesFim && diaAlerta >= diaFim) {
                Snackbar.make(getView(), "A data do Alerta deverá ser menor que o fim da vigência!", Snackbar.LENGTH_SHORT).show();
            } else {
                dataAlertaEditText.setText(diaAlerta + "/" + mesAlerta + "/" + anoAlerta);

            }
        }
    };
    //TimePicker para horaAlerta
    TimePickerDialog.OnTimeSetListener listenerHora = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            horaAlerta = hourOfDay;
            minutoAlerta = minute;
            horaAlertaEditText.setText(horaAlerta + ":" + minutoAlerta);
        }
    };

    private Corretor carregaDadosCorretorLogado() {
        //BUSCANDO AS PREFERENCIAS
        preferences = getActivity().getApplicationContext()
                .getSharedPreferences("DADOS_USUARIO_LOGADO", getActivity().getApplicationContext().MODE_PRIVATE);
        idUsuarioLogado = preferences.getString("idUsuario", null);
        urlImagem = preferences.getString("urlImagem", null);
        isPrimeiroAcesso = preferences.getBoolean("primeiro-acesso", true);
        isLogado = preferences.getBoolean("isLogado", false);

        CorretorDAO dao = new CorretorDAO(getActivity().getApplicationContext());
        return dao.findById(idUsuarioLogado);
    }

    private boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private boolean isConnectedOnWifi(){
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

}