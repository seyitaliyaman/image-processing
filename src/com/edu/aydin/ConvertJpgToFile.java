package com.edu.aydin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class processes the image entered in jpeg format, converts it into a text file format and saves it.
 * It reads the file in the directory according to the filename parameter entered in the constructor method and keeps it in the BufferedImage object.
 * In the convert method, the width and height values of the image are taken.
 * Then the r, g, and b values of each pixel are taken and get text with the StringBuilder.
 * Finally, the text created with the StringBuilder is written to the file with the BufferedWriter object and saved.
 **/

public class ConvertJpgToFile {

    private String fileName;
    private BufferedImage image;
    private BufferedWriter bufferedWriter;
    private String newFileName;

    public ConvertJpgToFile(String fileName) {
        this.fileName = fileName;
        this.newFileName = fileName.split("\\.")[0] + ".advprog";
        try {
            this.image = ImageIO.read(new File(fileName));
            this.bufferedWriter = new BufferedWriter(new FileWriter(this.newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convert() {
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        StringBuilder builder = new StringBuilder();
        builder.append("3")
                .append("\n")
                .append(width)
                .append(" ")
                .append(height)
                .append("\n");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(this.image.getRGB(j, i));
                int rValue = color.getRed();
                int gValue = color.getGreen();
                int bValue = color.getBlue();
                builder.append(rValue)
                        .append(" ")
                        .append(gValue)
                        .append(" ")
                        .append(bValue)
                        .append(" ");
            }
            builder.append("\n");
        }

        try {
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNewFileName() {
        return this.newFileName;
    }
}
