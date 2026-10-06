package ma.enset.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class DocumentHMacGenerator {

    public static void main(String[] args) throws Exception {

        // 1. Lire le document
        Path documentPath = Path.of("documents/document.txt");
        String document = Files.readString(documentPath);

        // 2. Définir la clé secrète
        String secret = "MaCleSecrete123";

        // 3. Créer la clé HMAC
        SecretKeySpec key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        // 4. Créer l'objet HMAC
        Mac mac = Mac.getInstance("HmacSHA256");

        // 5. Initialiser avec la clé
        mac.init(key);

        // 6. Calculer le HMAC
        byte[] hmacBytes = mac.doFinal(
                document.getBytes(StandardCharsets.UTF_8)
        );

        // 7. Convertir en Base64
        String hmacBase64 = Base64.getEncoder()
                .encodeToString(hmacBytes);

        // 8. Afficher
        System.out.println("Contenu du document :");
        System.out.println(document);

        System.out.println("\nHMAC :");
        System.out.println(hmacBase64);

        // 9. Enregistrer dans document.hmac
        Files.writeString(
                Path.of("documents/document.hmac"),
                hmacBase64
        );
    }
}
