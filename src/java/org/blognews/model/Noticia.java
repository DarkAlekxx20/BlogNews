package org.blognews.model;
/**
 * @author Alex SP
 */
public class Noticia{
    private int idNoticia;
    private String titulo;
    private String contenido;
    private String fechaPublicacion;
    private Personal personal;

    public Noticia(){}

    public Noticia(int idNoticia, String titulo, String contenido, String fechaPublicacion, Personal personal) {
        this.idNoticia = idNoticia;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.personal = personal;
    }

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    @Override
    public String toString() {
        return "Noticia{" + "idNoticia=" + idNoticia + ", titulo=" + titulo + ", contenido=" + contenido + ", fechaPublicacion=" + fechaPublicacion + ", personal=" + personal + '}';
    }
}