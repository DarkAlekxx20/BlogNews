package org.blognews.model;
/**
 * @author Alex SP
 */
public class Respuesta{
    private int idRespuesta;
    private String texto;
    private String fechayHora;
    private Comentario comentario;
    private Usuario usuario;

    public Respuesta() {}

    public Respuesta(int idRespuesta, String texto, String fechayHora, Comentario comentario, Usuario usuario) {
        this.idRespuesta = idRespuesta;
        this.texto = texto;
        this.fechayHora = fechayHora;
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(String fechayHora) {
        this.fechayHora = fechayHora;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Respuesta{" + "idRespuesta=" + idRespuesta + ", texto=" + texto + ", fechayHora=" + fechayHora + ", comentario=" + comentario + ", usuario=" + usuario + '}';
    }
}