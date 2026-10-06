package ma.enset.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class DocumentHMacVerifier {

    public static void main(String[] args) throws Exception {

        // Lire le document
        String document = Files.readString(
                Path.of("documents/document.txt")
        );

        // Lire le HMAC reçu
        String receivedHmac = Files.readString(
                Path.of("documents/document.hmac")
        ).trim();

        // Même clé secrète
        String secret = "MaCleSecrete123";

        SecretKeySpec key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        // Recalcul du HMAC
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        byte[] hmacBytes = mac.doFinal(
                document.getBytes(StandardCharsets.UTF_8)
        );

        String calculatedHmac = Base64.getEncoder()
                .encodeToString(hmacBytes);

        // Comparaison
        if (calculatedHmac.equals(receivedHmac)) {
            System.out.println("DOCUMENT VALIDE");
        } else {
            System.out.println("DOCUMENT MODIFIE");
        }
    }
}