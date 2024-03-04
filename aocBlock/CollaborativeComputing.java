import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;

public class CollaborativeComputing {

    // Define cryptographic functions
    public static String sign(String privateKey, String data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] privateBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            PrivateKey privateKeyObj = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyObj);
            signature.update(data.getBytes());

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String publicKey, String data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] publicBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            PublicKey publicKeyObj = keyFactory.generatePublic(keySpec);
    
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKeyObj);
    
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Step 1: Nearby edge node releases sub-tasks
    public static String releaseSubTasks(String privateKey, String skg, String enT, String pkid, String t1) {
        String envrv = sign(privateKey, skg + enT + pkid + t1);
        String sTe = encrypt(pkid, pkid + "Ys1" + "f1");

        if (sTe != null) {
            return "envrv: " + envrv + ", sTe: " + sTe;
        } else {
            // Handle encryption failure
            return "Encryption failed";
        }
    }

    // Broadcast computation result
    public static String broadcastComputationResult(String privateKey, String sk, String envrv) {
        String enT = encrypt(privateKey, hash(envrv));
        return sign(sk, sk + hash(envrv) + enT);
    }

    // Step 3: Nearby edge node releases envelope for sampling verification
    public static String releaseEnvelopeForSamplingVerification(String skg, Set<String> sAnS, String Ys1, String f1,
            String t2) {
        if (!hasDuplicateRecords(sAnS) && sAnS.size() == Ys1.length()) {
            return "envrg: " + sign(skg, Ys1 + f1 + t2 + hash("envcs"));
        } else {
            return releaseSubTasks(skg, skg, "envcs", "pkid", "t1");
        }
    }

    // Broadcast Negative Verification Report or computation result
    public static String broadcastNegativeVerificationReport(String privateKey, String skid, Set<String> Xns) {
        return sign(privateKey, hash("envcs") + Xns);
    }

    // Check for duplicate records in sAnS
    private static boolean hasDuplicateRecords(Set<String> sAnS) {
        Set<String> uniqueRecords = new HashSet<>();
        for (String record : sAnS) {
            if (!uniqueRecords.add(record)) {
                return true;
            }
        }
        return false;
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // You can adjust the key size as needed
        return keyPairGenerator.generateKeyPair();
    }

    // Function to get base64-encoded key
    public static String getBase64EncodedKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static void main(String[] args) {
        // Example usage
        try {
            // Example usage
            KeyPair keyPair = generateKeyPair();

            String privateKey = getBase64EncodedKey(keyPair.getPrivate());
            // String publicKey = getBase64EncodedKey(keyPair.getPublic());
            String skg = "skg";
            String enT = "enT";
            String pkid = "pkid";
            String t1 = "t1";
            String envrv = releaseSubTasks(privateKey, skg, enT, pkid, t1);
            System.out.println(envrv);

            // String sk = "sk";
            // String computationResult = broadcastComputationResult(privateKey, sk, envrv);
            // System.out.println(computationResult);

            // Set<String> sAnS = new HashSet<>();
            // String Ys1 = "Ys1";
            // String f1 = "f1";
            // String t2 = "t2";
            // String envrg = releaseEnvelopeForSamplingVerification(skg, sAnS, Ys1, f1, t2);
            // System.out.println(envrg);

            // String skid = "skid";
            // Set<String> Xns = new HashSet<>();
            // String envvs = broadcastNegativeVerificationReport(privateKey, skid, Xns);
            // System.out.println(envvs);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
