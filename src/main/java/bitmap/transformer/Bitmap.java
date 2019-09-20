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

    public void randomize() throws IOException {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(i,j,randomRGBValue());
            }
        }

        ImageIO.write(image, "bmp", new File("src/main/resources/marioRandomized.bmp"));
    }

    private int randomRGBValue() {
        return (int)(Math.random() * 16_777_216);
    }
}
