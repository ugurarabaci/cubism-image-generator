package com.cubicimagegenerator.domain.service;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.model.ImageMetadata;
import com.cubicimagegenerator.exception.ImageProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class ImageResizingService {
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;

    public Image resize(Image image) {
        try {
            if (!image.requiresResize()) {
                return image;
            }

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(image.getContent()));
            if (originalImage == null) {
                throw new ImageProcessingException("Failed to read image content", null);
            }

            int[] newDimensions = calculateNewDimensions(originalImage.getWidth(), originalImage.getHeight());
            BufferedImage resizedImage = resizeImage(originalImage, newDimensions[0], newDimensions[1]);
            
            byte[] resizedContent = toByteArray(resizedImage);
            ImageMetadata newMetadata = new ImageMetadata(
                    resizedContent.length,
                    image.getMetadata().getFormat(),
                    newDimensions[0],
                    newDimensions[1]
            );

            return new Image(resizedContent, newMetadata);
        } catch (IOException e) {
            throw new ImageProcessingException("Failed to resize image", e);
        }
    }

    private int[] calculateNewDimensions(int width, int height) {
        double ratio = Math.min((double) MAX_WIDTH / width, (double) MAX_HEIGHT / height);
        if (ratio >= 1) {
            return new int[]{width, height};
        }
        return new int[]{(int) (width * ratio), (int) (height * ratio)};
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }

    private byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", outputStream);
        return outputStream.toByteArray();
    }
} 