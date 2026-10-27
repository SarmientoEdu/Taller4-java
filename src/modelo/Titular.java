package modelo;

/*
 * EJERCICIO 1 (EJ1) - Clase Titular                                  [8 pts de estructura]
 *
 * Una persona titular de una cuenta.
 *
 * ATRIBUTOS (todos PRIVATE):
 *   String nombre, String apellido, int edad
 *
 * CONSTRUCTOR (public):
 *   Titular(String nombre, String apellido, int edad)
 *
 * METODOS (todos public, no static):
 *   String  getNombre()          String getApellido()        int getEdad()
 *   String  getNombreCompleto()  -> "nombre apellido"  (ej. "Maria Gonzalez")
 *   boolean setEdad(int nuevaEdad)
 *        Metodo de seguridad: solo acepta edades de 18 a 100 (inclusive).
 *        Si es valida, la guarda y devuelve true; si no, NO cambia nada y devuelve false.
 *   boolean esJubilado()         -> true si edad >= 60
 *
 * PROHIBIDO: static, arreglos, listas, herencia, try/catch, lambdas (aun no se ven).
 */


    // TODO(EJ1): declare los 3 atributos privados

    // TODO(EJ1): constructor

    // TODO(EJ1): getters, getNombreCompleto, setEdad y esJubilado

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

    // Método de seguridad: solo cambia la edad si está entre 18 y 100
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
