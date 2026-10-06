package ma.enset.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {

    public static void main(String[] args) throws Exception {

        // Générateur RSA
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        // Taille : 2048 bits
        generator.initialize(2048);

        // Génération de la paire
        KeyPair keyPair = generator.generateKeyPair();

        // Récupération des clés
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();

        // Conversion Base64
        String privateKeyBase64 =
                Base64.getEncoder().encodeToString(privateKey);

        String publicKeyBase64 =
                Base64.getEncoder().encodeToString(publicKey);

        // Sauvegarde
        Files.writeString(
                Path.of("documents/private.key"),
                privateKeyBase64
        );

        Files.writeString(
                Path.of("documents/public.key"),
                publicKeyBase64
        );

        System.out.println("Clé privée générée.");
        System.out.println("Clé publique générée.");
    }
}