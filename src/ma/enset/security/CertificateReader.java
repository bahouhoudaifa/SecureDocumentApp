package ma.enset.security;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class CertificateReader {

    public static void main(String[] args) throws Exception {

        // Charger CertificateFactory
        CertificateFactory factory =
                CertificateFactory.getInstance("X.509");

        // Lire le certificat
        FileInputStream input =
                new FileInputStream(
                        "documents/certificate.cer"
                );

        Certificate certificate =
                factory.generateCertificate(input);

        input.close();

        // Afficher les informations
        System.out.println("=== CERTIFICAT ===");
        System.out.println(certificate);

        // Récupérer la clé publique
        PublicKey publicKey =
                certificate.getPublicKey();

        System.out.println("\n=== CLE PUBLIQUE ===");

        System.out.println(
                "Algorithme : "
                        + publicKey.getAlgorithm()
        );

        System.out.println(
                "Format : "
                        + publicKey.getFormat()
        );

        String publicKeyBase64 =
                Base64.getEncoder()
                        .encodeToString(
                                publicKey.getEncoded()
                        );

        System.out.println(
                "Clé publique Base64 :"
        );

        System.out.println(publicKeyBase64);
    }
}