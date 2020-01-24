package sample;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainGUIController {

    public Image img;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXToggleButton toggleButton;
    @FXML
    private JFXSlider hueSlider, contrastSlider, satSlider, brightSlider;
    @FXML
    private Button exitBtn;

    public static File file;

    public void initialize() {
        colorAdjust();
        toggleButton.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (toggleButton.isSelected() && imageView.getImage() != null) {

                toggleButton.setText("Greyscale ON");
                imageView.setImage(greyScale());

            } else if (!toggleButton.isSelected() && file != null) {

                toggleButton.setText("Greyscale OFF");
                String path = file.toURI().toString();
                img = new Image(path);
                imageView.setImage(img);

            } else {
                toggleButton.setText("No Image Selected");
            }
        });
    }

    public void colorAdjust() {
        ColorAdjust colorAdjust = new ColorAdjust();

        hueSlider.valueProperty().addListener(
                (observableValue, number, t1) -> colorAdjust.setHue(hueSlider.getValue() / 10));
        contrastSlider.valueProperty().addListener(
                (observableValue, number, t1) -> colorAdjust.setContrast(contrastSlider.getValue() / 10));
        satSlider.valueProperty().addListener(
                (observableValue, number, t1) -> colorAdjust.setSaturation(satSlider.getValue() / 10));
        brightSlider.valueProperty().addListener(
                (observableValue, number, t1) -> colorAdjust.setBrightness(brightSlider.getValue() / 10));
        imageView.setEffect(colorAdjust);
    }

    public void fileChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        String path = file.toURI().toString();
        img = new Image(path);
        imageView.setImage(img);
    }

    @SuppressWarnings("Duplicates")
    public Image greyScale() {

        PixelReader pixelReader = img.getPixelReader();

        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        WritableImage grayImg = new WritableImage(width, height);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int rgb = pixelReader.getArgb(x, y);

                int alpha = (rgb >> 24) & 0xFF;     //0xff is the hex value of 255
                int red = (rgb >> 16) & 0xFF;       //bit shift operator used to shift bits
                int green = (rgb >> 8) & 0xFF;
                int blue = (rgb & 0xFF);

                int avg = (red + green + blue) / 3;

                rgb = (alpha << 24) | (avg << 16) | (avg << 8) | avg;

                grayImg.getPixelWriter().setArgb(x, y, rgb);
            }
        imageView.setImage(grayImg);
        return grayImg;
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

    public void handleCloseBtnAction(ActionEvent e) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
