package Models;

import java.util.ArrayList;

public class Compiled {
    private final ArrayList<String> responses;
    private final ArrayList<LSL> lists;

    public Compiled(ArrayList<String> responses, ArrayList<LSL> lists) {
        this.responses = responses;
        this.lists = lists;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public ArrayList<LSL> getLists() {
        return lists;
    }

    public String getResponse(int index) {
        return responses.get(index);
    }

    public LSL getList(int index) {
        return lists.get(index);
    }

    public String log() {
        String log = ":: Compiled ::\n" + ":: Recognition " + "\t|\t" + "List ::\n";

        for (int i = 0; i < this.responses.size(); i++) {
            log += (i + 1) + ". " + this.responses.get(i) + "\t\t|\t" + this.lists;
        }

        return log;
    }
}
