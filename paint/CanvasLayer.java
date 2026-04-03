package paint;

import java.awt.*;
import java.awt.image.BufferedImage;

// Owns and manages the persistent pixel buffer that stores everything drawn.
// Keeping this separate from CanvasPanel means the panel doesn't have to
// worry about buffer allocation or resize logic.
public class CanvasLayer {

    private BufferedImage image;

    public void ensureSize(int width, int height) {
        int w = Math.max(1, width);
        int h = Math.max(1, height);

        if (image == null || image.getWidth() != w || image.getHeight() != h) {
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newImage.createGraphics();

            if (image != null) {
                g2.drawImage(image, 0, 0, null); // preserve existing content on resize
            } else {
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, w, h);
            }

            g2.dispose();
            image = newImage;
        }
    }

    public Graphics2D createGraphics() {
        return image.createGraphics();
    }

    public BufferedImage getImg() {
        return image;
    }
}