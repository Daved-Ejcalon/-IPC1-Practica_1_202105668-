import java.util.Random;
import java.util.Scanner;

public class MenuSopaDeLetras {

    // Constructor
    public MenuSopaDeLetras(int filas, int columnas) {
        this.sopaDeLetras = new char[filas][columnas];
        this.palabras = new String[filas * columnas];
        this.cantidadPalabras = 0;
        inicializarSopaDeLetras();
    }

    // Variables consola
    
    public final static String ANSI_RED = "\u001B[31m";
    public final static String ANSI_GREEN = "\u001B[32m";
    public final static String ANSI_BLUE = "\u001B[34m";
    public final static String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Variables sopa de letras
    private char[][] sopaDeLetras;
    private String[] palabras;
    private int cantidadPalabras;
    //Variables historial
    public static Jugador[] historialJugadores = new Jugador[1];
    public static int cantidadJugadores;


    // Metodos historial
    public void aumentarHistorial() {
        // Crear un nuevo arreglo con una posición más grande
        Jugador[] nuevoHistorial = new Jugador[historialJugadores.length + 1];
        // Copiar los elementos del arreglo anterior al nuevo arreglo
        for (int i = 0; i < historialJugadores.length; i++) {
            nuevoHistorial[i] = historialJugadores[i];
        }
        // Asignar el nuevo arreglo al arreglo de la clase
        historialJugadores = nuevoHistorial;
    }

    public void agregarJugadorHistorial(Jugador jugador) {
        System.out.println("Agregando partida al historial...");
        // Si el arreglo está lleno (inicial de 1 puesto), aumentar el tamaño
        cantidadJugadores++; // Aumentar el contador de jugadores
        if(historialJugadores.length < cantidadJugadores){
            aumentarHistorial();
        }
        
        historialJugadores[historialJugadores.length - 1] = jugador;
    }

    public void imprimirHistorial() {
        if (historialJugadores == null) {
            System.out.println("No hay partidas registradas en el historial.");
            return;
        }

        for (int i = 0; i < historialJugadores.length; i++) {
            if(historialJugadores[i] != null){
                System.out.println(historialJugadores[i].toString());
            }
            
        }
    }

    // Metodos sopa de letras

    private void inicializarSopaDeLetras() {
        // Random random = new Random();

        for (int i = 0; i < sopaDeLetras.length; i++) {
            for (int j = 0; j < sopaDeLetras[i].length; j++) {
                sopaDeLetras[i][j] = generarLetraAleatoria();
            }
        }
    }

    public void agregarPalabra(String palabra) {
        palabras[cantidadPalabras] = palabra.toUpperCase();
        cantidadPalabras++;
    }

    public void modificarPalabra(int indice, String nuevaPalabra) {
        palabras[indice] = nuevaPalabra.toUpperCase();
    }

    public void eliminarPalabra(int indice) {
        palabras[indice] = null;
        // Reorganizar el arreglo para eliminar el espacio vacío
        for (int i = indice; i < cantidadPalabras - 1; i++) {
            palabras[i] = palabras[i + 1];
        }
        palabras[cantidadPalabras - 1] = null;
        cantidadPalabras--;
    }
    
    public void eliminarPalabra(String palabra) {
        palabra = palabra.toUpperCase();
        for (int i = 0; i < cantidadPalabras; i++) {
            if(palabras[i].equals(palabra)){
                eliminarPalabra(i);
                break;
            }
        }
    }

    private char generarLetraAleatoria() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'A');
    }

    public void generarSopaDeLetras() {
        Random random = new Random();

        for (int i = 0; i < cantidadPalabras; i++) {
            String palabra = palabras[i];
            int longitud = palabra.length();

            int filaInicial = random.nextInt(sopaDeLetras.length);
            int columnaInicial = random.nextInt(sopaDeLetras[0].length);
            boolean horizontal = random.nextBoolean();

            if (horizontal && columnaInicial + longitud <= sopaDeLetras[0].length) {
                for (int j = 0; j < longitud; j++) {
                    sopaDeLetras[filaInicial][columnaInicial + j] = palabra.charAt(j);
                }
            } else if (!horizontal && filaInicial + longitud <= sopaDeLetras.length) {
                for (int j = 0; j < longitud; j++) {
                    sopaDeLetras[filaInicial + j][columnaInicial] = palabra.charAt(j);
                }
            } else {
                i--;
            }
        }

        /* 
        //Impresión de la sopa de letras
        for (char[] fila : sopaDeLetras) {
            for (char letra : fila) {
                System.out.print(letra + " ");
            }
            System.out.println();
        }
        */
    }

    public void imprimirSopaDeLetras() {
        for (char[] fila : sopaDeLetras) {
            for (char letra : fila) {
                System.out.print(ANSI_CYAN +letra + " " + ANSI_RESET);
            }
            System.out.println();
        }
    }

    public boolean buscarPalabra(String palabra) {
       
        palabra = palabra.toUpperCase(); // Convertir la palabra a mayúsculas
        int rows = sopaDeLetras.length;
        int cols = sopaDeLetras[0].length;
        boolean encontrada = false;
    
        // Convertimos la palabra a buscar a un arreglo de caracteres
        char[] chars = palabra.toCharArray();
    
        // Buscamos en todas las filas de izquierda a derecha
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - chars.length; j++) {
                boolean found = true;
                for (int k = 0; k < chars.length; k++) {
                    if (sopaDeLetras[i][j + k] != chars[k]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    encontrada = true;
                    // Reemplazamos la palabra encontrada por el símbolo $
                    for (int k = 0; k < chars.length; k++) {
                        sopaDeLetras[i][j + k] = '$';
                    }
                }
            }
        }
    
        // Buscamos en todas las columnas de arriba hacia abajo
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i <= rows - chars.length; i++) {
                boolean found = true;
                for (int k = 0; k < chars.length; k++) {
                    if (sopaDeLetras[i + k][j] != chars[k]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    encontrada = true;
                    // Reemplazamos la palabra encontrada por el símbolo $
                    for (int k = 0; k < chars.length; k++) {
                        sopaDeLetras[i + k][j] = '$';
                    }
                }
            }
        }
    
        return encontrada;
    }

    public boolean confirmarPalabrasEncontradas(){

        for (String palabra : palabras) {
            if(palabra != null){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        char op = ' ';
        MenuSopaDeLetras sopa = null;
        Jugador jugadorActual = null;

        do {
            System.out.println("-----------MENU-----------");
            System.out.println("1.- Nueva Partida");
            System.out.println("2.- Jugar");
            System.out.println("3.- Mostrar informacion");
            System.out.println("4.- Historial de partidas");
            System.out.println("5.- Salir");
            System.out.println("--------------------------");

            op = scanner.next().charAt(0);

            switch (op) {
                case '1':
                    System.out.println("----NUEVA PARTIDA----");

                    int tam_minimo = 4;
                    
                    System.out.print("Ingrese el nombre del jugador: ");
                    String nombre = scanner.next();
                    jugadorActual = new Jugador(nombre);

                    System.out.print("Ingrese el numero de filas: ");
                    int filas = scanner.nextInt();

                    System.out.print("Ingrese el numero de columnas: ");
                    int columnas = scanner.nextInt();
                    if(filas < tam_minimo || columnas < tam_minimo){
                        System.out.println("El tamaño mínimo de la sopa de letras es de 4x4");
                        break;
                    }
                    sopa = new MenuSopaDeLetras(filas, columnas);

                    char subOp = ' ';
                    do {
                        System.out.println("---------CONFIGURAR PARTIDA---------");
                        System.out.println("1. Agregar Palabra");
                        System.out.println("2. Modificar Palabra");
                        System.out.println("3. Eliminar Palabra");
                        System.out.println("4. Volver al Menu Principal");
                        System.out.println("------------------------------------");

                        subOp = scanner.next().charAt(0);
                        scanner.nextLine();

                        switch (subOp) {
                            case '1':
                                System.out.print("Ingrese la palabra: ");
                                String palabra = scanner.nextLine();
                                sopa.agregarPalabra(palabra);
                                break;

                            case '2':
                                System.out.println("Lista de Palabras:");
                                for (int i = 0; i < sopa.cantidadPalabras; i++) {
                                    System.out.println((i + 1) + ". " + sopa.palabras[i]);
                                }

                                System.out.print("Ingrese el indice de la palabra a modificar: ");
                                int indiceMod = scanner.nextInt();
                                scanner.nextLine();

                                if (indiceMod >= 1 && indiceMod <= sopa.cantidadPalabras) {
                                    System.out.print("Ingrese la nueva palabra: ");
                                    String nuevaPalabra = scanner.nextLine();
                                    sopa.modificarPalabra(indiceMod - 1, nuevaPalabra);
                                } else {
                                    System.out.println("Indice invalido.");
                                }
                                break;

                            case '3':
                                System.out.println("Lista de Palabras:");
                                for (int i = 0; i < sopa.cantidadPalabras; i++) {
                                    System.out.println((i + 1) + ". " + sopa.palabras[i]);
                                }

                                System.out.print("Ingrese el indice de la palabra a eliminar: ");
                                int indiceElim = scanner.nextInt();
                                scanner.nextLine();

                                if (indiceElim >= 1 && indiceElim <= sopa.cantidadPalabras) {
                                    sopa.eliminarPalabra(indiceElim - 1);
                                } else {
                                    System.out.println("Indice invalido.");
                                }
                                break;

                            case '4':
                                System.out.println("Volviendo al Menu Principal...");
                                break;

                            default:
                                System.out.println("INGRESE UNA OPCION VALIDA");
                        }
                    } while (subOp != '4');

                    break;

                case '2':
                    // Inicio de partida
                    char op_partida = ' ';
                    if (sopa != null) {
                        System.out.println("Sopa de Letras:");
                        sopa.generarSopaDeLetras();
                        System.out.println("--------------PARTIDA--------------");
                        cicloPartida : do {
                            sopa.imprimirSopaDeLetras();
                            System.out.println("-----------------------------------");
                            System.out.println("JUGADOR: " + jugadorActual.getNombre());
                            System.out.println("1.- Buscar palabra");
                            System.out.println("2.- Terminar partida");
                            System.out.println("-----------------------------------");
                            op_partida = scanner.next().charAt(0);
                            scanner.nextLine();
                            switch (op_partida) {
                                case '1':
                                    System.out.println("Ingrese la palabra a buscar: ");
                                    String palabra = scanner.nextLine();

                                    if (sopa.buscarPalabra(palabra)) {
                                        System.out.println(ANSI_GREEN + "La palabra " + palabra + " ha sido encontrada."+ ANSI_RESET);
                                        jugadorActual.aumentarPuntaje(palabra);
                                        jugadorActual.aumentarAciertos();
                                        sopa.eliminarPalabra(palabra);
                                        System.out.println("Palabras restantes: " + sopa.cantidadPalabras);

                                        if(sopa.confirmarPalabrasEncontradas()){
                                            System.out.println(ANSI_GREEN + "-----FELICIDADES HAS GANADO-----" + ANSI_RESET);
                                            System.out.println("-------PARTIDA TERMINADA-------");
                                            sopa.agregarJugadorHistorial(jugadorActual);
                                            break cicloPartida; // Se sale del switch
                                        }

                                    } else {
                                        System.out.println(ANSI_RED + "La palabra " + palabra + " no ha sido encontrada." + ANSI_RESET);
                                        jugadorActual.aumentarFallos();
                                        boolean estadoJugador = jugadorActual.disminuirPuntaje();
                                        if(!estadoJugador){
                                            System.out.println(ANSI_RED + "Jugador eliminado."+ ANSI_RESET);
                                            op_partida = '2';
                                            System.out.println("-------PARTIDA TERMINADA-------");
                                            sopa.agregarJugadorHistorial(jugadorActual);
                                            break cicloPartida; // Se sale del switch
                                        }
                                    }

                                    break;
                                case '2':
                                    System.out.println("PARTIDA TERMINADA");
                                    sopa.agregarJugadorHistorial(jugadorActual);
                                    break cicloPartida;
                                default:
                                    System.out.println("INGRESE UNA OPCION VALIDA");
                                    break;
                            }
                        } while (op_partida != '2'); // Mientras no se seleccione la opcion de salir

                    } else {
                        System.out.println("Primero debe crear una nueva partida.");
                    }
                    System.out.println("-------------------------------------" + ANSI_RESET);
                    break;

                case '3':
                    System.out.println("------INFORMACION DE ESTUDIANTE------");
                    System.out.println("Nombre: DAVED ABSHALON EJCALON CHONAY");
                    System.out.println("Carnet: 202105668");
                    System.out.println("Seccion: P");
                    System.out.println("-------------------------------------");
                    break;

                case '4':
                    if(sopa == null){
                        System.out.println(ANSI_RED+"No hay historial."+ANSI_RESET);
                        break;
                    }
                    System.out.println(ANSI_CYAN + "--------------HISTORIAL--------------");
                    sopa.imprimirHistorial();
                    System.out.println("-------------------------------------" + ANSI_RESET);
                    break;
                case '5':
                    System.out.println("GRACIAS POR JUGAR");
                    scanner.close(); // cerrar el scanner
                    break;

                default:
                    System.out.println("INGRESE UNA OPCION VALIDA");
            }
        } while (op != '5');
    }
}
