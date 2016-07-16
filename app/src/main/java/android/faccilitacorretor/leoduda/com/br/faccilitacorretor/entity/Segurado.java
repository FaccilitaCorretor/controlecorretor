package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity;

import java.util.Date;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by turbiani on 08/08/15.
 */
public class Segurado extends RealmObject {
    private long    id;
    private Date dataCadastro;
    private String  status;
    @PrimaryKey
    private String nome;
    private String email;
    private String telefoneCelular;
    private String telefoneOutro;

    public Segurado(){
        this.id = new Random().nextInt(Integer.MAX_VALUE);
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTelefoneOutro() {
        return telefoneOutro;
    }

    public void setTelefoneOutro(String telefoneOutro) {
        this.telefoneOutro = telefoneOutro;
    }

}
