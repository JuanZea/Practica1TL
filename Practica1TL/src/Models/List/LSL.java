package Models.List;

import Controllers.Compiler;
import Models.Symbols;

import java.util.ArrayList;

/**
 *
 * @author JuanZea && VanessaTocasuche
 */
public class LSL {

    private NodoSimple primero, ultimo;

    public LSL() {
        primero = ultimo = null;
    }

    public NodoSimple primerNodo() {
        return primero;
    }

    public NodoSimple ultimoNodo() {
        return ultimo;
    }

    public boolean finDeRecorrido(NodoSimple p) {
        return p == null;
    }

    public String recorre() {
        String response="";
        NodoSimple p;
        p = primerNodo();
        while (!this.finDeRecorrido(p)) {
            //System.out.print(p.retornaClase() + "\t");
            response = response.concat("[" + p.retornaClase() + " | " + p.retornaValor() + "] -> ");
            p = p.retornaLiga();
        }
        if (response.length() > 0) {
            response = response.substring(0, response.length() - 4);
        }
        //System.out.println();
        return response;
    }

    public void insertarAlFinal(String clase, String valor) {
        NodoSimple x;
        x = new NodoSimple(clase, valor);
        conectar(x, this.ultimoNodo());
    }

    public void conectar(NodoSimple x, NodoSimple y) {
        if (y != null) {
            x.asignaLiga(y.retornaLiga());
            y.asignaLiga(x);
            if (y == ultimo) {
                ultimo = x;
            }
        } else {
            x.asignaLiga(primerNodo());
            if (primerNodo() == null) {
                ultimo = x;
            }
            primero = x;
        }
    }

    public void agregar(ArrayList<Character> inputs) {
        String valor = "";
        for (int i = Compiler.posI; i < Compiler.posF; i++) {
            if (!inputs.get(i).equals(' ')) {
                valor = valor.concat(String.valueOf(inputs.get(i)));
            }
        }
        this.insertarAlFinal(this.clase(valor), valor);
    }

    public String clase(String valor) {
        if (valor.equals("!=") || valor.equals("==") || valor.equals("+=") || valor.equals("-=") || valor.equals("*=") || valor.equals("/=") || valor.equals("%=")) {
            return "opdor";
        }
        if (Symbols.getNumbers().contains(valor.charAt(0)) || valor.charAt(0) == '\'' || valor.equals("true") || valor.equals("false") || valor.charAt(0) == '.') {
            return "cte";
        } else {
            return "var";
        }
    }

    public void restaurar(ArrayList<String> stringLists) {
        stringLists.add(this.recorre());
        this.primero = null;
        this.ultimo = null;
    }
}