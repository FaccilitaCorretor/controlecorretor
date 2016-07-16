package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Random;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by turbiani on 08/08/15.
 */
public class Apolice extends RealmObject {
    @PrimaryKey
    private long    id;
    private Date    dataCadastro;
    private String  status;

    private String  numeroApolice;
    private String  inicioVigencia;
    private String  finalVigencia;
    private String  valorPremio;


    private Corretor    corretor;
    private Seguradora  seguradora;
    private Segurado    segurado;
    private String tipoApolice;
    private RealmList<Alarme> alarmes;
    private long idAlarme;

    public Apolice () {this.id = new Random().nextInt(Integer.MAX_VALUE);}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumeroApolice() {
        if(StringUtils.isNotBlank(numeroApolice)) {
            return numeroApolice;
        }else{
            return null;
        }
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(String inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public String getFinalVigencia() {
        return finalVigencia;
    }

    public void setFinalVigencia(String finalVigencia) {
        this.finalVigencia = finalVigencia;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public Corretor getCorretor() {
        return corretor;
    }

    public void setCorretor(Corretor corretor) {
        this.corretor = corretor;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public Segurado getSegurado() {
        return segurado;
    }

    public void setSegurado(Segurado segurado) {
        this.segurado = segurado;
    }

    public String getTipoApolice() {
        return tipoApolice;
    }

    public void setTipoApolice(String tipoApolice) {
        this.tipoApolice = tipoApolice;
    }

    public RealmList<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(RealmList<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    public long getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(long idAlarme) {
        this.idAlarme = idAlarme;
    }
}
