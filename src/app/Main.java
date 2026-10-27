package app;

import modelo.*;   // GUIADO: trae Titular, CuentaAhorro y Banco desde el paquete modelo

public class Main {

    public static void main(String[] args) {
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);   // GUIADO: imprime las 2 primeras lineas (no las cambie)

        // PARTE 1
        System.out.println("== PARTE 1: TITULARES ==");
        Titular t1 = new Titular(Escenario.nombre(semilla, 1), Escenario.apellido(semilla, 1), Escenario.edad(semilla, 1));
        Titular t2 = new Titular(Escenario.nombre(semilla, 2), Escenario.apellido(semilla, 2), Escenario.edad(semilla, 2));

        String jub1;
        if (t1.esJubilado()) {
            jub1 = "SI";
        } else {
            jub1 = "NO";
        }
        String jub2;
        if (t2.esJubilado()) {
            jub2 = "SI";
        } else {
            jub2 = "NO";
        }
        System.out.println("Titular 1: " + t1.getNombreCompleto() + " | edad=" + t1.getEdad() + " | jubilado=" + jub1);
        System.out.println("Titular 2: " + t2.getNombreCompleto() + " | edad=" + t2.getEdad() + " | jubilado=" + jub2);

        int nueva1 = Escenario.edadNueva(semilla, 1);
        String res1;
        if (t1.setEdad(nueva1)) {
            res1 = "ACEPTADO";
        } else {
            res1 = "RECHAZADO";
        }
        System.out.println("Cambio de edad titular 1 a " + nueva1 + ": " + res1 + " | edad=" + t1.getEdad());

        int nueva2 = Escenario.edadNueva(semilla, 2);
        String res2;
        if (t2.setEdad(nueva2)) {
            res2 = "ACEPTADO";
        } else {
            res2 = "RECHAZADO";
        }
        System.out.println("Cambio de edad titular 2 a " + nueva2 + ": " + res2 + " | edad=" + t2.getEdad());

        int jubilados = 0;
        if (t1.esJubilado()) {
            jubilados++;
        }
        if (t2.esJubilado()) {
            jubilados++;
        }
        System.out.println("Jubilados tras el cambio: " + jubilados);

        // PARTE 2
        System.out.println("== PARTE 2: CUENTAS ==");
        CuentaAhorro cuenta1 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 1), t1, Escenario.saldoInicial(semilla, 1));
        CuentaAhorro cuenta2 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 2), t2);
        cuenta2.depositar(Escenario.saldoInicial(semilla, 2));
        System.out.println(cuenta1.estado());
        System.out.println(cuenta2.estado());

        // PARTE 3
        System.out.println("== PARTE 3: OPERACIONES ==");
        for (int i = 1; i <= 6; i++) {
            int numCuenta = Escenario.cuentaOp(semilla, i);
            int tipo = Escenario.tipoOp(semilla, i);
            double monto = Escenario.montoOp(semilla, i);

            CuentaAhorro c;
            if (numCuenta == 1) {
                c = cuenta1;
            } else {
                c = cuenta2;
            }

            boolean ok;
            String tipoTxt;
            if (tipo == 1) {
                ok = c.depositar(monto);
                tipoTxt = "DEPOSITO";
            } else {
                ok = c.retirar(monto);
                tipoTxt = "RETIRO";
            }

            String resultado;
            if (ok) {
                resultado = "OK";
            } else {
                resultado = "RECHAZADO";
            }
            System.out.println("Op " + i + " " + tipoTxt + " " + Contexto.dinero(monto) + " cuenta " + c.getNumero()
                    + ": " + resultado + " | saldo=" + Contexto.dinero(c.getSaldo()));
        }

        // PARTE 4
        System.out.println("== PARTE 4: TRANSFERENCIA ==");
        CuentaAhorro origen;
        CuentaAhorro destino;
        if (Escenario.cuentaOrigen(semilla) == 1) {
            origen = cuenta1;
            destino = cuenta2;
        } else {
            origen = cuenta2;
            destino = cuenta1;
        }
        double montoT = Escenario.montoTransferencia(semilla);
        String resT;
        if (origen.transferir(destino, montoT)) {
            resT = "OK";
        } else {
            resT = "RECHAZADA";
        }
        System.out.println("Transferencia de " + origen.getNumero() + " a " + destino.getNumero()
                + " por " + Contexto.dinero(montoT) + ": " + resT);
        System.out.println("Origen: saldo=" + Contexto.dinero(origen.getSaldo())
                + " | Destino: saldo=" + Contexto.dinero(destino.getSaldo()));

        // PARTE 5
        System.out.println("== PARTE 5: BANCO ==");
        Banco banco = new Banco(cuenta1, cuenta2);
        CuentaAhorro mayor = banco.cuentaMayorSaldo();
        double montoC = Escenario.montoComision(semilla);
        System.out.println("Total depositos: " + Contexto.dinero(banco.totalDepositos()));
        System.out.println("Cuenta con mayor saldo: " + mayor.getNumero());
        System.out.println("Comision estimada cuenta " + mayor.getNumero() + " por " + Contexto.dinero(montoC)
                + ": " + Contexto.dinero(banco.comisionEstimada(mayor, montoC)));
        System.out.println("Movimientos totales: " + banco.totalMovimientos());
    }
}