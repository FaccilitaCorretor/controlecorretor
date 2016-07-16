package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity.base;

import java.util.Date;

/**
 * Created by turbiani on 08/08/15.
 */
public abstract class AbstractEntity {
    private long    id;
    private Date    dataCadastro;
    private String  status;


    public AbstractEntity() {}

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
}
