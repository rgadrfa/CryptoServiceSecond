package model.file.interfaces;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface IPemFileReader {

    byte[] toPem(PublicKey key);

    byte[] toPem(PrivateKey key);

    byte[] toPem(SecretKey key);

    PublicKey fromPemPublic(byte[] pemBytes) throws GeneralSecurityException;

    PrivateKey fromPemPrivate(byte[] pemBytes) throws GeneralSecurityException;

    SecretKey fromPemSecret(byte[] pemBytes) throws GeneralSecurityException;
}
