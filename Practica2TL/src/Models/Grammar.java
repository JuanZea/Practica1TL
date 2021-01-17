package Models;

import java.util.ArrayList;
import java.util.Arrays;

public class Grammar {

    private ArrayList<Character> alphabet;
    private ArrayList<String> reservedWords;

    private boolean ifMode;
    private boolean whileMode;
    private boolean error;

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
        response = this.S1(line);
        if (!(this.ifMode || this.whileMode)) {
            return response;
        }
        // S2 y S3 [En Progreso...]
        return "RECHAZADA";
    }

    public void initialize() {
        this.ifMode = false;
        this.whileMode = false;
        this.error = false;
    }

    /**
     * Producción #1: <S> --> <ESP><TIPO> <ESP><NV><ESP>=<ESP><EX><ESP>;<ESP>
     */
    private String S1(String line) {
        // <ESP>
        line = this.ESP(line);
        // <TIPO>
        line = this.TYPE(line);
        if (this.hasError(line)) {
            return this.translation(line);
        }
        // " "
        line = this.terminal(line , ' ');
        if (this.hasError(line)) {
            return this.translation(line);
        }
        // <ESP>
        line = this.ESP(line);
        // <NV>
        line = this.NV(line);
        if (this.hasError(line)) {
            return this.translation(line);
        }
        // <ESP>
        line = this.ESP(line);
        // "="
        line = this.terminal(line , '=');
        if (this.hasError(line)) {
            return this.translation(line);
        }
        // <ESP>
        line = this.ESP(line);
        // <EX>
        line = this.EX(line);
        if (this.hasError(line)) {
            return this.translation(line);
        }
        // <ESP>
        line = this.ESP(line);
        // ";"
        line = this.terminal(line , ';');
        if (this.hasError(line)) {
            return this.translation(line);
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
            this.error = true;
            return "E1";
        }

        // Lectura de tipos de dato
        if (line.startsWith("int")) {
            return line.substring(3);
        }
        if (line.startsWith("char")) {
            return line.substring(4);
        }
        if (line.startsWith("double")) {
            return line.substring(6);
        }
        if (line.startsWith("boolean")) {
            return line.substring(7);
        }
        return " ".concat(line);
    }

    public String NV(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            this.error = true;
            return "E2";
        }

        String word = null;
        for (int i = 0; i < line.length(); i++) {
            if (!this.alphabet.contains(line.charAt(i))) {
                word = line.substring(0, i);
                break;
            }
        }
        if (word == null) {
            this.error = true;
            if (this.varName(line)) {
                return "E=";
            }
            return "E3";
        }

        // Se excluyen las palabras reservadas
        if (this.reservedWords.contains(word)) {
            if (word.equals("if")) {
                this.ifMode = true;
            }
            if (word.equals("while")) {
                this.whileMode = true;
            }
            this.error = true;
            return "E5";
        }
        if (this.varName(word)) {
            return line.substring(word.length());
        }
        this.error = true;
        return "E3";
    }

    public String EX(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            this.error = true;
            return "E4";
        }

        if (line.startsWith("EX")) {
            return line.substring(2);
        }
        this.error = true;
        return "Ez";
    }

    public String terminal(String line, char terminal) {
        // Verificación de la linea
        if (line.length() == 0) {
            this.error = true;
            return "E".concat(String.valueOf(terminal));
        }

        if (line.charAt(0) == terminal) {
            return line.substring(1);
        }
        this.error = true;
        return "E".concat(String.valueOf(terminal));
    }

    public boolean hasError(String line) {
        // Verificación de la linea
        if (line.length() == 0) {
            return false;
        }
        return line.charAt(0) == 'E' && this.error;
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
        }
    }
}
