package com.ejemplo.fa2.javaejemplo.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class GeneratedQr {
    public String generateQrCode(String qrCode) throws IOException, WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode, BarcodeFormat.QR_CODE, 250, 250);

        // 2. Escribir la imagen directamente en memoria (en un flujo de bytes)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        byte[] byteArray = outputStream.toByteArray();
        String base64 = Base64.getEncoder().encodeToString(byteArray);

        return "data:image/png;base64,"+base64;
    }
}
