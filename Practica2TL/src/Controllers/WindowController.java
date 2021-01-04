package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WindowController {

    @FXML
    Label lab_1;

    @FXML
    TextArea ta_file;

    @FXML
    ImageView img_file_analyze;

    public void searchFile() { // Método de la primera versión
        File file;
        FileChooser choose = new FileChooser();
        choose.setTitle("Seleccionar archivo");

        // Agregar filtros para facilitar la búsqueda
        choose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java File","*.java")
        );

        file = choose.showOpenDialog(null);

        try {
            // Abro el stream, el fichero debe existir
			FileReader fr = new FileReader(file);

			// Leemos el fichero y lo mostramos por pantalla
			int valor = fr.read();
			String text = "";
			while(valor != -1) {
				text = text.concat(String.valueOf(((char) valor)));
				valor = fr.read();
			}

			//Cerramos el stream
			fr.close();
			this.lab_1.setStyle("-fx-font-weight: 700");
			this.lab_1.setStyle("-fx-background-color: yellow");
			this.lab_1.setText(file.getName());
			this.ta_file.setText(text);
			System.out.print("Archivo leído correctamente");
        } catch (IOException e) {
            System.out.println("Error E/S: "+e);
        }
    }

    public void analyze() {
        if (!ta_file.getText().isEmpty()) {
            Compiler compiler = new Compiler();
//            ArrayList<ArrayList<String>> compiled = compiler.analyze(ta_file.getText());
//            ArrayList<String> responses = compiled.get(0);
//            ArrayList<String> stringLists = compiled.get(1);
//            int errors = Compiler.hasError(responses);
//            if (errors == 0) {
//                this.updateImages(1);
//            } else {
//                this.updateImages(0);
//            }
//            this.results(responses, stringLists, errors);
        } else {
            JOptionPane.showMessageDialog(null, "El archivo esta vacío", "Error", JOptionPane.ERROR_MESSAGE);
            this.updateImages(0);
        }
    }

    private void updateImages(int image) {
        if (image == 1) {
            img_file_analyze.setImage(new Image("Views/images/check.png"));
        } else {
            img_file_analyze.setImage(new Image("Views/images/cross.png"));
        }
    }
}
