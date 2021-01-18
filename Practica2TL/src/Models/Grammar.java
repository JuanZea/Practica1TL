package Models;

import java.util.ArrayList;
import java.util.Arrays;

public class Grammar {

    int parenthesis;
    private ArrayList<Character> alphabet;
    private ArrayList<String> reservedWords;

    // iIndicadores de la gramática principal
    private boolean ifMode;
    private boolean whileMode;
    private boolean exArit;
    private boolean exLog;
    private boolean exChar;

    // iIndicadores de la gramática de expresiones aritméticas
    private boolean exArit_e1;
    private boolean exArit_e2;
    private boolean exArit_e3;
    private boolean exArit_e4;

    private String error;

    public Grammar() {
        // Create Alphabet
        alphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'));

        // Create ReservedWords
        this.reservedWords = new ArrayList<>(Arrays.asList("if", "while", "null", "void"));

    }

    public String process(String line) {
        // Se restauran los valores iniciales
        this.initialize();

        // Arrange
        String response;

        // Se usan las producciones del símbolo inicial de la gramática
        response = this.S_S1(line);
        if (!(this.ifMode || this.whileMode)) {
            return response;
        }
        // S2 y S3 [En Progreso...]
        return "RECHAZADA";
    }

    public void initialize() {
        this.parenthesis = 0;
        this.ifMode = false;
        this.whileMode = false;
        exArit = false;
        exLog = false;
        exChar = false;
        this.error = null;
    }

    /**
     * Producción #1: <S> --> <ESP><TIPO> <ESP><NV><ESP>=<ESP><EX><ESP>;<ESP>
     */
    private String S_S1(String line) {
        // <ESP>
        line = this.ESP(line);
        // <TIPO>
        line = this.TYPE(line);
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // " "
        line = this.terminal(line , ' ');
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // <ESP>
        line = this.ESP(line);
        // <NV>
        line = this.NV(line);
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // <ESP>
        line = this.ESP(line);
        // "="
        line = this.terminal(line , '=');
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // <ESP>
        line = this.ESP(line);
        // <EX>
        line = this.EX(line);
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // <ESP>
        line = this.ESP(line);
        // ";"
        line = this.terminal(line , ';');
        if (this.error != null) { // Filtro de error
            return this.translation(this.error);
        }
        // <ESP>
        line = this.ESP(line);

        // Decisión
        if (line.length() == 0) {
            return "ACEPTADA";
        }
        return "RECHAZADA";
    }

    // Non-Terminals Functions

    private String ESP(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            return line;
        }

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                return line.substring(i);
            }
        }
        return "";
    }

    public String TYPE(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            return this.error = "E1";
        }

        // Lectura de tipos de dato
        if (line.startsWith("int ")) {
            return line.substring(3);
        }
        if (line.startsWith("char ")) {
            return line.substring(4);
        }
        if (line.startsWith("double ")) {
            return line.substring(6);
        }
        if (line.startsWith("boolean ")) {
            return line.substring(7);
        }
        return " ".concat(line);
    }

    public String NV(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            return this.error = "E2";
        }

        String word = null;
        for (int i = 0; i < line.length(); i++) {
            if (!this.alphabet.contains(line.charAt(i))) {
                word = line.substring(0, i);
                break;
            }
        }
        if (word == null) {
            if (this.varName(line)) {
                // Se excluyen las palabras reservadas
                if (this.reservedWords.contains(line)) {
                    return this.error = "E5";
                }
                return this.error = "E=";
            }
            return this.error = "E3";
        }

        // Se excluyen las palabras reservadas
        if (this.reservedWords.contains(word)) {
            if (word.equals("if")) {
                this.ifMode = true;
            }
            if (word.equals("while")) {
                this.whileMode = true;
            }
            return this.error = "E5";
        }
        if (this.varName(word)) {
            return line.substring(word.length());
        }
        return this.error = "E3";
    }

    public String EX(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            return this.error = "E4";
        }

        // Verificación de inicio de expresión
        if (!this.alphabet.contains(line.charAt(0)) && line.charAt(0) != '(') {
            return this.error = "E4";
        }

        // Arrange
        boolean numRead = false;
        boolean opdorRead = false;
        String word = null;
        int index = 0;

        // Se guardan los paréntesis
        while (index < line.length()) {
            if (line.charAt(index) != '(') {
                if (line.charAt(index) == ' ') {
                    index++;
                    continue;
                }
                break;
            }
            this.parenthesis++;
            index++;
        }
        line = line.substring(index);
        index = 0;

        // Se intenta leer un número
        while (index < line.length()) {
            if (!this.isNumber(line.charAt(index))) {
                break;
            }
            numRead = true;
            index++;
        }
        if (numRead) {
            line = line.substring(index);
        }
        index = 0;

        // Verificación de la linea
        if (line.length() == 0 && numRead) {
            return this.error = "E;";
        }

        // Se deshacen los paréntesis de cierre
        if (numRead) {
            while (index < line.length()) {
                if (line.charAt(index) != ')') {
                    if (line.charAt(index) == ' ') {
                        index++;
                        continue;
                    }
                    break;
                }
                this.parenthesis--;
                index++;
            }
            if (parenthesis < 0) {
                return this.error = "E7";
            }
            line = line.substring(index);
            index = 0;
        }

        // Se intenta leer un nombre de variable teniendo en cuenta que no se leyó un número
        if (!numRead) {
            while (index < line.length()) {
                if (!this.alphabet.contains(line.charAt(index))) {
                    word = line.substring(0, index);
                    break;
                }
                index++;
            }
            index = 0;
            if (word == null) {
                if (this.varName(line)) {
                    // Se excluyen las palabras reservadas
                    if (this.reservedWords.contains(line)) {
                        return this.error = "E5";
                    }
                    return this.error = "E;";
                }
                return this.error = "E3";
            }
            // Se excluyen las palabras reservadas
            if (this.reservedWords.contains(word)) {
                return this.error = "E5";
            }
            if (this.varName(word)) {
                line = line.substring(word.length());
            }
        }

        // Verificación de la linea
        if (line.length() == 0) {
            return "";
        }

        // Se deshacen los paréntesis de cierre
        while (index < line.length()) {
            if (line.charAt(index) != ')') {
                if (line.charAt(index) == ' ') {
                    index++;
                    continue;
                }
                break;
            }
            this.parenthesis--;
            index++;
        }
        if (parenthesis < 0) {
            return this.error = "E7";
        }
        line = line.substring(index);

        // <ESP>
        line = this.ESP(line);

        // Se intenta leer in operador
        if (line.startsWith("==") || line.startsWith("!=") || line.startsWith("&&") || line.startsWith("||") || line.startsWith("<=") || line.startsWith(">=")) {
            line = line.substring(2);
            opdorRead = true;
        } else if (line.startsWith("+") || line.startsWith("-") || line.startsWith("*") || line.startsWith("/") || line.startsWith("%") || line.startsWith("&") || line.startsWith("|") || line.startsWith("<") || line.startsWith(">")) {
            line = line.substring(1);
            opdorRead = true;
        }


        // <ESP>
        line = this.ESP(line);

        // Se hace llamado recursivo a leer una expresión
        if (opdorRead) {
            return this.EX(line);
        }

        // Finaliza la lectura correctamente
        return line;
    }

    public String terminal(String line, char terminal) {
        // Verificación de la linea
        if (line.length() == 0) {
            return this.error = "E".concat(String.valueOf(terminal));
        }

        if (line.charAt(0) == terminal) {
            return line.substring(1);
        }
        return this.error = "E".concat(String.valueOf(terminal));
    }

    public boolean varName(String line) {
        if (this.isNumber(line.charAt(0))) {
            return false;
        }
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '_') {
                return true;
            }
        }
        return false;
    }

    public boolean isNumber(char character) {
        switch (character) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public String translation(String token) {
        token = token.substring(1);
        int error;
        try {
            error = Integer.parseInt(token);
        } catch (NumberFormatException exception) {
            error = 0;
        }
        switch (error) {
            default: {
                return "ERROR";
            }
            case 0: {
                return "se esperaba un \"" + token + "\"";
            }
            case 1: {
                return "la linea está vacía o solo contiene espacios";
            }
            case 2: {
                return "se esperaba un nombre de variable válido";
            }
            case 3: {
                return "nombre de variable inválido";
            }
            case 4: {
                return "se esperaba una expresión válida";
            }
            case 5: {
                return "uso de palabra reservada";
            }
            case 6: {
                return "se esperaba un operador";
            }
            case 7: {
                return "la expresión no esta balanceada con respecto a sus paréntesis";
            }
        }
    }
}
