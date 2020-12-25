package com.edu.aydin;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *In this class, the image converted to a text file is processed and the original image,
 *gray-scale image, binary image, vertical histogram, horizontal histogram and the image whose characters are detected are created.
 *The file entered as a parameter in the constructor method is read.
 *Width and length information is taken and this information is multiplied. An image array is created in line with this information.
 *Then the pixel values of the image are entered in this array.
 * **/

public class ProcessImage {

    private String fileType;
    private int width;
    private int height;
    private int[] imageArray;
    private Color[] colorArray;
    private Scanner scanner;
    private final int threshold = 383;
    private int[] binaryImage;

    public ProcessImage(String fileName) {
        try {
            this.scanner = new Scanner(new File(fileName));
            this.fileType = this.scanner.next();
            this.width = this.scanner.nextInt();
            this.height = this.scanner.nextInt();
            this.imageArray = new int[width * height * 3];
            this.binaryImage = new int[width * height];
            for (int i = 0; i < (width * height * 3); i++)
                this.imageArray[i] = scanner.nextInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *In this method, DrawingPanel class is created that will create the original image.
     *A Color sequence is created according to the information read from the image sequence and the information is written into this sequence.
     *The size of the color array is one third of the image array. This is because each pixel of the original image represents 3 values. These values are r, g, b values.
     *A single Color object is created from the values of r, g, b and put in the order where it represents the pixel in the array.
     *Finally, a DrawingPanel object is created from this information and returned.
     * **/

    public DrawingPanel getOriginalImage() {
        int k = 0;
        this.colorArray = new Color[width * height];
        for (int j = 0; j < (width * height * 3); j += 3)
            this.colorArray[k++] = new Color(imageArray[j], imageArray[j + 1], imageArray[j + 2]);
        return new DrawingPanel(width, height, this.colorArray, "original");
    }

    /**
     *In this method, DrawingPanel class is created that will create the gray scale image.
     *Unlike the previous method, this method creates a single value from the r, g, b values that make up a pixel.
     *Each of the values of R, g, b is multiplied by a different coefficient and the result of these values is added to a new value.
     *Then a Color object is created according to the obtained value and added to the Color array.
     * Finally, a DrawingPanel object is created from this information and returned.
     * **/
    public DrawingPanel getGrayscaleImage() {
        int k = 0;
        this.colorArray = new Color[width * height];
        for (int j = 0; j < (width * height * 3); j += 3) {
            int grayValue = (int) ((imageArray[j] * 0.299) + (imageArray[j + 1] * 0.587) + (imageArray[j + 2] * 0.114));
            this.colorArray[k++] = new Color(grayValue, grayValue, grayValue);
        }
        return new DrawingPanel(width, height, this.colorArray, "gray");
    }

    /**
     *In this method, DrawingPanel class is created that will create the binary image.
     *R, g, b values of the visual are taken and these values are summed and these values are compared with the threshold value.
     *Our threshold value is 383.Because of 383 is the average of black and white colors, if the total RGB value of one color is greater than 383 it means that color is the light color
     *we will set to it white, otherwise, we will set black for that color.
     *Finally, a DrawingPanel object is created from this information and returned.
     *
     * **/
    public DrawingPanel getBinarizedImage() {
        int k = 0;
        this.colorArray = new Color[width * height];
        for (int j = 0; j < (width * height * 3); j += 3) {
            int redValue = imageArray[j];
            int greenValue = imageArray[j + 1];
            int blueValue = imageArray[j + 2];
            if ((redValue + greenValue + blueValue) >= threshold) {
                this.colorArray[k++] = new Color(Color.WHITE.getRGB());
            } else {
                this.colorArray[k++] = new Color(Color.BLACK.getRGB());
            }
        }
        return new DrawingPanel(width, height, this.colorArray, "binary");
    }

    /**
     *In this method, DrawingPanel class is created that will create the horizontal histogram of image.
     *First, the binary image array is obtained. An array is created as the width of the image.
     *Horizontal histogram is calculated for every row as sum of all column pixel values inside the row.
     * Finally, a DrawingPanel object is created from this information and returned.
     * **/
    public DrawingPanel getHorizontalProjection() {
        getBinaryImageArr();
        int[] horizontalProjection = new int[width];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                horizontalProjection[row] += binaryImage[col * width + row];
            }
        }
        return new DrawingPanel(width, height, horizontalProjection, "horizontal");
    }

    /**
     *In this method, DrawingPanel class is created that will create the vertical histogram of image.
     *First, the binary image array is obtained. An array is created as the height of the image.
     *Vertical histogram is calculated for every column as sum of all row pixel values inside the column.
     *Finally, a DrawingPanel object is created from this information and returned.
     * **/
    public DrawingPanel getVerticalProjection() {
        getBinaryImageArr();
        int[] verticalProjection = new int[height];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                verticalProjection[col] += binaryImage[col * width + row];
            }
        }
        return new DrawingPanel(width, height, verticalProjection, "vertical");
    }

    /**
     *In this method, DrawingPanel class is created that will create the original image.
     *A binary image array, horizontal and vertical histogram is obtained.
     *Finally, a DrawingPanel object is created from this information and returned.
     * **/
    public DrawingPanel detectCharacters() {
        getBinaryImageArr();
        int[] horizontalProjection = new int[width];
        int[] verticalProjection = new int[height];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                horizontalProjection[row] += binaryImage[col * width + row];
                verticalProjection[col] += binaryImage[col * width + row];
            }
        }
        return new DrawingPanel(width, height, verticalProjection, horizontalProjection, binaryImage, "detect");
    }

    /**
     *In this method, the original image array is converted into a binary image array.
     *R, g, b values of the visual are taken and these values are summed and these values are compared with the threshold value.
     * 1 is assigned for pixels larger than the threshold value and 0 for smaller ones.
     *
     * **/
    private void getBinaryImageArr() {
        int k = 0;
        for (int j = 0; j < (width * height * 3); j += 3) {
            int redValue = imageArray[j];
            int greenValue = imageArray[j + 1];
            int blueValue = imageArray[j + 2];
            if ((redValue + greenValue + blueValue) >= threshold) {
                binaryImage[k++] = 1;
            } else {
                binaryImage[k++] = 0;
            }
        }
    }
}