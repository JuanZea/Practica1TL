package Models;

import java.util.Stack;

public class AP1 {
    private final Stack<Character> stack = new Stack<>();

    public String process(Character symbol) {
        switch (this.state()) {
            case "O": {
                switch (symbol) {
                    case 'O': {
                        return this.transition(1);
                    }
                    case 'X': {
                        return this.transition(2);
                    }
                    case '¬': {
                        return "E16";
                    }
                }
                break;
            }
            case "■": {
                switch (symbol) {
                    case 'O': {
                        return this.transition(1);
                    }
                    case 'X': {
                        return "ES10";
                    }
                    case '¬': {
                        return "A";
                    }
                }
                break;
            }
        }
        return null;
    }

    public String state() {
        if (this.stack.empty()) {
            return "■";
        } else {
            return  "O";
        }
    }

    public String transition(int index) {
        if (index == 1) {
            this.stack.push('O');
            return "S";
        } else {
            this.stack.pop();
            return "O";
        }
    }

    public void reset() {
        this.stack.clear();
    }

    public Stack<Character> getStack() {
        return stack;
    }
}
