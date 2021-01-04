package Models;

import java.util.ArrayList;

public interface Automaton {

    void loadSymbols();
    String process(String state, int position, ArrayList<Character> inputs);
    String filter(char c);
}
