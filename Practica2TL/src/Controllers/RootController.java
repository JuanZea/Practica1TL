package Controllers;

import Models.Compiled;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;

public class RootController {

    public Compiled compiled;

    @FXML
    Label t1_lab_1;

    @FXML
    TextArea t1_ta;

    @FXML
    ImageView t1_img;

    public void searchFile() {
        // Arrange
        String text = "";
        File file;
        InputStreamReader in = null;
        BufferedReader br;
        FileChooser choose = new FileChooser();
        choose.setTitle("Seleccionar archivo");

        // Agregar filtros para facilitar la búsqueda
        choose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java File","*.java")
        );

        file = choose.showOpenDialog(null);

        // En caso de no seleccionar ningún archivo
        if (file == null) {
            this.t1_lab_1.getStyleClass().clear();
			this.t1_lab_1.getStyleClass().add("danger");
			this.t1_lab_1.setText("Archivo no detectado");
			return;
        }

        try {
            // Abro el stream, el fichero debe existir
            in = new FileReader(file);
            br = new BufferedReader(in);

            // Se lee linea a linea
            String line;
            while ((line = br.readLine()) != null) {
                text = text.concat(line + "\n");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            //Cerramos el stream
            try {
			    in.close();
			    this.t1_lab_1.getStyleClass().clear();
                this.t1_lab_1.getStyleClass().add("success");
                this.t1_lab_1.setText(file.getName());
                this.t1_ta.setText(text);
                System.out.print("Archivo leído correctamente");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void analyze() {
        if (!t1_ta.getText().isEmpty()) {
            Compiler compiler = new Compiler();
            compiler.setFile(t1_ta.getText());
            this.compiled = compiler.analyze();
            System.out.println(this.compiled.log());
            this.updateImages(1);
        } else {
            JOptionPane.showMessageDialog(null, "El archivo esta vacío", "Error", JOptionPane.ERROR_MESSAGE);
            this.updateImages(0);
        }
    }

    private void updateImages(int image) {
        if (image == 0) {
            t1_img.setImage(new Image("Resources/Images/cross.png"));
        } else {
            t1_img.setImage(new Image("Resources/Images/check.png"));
        }
    }
}
