package tony.conecta4;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Tony on 14/11/2016.
 */

public class Game {
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    static final String JUGADORGANA = "1111";
    static final String MAQUINAGANA = "2222";
    private int turno;
    public int[][] tablero;

    public int getTurno() {
        return turno;
    }
    void setTurno(int turno){
        this.turno = turno;
    }
    public int getTurnoJugador() {
        return JUGADOR;
    }
    void setFicha(int i,int j){
        tablero[i][j] = this.turno;
    }

    public Game(int jugador) {
        tablero =  new int[NFILAS][NCOLUMNAS];
        inicializarVacioTablero();
        turno = jugador;
    }

    private void inicializarVacioTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    boolean isVacio(int i,int j){
        if (tablero[i][j]== VACIO)
            return true;
        return false;
    }
    void cambiarTurno(){
        if (getTurno() == JUGADOR){
            setTurno(MAQUINA);
        }else{
            setTurno(JUGADOR);
        }
    }

    public String fila(int fila){
        String patronFila = "";
        for (int i = 0; i < NCOLUMNAS; i++) {
            patronFila+=tablero[fila][i];
        }
        return patronFila;
    }
    public String columna(int columna){
        String patronColumna = "";
        for (int i = 0; i < NFILAS-1; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                if (j == columna) {
                    for (int k = 0; k < NFILAS; k++) {
                        patronColumna += tablero[k][j];
                    }
                }
            }
        }
        return patronColumna;
    }

    String diagonal(int fila, int columna) {
        String patronDiagonal = "";
        for (int i = fila, j= columna; i < NFILAS && j < NCOLUMNAS; i++, j++)
            patronDiagonal += tablero[i][j];
        for (int i = fila-1, j= columna - 1; j >= 0 && i >=0; i--, j--)
            patronDiagonal = tablero[i][j] + patronDiagonal;

        return patronDiagonal;
    }

    String diagonalInversa(int fila, int columna) {
        String patronDiagonalInversa = "";
        for (int i = fila, j= columna; i < NFILAS && j >= 0; i++, j--)
            patronDiagonalInversa += tablero[i][j];
        for (int i = fila-1, j= columna + 1; j < NCOLUMNAS && i >=0; i--, j++)
            patronDiagonalInversa = tablero[i][j] + patronDiagonalInversa;

        return patronDiagonalInversa;
    }

    public boolean comprobarJugada(int fila, int columna){
        if (fila(fila).contains(MAQUINAGANA) || fila(fila).contains(JUGADORGANA)){
            return true;
        }
        if (columna(columna).contains(MAQUINAGANA) || columna(columna).contains(JUGADORGANA)){
            return true;
        }
        if (diagonal(fila,columna).contains(MAQUINAGANA) || diagonal(fila,columna).contains(JUGADORGANA)){
            return true;
        }
        if (diagonalInversa(fila,columna).contains(MAQUINAGANA) || diagonalInversa(fila,columna).contains(JUGADORGANA)){
            return true;
        }
        return false;
    }
    public String tableroToString(){
        String cadenaTablero = "";
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                cadenaTablero += tablero[i][j];
            }
        }
        return cadenaTablero;
    }

    public void stringToTablero(String cadenaTablero){
        int contador = 0;
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                tablero[i][j] = cadenaTablero.charAt(contador) -48;
            }
        }
    }
}
