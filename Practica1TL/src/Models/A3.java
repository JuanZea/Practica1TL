package Models;

import java.util.ArrayList;
import java.util.Arrays;

public class A3 implements Automaton {
    private String state;
    private int position;
    private ArrayList<Character> symbols;
    private ArrayList<Character> inputs;

    public A3() {
        this.loadSymbols();
    }

    @Override
    public void loadSymbols() {
        symbols = new ArrayList<>();
        Symbols.addArray(symbols, 0); // Az
        Symbols.addArray(symbols, 1); // #
        symbols.addAll(new ArrayList<>(Arrays.asList('_', ' ')));
    }
    @Override
    public String process(String state, int position, ArrayList<Character> inputs) {
        if (state == null) {
            this.state = "S"; // Initial state
        } else {
            this.state = state;
        }
        this.position = position;
        this.inputs = inputs;

        String symbol;
        for (;this.position < inputs.size(); this.position++) {
            symbol = this.filter(inputs.get(this.position));
            switch (this.state) {
                case "S1": {
                    switch (symbol) {
                        case "Az": {
                            String response = this.useAutomaton("3.1", "S", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "_": {
                            this.state = "G1";
                            break;
                        }
                    }
                    break;
                }
                case "X1": {
                    switch (symbol) {
                        case "Az":
                        case "#":
                        case "_":{
                            this.state = "V1";
                            break;
                        }
                        default: {
                            return "EP";
                        }
                    }
                    break;
                }
                case "V1": {
                    switch (symbol) {
                        case "Az":
                        case "#":
                        case "_": {
                            // The state is maintained
                            break;
                        }
                        case " ":
                        default: {
                            return "N1";
                        }
                    }
                    break;
                }
                case "G1": {
                    switch (symbol) {
                        case "Az":
                        case "#": {
                            this.state = "V1";
                            break;
                        }
                        case "_": {
                            this.state = "G1";
                            break;
                        }
                        case " ":
                        default: {
                            return "E2";
                        }
                    }
                    break;
                }
                case "S2": {
                    switch (symbol) {
                        case "Az":
                        case "#": {
                            // The state is maintained
                            break;
                        }
                        case "_": {
                            this.state = "G2";
                            break;
                        }
                        default: {
                            return "N2";
                        }
                    }
                    break;
                }
                case "G2": {
                    switch (symbol) {
                        case "Az":
                        case "#": {
                            this.state = "S2";
                            break;
                        }
                        case "_": {
                            this.state = "G2";
                            break;
                        }
                        default: {
                            return "E2";
                        }
                    }
                    break;
                }
                case "S3": {
                    switch (symbol) {
                        case "Az": {
                            String response = this.useAutomaton("3.3", "S", this.position);
                            if (response.charAt(0) == 'E') {
                                return response;
                            }
                            this.state = response;
                            this.position--;
                            break;
                        }
                        case "_": {
                            this.state = "G3";
                            break;
                        }
                    }
                    break;
                }
                case "X3": {
                    switch (symbol) {
                        case "Az":
                        case "#":
                        case "_":{
                            this.state = "V3";
                            break;
                        }
                        default: {
                            return "EP";
                        }
                    }
                    break;
                }
                case "V3": {
                    switch (symbol) {
                        case "Az":
                        case "#":
                        case "_": {
                            // The state is maintained
                            break;
                        }
                        default: {
                            return "O";
                        }
                    }
                    break;
                }
                case "G3": {
                    switch (symbol) {
                        case "Az":
                        case "#": {
                            this.state = "V3";
                            break;
                        }
                        case "_": {
                            // The state is maintained
                            break;
                        }
                        default: {
                            return "E2";
                        }
                    }
                    break;
                }
                case "O": {
                    return "O";
                }
            }
        }

        switch (this.state) {
            case "S2": {
                return "N2";
            }
            case "V1": {
                return "N1";
            }
            case "V3":
            case "O": {
                return "O";
            }
            case "X1":
            case "X3": {
                return "EP";
            }
            case "G1":
            case "G2":
            case "G3": {
                return "E2";
            }
        }
        return null;
    }

    private String useAutomaton(String id, String state, int position) {
        switch (id) {
            case "3.1": {
                AG1 automaton3_1 = new AG1("V1", "X1", "X1", "EP","EP", "EP");
                String response = automaton3_1.process(state, position, this.inputs);
                this.position = automaton3_1.getPosition();
                return response;
            }
            case "3.3": {
                AG1 automaton3_3 = new AG1("V3", "X3", "O", "EP","EP", "O");
                String response = automaton3_3.process(state, position, this.inputs);
                this.position = automaton3_3.getPosition();
                return response;
            }
        }
        return null;
    }

    @Override
    public String filter(char c) {
        if(!this.symbols.contains(c)) {
            return "null";
        }
        if (Symbols.getAz().contains(c)) {
            return "Az";
        }
        if (Symbols.getNumbers().contains(c)) {
            return "#";
        }
        return String.valueOf(c);
    }

    public int getPosition() {
        return this.position;
    }
}
