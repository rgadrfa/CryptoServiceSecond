package model.file.pem_decoder;

import model.file.interfaces.IPemFileReader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PemFileReaderController implements IPemFileReader {

    @Override
    public byte[] toPem(PublicKey key) {
        return formatPem("PUBLIC KEY", key.getAlgorithm(), key.getEncoded());
    }

    @Override
    public byte[] toPem(PrivateKey key) {
        return formatPem("PRIVATE KEY", key.getAlgorithm(), key.getEncoded());
    }

    @Override
    public byte[] toPem(SecretKey key) {
        return formatPem("SECRET KEY", key.getAlgorithm(), key.getEncoded());
    }

    private byte[] formatPem(String type, String algorithm, byte[] keyBytes) {
        String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(keyBytes);
        String pem = """
            -----BEGIN %s-----
            Alg: %s
            Data: 
            %s
            -----END %s-----
            """.formatted(type, algorithm, base64, type);
        return pem.getBytes();
    }

    // ====================== ЧТЕНИЕ — ПОЛНАЯ АВТОМАТИЗАЦИЯ ======================

    @Override
    public PublicKey fromPemPublic(byte[] pemBytes) throws GeneralSecurityException {
        String alg = extractAlgorithm(pemBytes);
        byte[] data = extractData(pemBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        return KeyFactory.getInstance(alg).generatePublic(spec);
    }

    @Override
    public PrivateKey fromPemPrivate(byte[] pemBytes) throws GeneralSecurityException {
        String alg = extractAlgorithm(pemBytes);
        byte[] data = extractData(pemBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
        return KeyFactory.getInstance(alg).generatePrivate(spec);
    }

    @Override
    public SecretKey fromPemSecret(byte[] pemBytes) throws GeneralSecurityException {
        String alg = extractAlgorithm(pemBytes);
        byte[] data = extractData(pemBytes);
        return new SecretKeySpec(data, alg);
    }

    // ====================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ======================

    private String extractAlgorithm(byte[] pemBytes) {
        String pem = new String(pemBytes, java.nio.charset.StandardCharsets.UTF_8);

        int algIndex = pem.indexOf("Alg:");
        if (algIndex == -1) {
            throw new IllegalArgumentException("В PEM-файле отсутствует строка 'Alg:'");
        }

        int valueStart = algIndex + 4; // после "Alg:"
        // Пропускаем все пробельные символы (пробелы, табы, переводы строк)
        while (valueStart < pem.length() && Character.isWhitespace(pem.charAt(valueStart))) {
            valueStart++;
        }

        int valueEnd = pem.indexOf('\n', valueStart);
        if (valueEnd == -1) valueEnd = pem.length();

        return pem.substring(valueStart, valueEnd).trim();
    }

    private byte[] extractData(byte[] pemBytes) {
        String pem = new String(pemBytes, java.nio.charset.StandardCharsets.UTF_8);

        int dataIndex = pem.indexOf("Data:");
        if (dataIndex == -1) {
            throw new IllegalArgumentException("В PEM-файле отсутствует строка 'Data:'");
        }

        int dataStart = dataIndex + 5; // после "Data:"

        // Пропускаем все пробельные символы (включая \n после Data:)
        while (dataStart < pem.length() && Character.isWhitespace(pem.charAt(dataStart))) {
            dataStart++;
        }

        int endTagIndex = pem.indexOf("-----END ", dataStart);
        if (endTagIndex == -1) {
            throw new IllegalArgumentException("Не найден конец PEM-блока (-----END ...-----)");
        }

        String base64Block = pem.substring(dataStart, endTagIndex);
        String cleanBase64 = base64Block.replaceAll("\\s", ""); // убираем ВСЁ: \n, \r, пробелы

        return Base64.getMimeDecoder().decode(cleanBase64);
    }
}