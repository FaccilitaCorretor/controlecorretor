package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by turbiani on 08/08/15.
 */
public class Corretor extends RealmObject {
    private String    id;
    private Date dataCadastro;
    private String  status;
    private String  nome;
    @PrimaryKey
    private String  email;
    private String  susep;
    private String  nomeImagem;
    private String telefoneCelular;
    private String telefoneOutro;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getSusep() {
        return susep;
    }

    public void setSusep(String susep) {
        this.susep = susep;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
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
