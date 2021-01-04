package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WindowController implements ClipboardOwner{

    @FXML
    TextArea ta_file;
    @FXML
    TextArea ta_errors;
    @FXML
    TextArea ta_lsl;
    @FXML
    ImageView img_analyze;
    @FXML
    ImageView img_analyze1;
    @FXML
    Label lab_result;

    public void searchFile() {
        File file;
        FileChooser choose = new FileChooser();
        choose.setTitle("Seleccionar archivo");

        // Agregar filtros para facilitar la búsqueda
        choose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java File","*.java")
        );

        file = choose.showOpenDialog(null);

        try {
            //Abro el stream, el fichero debe existir
			FileReader fr = new FileReader(file);

			//Leemos el fichero y lo mostramos por pantalla
			int valor = fr.read();
			String text = "";
			while(valor != -1) {
				text = text.concat(String.valueOf(((char) valor)));
				valor = fr.read();
			}

			//Cerramos el stream
			fr.close();
			ta_file.setText(text);
			System.out.print("Archivo leído correctamente");
        } catch (IOException e) {
            System.out.println("Error E/S: "+e);
        }
    }

    public void analyze() {
        if (!ta_file.getText().isEmpty()) {
            Compiler compiler = new Compiler();
            ArrayList<ArrayList<String>> compiled = compiler.analyze(ta_file.getText());
            ArrayList<String> responses = compiled.get(0);
            ArrayList<String> stringLists = compiled.get(1);
            int errors = Compiler.hasError(responses);
            if (errors == 0) {
                this.updateImages(1);
            } else {
                this.updateImages(0);
            }
            this.results(responses, stringLists, errors);
        } else {
            JOptionPane.showMessageDialog(null, "El archivo esta vacío", "Error", JOptionPane.ERROR_MESSAGE);
            this.updateImages(0);
        }
    }

    private void results(ArrayList<String> responses, ArrayList<String> stringLists, int errors) {
        String result;
        if (errors == 0) {
            this.lab_result.setTextFill(Color.web("#22bb33"));
            this.lab_result.setText("¡El archivo no tiene errores!");
            result = "Las listas simplemente ligadas para cada linea son las siguientes:\n\n";
            for (int i = 0; i < responses.size(); i++) {
                result = result.concat((i + 1) + ") "+stringLists.get(i)+".\n");
            }
            this.ta_lsl.setText(result);
            this.ta_errors.clear();
            this.ta_errors.setPromptText("Sin errores detectados.");
        } else {
            this.lab_result.setTextFill(Color.web("#bb2124"));
            if (errors == 1) {
                this.lab_result.setText("¡El archivo tiene solo una linea errónea!");
            } else {
                this.lab_result.setText("¡El archivo tiene " + errors + " lineas erróneas!");
            }
            result = "Los errores detectados según la linea leída son los siguientes:\n\n";
            for (int i = 0; i < responses.size(); i++) {
                result = result.concat((i + 1) + ") "+Compiler.translate(responses.get(i))+".\n");
            }
            this.ta_errors.setText(result);
            result = "Debido a los errores las listas están incompletas y pueden tener fallos:\n\n";
            for (int i = 0; i < responses.size(); i++) {
                result = result.concat((i + 1) + ") "+stringLists.get(i)+".\n");
            }
            this.ta_lsl.setText(result);
        }
    }

    private void updateImages(int image) {
        if (image == 1) {
            img_analyze.setImage(new Image("Views/images/check.png"));
            img_analyze1.setImage(new Image("Views/images/check.png"));
        } else {
            img_analyze.setImage(new Image("Views/images/cross.png"));
            img_analyze1.setImage(new Image("Views/images/cross.png"));
        }
    }

    public void link0() {
        StringSelection link =  new StringSelection("https://github.com/JuanZea/Practica1TL");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(link,this);
        JOptionPane.showMessageDialog(null, "Se ha copiado el link al portapapeles.", "Notificación (GitHub)", JOptionPane.INFORMATION_MESSAGE);
    }
    public void link1() {
        StringSelection link =  new StringSelection("https://docs.google.com/document/d/1Md9R-8c2HkW1zbmjwIYFwPmi_ZQJVgOencJAI18W6R4/edit?usp=sharing");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(link,this);
        JOptionPane.showMessageDialog(null, "Se ha copiado el link al portapapeles.", "Notificación (Manual de uso)", JOptionPane.INFORMATION_MESSAGE);
    }
    public void link2() {
        StringSelection link =  new StringSelection("https://docs.google.com/document/d/1mYVUWQTJ0nq0NdX5rs1-1klVFLxMn_7oJt0vkVzVZGk/edit?usp=sharing");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(link,this);
        JOptionPane.showMessageDialog(null, "Se ha copiado el link al portapapeles.", "Notificación (Manual técnico)", JOptionPane.INFORMATION_MESSAGE);
    }
    public void link3() {
        StringSelection link =  new StringSelection("https://docs.google.com/document/d/1mmVgLMXd56tFlZq--fQn525q0qECjPN0F8oOt6C2Cj4/edit?usp=sharing");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(link,this);
        JOptionPane.showMessageDialog(null, "Se ha copiado el link al portapapeles.", "Notificación (Informe de análisis)", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
