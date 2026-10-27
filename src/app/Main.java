package app;

import modelo.*;   // GUIADO: trae Titular, CuentaAhorro y Banco desde el paquete modelo

/*
 * EJERCICIO 4 (EJ4) - Programa principal                             [50 pts de salida + 3 de estructura]
 *
 * Main esta en el paquete "app"; las clases estan en "modelo". Por eso Main solo puede usar
 * lo que es PUBLIC (no puede usar validarMonto ni calcularComision directamente).
 *
 * Los datos NO se escriben a mano: salen de Escenario con la semilla.
 * Ejemplos:  Escenario.nombre(semilla, 1)   Escenario.edad(semilla, 2)   Escenario.montoOp(semilla, 3)
 * Lea los comentarios de Escenario.java para ver que devuelve cada metodo.
 *
 * Los formatos EXACTOS de salida estan en el documento del taller (Parte 4: Main).
 */
public class Main {

    public static void main(String[] args) {
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);   // GUIADO: imprime las 2 primeras lineas (no las cambie)

        // PARTE 1: crear los 2 titulares, imprimir, intentar cambiar la edad, contar jubilados
        Titular titular1 = new Titular(Escenario.nombre(semilla, 1),
                Escenario.apellido(semilla, 1), Escenario.edad(semilla, 1));
        Titular titular2 = new Titular(Escenario.nombre(semilla, 2),
                Escenario.apellido(semilla, 2), Escenario.edad(semilla, 2));

        System.out.println("== PARTE 1: TITULARES ==");
        String jubilado1 = titular1.esJubilado() ? "SI" : "NO";
        System.out.println("Titular 1: " + titular1.getNombreCompleto()
                + " | edad=" + titular1.getEdad() + " | jubilado=" + jubilado1);
        String jubilado2 = titular2.esJubilado() ? "SI" : "NO";
        System.out.println("Titular 2: " + titular2.getNombreCompleto()
                + " | edad=" + titular2.getEdad() + " | jubilado=" + jubilado2);

        int nuevaEdad1 = Escenario.edadNueva(semilla, 1);
        boolean aceptada1 = titular1.setEdad(nuevaEdad1);
        System.out.println("Cambio de edad titular 1 a " + nuevaEdad1 + ": "
                + (aceptada1 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + titular1.getEdad());

        int nuevaEdad2 = Escenario.edadNueva(semilla, 2);
        boolean aceptada2 = titular2.setEdad(nuevaEdad2);
        System.out.println("Cambio de edad titular 2 a " + nuevaEdad2 + ": "
                + (aceptada2 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + titular2.getEdad());

        int jubilados = 0;
        if (titular1.esJubilado()) {
            jubilados = jubilados + 1;
        }
        if (titular2.esJubilado()) {
            jubilados = jubilados + 1;
        }
        System.out.println("Jubilados tras el cambio: " + jubilados);

        // PARTE 2: crear las 2 cuentas (una con cada constructor) e imprimir su estado()
        CuentaAhorro cuenta1 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 1), titular1,
                Escenario.saldoInicial(semilla, 1));
        CuentaAhorro cuenta2 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 2), titular2);
        cuenta2.depositar(Escenario.saldoInicial(semilla, 2));

        System.out.println("== PARTE 2: CUENTAS ==");
        System.out.println(cuenta1.estado());
        System.out.println(cuenta2.estado());

        // PARTE 3: ciclo for de 6 operaciones (deposito/retiro) sobre la cuenta indicada
        System.out.println("== PARTE 3: OPERACIONES ==");
        for (int i = 1; i <= 6; i++) {
            CuentaAhorro cuentaOperacion = cuenta1;
            if (Escenario.cuentaOp(semilla, i) == 2) {
                cuentaOperacion = cuenta2;
            }

            double monto = Escenario.montoOp(semilla, i);
            boolean exitosa;
            String nombreOperacion;
            if (Escenario.tipoOp(semilla, i) == 1) {
                nombreOperacion = "DEPOSITO";
                exitosa = cuentaOperacion.depositar(monto);
            } else {
                nombreOperacion = "RETIRO";
                exitosa = cuentaOperacion.retirar(monto);
            }

            System.out.println("Op " + i + " " + nombreOperacion + " " + Contexto.dinero(monto)
                    + " cuenta " + cuentaOperacion.getNumero() + ": "
                    + (exitosa ? "OK" : "RECHAZADO")
                    + " | saldo=" + Contexto.dinero(cuentaOperacion.getSaldo()));
        }

        // PARTE 4: transferencia entre las cuentas
        CuentaAhorro origen = cuenta1;
        CuentaAhorro destino = cuenta2;
        if (Escenario.cuentaOrigen(semilla) == 2) {
            origen = cuenta2;
            destino = cuenta1;
        }

        double montoTransferencia = Escenario.montoTransferencia(semilla);
        boolean transferida = origen.transferir(destino, montoTransferencia);

        System.out.println("== PARTE 4: TRANSFERENCIA ==");
        System.out.println("Transferencia de " + origen.getNumero() + " a " + destino.getNumero()
                + " por " + Contexto.dinero(montoTransferencia) + ": "
                + (transferida ? "OK" : "RECHAZADA"));
        System.out.println("Origen: saldo=" + Contexto.dinero(origen.getSaldo())
                + " | Destino: saldo=" + Contexto.dinero(destino.getSaldo()));

        // PARTE 5: crear el Banco e imprimir el resumen
        Banco banco = new Banco(cuenta1, cuenta2);
        CuentaAhorro mayor = banco.cuentaMayorSaldo();
        double montoComision = Escenario.montoComision(semilla);

        System.out.println("== PARTE 5: BANCO ==");
        System.out.println("Total depositos: " + Contexto.dinero(banco.totalDepositos()));
        System.out.println("Cuenta con mayor saldo: " + mayor.getNumero());
        System.out.println("Comision estimada cuenta " + mayor.getNumero() + " por "
                + Contexto.dinero(montoComision) + ": "
                + Contexto.dinero(banco.comisionEstimada(mayor, montoComision)));
        System.out.println("Movimientos totales: " + banco.totalMovimientos());
    }

}
