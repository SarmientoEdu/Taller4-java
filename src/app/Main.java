package app;

import modelo.*;

public class Main {
    public static void main(String[] args) {
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);

        // PARTE 1
        System.out.println("== PARTE 1: TITULARES ==");
        Titular t1 = new Titular(Escenario.nombre(semilla, 1), Escenario.apellido(semilla, 1), Escenario.edad(semilla, 1));
        Titular t2 = new Titular(Escenario.nombre(semilla, 2), Escenario.apellido(semilla, 2), Escenario.edad(semilla, 2));
        System.out.println("Titular 1: " + t1.getNombreCompleto() + " | edad=" + t1.getEdad() + " | jubilado=" + (t1.esJubilado() ? "SI" : "NO"));
        System.out.println("Titular 2: " + t2.getNombreCompleto() + " | edad=" + t2.getEdad() + " | jubilado=" + (t2.esJubilado() ? "SI" : "NO"));
        int nueva1 = Escenario.edadNueva(semilla, 1);
        boolean ok1 = t1.setEdad(nueva1);
        System.out.println("Cambio de edad titular 1 a " + nueva1 + ": " + (ok1 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + t1.getEdad());
        int nueva2 = Escenario.edadNueva(semilla, 2);
        boolean ok2 = t2.setEdad(nueva2);
        System.out.println("Cambio de edad titular 2 a " + nueva2 + ": " + (ok2 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + t2.getEdad());
        int jubilados = 0;
        if (t1.esJubilado()) {
            jubilados++;
        }
        if (t2.esJubilado()) {
            jubilados++;
        }
        System.out.println("Jubilados tras el cambio: " + jubilados);

        // PARTE 2
        CuentaAhorro c1 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 1), t1, Escenario.saldoInicial(semilla, 1));
        CuentaAhorro c2 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 2), t2);
        c2.depositar(Escenario.saldoInicial(semilla, 2));
        System.out.println("== PARTE 2: CUENTAS ==");
        System.out.println(c1.estado());
        System.out.println(c2.estado());

        // PARTE 3
        System.out.println("== PARTE 3: OPERACIONES ==");
        for (int i = 1; i <= 6; i++) {
            int numCuenta = Escenario.cuentaOp(semilla, i);
            int tipo = Escenario.tipoOp(semilla, i);
            double monto = Escenario.montoOp(semilla, i);
            CuentaAhorro c;
            if (numCuenta == 1) {
                c = c1;
            } else {
                c = c2;
            }
            String nombreTipo;
            boolean ok;
            if (tipo == 1) {
                nombreTipo = "DEPOSITO";
                ok = c.depositar(monto);
            } else {
                nombreTipo = "RETIRO";
                ok = c.retirar(monto);
            }
            System.out.println("Op " + i + " " + nombreTipo + " " + Contexto.dinero(monto) + " cuenta " + c.getNumero()
                    + ": " + (ok ? "OK" : "RECHAZADO") + " | saldo=" + Contexto.dinero(c.getSaldo()));
        }

        // PARTE 4
        System.out.println("== PARTE 4: TRANSFERENCIA ==");
        CuentaAhorro origen;
        CuentaAhorro destino;
        if (Escenario.cuentaOrigen(semilla) == 1) {
            origen = c1;
            destino = c2;
        } else {
            origen = c2;
            destino = c1;
        }
        double montoT = Escenario.montoTransferencia(semilla);
        boolean okT = origen.transferir(destino, montoT);
        System.out.println("Transferencia de " + origen.getNumero() + " a " + destino.getNumero() + " por "
                + Contexto.dinero(montoT) + ": " + (okT ? "OK" : "RECHAZADA"));
        System.out.println("Origen: saldo=" + Contexto.dinero(origen.getSaldo()) + " | Destino: saldo=" + Contexto.dinero(destino.getSaldo()));

        // PARTE 5
        System.out.println("== PARTE 5: BANCO ==");
        Banco banco = new Banco(c1, c2);
        CuentaAhorro mayor = banco.cuentaMayorSaldo();
        double montoCom = Escenario.montoComision(semilla);
        System.out.println("Total depositos: " + Contexto.dinero(banco.totalDepositos()));
        System.out.println("Cuenta con mayor saldo: " + mayor.getNumero());
        System.out.println("Comision estimada cuenta " + mayor.getNumero() + " por " + Contexto.dinero(montoCom) + ": "
                + Contexto.dinero(banco.comisionEstimada(mayor, montoCom)));
        System.out.println("Movimientos totales: " + banco.totalMovimientos());
    }
}