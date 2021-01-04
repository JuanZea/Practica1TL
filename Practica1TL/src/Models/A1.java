package Models;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import Controllers.Compiler;

public class A1 implements Automaton {

    private String state;
    private int position;
    private ArrayList<Character> symbols;
    private ArrayList<Character> inputs = new ArrayList<>();

    public A1() {
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
        symbols.addAll( new ArrayList<>( Arrays.asList('_', ' ', ';', '=', '+', '-', '*', '/', '%', ',') ) );
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
                        case "Az": {
                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("1.1", "S", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;

                            // List - Final
                            if (!this.state.equals("NV") && !this.state.equals("S")) {
                                Compiler.posF = this.position;
                                Compiler.lsl.agregar(this.inputs);
                            }

                            this.position--;
                            break;
                        }
                        case "_": {
                            this.state = this.useAutomaton("3", "S2", this.position);
                            break;
                        }
                        case " ": {
                            // The state is maintained
                            break;
                        }
                        default: {
                            return "E1,E2";
                        }
                    }
                    break;
                }
                case "D": {
                    switch (symbol) {
                        case "Az":
                        case "_": {
                            // List - Initial
                            Compiler.posI = this.position;

                            String response = this.useAutomaton("3", "S1", this.position);
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
                        case " ": {
                            // The state is maintained
                            break;
                        }
                        default: {
                            return "E2";
                        }
                    }
                    break;
                }
                case "NV": {
                    switch (symbol) {
                        case "Az":
                        case "_":
                        case "#": {
                            String response = this.useAutomaton("3", "S2", this.position);
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
                        case "=": {
                            if (this.inputs.get(this.position - 1) != ' ') {
                                // List - Final
                                Compiler.posF = this.position;
                                Compiler.lsl.agregar(this.inputs);
                            }

                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "=");

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case " ": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                           // The state is maintained
                            break;
                        }
                        case "+": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I+";
                            break;
                        }
                        case "-": {
                            // List - Final
                            Compiler.posF = this.position;
                            Compiler.lsl.agregar(this.inputs);

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I-";
                            break;
                        }
                        case "*":
                        case "/":
                        case "%": {
                            if (this.inputs.get(this.position - 1) != ' ') {
                                // List - Final
                                Compiler.posF = this.position;
                                Compiler.lsl.agregar(this.inputs);
                            }

                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I";
                            break;
                        }
                        case ",":
                        case ";":
                        default: {
                            return "E2";
                        }
                    }
                    break;
                }
                case "N1": {
                    switch (symbol) {
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case "=": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "=");

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case ",": {
                            // List
                            Compiler.lsl.insertarAlFinal("sep", ",");

                            this.state = "D";
                            break;
                        }
                        case ";": {
                            this.state = "A";
                            break;
                        }
                        case "null":
                        default: {
                            return "E3,E4,E9";
                        }
                    }
                    break;
                }
                case "N2": {
                    switch (symbol) {
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case "=": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "=");

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case "+": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I+";
                            break;
                        }
                        case "-": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I-";
                            break;
                        }
                        case "*":
                        case "/":
                        case "%": {
                            // List - Initial
                            Compiler.posI = this.position;

                            this.state = "I";
                            break;
                        }
                        default: {
                            return "E4,E5,E6,E7,E8";
                        }
                    }
                    break;
                }
                case "I": {
                    switch (symbol) {
                        case "=": {
                            // List - Final
                            Compiler.posF = this.position + 1;
                            Compiler.lsl.agregar(this.inputs);

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case "null":
                        default: {
                            return "E4";
                        }
                    }
                    break;
                }
                case "I+": {
                    switch (symbol) {
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case "=": {
                            // List - Final
                            Compiler.posF = this.position + 1;
                            Compiler.lsl.agregar(this.inputs);

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case "+": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "++");
                            this.state = "P";
                            break;
                        }
                        case "null":
                        default: {
                            return "E5";
                        }
                    }
                    break;
                }
                case "I-": {
                    switch (symbol) {
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case "=": {
                            // List - Final
                            Compiler.posF = this.position + 1;
                            Compiler.lsl.agregar(this.inputs);

                            String response = this.useAutomaton("4", "S", this.position + 1);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            break;
                        }
                        case "-": {
                            // List
                            Compiler.lsl.insertarAlFinal("opdor", "--");
                            this.state = "P";
                            break;
                        }
                        case "null":
                        default: {
                            return "E6";
                        }
                    }
                    break;
                }
                case "P": {
                    switch (symbol) {
                        case " ": {
                           // The state is maintained
                            break;
                        }
                        case ";": {
                            this.state = "A";
                            break;
                        }
                        case "null":
                        default: {
                            return "E9";
                        }
                    }
                    break;
                }
                case "A": {
                    if (!" ".equals(symbol)) {
                        return "E10";
                    }
                    break;
                }
            }
        }

        switch (this.state) {
            case "S":
            case "A": {
                boolean empty = true;
                for (Character in :
                        this.inputs) {
                    if (in != ' ') {
                        empty = false;
                        break;
                    }
                    }
                if (empty) {
                    // List
                    Compiler.lsl.insertarAlFinal("sep", "¬");
                } else {
                    // List
                    Compiler.lsl.insertarAlFinal("sep", "; | ¬"); // Trucazo
                }

                return this.state = Compiler.stackAutomaton1.process('¬');
            }
            case "D":
            case "NV": {
                return "E2";
            }
            case "N1": {
                return "E3,E4,E9";
            }
            case "N2": {
                return "EOA,E4";
            }
            case "I": {
                return "E4";
            }
            case "I+": {
                return "E5";
            }
            case "I-": {
                return "E6";
            }
            case "P": {
                return "E9";
            }
        }
        return null;
    }

    public String useAutomaton(String id, String state, int position) {
        switch (id) {
            case "1.1": {
                AG1 automaton1_1 = new AG1("NV", "S", "S", "D","EP", "EP");
                String response = automaton1_1.process(state, position, this.inputs);
                this.position = automaton1_1.getPosition();
                return response;
            }
            case "3": {
                A3 automaton3 = new A3();
                String response = automaton3.process(state, position, this.inputs);
                this.position = automaton3.getPosition();
                return  response;
            }
            case "4": {
                A4 automaton4 = new A4();
                String response = automaton4.process(state, position, this.inputs);
                this.position = automaton4.getPosition();
                return  response;
            }
        }
        return null;
    }

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
}
