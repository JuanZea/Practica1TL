package Models;

import Controllers.Compiler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class A4 implements Automaton{

    private String state;
    private int position;
    private ArrayList<Character> symbols;
    private ArrayList<Character> inputs = new ArrayList<>();

    public A4() {
        this.loadSymbols();
    }

    /**
     * Loads automaton symbols
     */
    @Override
    public void loadSymbols() {
        symbols = new ArrayList<>();
        Symbols.addArray(symbols, 0); // Az
        Symbols.addArray(symbols, 1); // #
        symbols.addAll( new ArrayList<>( Arrays.asList('_', ' ', ';', '+', '-', '*', '/', '%', '=' , '\'', '.', '<', '>', '!', '(', ')', '&', '|') ) );
    }

    @Override
    public String process(@Nullable String state, int position, ArrayList<Character> inputs) {
        if (state == null) {
            this.state = "S"; // Initial state
        } else {
            this.state = state;
        }
        this.position = position;
        this.inputs = inputs;

        String symbol;

        for (; this.position < this.inputs.size(); this.position++) {
            symbol = this.filter(this.inputs.get(this.position));
            switch (this.state) {
                case "S": {
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S3", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "+":
                        case "-": {
                            this.state = "SIG";
                            break;
                        }
                        case "#": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "NE";
                            break;
                        }
                        case " ": {
                            // The state is maintained
                            break;
                        }
                        case "*":
                        case "/":
                        case "%":
                        case ">":
                        case "<":
                        case "&":
                        case "|":
                        case ";":
                        case "=":
                        case ")": {
                            return "E11";
                        }
                        case "'": {
                            this.state = "C1";
                            break;
                        }
                        case ".": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "PD";
                            break;
                        }
                        case "!": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "!");

                            this.state = "S";
                            break;
                        }
                        case "(": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", "(");

                            this.state = Compiler.stackAutomaton1.process('O');
                            break;
                        }
                    }
                    break;
                }
                case "SIG": {
                    // List
                    Compiler.lsl.insertarAlFinal("opdor", String.valueOf(this.inputs.get(this.position - 1)));
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S3", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "+":
                        case "-": {
                            return "ES";
                        }
                        case "*":
                        case "/":
                        case "%":
                        case ">":
                        case "<":
                        case "&":
                        case "|":
                        case ";":
                        case "=":
                        case ")": {
                            return "E11";
                        }
                        case "#": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "NE";
                            break;
                        }
                        case " ": {
                            this.state = "S";
                            break;
                        }
                        case "'": {
                            this.state = "C1";
                            break;
                        }
                        case ".": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "PD";
                            break;
                        }
                        case "!": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "!");

                            this.state = "S";
                            break;
                        }
                        case "(": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", "(");

                            this.state = Compiler.stackAutomaton1.process('O');
                            break;
                        }
                    }
                    break;
                }
                case "NE": {
                    switch (symbol) {
                        case "Az":
                        case "_":
                        case "'":
                        case "(": {
                            return "EO";
                        }
                        case "#": {
                            this.state= "NE";
                            break;
                        }
                        case " ": {
                            this.state = "O";

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);
                            break;
                        }
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "%": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", String.valueOf(this.inputs.get(this.position)));

                            this.state = "S";
                            break;
                        }
                        case ">":
                        case "<": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "MM";
                            break;
                        }
                        case ".": {
                            this.state = "PD";
                            break;
                        }
                        case "=":
                        case "!": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I";
                            break;
                        }
                        case "&": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "AND";
                            break;
                        }
                        case "|": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "OR";
                            break;
                        }
                        case ")": {
                            this.state = Compiler.stackAutomaton1.process('X');

                            if (this.inputs.get(this.position - 1) != ' ' || !Compiler.stackAutomaton1.getStack().empty()) {
                                // List - Final
                                Compiler.posF = this.position;
                                Compiler.lsl.agregar(this.inputs);
                            }

                            // List
                            Compiler.lsl.insertarAlFinal("sep", ")");

                            break;
                        }
                        case ";": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            return "A";
                        }
                    }
                    break;
                }
                case "PD": {
                    if ("#".equals(symbol)) {
                        this.state = "ND";
                    } else {
                        return "E12";
                    }
                    break;
                }
                case "ND": {
                    switch (symbol) {
                        case "#": {
                            this.state= "ND";
                            break;
                        }
                        case " ": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "O";
                            break;
                        }
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "%": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", String.valueOf(this.inputs.get(this.position)));

                            this.state = "S";
                            break;
                        }
                        case ">":
                        case "<": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "MM";
                            break;
                        }
                        case "=":
                        case "!": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I";
                            break;
                        }
                        case "&": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "AND";
                            break;
                        }
                        case "|": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = "OR";
                            break;
                        }
                        case ";": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            return "A";
                        }
                        default: {
                            return "EO";
                        }
                    }
                    break;
                }
                case "C1": {
                    // List - Initial
                    Compiler.posI = this.position - 1;

                    this.state = "C2";
                    break;
                }
                case "C2": {
                    if ("'".equals(symbol)) {
                        this.state = "O";

                        // List - Final
                        Compiler.posF = this.position + 1;
                        Compiler.lsl.agregar(this.inputs);
                    } else {
                        return "E13";
                    }
                    break;
                }
                case "O": {
                    switch (symbol) {
                        case " ": {
                            // The state is maintained
                            break;
                        }
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "%":{
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", String.valueOf(this.inputs.get(this.position)));

                            this.state = "S";
                            break;
                        }
                        case ">":
                        case "<": {
                            this.state = "MM";
                            break;
                        }
                        case "=":
                        case "!": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I";
                            break;
                        }
                        case "&": {
                            this.state = "AND";
                            break;
                        }
                        case "|": {
                            this.state = "OR";
                            break;
                        }
                        case ")": {
                            this.state = Compiler.stackAutomaton1.process('X');

                            // List
                            Compiler.lsl.insertarAlFinal("sep", ")");

                            break;
                        }
                        case ";": {
                            return "A";
                        }
                        default: {
                            return "EO";
                        }
                    }
                    break;
                }
                case "I": {
                    if ("=".equals(symbol)) {
                        this.state = "S";

                        // List - Final
                        Compiler.posF = this.position + 1;
                        Compiler.lsl.agregar(this.inputs);
                    } else {
                        return "E4";
                    }
                    break;
                }
                case "AND": {
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");

                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S3", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "+":
                        case "-": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");

                            this.state = "SIG";
                            break;
                        }
                        case "#": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "NE";
                            break;
                        }
                        case " ": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");
                            this.state = "S";
                            break;
                        }
                        case "&": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&&");

                            this.state = "S";
                            break;
                        }
                        case "!": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "!");

                            this.state = "S";
                            break;
                        }
                        case "'": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "&");

                            this.state = "C1";
                            break;
                        }
                        case ".": {
                            this.state = "PD";
                            break;
                        }
                        case "(": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", "(");

                            this.state = Compiler.stackAutomaton1.process('O');
                            break;
                        }
                        default: {
                            return "E11,14";
                        }
                    }
                    break;
                }
                case "OR": {
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "|");

                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S3", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "+":
                        case "-": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "|");

                            this.state = "SIG";
                            break;
                        }
                        case "#": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "|");

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "NE";
                            break;
                        }
                        case " ": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "|");

                            this.state = "S";
                            break;
                        }
                        case "|": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "||");

                            this.state = "S";
                            break;
                        }
                        case "!": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "|");

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "!");

                            this.state = "S";
                            break;
                        }
                        case "'": {
                            this.state = "C1";
                            break;
                        }
                        case ".": {
                            this.state = "PD";
                            break;
                        }
                        case "(": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", "(");

                            this.state = Compiler.stackAutomaton1.process('O');
                            break;
                        }
                        default: {
                            return "E11,15";
                        }
                    }
                    break;
                }
                case "MM": {
                    String mm = String.valueOf(this.inputs.get(this.position - 1));
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S3", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }

                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "#": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "NE";
                            break;
                        }
                        case "+":
                        case "-": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            this.state = "SIG";
                            break;
                        }
                        case "!": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "!");

                            this.state = "S";
                            break;
                        }
                        case " ": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            this.state = "S";
                            break;
                        }
                        case "=": {
                            // List
                            mm = mm.concat("=");
                            Compiler.lsl.insertarAlFinal("opdor", mm);

                            this.state = "S";
                            break;
                        }
                        case "'": {
                            this.state = "C1";
                            break;
                        }
                        case ".": {
                            this.state = "PD";
                            break;
                        }
                        case "(": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", "(");

                            this.state = Compiler.stackAutomaton1.process('O');
                            break;
                        }
                        default: {
                            return "E4,E11";
                        }
                    }
                    break;
                }
            }
        }
        return "E9";
    }

    public String useAutomaton(String id, String state, int position) {
        if ("3".equals(id)) {
            A3 automaton3 = new A3();
            String response = automaton3.process(state, position, this.inputs);
            this.position = automaton3.getPosition();
            return response;
        }
        return null;
    }

    @Override
    public String filter(char c) {
        if (Symbols.getAz().contains(c)) {
            return "Az";
        }
        if (Symbols.getNumbers().contains(c)) {
            return "#";
        }
        if(!this.symbols.contains(c)) {
            return "null";
        }
        return String.valueOf(c);
    }

    public int getPosition() {
        return this.position;
    }
}