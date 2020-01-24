package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class RGBChannelController {

    @FXML
    private Tab redChannel, greenChannel, blueChannel;
    @FXML
    private ImageView greenImage, redImage, blueImage;
    public Image rgbImg;

    @SuppressWarnings("Duplicates")
    public void initialize() {
        if (MainGUIController.file != null) {
            String path = MainGUIController.file.toURI().toString();
            rgbImg = new Image(path);

            PixelReader pixelReader = rgbImg.getPixelReader();

            int width = (int) rgbImg.getWidth();
            int height = (int) rgbImg.getHeight();

            WritableImage writeImg = new WritableImage(width, height);

            if (redChannel.isSelected()) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int pixel = pixelReader.getArgb(x, y);

                        int alpha = (pixel >> 24) & 0xff;
                        int red = (pixel >> 16) & 0xff;

                        pixel = (alpha << 24) | (red << 16) | (0);                                                      //set new RGB, keeping the red value and setting green and blue as 0.
                        writeImg.getPixelWriter().setArgb(x, y, pixel);
                    }
                }
                redImage.setImage(writeImg);
                System.out.println("Red");
            } else if (greenChannel.isSelected()) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int pixel = pixelReader.getArgb(x, y);

                        int alpha = (pixel >> 24) & 0xFF;
                        int green = (pixel >> 8) & 0xFF;

                        pixel = (alpha << 24) | (green << 8) | (0);
                        writeImg.getPixelWriter().setArgb(x, y, pixel);
                    }
                }
                greenImage.setImage(writeImg);
                System.out.println("Green");
            } else if (blueChannel.isSelected()) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int pixel = pixelReader.getArgb(x, y);

                        int alpha = (pixel >> 24) & 0xFF;
                        int blue = (pixel) & 0xFF;

                        pixel = (alpha << 24) | (0) | blue;
                        writeImg.getPixelWriter().setArgb(x, y, pixel);
                    }
                }
                blueImage.setImage(writeImg);
                System.out.println("Blue");
            }
        } else {
            System.out.println("No Image Selected");
        }
    }
}
