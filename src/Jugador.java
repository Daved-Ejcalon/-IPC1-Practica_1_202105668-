public class Jugador {
    // Atributos
    private String nombre;
    private int puntaje;
    private int fallos;
    private int aciertos;

    // Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 20; // Puntaje inicial de 20 puntos
        this.fallos = 0;
        this.aciertos = 0;
    }

    // Métodos
    public void aumentarPuntaje(String palabra) {
        this.puntaje += palabra.length();
    }

    public boolean disminuirPuntaje() {
        this.puntaje -= 5;

        // Si el puntaje es negativo, se envia false para indicar que el jugador perdió
        if (this.puntaje < 0) {
            return false;
        }
        // Si el puntaje es positivo, se envia true para indicar que el jugador no ha perdido
        return true;
    }

    public void aumentarFallos() {
        this.fallos++;
    }

    public void aumentarAciertos() {
        this.aciertos++;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getFallos() {
        return fallos;
    }

    public int getAciertos() {
        return aciertos;
    }

    @Override
    public String toString() {
        return  "Nombre = " + nombre  +
                ", Puntaje = " + puntaje +
                ", Fallos = " + fallos +
                ", Aciertos = " + aciertos +
                '\n';
    }



}
