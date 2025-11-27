package model.file.pem_decoder;

import java.util.Base64;

public class PemFileParser {


    public PemInfo parseHeader(byte[] pemBytes) {
        String pem = new String(pemBytes);
        int start = pem.indexOf("-----BEGIN ") + 11;
        int end = pem.indexOf("-----", start);
        if (start < 11 || end == -1) {
            throw new IllegalArgumentException("Неверный PEM формат");
        }
        String header = pem.substring(start, end).trim(); // например: "RSA PUBLIC KEY"
        String[] parts = header.split(" ", 2);
        String algorithm = parts[0];
        String type = parts.length > 1 ? parts[1] : "";
        return new PemInfo(header, algorithm);
    }

    public byte[] extractKeyBytes(byte[] pemBytes) {
        String pem = new String(pemBytes);
        String[] parts = pem.split("-----");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Повреждённый PEM файл");
        }
        String base64 = parts[2].replaceAll("\\s", "");
        return Base64.getMimeDecoder().decode(base64);
    }
}
