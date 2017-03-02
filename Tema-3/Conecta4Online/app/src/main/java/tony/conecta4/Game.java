package tony.conecta4;

public class Game {
    public int[][] tablero;
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final int JUGADOR = 2;
    static final int MAQUINA = 1;
    static final String JUGADORGANA = "2222";
    static final String MAQUINAGANA = "1111";
    private String estado = "En curso";
    private String ganador = "";
    private int turno;
    static final String JUGADORCASIGANA_A = "222";
    static final String JUGADORCASIGANA_B = "22";
    static final String JUGADORCASIGANA_C = "2022";
    private boolean terminada = false;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public int getTurno() {
        return turno;
    }

    void setTurno(int turno){
        this.turno = turno;
    }

    public int getTurnoJugador() {
        return JUGADOR;
    }

    public int getTurnoMaquina() {
        return MAQUINA;
    }

    public String getGanador() {
        return ganador;
    }

    void setFicha(int i,int j){
        tablero[i][j] = this.turno;
    }

    boolean isTerminada(){
        return terminada;
    }

    public Game(int jugador) {
        tablero =  new int[NFILAS][NCOLUMNAS];
        setTurno(jugador);
        inicializarVacioTablero();
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
        if (fila(fila).contains(MAQUINAGANA)
                || columna(columna).contains(MAQUINAGANA)
                || diagonal(fila,columna).contains(MAQUINAGANA)
                || diagonalInversa(fila,columna).contains(MAQUINAGANA)){
            ganador = "Maquina";
            return true;
        }else if (fila(fila).contains(JUGADORGANA)
                || columna(columna).contains(JUGADORGANA)
                || diagonal(fila,columna).contains(JUGADORGANA)
                || diagonalInversa(fila,columna).contains(JUGADORGANA)){
            ganador = "Jugador";
            return true;
        }
        return false;
    }

    public boolean comprobarJugadaOnline(int fila, int columna){
        if (fila(fila).contains(MAQUINAGANA)
                || columna(columna).contains(MAQUINAGANA)
                || diagonal(fila,columna).contains(MAQUINAGANA)
                || diagonalInversa(fila,columna).contains(MAQUINAGANA)){
            ganador = "1";
            return true;
        }else if (fila(fila).contains(JUGADORGANA)
                || columna(columna).contains(JUGADORGANA)
                || diagonal(fila,columna).contains(JUGADORGANA)
                || diagonalInversa(fila,columna).contains(JUGADORGANA)){
            ganador = "2";
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

    int comprobarMovimiento(int columna){
        String fila = "";
        int columna2;

        for (int i = 0; i < NFILAS; i++) {
            fila = "";
            for (int j = 0; j < NCOLUMNAS; j++) {
                fila += tablero[i][j];
                columna2 = j;
                if (fila.contains(JUGADORCASIGANA_A) && columna2!=(NCOLUMNAS - 1) && tablero[i][columna2 + 1] == VACIO){
                    return columna2 + 1;
                }
                if (fila.contains(JUGADORCASIGANA_A) && (columna2-3)>=0 && tablero[i][columna2 - 3] == VACIO){
                    return columna2 - 3;
                }
            }
        }
        return columna;
    }

    String mostrarTablero(){
        String cadena="";
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                cadena+=  tablero[i][j];
            }
        }
        return cadena;
    }

    public int[][] getTablero(){
        return tablero;
    }


}
