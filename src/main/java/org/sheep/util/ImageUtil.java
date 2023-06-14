package org.sheep.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    private ImageUtil() {
    }

    public static Image getImageFromResources(String name, String path) throws IOException {
        InputStream is = FileUtil.getFileStreamFromResources(name, path);
        if (is != null) {
            return ImageIO.read(is);
        }
        throw new IOException("InputStream for [" + name + "] was null");
    }

    public static BufferedImage resize(Image originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }

    public static InputStream convertBufferedImageToInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }
}
