package org.dru.car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class Track {
    private static final BufferedImage image;
    private static final int[] pixels;

    static {
        try {
            image = ImageIO.read(Track.class.getResource("/track1.png"));
            pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        } catch (final Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    public Track() {
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void paintTrack(final Graphics2D g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.drawImage(image, 0, 0, null);
    }

    public int pixel(double x, double y) {
        int ix = (int) x;
        int iy = (int) y;
        if (ix >= 0 && ix < image.getWidth() && iy >= 0 && iy < image.getHeight()) {
            return pixels[iy * image.getWidth() + ix];
        }
        return 0;
    }
}
