package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

public class Compiler {
    String file;

    public ArrayList<String> analyze() {
        ArrayList<String> responses = new ArrayList<String>();
        ArrayList<String> blocks = this.splitBlocks(file);
        
        for (String block : blocks) {
            responses.add(this.process(block));
        }

        return responses;
    }



    public String process(String block) {
        return " ";
    }

    public void setFile(String file){
        this.file = file;
    }

    public ArrayList<String> splitBlocks(String file){
        ArrayList<String> blocks = new ArrayList<String>();
        
        String[] lines = file.split("\n");

        for (String line : lines) {
            String isBlock = this.isBlock(line);
            String semiBlock;
            switch(isBlock) {
                case "block": {
                    blocks.add(line);
                    break;
                }
                case "semi": {
                    // TODO
                    break;
                }
                default: {
                    // TODO
                    break;
                }
            }
        }
        

        

        return new ArrayList<>(Arrays.asList(lines));
    }

    public String isBlock(String line) {
//        for (int i = 0; i < lines.length; i++) {
//            int posLine = lines[i].length();
//            while ( lines[i].substring(posLine,posLine+1).equals(" ") ) {
//                posLine--;
//            }
//            if(lines[i].substring(posLine,posLine+1).equals(";")){
//                blocks.add(lines[i].substring(posLine,posLine+1);
//            }
//        }
        return " ";
    }
}