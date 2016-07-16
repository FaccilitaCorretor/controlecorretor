package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity;

import java.util.Date;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by turbiani on 08/08/15.
 */
public class Alarme extends RealmObject {
    @PrimaryKey
    private long    id;
    private Date    dataCadastro;
    private String  status;

    private String  mensagem;
    private int     diaAlerta;
    private int     mesAlerta;
    private int     anoAlerta;
    private int     horaAlerta;
    private int     minutoAlerta;;

    public Alarme(){this.id = new Random().nextInt(Integer.MAX_VALUE);}

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

    public String getMensagem() { return null;  }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getDiaAlerta() {
        return diaAlerta;
    }

    public void setDiaAlerta(int diaAlerta) {
        this.diaAlerta = diaAlerta;
    }

    public int getMesAlerta() {
        return mesAlerta;
    }

    public void setMesAlerta(int mesAlerta) {
        this.mesAlerta = mesAlerta;
    }

    public int getAnoAlerta() {
        return anoAlerta;
    }

    public void setAnoAlerta(int anoAlerta) {
        this.anoAlerta = anoAlerta;
    }

    public int getHoraAlerta() {
        return horaAlerta;
    }

    public void setHoraAlerta(int horaAlerta) {
        this.horaAlerta = horaAlerta;
    }

    public int getMinutoAlerta() {
        return minutoAlerta;
    }

    public void setMinutoAlerta(int minutoAlerta) {
        this.minutoAlerta = minutoAlerta;
    }
}
