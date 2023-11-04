
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.*;

public class imageEditor {

    /**
     * Converts a color image to grayscale.
     *
     * @param inputImg The input color image to be converted.
     * @return The grayscale version of the input image.
     */
    public static BufferedImage grayScale(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions, but in grayscale format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the RGB color of the pixel in the input image.
                int rgb = inputImg.getRGB(j, i);

                // Set the same grayscale value for the pixel in the output image.
                outputImg.setRGB(j, i, rgb);
            }
        }

        // Return the grayscale image.
        return outputImg;
    }

    /**
     * Changes the brightness of an image.
     *
     * @param inputImg The input image to adjust the brightness.
     * @param percent  The percentage by which to change brightness. Positive values
     *                 increase brightness,
     *                 and negative values decrease it.
     * @return The image with adjusted brightness.
     */
    public static BufferedImage changeBrightness(BufferedImage inputImg, double percent) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Calculate the new brightness based on the input percentage.
                double brightness = 1 + (percent / 100);

                // Adjust the color channels (red, green, blue) by applying the brightness
                // factor.
                int red = (int) (color.getRed() * brightness);
                int green = (int) (color.getGreen() * brightness);
                int blue = (int) (color.getBlue() * brightness);

                // Ensure that color values stay within the valid range [0, 255].
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                // Create a new color with the adjusted channels.
                Color newColor = new Color(red, green, blue);

                // Set the pixel color in the output image.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with adjusted brightness.
        return outputImg;
    }

    /**
     * Converts an image to its negative (color inversion).
     *
     * @param inputImg The input image to be converted to its negative.
     * @return The negative image.
     */
    public static BufferedImage negative(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Calculate the negative color values by subtracting each channel value from
                // 255.
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();

                // Create a new color with the negative color values.
                Color newColor = new Color(red, green, blue);

                // Set the pixel color in the output image.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the negative image.
        return outputImg;
    }

    /**
     * Converts an image to a sepia (warm vintage) look.
     *
     * @param inputImg The input image to be converted to sepia.
     * @return The image with the sepia effect applied.
     */
    public static BufferedImage sepia(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Calculate the sepia color values based on a weighted combination of the
                // original color channels.
                int red = (int) (color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189);
                int green = (int) (color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168);
                int blue = (int) (color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131);

                // Ensure that color values stay within the valid range [0, 255].
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                // Create a new color with the sepia color values.
                Color newColor = new Color(red, green, blue);

                // Set the pixel color in the output image.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with the sepia effect applied.
        return outputImg;
    }

    /**
     * Rotates an image clockwise by a specified angle.
     *
     * @param inputImg The input image to be rotated.
     * @param angle    The angle in degrees by which to rotate the image clockwise.
     * @return The rotated image.
     */
    public static BufferedImage rotate(BufferedImage inputImg, double angle) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Convert the angle from degrees to radians.
        double radians = Math.toRadians(angle);

        // Calculate sine and cosine values for the rotation angle.
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        // Calculate the center of rotation.
        double x0 = 0.5 * (inputImg.getWidth() - 1);
        double y0 = 0.5 * (inputImg.getHeight() - 1);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Calculate the new pixel position after rotation.
                double a = j - x0;
                double b = i - y0;
                int x = (int) (+a * cos - b * sin + x0);
                int y = (int) (+a * sin + b * cos + y0);

                // Check if the new pixel position is within the bounds of the input image.
                if (x >= 0 && x < inputImg.getWidth() && y >= 0 && y < inputImg.getHeight()) {
                    // Get the color of the pixel in the input image and set it in the output image.
                    outputImg.setRGB(j, i, inputImg.getRGB(x, y));
                }
            }
        }

        // Return the rotated image.
        return outputImg;
    }

    /**
     * Rotates an image counterclockwise by a specified angle.
     *
     * @param inputImg The input image to be rotated.
     * @param angle    The angle in degrees by which to rotate the image
     *                 counterclockwise.
     * @return The rotated image.
     */
    public static BufferedImage rotateCounterclockwise(BufferedImage inputImg, double angle) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Convert the angle from degrees to radians.
        double radians = Math.toRadians(angle);

        // Calculate sine and cosine values for the rotation angle.
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        // Calculate the center of rotation.
        double x0 = 0.5 * (inputImg.getWidth() - 1);
        double y0 = 0.5 * (inputImg.getHeight() - 1);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Calculate the new pixel position after rotation.
                double a = j - x0;
                double b = i - y0;
                int x = (int) (+a * cos + b * sin + x0);
                int y = (int) (-a * sin + b * cos + y0);

                // Check if the new pixel position is within the bounds of the input image.
                if (x >= 0 && x < inputImg.getWidth() && y >= 0 && y < inputImg.getHeight()) {
                    // Get the color of the pixel in the input image and set it in the output image.
                    outputImg.setRGB(j, i, inputImg.getRGB(x, y));
                }
            }
        }

        // Return the counterclockwise rotated image.
        return outputImg;
    }

    /**
     * Mirrors an image horizontally.
     *
     * @param inputImg The input image to be mirrored.
     * @return The horizontally mirrored image.
     */
    public static BufferedImage mirrorHorizontal(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel row in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            // Iterate over each pixel column in the input image.
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image and mirror it horizontally.
                outputImg.setRGB(j, i, inputImg.getRGB(inputImg.getWidth() - j - 1, i));
            }
        }

        // Return the horizontally mirrored image.
        return outputImg;
    }

    /**
     * Mirrors an image vertically.
     *
     * @param inputImg The input image to be mirrored.
     * @return The vertically mirrored image.
     */
    public static BufferedImage mirrorVertical(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel row in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            // Iterate over each pixel column in the input image.
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image and mirror it vertically.
                outputImg.setRGB(j, i, inputImg.getRGB(j, inputImg.getHeight() - i - 1));
            }
        }

        // Return the vertically mirrored image.
        return outputImg;
    }

    /**
     * Applies a red filter to an image, preserving only the red color channel.
     *
     * @param inputImg The input image to which the red filter will be applied.
     * @return The image with only the red color channel preserved.
     */
    public static BufferedImage redFilter(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Extract the red color channel and set other color channels to zero.
                int red = color.getRed();
                Color newColor = new Color(red, 0, 0);

                // Set the pixel color in the output image with only the red color channel
                // preserved.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with the red filter applied.
        return outputImg;
    }

    /**
     * Applies a green filter to an image, preserving only the green color channel.
     *
     * @param inputImg The input image to which the green filter will be applied.
     * @return The image with only the green color channel preserved.
     */
    public static BufferedImage greenFilter(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Extract the green color channel and set other color channels to zero.
                int green = color.getGreen();
                Color newColor = new Color(0, green, 0);

                // Set the pixel color in the output image with only the green color channel
                // preserved.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with the green filter applied.
        return outputImg;
    }

    /**
     * Applies a blue filter to an image, preserving only the blue color channel.
     *
     * @param inputImg The input image to which the blue filter will be applied.
     * @return The image with only the blue color channel preserved.
     */
    public static BufferedImage blueFilter(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel in the input image.
                Color color = new Color(inputImg.getRGB(j, i));

                // Extract the blue color channel and set other color channels to zero.
                int blue = color.getBlue();
                Color newColor = new Color(0, 0, blue);

                // Set the pixel color in the output image with only the blue color channel
                // preserved.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with the blue filter applied.
        return outputImg;
    }

    /**
     * Applies a pixelated effect to an image.
     *
     * @param inputImg  The input image to which the pixelation effect will be
     *                  applied.
     * @param pixelSize The size of each pixel block for the pixelation effect.
     * @return The pixelated image.
     */
    public static BufferedImage pixelate(BufferedImage inputImg, int pixelSize) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over the input image in blocks of pixelSize x pixelSize.
        for (int i = 0; i < inputImg.getHeight(); i += pixelSize) {
            for (int j = 0; j < inputImg.getWidth(); j += pixelSize) {
                int red = 0, green = 0, blue = 0;
                int count = 0;

                // Iterate over the pixels within the current pixel block.
                for (int k = 0; k < pixelSize; k++) {
                    for (int l = 0; l < pixelSize; l++) {
                        if (i + k < inputImg.getHeight() && j + l < inputImg.getWidth()) {
                            // Get the color of the pixel in the input image.
                            Color color = new Color(inputImg.getRGB(j + l, i + k));

                            // Accumulate color channel values.
                            red += color.getRed();
                            green += color.getGreen();
                            blue += color.getBlue();
                            count++;
                        }
                    }
                }

                // Calculate the average color within the pixel block.
                red /= count;
                green /= count;
                blue /= count;

                // Create a new color with the average values.
                Color newColor = new Color(red, green, blue);

                // Set the entire pixel block in the output image with the average color.
                for (int k = 0; k < pixelSize; k++) {
                    for (int l = 0; l < pixelSize; l++) {
                        if (i + k < inputImg.getHeight() && j + l < inputImg.getWidth()) {
                            outputImg.setRGB(j + l, i + k, newColor.getRGB());
                        }
                    }
                }
            }
        }

        // Return the pixelated image.
        return outputImg;
    }

    /**
     * Prints the pixel values (RGB components) of each pixel in the input image.
     *
     * @param inputImg The input image to print pixel values from.
     */
    public static void printPixelValues(BufferedImage inputImg) {
        // Iterate over the image rows (height).
        for (int i = 0; i < inputImg.getHeight(); i++) {
            // Iterate over the image columns (width).
            for (int j = 0; j < inputImg.getWidth(); j++) {
                // Get the color of the pixel at position (j, i).
                Color color = new Color(inputImg.getRGB(j, i));

                // Print the RGB components of the pixel in the format (R G B).
                System.out.print("(" + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + ")");
            }

            // Move to the next line for the next row of pixels.
            System.out.println();
        }
    }

    /**
     * Applies a blur effect to an image.
     *
     * @param inputImg The input image to be blurred.
     * @return The blurred image.
     */
    public static BufferedImage blur(BufferedImage inputImg) {
        // Create a new BufferedImage with the same dimensions and color format.
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        // Iterate over each pixel in the input image.
        for (int i = 0; i < inputImg.getHeight(); i++) {
            for (int j = 0; j < inputImg.getWidth(); j++) {
                int red = 0, green = 0, blue = 0;
                int count = 0;

                // Apply a 3x3 blur kernel to the neighboring pixels.
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int x = j + l;
                        int y = i + k;

                        // Check if the pixel coordinates are within the image bounds.
                        if (x >= 0 && x < inputImg.getWidth() && y >= 0 && y < inputImg.getHeight()) {
                            Color color = new Color(inputImg.getRGB(x, y));
                            red += color.getRed();
                            green += color.getGreen();
                            blue += color.getBlue();
                            count++;
                        }
                    }
                }

                // Calculate the average color values to obtain the blurred pixel color.
                red /= count;
                green /= count;
                blue /= count;

                // Create a new color with the blurred color values.
                Color newColor = new Color(red, green, blue);

                // Set the pixel color in the output image.
                outputImg.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the image with the blur effect applied.
        return outputImg;
    }

    /**
     * The main method for the Image Editor program.
     *
     * @param args The command-line arguments provided by the user (not used
     *             directly for this application).
     * @throws IOException If there is an error reading or writing an image file.
     */
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Image Editor!\nChoose a number between 1 and 16:");
        System.out.println("\t1. Convert to grayscale");
        System.out.println("\t2. Change brightness");
        System.out.println("\t3. Convert to negative");
        System.out.println("\t4. Convert to sepia");
        System.out.println("\t5. Rotate clockwise at an angle");
        System.out.println("\t6. Rotate anti-clockwise at an angle");
        System.out.println("\t7. Mirror horizontally");
        System.out.println("\t8. Mirror vertically");
        System.out.println("\t9. Red filter");
        System.out.println("\t10. Green filter");
        System.out.println("\t11. Blue filter");
        System.out.println("\t12. Pixelate");
        System.out.println("\t13. Print pixel values");
        System.out.println("\t14. Blur the image");
        System.out.println("\t15. Exit");
        System.out.println();

        // Load an image from a file
        BufferedImage inputImage = ImageIO.read(new File("Wallpaper-1.jpeg"));

        // Select operation to be performed
        int choice = sc.nextInt();

        System.out.println("The number you've chosen is " + choice);
        System.out.println("You can see the output by opening the file -> EditedWallpaper-1.jpeg");

        BufferedImage editedImage = null;

        switch (choice) {
            case 1:
                editedImage = grayScale(inputImage);
                break;
            case 2:
                double brightnessPercent = sc.nextDouble();
                editedImage = changeBrightness(inputImage, brightnessPercent);
                break;
            case 3:
                editedImage = negative(inputImage);
                break;
            case 4:
                editedImage = sepia(inputImage);
                break;
            /*
             * case 5:
             * editedImage = blur(inputImage);
             * break;
             */
            case 5:
                double angle = sc.nextDouble();
                editedImage = rotate(inputImage, angle);
                break;
            case 6:
                double anticlockwiseAngle = sc.nextDouble();
                editedImage = rotateCounterclockwise(inputImage, anticlockwiseAngle);
                break;
            case 7:
                editedImage = mirrorHorizontal(inputImage);
                break;
            case 8:
                editedImage = mirrorVertical(inputImage);
                break;
            case 9:
                editedImage = redFilter(inputImage);
                break;
            case 10:
                editedImage = greenFilter(inputImage);
                break;
            case 11:
                editedImage = blueFilter(inputImage);
                break;
            case 12:
                int pixelSize = sc.nextInt();
                editedImage = pixelate(inputImage, pixelSize);
                break;
            case 13:
                printPixelValues(inputImage);
                break;
            case 14:
                editedImage = blur(inputImage);
                break;
            case 15:
                System.out.println("Exiting the code!");
                return;
            default:
                System.out.println("Error:Invalid Choice");
                break;
        }

        // Display or save the edited image as needed
        ImageIO.write(editedImage, "jpg", new File("EditedWallpaper-1.jpeg"));
    }
}