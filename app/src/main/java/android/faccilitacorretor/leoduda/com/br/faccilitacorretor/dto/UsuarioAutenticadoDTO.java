package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.dto;

/**
 * Created by turbiani on 05/09/15.
 */
public class UsuarioAutenticadoDTO {
    private String nome;
    private String email;
    private String token;
    private String imageURL;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
