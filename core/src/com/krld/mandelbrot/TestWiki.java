package com.krld.mandelbrot;

/**
 * Created by Andrey on 7/8/2014.
 */
public class TestWiki {
    public static void main(String[] args) {
        double realCoord, imagCoord;
        double realTemp, imagTemp, realTemp2, arg;
        int iterations;
        for (imagCoord = 1.2; imagCoord >= -1.2; imagCoord -= 0.05) {
            for (realCoord = 1.77; realCoord >= -0.6; realCoord -= 0.03) {
                iterations = 0;
                realTemp = realCoord;
                imagTemp = imagCoord;
                arg = (realCoord * realCoord) + (imagCoord * imagCoord);
                while ((arg < 4) && (iterations < 40)) {
                    realTemp2 = (realTemp * realTemp) - (imagTemp * imagTemp) - realCoord;
                    imagTemp = (2 * realTemp * imagTemp) - imagCoord;
                    realTemp = realTemp2;
                    arg = (realTemp * realTemp) + (imagTemp * imagTemp);
                    iterations += 1;
                }
                if (arg < 4) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        System.out.println();
    }
}
