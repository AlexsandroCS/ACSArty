package org.example.services;

import net.sourceforge.tess4j.Tesseract;
import org.example.utils.StringCleaner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;

public class OCRService {
    private static final String TESSERACT_DATA_PATH = "C:/Users/alexs/AppData/Local/Programs/Tesseract-OCR/tessdata";
    private final StringCleaner stringCleaner = new StringCleaner();

    public String processImage(BufferedImage image) {
        try {
            File debugImageFile = new File("image_mil.jpg");
            if (debugImageFile.exists()) {
                debugImageFile.delete();
            }
            ImageIO.write(image, "jpg", debugImageFile);

            // Configuração do Tesseract para modo silencioso.
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(TESSERACT_DATA_PATH);
            tesseract.setLanguage("eng");
            tesseract.setTessVariable("debug_file", "/dev/null");

            // Desativa os logs do Tess4J.
            java.util.logging.Logger.getLogger("net.sourceforge.tess4j").setLevel(Level.OFF);

            String result = tesseract.doOCR(debugImageFile);

            for (String line : result.split("\n")) {
                if (line.toUpperCase().contains("ELEVACAO") && line.toUpperCase().contains("MIL")) {
                    String milPart = line.replaceAll("[^0-9]", "");
                    if (milPart.length() >= 3) {
                        String milValue = milPart.substring(milPart.length() - 3);
                        return stringCleaner.cleanMilString(milValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}