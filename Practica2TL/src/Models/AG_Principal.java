package Models;

import java.util.Stack;

public class AG_Principal implements AG_Interface{
    
    public String state;
    public Stack stack;

    @Override
    public void process(String terminal) {
        switch (this.state) {
            case "S": {
                switch (terminal) {
                    case "Gg1": {
                        this.transition(0);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void transition(int id) {
        if (id == 0) {
            this.unstack();
            this.go();
        }
    }

    @Override
    public void unstack() {
        this.stack.pop();
    }

    @Override
    public void go() {
        // TODO Auto-generated method stub

    }
}
