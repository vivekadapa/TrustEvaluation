import java.util.Base64;
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
    private SubTask subtask;
    private String result;

    public Envelope(EnvelopeType type, String envId, Node sentBy, SubTask subtask, String result) {
        this.type = type;
        this.envId = envId;
        this.sentBy = sentBy;
        this.subtask = subtask;
        this.result = result;
    }

    public Envelope(EnvelopeType type, String envId, Node sentBy, Node receivedBy) {
        this.type = type;
        this.envId = envId;
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
    }

    public Envelope(EnvelopeType type, String envId, Node sentBy, Node receivedBy, SubTask subtask, String result) {
        this.type = type;
        this.envId = envId;
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
        this.subtask = subtask;
        this.result = result;
    }

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

    public SubTask getSubtask() {
        return subtask;
    }

    public void setSubtask(SubTask subtask) {
        this.subtask = subtask;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Node getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Node receivedBy) {
        this.receivedBy = receivedBy;
    }

    private static byte[] generateSignature(String secretKey, String e, PublicKey publicKey, String t1, String y_s1,
            String f1) {
        try {
            byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            javax.crypto.Mac sha256_HMAC = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(secretKeyBytes,
                    "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String dataToSign = e + publicKey.toString() + t1 + y_s1 + f1;
            byte[] hash = sha256_HMAC.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encode(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static Envelope releaseSubTask(Node nearbyEdgeNode, Node cooperativeEdgeNode, SubTask subtask) {
        String nearbyEdgeNodeSecretKey = "afjasDJKSJHAWjeweqWEQ";
        String envelopeContent = generateEnvelopeContent(nearbyEdgeNode, cooperativeEdgeNode, subtask);
        // System.out.println(envelopeContent);
        byte[] signature = generateSignature(nearbyEdgeNodeSecretKey, envelopeContent,
                cooperativeEdgeNode.getPublicKey(), "example", "yvalue", "function");
        String base64Signature = Base64.getEncoder().encodeToString(signature);
        System.out.println("Signature of the envelope:" + base64Signature);
        return new Envelope(EnvelopeType.envrv, generateEnvId(), nearbyEdgeNode, cooperativeEdgeNode, subtask,
                bytesToHex(signature));
    }

    private static String generateEnvelopeContent(Node nearbyEdgeNode, Node cooperativeEdgeNode, SubTask subtask) {
        String subTaskString = subtask.toString();
        return nearbyEdgeNode.getNodeId() + cooperativeEdgeNode.getNodeId() + subTaskString;
    }

    private static String generateEnvId() {
        return "envId_" + System.currentTimeMillis();
    }

    public String toString() {
        return "Envelope Type: " + this.type + ",Envelope Id: " + this.envId + "\nEnvelope SentBy: " + this.sentBy
                + "Envelope Sent to:" + this.receivedBy;
    }

}
