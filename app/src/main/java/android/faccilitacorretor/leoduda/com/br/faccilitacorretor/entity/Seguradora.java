package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity;

import java.util.Date;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by turbiani on 08/08/15.
 */
public class Seguradora extends RealmObject {
    @PrimaryKey
    private long id;
    private Date dataCadastro;
    private String status;
    private String nomeSeguradora;

    public Seguradora(){this.id = new Random().nextInt(Integer.MAX_VALUE);}

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

    public String getNomeSeguradora() {
        return nomeSeguradora;
    }

    public void setNomeSeguradora(String nomeSeguradora) {
        this.nomeSeguradora = nomeSeguradora;
    }


}
