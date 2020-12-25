package com.edu.aydin;

import javax.swing.*;

/**
 * It is the main class of the application.
 **/

public class Application extends JFrame {

    public Application() {
        ConvertJpgToFile convertJpgToFile = new ConvertJpgToFile("plate.jpeg");
        convertJpgToFile.convert();
        ProcessImage processImage = new ProcessImage(convertJpgToFile.getNewFileName());
        JTabbedPane tp = new JTabbedPane();
        DrawingPanel dp1 = processImage.getOriginalImage();
        tp.addTab("Original Image", dp1);
        DrawingPanel dp2 = processImage.getGrayscaleImage();
        tp.addTab("Grayscale Image", dp2);
        DrawingPanel dp3 = processImage.getBinarizedImage();
        tp.addTab("Binarized Image", dp3);
        DrawingPanel dp4 = processImage.getHorizontalProjection();
        tp.addTab("Horizontal Histogram", dp4);
        DrawingPanel dp5 = processImage.getVerticalProjection();
        tp.addTab("Vertical Histogram", dp5);
        DrawingPanel dp6 = processImage.detectCharacters();
        tp.addTab("Boxed Characters", dp6);
        tp.setSelectedIndex(0);
        this.add(tp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(850, 800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Application();
    }
}
