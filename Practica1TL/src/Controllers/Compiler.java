package Controllers;

import Models.A1;
import Models.AP1;
import Models.List.LSL;

import java.util.ArrayList;
import java.util.Arrays;

public class Compiler {

    public static LSL lsl = new LSL();
    public static int posI;
    public static int posF;
    public static AP1 stackAutomaton1;

    public ArrayList<ArrayList<String>> analyze(String text) {
        ArrayList<String> lines = this.splitLines(text);
        ArrayList<String> responses = new ArrayList<>();
        ArrayList<String> stringLists = new ArrayList<>();
        ArrayList<Character> input;
        A1 automaton1 = new A1();
        stackAutomaton1 = new AP1();
        for (String line:
             lines) {
            input = this.partition(line);
            responses.add(automaton1.process(null, 0, input));
            stackAutomaton1.reset();
            lsl.restaurar(stringLists);
        }

        // Errors
        System.out.println("---------------------------------");
        for (String error :
                responses) {
            System.out.println(error);
        }
        System.out.println(stringLists);
        System.out.println("---------------------------------");


        return new ArrayList<>(new ArrayList<>( Arrays.asList(responses, stringLists) ));
    }

    public ArrayList<String> splitLines(String text){

        String[] linesAux = text.split("\n");

        return new ArrayList<>(Arrays.asList(linesAux));
    }

    public ArrayList<Character> partition(String line) {
        ArrayList<Character> inputs = new ArrayList<>();
        int length = line.length();
        for (int i = 0; i < length; i++) {
            inputs.add(line.charAt(i));
        }
        return inputs;
    }

    public static int hasError(ArrayList<String> responses) {
        int errors = 0;
        for (String response :
                responses) {
            if (response.charAt(0) != 'A') {
                errors++;
            }
        }
        return errors;
    }

    public static String translate(String pErrors) {
        String[] errors = pErrors.split(",");
        String response = "";
        for (String error:
             errors) {
            switch (error) {
                case "EC": {
                    response = response.concat("Uso de carácter inválido");
                    break;
                }
                case "EP": {
                    response = response.concat("Uso de palabra reservada");
                    break;
                }
                case "ES": {
                    response = response.concat("Signos consecutivos");
                    break;
                }
                case "EO": {
                    response = response.concat("Se esperaba un operador");
                    break;
                }
                case "EOA": {
                    response = response.concat("Se esperaba un operador aritmético");
                    break;
                }
                case "E1": {
                    response = response.concat("Se esperaba un tipo de variable");
                    break;
                }
                case "E2": {
                    response = response.concat("Se esperaba un nombre de variable válido");
                    break;
                }
                case "E3": {
                    response = response.concat("Se esperaba un separador ','");
                    break;
                }
                case "E4": {
                    response = response.concat("Se esperaba un igual '='");
                    break;
                }
                case "E5": {
                    response = response.concat("Se esperaba un más '+'");
                    break;
                }
                case "E6": {
                    response = response.concat("Se esperaba un menos '-'");
                    break;
                }
                case "E7": {
                    response = response.concat("Se esperaba un asterisco '*'");
                    break;
                }
                case "E8": {
                    response = response.concat("Se esperaba un slash '/'");
                    break;
                }
                case "E9": {
                    response = response.concat("Se esperaba un punto y coma ';'");
                    break;
                }
                case "E10": {
                    response = response.concat("Se esperaba fin de linea '¬'");
                    break;
                }
                case "E11": {
                    response = response.concat("Se esperaba una expresión");
                    break;
                }
                case "E12": {
                    response = response.concat("Se esperaba un punto '.'");
                    break;
                }
                case "E13": {
                    response = response.concat("Se esperaba una comilla '");
                    break;
                }
                case "E14": {
                    response = response.concat("Se esperaba un '&'");
                    break;
                }
                case "E15": {
                    response = response.concat("Se esperaba un '|'");
                    break;
                }
                case "E16": {
                    response = response.concat("Hace falta cierre paréntesis ')'");
                    break;
                }
                case "A": {
                    return "La linea esta correcta";
                }
            }
            response = response.concat(" o ");
        }
        return response.substring(0, response.length() - 3);
    }
}
