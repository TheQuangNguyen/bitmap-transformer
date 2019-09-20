package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.*;

public class Bitmap {

    BufferedImage image;

    public Bitmap(File file) throws IOException {
        this.image = read(file);
    }

    // Transform the input bmp file to ones with random colors for every pixels
    public void randomize() throws IOException {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(i,j,randomRGBValue());
            }
        }

        ImageIO.write(image, "bmp", new File("src/main/resources/randomized.bmp"));
    }

    private int randomRGBValue() {
        return (int)(Math.random() * 16_777_216);
    }

    // Transform the input bmp file by inverting the color for each pixel
    public void invert() throws IOException {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,invertRGBValue(currentRGB));
            }
        }

        ImageIO.write(image, "bmp", new File("src/main/resources/invert.bmp"));
    }
    private int invertRGBValue(int currentRGBValue) {
        return 16_777_215 - currentRGBValue;
    }

    // Transform input bmp file to black or white/grayscale
    // https://www.tutorialspoint.com/dip/grayscale_to_rgb_conversion.htm
    // I use this resource to figure out the conversion of RGB to grayscale
    public void grayscale() throws IOException {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,grayscaleRGBValue(currentRGB));
            }
        }

        ImageIO.write(image, "bmp", new File("src/main/resources/grayscale.bmp"));
    }

    // https://stackoverflow.com/questions/4801366/convert-rgb-values-to-integer
    // I use this resource to get formula to convert single rgb integer to each red, blue, and green values.
    private int grayscaleRGBValue(int currentRGBValue) {
        int red = (int) ((Math.pow(256,3) + currentRGBValue) / 65536);
        int green = (int) (((Math.pow(256,3) + currentRGBValue) / 256 ) % 256 );
        int blue = (int) ((Math.pow(256,3) + currentRGBValue) % 256);
        return (int)((0.3 * red) + (0.59 * green) + (0.11 * blue));
    }
}
