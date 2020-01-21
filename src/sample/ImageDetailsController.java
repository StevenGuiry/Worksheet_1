package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageDetailsController {

    @FXML
    private TextField pathTextField, sizeTextField, nameTextField;

    public void initialize() {
        if (MainGUIController.file != null) {
            try {
                BufferedImage bimg = ImageIO.read(MainGUIController.file);
                int width = bimg.getWidth();
                int height = bimg.getHeight();

                pathTextField.setText(MainGUIController.file.getAbsolutePath());
                sizeTextField.setText(width + " x " + height);
                nameTextField.setText(MainGUIController.file.getName());
            } catch (IOException ex) {
                System.err.println(ex);

            }
        }
    }
}
