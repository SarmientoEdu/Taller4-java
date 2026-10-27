package modelo;

import app.Contexto;   // GUIADO: se usa solo en estado() -> Contexto.dinero(valor)

/*
 * EJERCICIO 2 (EJ2) - Clase CuentaAhorro
 */
public class CuentaAhorro {

    private int numero;
    private double saldo;
    private Titular titular;
    private int movimientos;

    public CuentaAhorro(int numero, Titular titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0;
        this.movimientos = 0;
    }

    public CuentaAhorro(int numero, Titular titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.movimientos = 0;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Titular getTitular() {
        return titular;
    }

    public int getMovimientos() {
        return movimientos;
    }

    // visibilidad de paquete (sin modificador)
    boolean validarMonto(double monto) {
        return monto > 0 && monto <= 10000;
    }

    protected double calcularComision(double monto) {
        if (titular.esJubilado()) {
            return 0.0;
        } else if (monto > 500) {
            return monto * 0.01;
        } else if (monto > 100) {
            return 0.50;
        } else {
            return 0.25;
        }
    }

    public boolean depositar(double monto) {
        if (!validarMonto(monto)) {
            return false;
        }
        saldo = saldo + monto;
        movimientos++;
        return true;
    }

    public boolean retirar(double monto) {
        if (!validarMonto(monto)) {
            return false;
        }
        double total = monto + calcularComision(monto);
        if (total > saldo) {
            return false;
        }
        saldo = saldo - total;
        movimientos++;
        return true;
    }

    // destino llega por referencia, asi que el deposito cambia la cuenta original
    public boolean transferir(CuentaAhorro destino, double monto) {
        if (!retirar(monto)) {
            return false;
        }
        destino.depositar(monto);
        return true;
    }

    public String estado() {
        return "Cuenta " + numero + " | titular=" + titular.getNombreCompleto()
                + " | saldo=" + Contexto.dinero(saldo) + " | movimientos=" + movimientos;
    }
}