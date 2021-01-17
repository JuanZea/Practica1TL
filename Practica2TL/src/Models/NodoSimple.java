package Models;

/**
 *
 * @author JuanZea && VanessaTocasuche
 */
public class NodoSimple {

    private String clase;
    private String valor;
    private NodoSimple liga;

    public NodoSimple(String clase, String valor) {
        this.clase = clase;
        this.valor = valor;
        liga = null;
    }

    public String retornaClase() {
        return this.clase;
    }

    public String retornaValor() {
        return this.valor;
    }

    public NodoSimple retornaLiga() {
        return this.liga;
    }

    public void asignaClase(String clase) {
        this.clase = clase;
    }

    public void asignaValor(String valor) {
        this.valor = valor;
    }

    public void asignaLiga(NodoSimple liga) {
        this.liga = liga;
    }
}