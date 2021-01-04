package Models;

import java.util.ArrayList;

public class AG1 implements Automaton {
    private String state;
    private int position;
    private final String O;
    private final String X1;
    private final String X2;
    private final String R1;
    private final String R2;
    private final String R3;
    private ArrayList<Character> symbols;

    public AG1(String O, String X1, String X2, String R1, String R2, String R3) {
        this.O = O;
        this.X1 = X1;
        this.X2 = X2;
        this.R1 = R1;
        this.R2 = R2;
        this.R3 = R3;
        this.loadSymbols();
    }

    @Override
    public void loadSymbols() {
        symbols = new ArrayList<>();
        Symbols.addArray(symbols, 3); //keyChar
        symbols.add(' ');
    }
    @Override
    public String process(String state, int position, ArrayList<Character> inputs) {
        if (state == null) {
            this.state = "S"; // Initial state
        } else {
            this.state = state;
        }
        this.position = position;

        String symbol;

        for (;this.position < inputs.size(); this.position++) {
            symbol = this.filter(inputs.get(this.position));
            switch (this.state) {
                case "S": {
                    switch (symbol) {
                        case "b": {
                            this.state = "bool1";
                            break;
                        }
                        case "c": {
                            this.state = "char1";
                            break;
                        }
                        case "d": {
                            this.state = "double1";
                            break;
                        }
                        case "e": {
                            this.state = "else1";
                            break;
                        }
                        case "f": {
                            this.state = "float1-false1";
                            break;
                        }
                        case "i": {
                            this.state = "int1-if1";
                            break;
                        }
                        case "t": {
                            this.state = "true1";
                            break;
                        }
                        default: {
                            return this.O;
                        }
                    }
                    break;
                }
                case "bool1": {
                    if ("o".equals(symbol)) {
                        this.state = "bool2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "bool2": {
                    if ("o".equals(symbol)) {
                        this.state = "bool3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "bool3": {
                    if ("l".equals(symbol)) {
                        this.state = "bool4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "char1": {
                    if ("h".equals(symbol)) {
                        this.state = "char2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "char2": {
                    if ("a".equals(symbol)) {
                        this.state = "char3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "char3": {
                    if ("r".equals(symbol)) {
                        this.state = "char4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "double1": {
                    if ("o".equals(symbol)) {
                        this.state = "double2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "double2": {
                    if ("u".equals(symbol)) {
                        this.state = "double3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "double3": {
                    if ("b".equals(symbol)) {
                        this.state = "double4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "double4": {
                    if ("l".equals(symbol)) {
                        this.state = "double5";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "double5": {
                    if ("e".equals(symbol)) {
                        this.state = "double6";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "float1-false1": {
                    switch (symbol) {
                        case "l": {
                            this.state = "float2";
                            break;
                        }
                        case "a": {
                            this.state = "false2";
                            break;
                        }
                        default: {
                            return this.O;
                        }
                    }
                    break;
                }
                case "float2": {
                    if ("o".equals(symbol)) {
                        this.state = "float3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "float3": {
                    if ("a".equals(symbol)) {
                        this.state = "float4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "float4": {
                    if ("t".equals(symbol)) {
                        this.state = "float5";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "int1-if1": {
                    switch (symbol) {
                        case "n": {
                            this.state = "int2";
                            break;
                        }
                        case "f": {
                            this.state = "if2";
                            break;
                        }
                        default: {
                            return this.O;
                        }
                    }
                    break;
                }
                case "int2": {
                    if ("t".equals(symbol)) {
                        this.state = "int3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "if1": {
                    if ("f".equals(symbol)) {
                        this.state = "int";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "else1": {
                    if ("l".equals(symbol)) {
                        this.state = "else2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "else2": {
                    if ("s".equals(symbol)) {
                        this.state = "else3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "else3": {
                    if ("e".equals(symbol)) {
                        this.state = "else4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "true1": {
                    if ("r".equals(symbol)) {
                        this.state = "true2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "true2": {
                    if ("u".equals(symbol)) {
                        this.state = "true3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "true3": {
                    if ("e".equals(symbol)) {
                        this.state = "true4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "false1": {
                    if ("a".equals(symbol)) {
                        this.state = "false2";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "false2": {
                    if ("l".equals(symbol)) {
                        this.state = "false3";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "false3": {
                    if ("s".equals(symbol)) {
                        this.state = "false4";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "false4": {
                    if ("e".equals(symbol)) {
                        this.state = "false5";
                    } else {
                        return this.O;
                    }
                    break;
                }
                case "bool4":
                case "int3":
                case "double6":
                case "float5":
                case "char4": {
                    switch (symbol) {
                        case " ": {
                            return this.R1;
                        }
                        case "null": {
                            return this.X1;
                        }
                        default: {
                            return this.O;
                        }
                    }
                }
                case "false5":
                case "true4": {
                    if (" ".equals(symbol)) {
                        return this.R3;
                    } else {
                        return this.X2;
                    }
                }
                case "if2":
                case "else4": {
                    if (" ".equals(symbol)) {
                        return this.R2;
                    } else {
                        return this.X1;
                    }
                }
            }
        }
        return this.O;
    }

    @Override
    public String filter(char c) {
        if(!this.symbols.contains(c)) {
            return "null";
        }
        return String.valueOf(c);
    }

    public int getPosition() {
        return this.position;
    }
}
