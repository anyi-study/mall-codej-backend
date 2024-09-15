package com.codej.springbootinit.common;

import com.codej.springbootinit.exception.BusinessException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
@Component
public class CodeGenerator {

    /**
     * 生成二维码
     *
     * @param code 兑换码
     * @param width 二维码宽度
     * @param height 二维码高度
     * @return 返回二维码图片的URL
     */
    public String generateQRCode(String code, int width, int height) {
        try {
            // 创建文件保存路径
            String directoryPath = "qrcodes";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 创建目录
            }

            String filePath = "qrcodes/" + code + "_qrcode.png";
            BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height);
            Path path = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            return filePath;
        } catch (WriterException | IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "生成二维码失败");
        }
    }

    /**
     * 生成条形码
     *
     * @param code 兑换码
     * @param width 条形码宽度
     * @param height 条形码高度
     * @return 返回条形码图片的URL
     */
    public String generateBarCode(String code, int width, int height) {
        try {

            // 创建文件保存路径
            String directoryPath = "barcodes";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 创建目录
            }


            String filePath = "barcodes/" + code + "_barcode.png";
            BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, width, height);
            Path path = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            return filePath;
        } catch (WriterException | IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "生成条形码失败");
        }
    }
}
