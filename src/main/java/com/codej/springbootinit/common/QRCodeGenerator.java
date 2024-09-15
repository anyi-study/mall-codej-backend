package com.codej.springbootinit.common;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {

    public static void generateQRCode(String code, String filePath) throws WriterException {
        int width = 300;
        int height = 300;
        String imageFormat = "png";

        BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        try {
            ImageIO.write(image, imageFormat, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
