package Models;

public interface AG_Interface {

    public void process(String terminal);
    public void transition(int id);
    public void unstack();
    public void go();

}