package com.edu.aydin;

import javax.swing.*;
import java.awt.*;

/**
 * It is the class in which images are created.
 * **/
public class DrawingPanel extends JPanel {
    private int width;
    private int height;
    private String type;
    private Color color[];
    private int[] imageArray;
    private int[] protection;
    private int[] verticalProtection;
    private int[] horizontalProtection;


    /**
     *This constructor method is the method used in the creation of horizontal and vertical histogram graphics.
     * It takes width, height, histogram array and histogram type as parameters.
     * **/
    public DrawingPanel(int width, int height, int[] protection, String type) {
        this.width = width;
        this.height = height;
        this.protection = protection;
        this.type = type;
    }

    /**
     *This constructor method is the method that creates the image in which the characters in the image are framed.
     * It takes width, height,vertical and horizontal histogram array, image array type as parameters.
     * **/
    public DrawingPanel(int width, int height, int[] verticalProtection, int[] horizontalProtection, int[] imageArray, String type) {
        this.width = width;
        this.height = height;
        this.verticalProtection = verticalProtection;
        this.horizontalProtection = horizontalProtection;
        this.imageArray = imageArray;
        this.type = type;
    }

    /**
     * This constructive method is the method in which the original, gray-scale and binary image is created.
     * It takes width, height, color array and type as parameters.
     * **/
    public DrawingPanel(int width, int height, Color[] color, String type) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
    }

    /**
     * It is the method that creates images.
     * Creates the visual according to the type parameter and the parameters coming from the constructor methods and displays it in the panel.
     *
     * **/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.type) {
            case "binary":
            case "original":
            case "gray":
                for (int col = 0; col < width; col++)
                    for (int row = 0; row < height; row++) {
                        g.setColor(color[row * width + col]);
                        g.fillRect(col, row, 1, 1);
                    }
                break;
            case "horizontal":
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height - protection[i]; j++) {
                        g.setColor(Color.BLACK);
                        g.fillRect(i + 10, height - j, 1, 1);
                    }
                break;
            case "vertical":
                for (int i = 0; i < height; i++)
                    for (int j = 0; j < width - protection[i]; j++) {
                        g.setColor(Color.BLACK);
                        g.fillRect(i + 10, width - j, 1, 1);
                    }
                break;
            case "detect":
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        g.setColor(imageArray[j * width + i] == 0 ? Color.BLACK : Color.WHITE);
                        g.fillRect(i, j, 1, 1);
                        g.setColor(Color.RED);
                        if (horizontalProtection[i] > (height / 1.13) || verticalProtection[j] > (width / 1.12)) {
                            g.fillRect(i, j, 1, 1);
                        }
                    }
                break;
        }
    }
}