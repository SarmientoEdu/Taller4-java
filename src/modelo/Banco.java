package modelo;

/*
 * EJERCICIO 3 (EJ3) - Clase Banco
 */
public class Banco {

    private CuentaAhorro cuenta1;
    private CuentaAhorro cuenta2;

    public Banco(CuentaAhorro cuenta1, CuentaAhorro cuenta2) {
        this.cuenta1 = cuenta1;
        this.cuenta2 = cuenta2;
    }

    public double totalDepositos() {
        return cuenta1.getSaldo() + cuenta2.getSaldo();
    }

    public CuentaAhorro cuentaMayorSaldo() {
        if (cuenta1.getSaldo() >= cuenta2.getSaldo()) {
            return cuenta1;
        }
        return cuenta2;
    }

    // se puede llamar porque Banco esta en el mismo paquete que CuentaAhorro
    public double comisionEstimada(CuentaAhorro cuenta, double monto) {
        return cuenta.calcularComision(monto);
    }

    public int totalMovimientos() {
        return cuenta1.getMovimientos() + cuenta2.getMovimientos();
    }
}