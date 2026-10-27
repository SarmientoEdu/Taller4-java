
package app;

import modelo.*;   // GUIADO: trae Titular, CuentaAhorro y Banco desde el paquete modelo

/*
 * EJERCICIO 4 (EJ4) - Programa principal
 *
 * Main esta en el paquete "app"; las clases estan en "modelo". Por eso Main solo puede usar
 * lo que es PUBLIC (no puede usar validarMonto ni calcularComision directamente).
 *
 * Los datos NO se escriben a mano: salen de Escenario con la semilla.
 * Ejemplos: Escenario.nombre(semilla, 1), Escenario.edad(semilla, 2),
 * Escenario.montoOp(semilla, 3).
 *
 * Lea los comentarios de Escenario.java para ver que devuelve cada metodo.
 * Los formatos de salida deben coincidir exactamente con los del documento del taller.
 */
public class Main {

    public static void main(String[] args) {

        // GUIADO: estas dos primeras lineas no se deben cambiar.
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);

        // =========================================================
        // PARTE 1: TITULARES
        // Crear los dos titulares e imprimir sus datos.
        // Intentar cambiar sus edades y contar los jubilados.
        // =========================================================

        System.out.println("== PARTE 1: TITULARES ==");

        Titular t1 = new Titular(
                Escenario.nombre(semilla, 1),
                Escenario.apellido(semilla, 1),
                Escenario.edad(semilla, 1)
        );

        Titular t2 = new Titular(
                Escenario.nombre(semilla, 2),
                Escenario.apellido(semilla, 2),
                Escenario.edad(semilla, 2)
        );

        System.out.println("Titular 1: " + t1.getNombreCompleto()
                + " | edad=" + t1.getEdad()
                + " | jubilado=" + (t1.esJubilado() ? "SI" : "NO"));

        System.out.println("Titular 2: " + t2.getNombreCompleto()
                + " | edad=" + t2.getEdad()
                + " | jubilado=" + (t2.esJubilado() ? "SI" : "NO"));

        int edadNueva1 = Escenario.edadNueva(semilla, 1);
        int edadNueva2 = Escenario.edadNueva(semilla, 2);

        boolean cambio1 = t1.setEdad(edadNueva1);
        boolean cambio2 = t2.setEdad(edadNueva2);

        System.out.println("Cambio de edad titular 1 a " + edadNueva1
                + ": " + (cambio1 ? "ACEPTADO" : "RECHAZADO")
                + " | edad=" + t1.getEdad());

        System.out.println("Cambio de edad titular 2 a " + edadNueva2
                + ": " + (cambio2 ? "ACEPTADO" : "RECHAZADO")
                + " | edad=" + t2.getEdad());

        int jubilados = 0;

        if (t1.esJubilado()) {
            jubilados++;
        }

        if (t2.esJubilado()) {
            jubilados++;
        }

        System.out.println("Jubilados tras el cambio: " + jubilados);


        // =========================================================
        // PARTE 2: CUENTAS
        // Crear dos cuentas usando los dos constructores diferentes.
        // Mostrar el estado de cada cuenta.
        // =========================================================

        System.out.println("== PARTE 2: CUENTAS ==");

        CuentaAhorro cuenta1 = new CuentaAhorro(
                Escenario.numeroCuenta(semilla, 1),
                t1,
                Escenario.saldoInicial(semilla, 1)
        );

        CuentaAhorro cuenta2 = new CuentaAhorro(
                Escenario.numeroCuenta(semilla, 2),
                t2
        );

        cuenta2.depositar(Escenario.saldoInicial(semilla, 2));

        System.out.println(cuenta1.estado());
        System.out.println(cuenta2.estado());


        // =========================================================
        // PARTE 3: OPERACIONES
        // Ejecutar seis operaciones de deposito o retiro.
        // La cuenta, el tipo y el monto vienen de Escenario.
        // =========================================================

        System.out.println("== PARTE 3: OPERACIONES ==");

        for (int i = 1; i <= 6; i++) {

            int tipo = Escenario.tipoOp(semilla, i);
            int numero = Escenario.cuentaOp(semilla, i);
            double monto = Escenario.montoOp(semilla, i);

            CuentaAhorro cuenta;

            if (numero == 1) {
                cuenta = cuenta1;
            } else {
                cuenta = cuenta2;
            }

            boolean resultado;

            if (tipo == 1) {
                resultado = cuenta.depositar(monto);
            } else {
                resultado = cuenta.retirar(monto);
            }

            String operacion;

            if (tipo == 1) {
                operacion = "DEPOSITO";
            } else {
                operacion = "RETIRO";
            }

            String estadoOperacion;

            if (resultado) {
                estadoOperacion = "OK";
            } else {
                estadoOperacion = "RECHAZADO";
            }

            System.out.println("Op " + i + " " + operacion + " "
                    + Contexto.dinero(monto) + " cuenta "
                    + cuenta.getNumero() + ": " + estadoOperacion
                    + " | saldo=" + Contexto.dinero(cuenta.getSaldo()));
        }


        // =========================================================
        // PARTE 4: TRANSFERENCIA
        // Determinar la cuenta de origen y la cuenta de destino.
        // Realizar la transferencia e imprimir los saldos finales.
        // =========================================================

        System.out.println("== PARTE 4: TRANSFERENCIA ==");

        int origen = Escenario.cuentaOrigen(semilla);
        double montoTransferencia = Escenario.montoTransferencia(semilla);

        CuentaAhorro cuentaOrigen;
        CuentaAhorro cuentaDestino;

        if (origen == 1) {
            cuentaOrigen = cuenta1;
            cuentaDestino = cuenta2;
        } else {
            cuentaOrigen = cuenta2;
            cuentaDestino = cuenta1;
        }

        boolean transferencia = cuentaOrigen.transferir(
                cuentaDestino, montoTransferencia
        );

        String resultadoTransferencia;

        if (transferencia) {
            resultadoTransferencia = "OK";
        } else {
            resultadoTransferencia = "RECHAZADA";
        }

        System.out.println("Transferencia de "
                + cuentaOrigen.getNumero() + " a "
                + cuentaDestino.getNumero() + " por "
                + Contexto.dinero(montoTransferencia) + ": "
                + resultadoTransferencia);

        System.out.println("Origen: saldo="
                + Contexto.dinero(cuentaOrigen.getSaldo())
                + " | Destino: saldo="
                + Contexto.dinero(cuentaDestino.getSaldo()));


        // =========================================================
        // PARTE 5: BANCO
        // Crear el banco y mostrar el resumen de sus cuentas.
        // =========================================================

        System.out.println("== PARTE 5: BANCO ==");

        Banco banco = new Banco(cuenta1, cuenta2);

        System.out.println("Total depositos: "
                + Contexto.dinero(banco.totalDepositos()));

        CuentaAhorro mayor = banco.cuentaMayorSaldo();

        System.out.println("Cuenta con mayor saldo: "
                + mayor.getNumero());

        double montoComision = Escenario.montoComision(semilla);

        double comision = banco.comisionEstimada(
                mayor, montoComision
        );

        System.out.println("Comision estimada cuenta "
                + mayor.getNumero() + " por "
                + Contexto.dinero(montoComision) + ": "
                + Contexto.dinero(comision));

        System.out.println("Movimientos totales: "
                + banco.totalMovimientos());
    }
}
