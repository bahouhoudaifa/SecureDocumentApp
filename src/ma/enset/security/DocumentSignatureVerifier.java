package ma.enset.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DocumentSignatureVerifier {

    public static void main(String[] args) throws Exception {

        // Lire le document
        byte[] document = Files.readAllBytes(
                Path.of("documents/document.txt")
        );

        // Lire la signature
        String signatureBase64 = Files.readString(
                Path.of("documents/document.sig")
        ).trim();

        byte[] signatureBytes =
                Base64.getDecoder().decode(signatureBase64);

        // Lire la clé publique
        String publicKeyBase64 = Files.readString(
                Path.of("documents/public.key")
        ).trim();

        byte[] publicKeyBytes =
                Base64.getDecoder().decode(publicKeyBase64);

        // Reconstruire la clé publique
        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(publicKeyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        PublicKey publicKey =
                keyFactory.generatePublic(keySpec);

        // Vérification
        Signature signature =
                Signature.getInstance("SHA256withRSA");

        signature.initVerify(publicKey);

        signature.update(document);

        boolean valid =
                signature.verify(signatureBytes);

        if (valid) {
            System.out.println("SIGNATURE VALIDE");
        } else {
            System.out.println("SIGNATURE INVALIDE");
        }
    }
}