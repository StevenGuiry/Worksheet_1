package sample;

import com.jfoenix.controls.JFXToggleButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGUIController {

    @FXML
    private ImageView imageView;
    @FXML
    private JFXToggleButton toggleButton;
    @FXML
    private Button exitBtn;

    public static File file;

    public void initialize() {
        toggleButton.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(toggleButton.isSelected() && imageView.getImage() != null){
                toggleButton.setText("Greyscale ON");
                Image img = imageView.getImage();
                BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
                imageView.setImage(greyScale(bimg));
            } else if (!toggleButton.isSelected() && file != null){
                toggleButton.setText("Greyscale OFF");

                try {
                    BufferedImage buffImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(buffImage, null);
                    imageView.setImage(image);
                } catch (IOException iox) {
                    System.err.println("Exception");
                }
            } else {
                toggleButton.setText("No Image Selected");
            }
        });
    }

    public void fileChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        try {
            BufferedImage buffImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(buffImage,null);
            imageView.setImage(image);
        } catch (IOException ex) {
            System.err.println("IO Exception");
        }
    }

    public static Image greyScale(BufferedImage img) {

        for (int x =0; x < img.getWidth();x++)
            for (int y = 0;y < img.getHeight(); y++) {
                int rgb = img.getRGB(x,y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;       //bit shift operator used to shift bit 16 bits to the right
                int green = (rgb >> 8) & 0xFF;
                int blue = (rgb & 0xFF);

                int avg = (red + green + blue)/3;

                rgb = (alpha << 24) | (avg << 16) | (avg << 8) | avg;

                img.setRGB(x,y,rgb);
            }
        Image image = SwingFXUtils.toFXImage(img,null);
            return image;
    }

    public void changeScene(ActionEvent e, String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(pane);

      //Stage newStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(pane.getScene().getWindow());
        newStage.setScene(scene);
        newStage.show();
    }

    public void rgbScene(ActionEvent e) throws IOException {
        changeScene(e, "rgbChannels.fxml");
    }

    public void imageDetailsScene(ActionEvent e) throws IOException {
        changeScene(e, "imageDetails.fxml");
    }

    public void handleCloseBtnAction(ActionEvent e){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
