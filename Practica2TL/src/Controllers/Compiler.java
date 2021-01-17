package Controllers;

import Models.Compiled;
import Models.Grammar;
import Models.LSL;

import java.util.ArrayList;
import java.util.Arrays;

public class Compiler {
    private String file;

    public void setFile(String file) {
        this.file = file;
    }

    public Compiled analyze() {
        // Arrange
        Grammar grammar = new Grammar();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(file.split("\n")));
        ArrayList<String> responses = new ArrayList<>();
        ArrayList<LSL> lists = new ArrayList<>();

        // Genera las respuestas
        for (String line :
                lines) {
            // Usar la gramática para procesar la linea
            responses.add(grammar.process(line));
        }

        // Genera las listas simplemente ligadas
        for (int i = 0; i < lines.size(); i++) {
            lists.add(this.transform(lines.get(i), responses.get(i)));
        }

        return new Compiled(responses, lists);
    }

    public LSL transform(String line, String response) {
        return null;
    }
}
