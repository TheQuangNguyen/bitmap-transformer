package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.*;

public class Bitmap {

    private File file;
    private BufferedImage image;
    private int height;
    private int width;

    public Bitmap(File file) throws IOException {
        this.file = file;
        this.image = ImageIO.read(file);
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }

    public void writeToFile(String filePath) throws IOException {
        ImageIO.write(image, "bmp", new File(filePath));
    }

    // Transform the input bmp file to ones with random colors for every pixels
    public void randomize() throws IOException {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i,j,randomRGBValue());
            }
        }
    }

    private int randomRGBValue() {
        return (int)(Math.random() * 16_777_216);
    }

    // Transform the input bmp file by inverting the color for each pixel
    public void invert() throws IOException {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,invertRGBValue(currentRGB));
            }
        }
    }
    private int invertRGBValue(int currentRGBValue) {
        return 16_777_215 - currentRGBValue;
    }

    // Transform input bmp file to black or white/grayscale
    // https://www.tutorialspoint.com/dip/grayscale_to_rgb_conversion.htm
    // I use this resource to figure out the conversion of RGB to grayscale
    public void grayscale() throws IOException {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,grayscaleRGBValue(currentRGB));
            }
        }
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int currentRGB = image.getRGB(i,j);
                image.setRGB(i,j,changeBrightnessRGBValue(currentRGB, percentage));
            }
        }
    }

    private int changeBrightnessRGBValue(int currentRGBValue, int percentage) {

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


    // Rotate the image 90 degrees clockwise or counterclockwise
    // input i should be either positive or negative for clockwise or counterclockwise.
    // Input i is multiple of 90 degree so if i = 3, rotate 270 degrees clockwise
    public void rotate90degrees(int clockWiseOrCounterClockwise) throws IOException {
        BufferedImage imageCopy = ImageIO.read(file);
        int width = imageCopy.getWidth()-1;
        int height = imageCopy.getHeight()-1;
        if (clockWiseOrCounterClockwise > 0) {
            for (int i = 0; i <= width; i++) {
                for (int j = 0; j <= height; j++) {
                    int currentRGB = imageCopy.getRGB(i, j);
                    image.setRGB(height - j, width - i, currentRGB);
                }
            }
        } else if (clockWiseOrCounterClockwise < 0) {
            for (int i = 0; i <= width; i++) {
                for (int j = 0; j <= height; j++) {
                    int currentRGB = imageCopy.getRGB(i, j);
                    image.setRGB(j, height - i, currentRGB);
                }
            }
        } else {
            System.out.println("Enter positive or negative for clockwise or counterclockwise");
        }
    }

    // reverse an image vertically
    public void reverseVertically() {
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height/2; j++){
                int temp = image.getRGB(i,j);
                image.setRGB(i,j,image.getRGB(i,height - j - 1));
                image.setRGB(i,height -j -1, temp);
            }
        }
    }

    // reverse an image horizontally
    public void reverseHorizontally() {
        for (int i = 0; i < height/2; i++){
            for (int j = 0; j < width; j++){
                int temp = image.getRGB(i,j);
                image.setRGB(i,j,image.getRGB(width - i - 1,j));
                image.setRGB(width - i - 1,j, temp);
            }
        }
    }
}
