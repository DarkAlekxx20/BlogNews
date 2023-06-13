package org.blognews.model;
/**
 * @author Alex SP
 */
public class Comentario{
    private int idComentario;
    private String texto;
    private String fechayHora;
    private Noticia noticia;
    private Usuario usuario;

    public Comentario(){}

    public Comentario(int idComentario, String texto, String fechayHora, Noticia noticia, Usuario usuario) {
        this.idComentario = idComentario;
        this.texto = texto;
        this.fechayHora = fechayHora;
        this.noticia = noticia;
        this.usuario = usuario;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
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

    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Comentario{" + "idComentario=" + idComentario + ", texto=" + texto + ", fechayHora=" + fechayHora + ", noticia=" + noticia + ", usuario=" + usuario + '}';
    }
}