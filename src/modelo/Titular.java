package modelo;

/*
 * EJERCICIO 1 (EJ1) - Clase Titular
 */
public class Titular {

    private String nombre;
    private String apellido;
    private int edad;

    public Titular(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // solo acepta edades entre 18 y 100
    public boolean setEdad(int nuevaEdad) {
        if (nuevaEdad >= 18 && nuevaEdad <= 100) {
            edad = nuevaEdad;
            return true;
        }
        return false;
    }

    public boolean esJubilado() {
        return edad >= 60;
    }
}