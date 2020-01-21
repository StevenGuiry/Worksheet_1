package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RGBChannelController {

    @FXML
    private ImageView greenImage,redImage,blueImage;

    public void initialize () {

        if (MainGUIController.file != null) {
            BufferedImage bimgr,bimgg,bimgb;
            //read image
            try {
                bimgr = ImageIO.read(MainGUIController.file);
                bimgg = ImageIO.read(MainGUIController.file);
                bimgb = ImageIO.read(MainGUIController.file);
                redImage.setImage(makeRed(bimgr));
                greenImage.setImage(makeGreen(bimgg));
                blueImage.setImage(makeBlue(bimgb));
            } catch (IOException e) {
                System.err.println("IO Exception");
            }
        } else {
            System.err.println("No Image Selected");
        }
    }

    public static Image makeRed(BufferedImage bimg) {
        int width = bimg.getWidth();
        int height = bimg.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bimg.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;

                //set new RGB, keeping the red value and setting green
                //and blue as 0.
                pixel = (alpha << 24) | (red << 16) | (0);

                bimg.setRGB(x, y, pixel);
            }
        }
        Image rImage = SwingFXUtils.toFXImage(bimg, null);
        return rImage;
    }

    public static Image makeGreen(BufferedImage bimg) {
                int width = bimg.getWidth();
                int height = bimg.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width;x++) {
                        int pixel = bimg.getRGB(x,y);

                        int a = (pixel >> 24) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;

                        pixel = (a<<24) | (g<<8) | (0);

                        bimg.setRGB(x,y,pixel);
                    }
                }
                Image gImage = SwingFXUtils.toFXImage(bimg,null);
                return gImage;
    }

    public static Image makeBlue(BufferedImage bimg) {
        int width = bimg.getWidth();
        int height = bimg.getHeight();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width;x++) {
                int pixel = bimg.getRGB(x,y);

                int a = (pixel >> 24) & 0xFF;
                int b = (pixel) & 0xFF;

                pixel = (a<<24) | (0) | b;

                bimg.setRGB(x,y,pixel);
            }
        }
        Image bImage = SwingFXUtils.toFXImage(bimg,null);
        return bImage;

    }
}
