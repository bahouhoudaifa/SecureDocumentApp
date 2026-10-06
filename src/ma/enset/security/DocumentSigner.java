package ma.enset.security;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import java.security.Signature;

public class DocumentSigner {

    public static void main(String[] args) throws Exception {

        // Lire le document
        byte[] document = Files.readAllBytes(
                Path.of("documents/document.txt")
        );

        // Lire la clé privée Base64
        String privateKeyBase64 = Files.readString(
                Path.of("documents/private.key")
        ).trim();

        // Décoder Base64
        byte[] privateKeyBytes =
                Base64.getDecoder().decode(privateKeyBase64);

        // Reconstruire la clé privée RSA
        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(privateKeyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        PrivateKey privateKey =
                keyFactory.generatePrivate(keySpec);

        // Créer l'objet Signature
        Signature signature =
                Signature.getInstance("SHA256withRSA");

        // Initialiser avec la clé privée
        signature.initSign(privateKey);

        // Fournir le document
        signature.update(document);

        // Générer la signature
        byte[] signatureBytes =
                signature.sign();

        // Base64
        String signatureBase64 =
                Base64.getEncoder()
                        .encodeToString(signatureBytes);

        // Affichage
        System.out.println("Signature :");
        System.out.println(signatureBase64);

        // Sauvegarde
        Files.writeString(
                Path.of("documents/document.sig"),
                signatureBase64
        );
    }
}