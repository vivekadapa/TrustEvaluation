import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.DoubleFunction;

import javax.crypto.Cipher;

import java.nio.charset.StandardCharsets;
import java.security.*;

enum EnvelopeType {
    envrv,
    envcs,
    envvs,
    envcm,
    envch,
    envpr,
    envvt
}

public class Envelope {
    private EnvelopeType type;
    private String envId;
    private Node sentBy;
    private Node receivedBy;
    // private SubTask subtask;
    private String time;
    private String Signature;
    private byte[] EncryptedY;
    private byte[] encryptedF;
    private byte[] encryptedResult;
    private String prevHash;
    

    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    


    public byte[] getEncryptedY() {
        return EncryptedY;
    }

    public void setEncryptedY(byte[] encryptedY) {
        EncryptedY = encryptedY;
    }

    public byte[] getEncryptedF() {
        return encryptedF;
    }

    public void setEncryptedF(byte[] encryptedF) {
        this.encryptedF = encryptedF;
    }

    public Envelope(EnvelopeType type, Node sentBy, String Signature, String time) {
        this.type = type;
        this.envId = generateEnvId();
        this.sentBy = sentBy;
        // this.subtask = subtask;
        this.Signature = Signature;
    }

    public Envelope(EnvelopeType type,Node sentBy,Node receivedBy,byte[] EncryptedResult,String prevHash){
        this.type = type;
        this.envId = generateEnvId();
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
        this.encryptedResult = EncryptedResult;
        this.prevHash = prevHash;
    }

    // new Envelope(EnvelopeType.envrv, nearbyEdgeNode, cooperativeEdgeNode,y_s1,f1
    // base64Signature);

    public Envelope(EnvelopeType type, Node sentBy, Node receivedBy, String signature, byte[] encryptedY,byte[] encryptedF,String time) {
        this.type = type;
        this.envId = generateEnvId();
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
        this.Signature = signature;
        this.encryptedF = encryptedF;
        this.EncryptedY = encryptedY;
        this.time = time;
    }

    private static byte[] generateSignature(PrivateKey secretKey, PublicKey publicKey, byte[] encryptedY,
            byte[] encryptedF) {
        try {
            // Obtain the encoded bytes of the secret key
            byte[] secretKeyBytes = secretKey.getEncoded();

            // Initialize the HMAC-SHA256 algorithm with the secret key
            javax.crypto.Mac sha256_HMAC = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(secretKeyBytes,
                    "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            String dataToSign = encryptedF.toString() + encryptedY.toString();
            byte[] hash = sha256_HMAC.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encode(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String hashEnvelope() {
        try {
            // Concatenate relevant fields into a string
            String envelopeData = this.type.toString() + this.envId + this.sentBy.toString() + this.receivedBy.toString() + this.time + this.Signature;

            // Compute the hash of the concatenated string using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(envelopeData.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // private static byte[] encryptWithPublicKey(byte[] input, PublicKey publicKey) throws Exception {
    //     Cipher cipher = Cipher.getInstance("RSA");
    //     cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    //     return cipher.doFinal(input);
    // }

    private static byte[] encryptWithPublicKey(byte[] input, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(input);
    }

    static byte[] decryptWithPrivateKey(byte[] input, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(input);
    }

    public static Envelope releaseSubTask(Node nearbyEdgeNode, Node cooperativeEdgeNode, String y_s1,
            String f1) throws Exception {
        PrivateKey nearbyEdgeNodeSecretKey = nearbyEdgeNode.getPrivateKey();
        // System.out.println(f1);
        byte[] encryptedY = encryptWithPublicKey(y_s1.getBytes(StandardCharsets.UTF_8),
                cooperativeEdgeNode.getPublicKey());
        byte[] encryptedF = encryptWithPublicKey(f1.getBytes(StandardCharsets.UTF_8),
                cooperativeEdgeNode.getPublicKey());
        byte[] signature = generateSignature(nearbyEdgeNodeSecretKey, cooperativeEdgeNode.getPublicKey(), encryptedY,
                encryptedF);
        String base64Signature = Base64.getEncoder().encodeToString(signature);
        // System.out.println("Signature of the envelope:" + base64Signature);
        return new Envelope(EnvelopeType.envrv, nearbyEdgeNode, cooperativeEdgeNode, base64Signature, encryptedY,
                encryptedF, "t1");
    }

    public static Envelope subTaskResultEnvelope(Node sentBy,Node receivedBy,String sAns,String prevHash) throws Exception{
        // System.out.println(Arrays.toString(sAns.getBytes(StandardCharsets.UTF_8)));
        byte[] encryptedResult = encryptWithPublicKey(sAns.getBytes(StandardCharsets.UTF_8), receivedBy.getPublicKey());
        return new Envelope(EnvelopeType.envcs, sentBy, receivedBy,encryptedResult,prevHash);
    }


    public static String extractDataFromSignature(String base64Signature, PublicKey publicKey, Signature signature)
            throws Exception {
        // Decode the Base64-encoded signature to obtain the signature byte array
        byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);

        // Verify the signature
        signature.update(signatureBytes);
        boolean verified = signature.verify(signatureBytes);

        if (verified) {
            String signedMessage = new String(signatureBytes, StandardCharsets.UTF_8);
            return signedMessage;
        } else {
            // If the signature is not valid, return null or handle the error as needed
            return null;
        }
    }

    // public Envelope(EnvelopeType type, Node sentBy, Node receivedBy, String
    // Signature, String time) {
    // this.type = type;
    // this.envId = generateEnvId();
    // this.sentBy = sentBy;
    // this.receivedBy = receivedBy;
    // // this.subtask = subtask;
    // this.Signature = Signature;
    // }

    public EnvelopeType getType() {
        return type;
    }

    public void setType(EnvelopeType type) {
        this.type = type;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public Node getSentBy() {
        return sentBy;
    }

    public void setSentBy(Node sentBy) {
        this.sentBy = sentBy;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public Node getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Node receivedBy) {
        this.receivedBy = receivedBy;
    }

    // private static DoubleFunction<Double> parseFunction(String f1) throws
    // Exception {
    // if (f1.equals("sqrt")) {
    // return (num) -> Math.sqrt(num);
    // } else {
    // throw new IllegalArgumentException("Unsupported function: " + f1);
    // }
    // }

    // private static String generateEnvelopeContent(Node nearbyEdgeNode, Node
    // cooperativeEdgeNode, SubTask subtask) {
    // String subTaskString = subtask.toString();
    // return nearbyEdgeNode.getNodeId() + cooperativeEdgeNode.getNodeId() +
    // subTaskString;
    // }

    private static String generateEnvId() {
        return "envId_" + System.currentTimeMillis();
    }

    public String toString() {
        // return "\nEnvelope Signature: " + this.Signature + "\nEnvelope Type: " +
        // this.type + ",Envelope Id: "
        // + this.envId
        // + "\nEnvelope SentBy: " + this.sentBy
        // + " Envelope Sent to:" + this.receivedBy;
        return "\nEnvelope Type: " + this.type + "\nEnvelope SentBy: " + this.sentBy + "\nEnvelope Received By: "
                + this.receivedBy + "\n" + "EncryptedY: " + this.EncryptedY ;
    }

    public byte[] getEncryptedResult() {
        return encryptedResult;
    }

    public void setEncryptedResult(byte[] encryptedResult) {
        this.encryptedResult = encryptedResult;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

}
