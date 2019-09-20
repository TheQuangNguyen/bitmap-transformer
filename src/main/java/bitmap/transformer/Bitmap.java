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

        // Convert single rgb value to separate values for red, green, and blue;
        int red = (int) ((Math.pow(256,3) + currentRGBValue) / 65536);
        int green = (int) (((Math.pow(256,3) + currentRGBValue) / 256 ) % 256 );
        int blue = (int) ((Math.pow(256,3) + currentRGBValue) % 256);

        int grayscale = (int)( (0.3 * red) + (0.59 * green) + (0.11 * blue) );
        return 65536 * grayscale + 256 * grayscale + grayscale;
    }

    // Lighten and Darken an image
    // input positive number for lighten the image, negative number for darken the image

    public void changeBrightness(int percentage) throws IOException {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,changeBrightnessRGBValue(currentRGB, percentage));
            }
        }

        ImageIO.write(image, "bmp", new File("src/main/resources/brightness.bmp"));
    }

    public int changeBrightnessRGBValue(int currentRGBValue, int percentage) {

        // Convert single rgb value to separate values for red, green, and blue;
        double red =  ((Math.pow(256,3) + currentRGBValue) / 65536);
        double green = (((Math.pow(256,3) + currentRGBValue) / 256 ) % 256 );
        double blue = ((Math.pow(256,3) + currentRGBValue) % 256);

        // Change RGB values accordingly to brighten/darken image
        red = red + red*((double)percentage / 100d);
        blue = blue + blue*((double)percentage / 100d);
        green = green + green*((double)percentage / 100d);

        red = checkingForCappedRGB(red);
        blue = checkingForCappedRGB(blue);
        green = checkingForCappedRGB(green);

        return (int)(65536 * red + 256 * green + blue);
    }

    private int checkingForCappedRGB(double RGB) {
        if (RGB > 255) {
            return 255;
        } else if (RGB < 0) {
            return 0;
        } else {
            return (int)RGB;
        }
    }
}
