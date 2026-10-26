package app;

import modelo.*;

public class Main {
    public static void main(String[] args) {
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);

        // ---------- PARTE 1 ----------
        System.out.println("== PARTE 1: TITULARES ==");
        Titular t1 = new Titular(Escenario.nombre(semilla, 1), Escenario.apellido(semilla, 1), Escenario.edad(semilla, 1));
        Titular t2 = new Titular(Escenario.nombre(semilla, 2), Escenario.apellido(semilla, 2), Escenario.edad(semilla, 2));
        String j1 = "NO";
        if (t1.esJubilado()) { j1 = "SI"; }
        String j2 = "NO";
        if (t2.esJubilado()) { j2 = "SI"; }
        System.out.println("Titular 1: " + t1.getNombreCompleto() + " | edad=" + t1.getEdad() + " | jubilado=" + j1);
        System.out.println("Titular 2: " + t2.getNombreCompleto() + " | edad=" + t2.getEdad() + " | jubilado=" + j2);

        int n1 = Escenario.edadNueva(semilla, 1);
        String r1 = "RECHAZADO";
        if (t1.setEdad(n1)) { r1 = "ACEPTADO"; }
        System.out.println("Cambio de edad titular 1 a " + n1 + ": " + r1 + " | edad=" + t1.getEdad());
        int n2 = Escenario.edadNueva(semilla, 2);
        String r2 = "RECHAZADO";
        if (t2.setEdad(n2)) { r2 = "ACEPTADO"; }
        System.out.println("Cambio de edad titular 2 a " + n2 + ": " + r2 + " | edad=" + t2.getEdad());

        int jubilados = 0;
        if (t1.esJubilado()) { jubilados++; }
        if (t2.esJubilado()) { jubilados++; }
        System.out.println("Jubilados tras el cambio: " + jubilados);

        // ---------- PARTE 2 ----------
        System.out.println("== PARTE 2: CUENTAS ==");
        CuentaAhorro c1 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 1), t1, Escenario.saldoInicial(semilla, 1));
        CuentaAhorro c2 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 2), t2);
        c2.depositar(Escenario.saldoInicial(semilla, 2));
        System.out.println(c1.estado());
        System.out.println(c2.estado());

        // ---------- PARTE 3 ----------
        System.out.println("== PARTE 3: OPERACIONES ==");
        for (int i = 1; i <= 6; i++) {
            CuentaAhorro c = c1;
            if (Escenario.cuentaOp(semilla, i) == 2) { c = c2; }
            double monto = Escenario.montoOp(semilla, i);
            String nombreOp;
            boolean ok;
            if (Escenario.tipoOp(semilla, i) == 1) {
                nombreOp = "DEPOSITO";
                ok = c.depositar(monto);
            } else {
                nombreOp = "RETIRO";
                ok = c.retirar(monto);
            }
            String res = "RECHAZADO";
            if (ok) { res = "OK"; }
            System.out.println("Op " + i + " " + nombreOp + " " + Contexto.dinero(monto) + " cuenta " + c.getNumero()
                    + ": " + res + " | saldo=" + Contexto.dinero(c.getSaldo()));
        }

        // ---------- PARTE 4 ----------
        System.out.println("== PARTE 4: TRANSFERENCIA ==");
        CuentaAhorro origen = c1;
        CuentaAhorro destino = c2;
        if (Escenario.cuentaOrigen(semilla) == 2) { origen = c2; destino = c1; }
        double montoT = Escenario.montoTransferencia(semilla);
        String rt = "RECHAZADA";
        if (origen.transferir(destino, montoT)) { rt = "OK"; }
        System.out.println("Transferencia de " + origen.getNumero() + " a " + destino.getNumero()
                + " por " + Contexto.dinero(montoT) + ": " + rt);
        System.out.println("Origen: saldo=" + Contexto.dinero(origen.getSaldo()) + " | Destino: saldo=" + Contexto.dinero(destino.getSaldo()));

        // ---------- PARTE 5 ----------
        System.out.println("== PARTE 5: BANCO ==");
        Banco banco = new Banco(c1, c2);
        System.out.println("Total depositos: " + Contexto.dinero(banco.totalDepositos()));
        CuentaAhorro mayor = banco.cuentaMayorSaldo();
        System.out.println("Cuenta con mayor saldo: " + mayor.getNumero());
        double montoC = Escenario.montoComision(semilla);
        System.out.println("Comision estimada cuenta " + mayor.getNumero() + " por " + Contexto.dinero(montoC)
                + ": " + Contexto.dinero(banco.comisionEstimada(mayor, montoC)));
        System.out.println("Movimientos totales: " + banco.totalMovimientos());
    }
}
