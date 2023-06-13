package org.blognews.model;
/**
 * @author Alex SP
 */
public class Personal{
    private int idPersonal;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String direccion;
    private String fechaIngreso;

    public Personal(){}

    public Personal(int idPersonal, String apellidoPaterno, String apellidoMaterno, String nombre, String direccion, String fechaIngreso) {
        this.idPersonal = idPersonal;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return "Personal{" + "idPersonal=" + idPersonal + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", nombre=" + nombre + ", direccion=" + direccion + ", fechaIngreso=" + fechaIngreso + '}';
    }
}