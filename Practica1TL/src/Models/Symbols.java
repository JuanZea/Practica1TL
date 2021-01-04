package Models;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Contains the basic symbols of automatons
 */
public class Symbols {
//    private static ArrayList<Character> Az = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
    private static final ArrayList<Character> numbers = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private static final ArrayList<Character> basics = new ArrayList<>(Arrays.asList('_', ';', ',', '.', '+', '-', '*', '/', '=', '>', '<', '!', '(', ')', '&', '|', ' '));
    private static final ArrayList<Character> keyChar = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u'));
    private static final ArrayList<Character> keyChar_Complement = new ArrayList<>(Arrays.asList('g', 'j', 'k', 'm', 'p', 'q', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

    /**
     *
     * @param type
     */
    public static void addArray(ArrayList<Character> array, @Nullable Integer type) {
        if(type == null) {
            array.addAll(getAz());
            array.addAll(numbers);
            array.addAll(basics);
            return;
        }
        switch (type) {
            case 0: {
                array.addAll(getAz());
                break;
            }
            case 1: {
                array.addAll(numbers);
                break;
            }
            case 2: {
                array.addAll(basics);
                break;
            }
            case 3: {
                array.addAll(keyChar);
                break;
            }
        }
    }

    public static ArrayList<Character> getAz() {
        ArrayList<Character> Az = new ArrayList<>();
        Az.addAll(keyChar);
        Az.addAll(keyChar_Complement);
        Collections.sort(Az);
        return Az;
    }

    public static ArrayList<Character> getNumbers() {
        return numbers;
    }
}
