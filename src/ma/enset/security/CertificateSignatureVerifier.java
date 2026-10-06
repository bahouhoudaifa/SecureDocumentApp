package ma.enset.security;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class CertificateSignatureVerifier {

    public static void main(String[] args) throws Exception {

        // 1. Lire le document
        byte[] document = Files.readAllBytes(
                Path.of("documents/document.txt")
        );

        // 2. Lire la signature
        String signatureBase64 = Files.readString(
                Path.of("documents/document.sig")
        ).trim();

        byte[] signatureBytes =
                Base64.getDecoder()
                        .decode(signatureBase64);

        // 3. Charger le certificat
        CertificateFactory factory =
                CertificateFactory.getInstance("X.509");

        FileInputStream input =
                new FileInputStream(
                        "documents/certificate.cer"
                );

        Certificate certificate =
                factory.generateCertificate(input);

        input.close();

        // 4. Extraire la clé publique
        PublicKey publicKey =
                certificate.getPublicKey();

        // 5. Vérifier la signature
        Signature signature =
                Signature.getInstance("SHA256withRSA");

        signature.initVerify(publicKey);

        signature.update(document);

        boolean valid =
                signature.verify(signatureBytes);

        // 6. Résultat
        if (valid) {
            System.out.println(
                    "DOCUMENT VALIDE - SIGNTURE VALIDE"
            );
        } else {
            System.out.println(
                    "DOCUMENT INVALIDE - SIGNATURE INVALIDE"
            );
        }
    }
}