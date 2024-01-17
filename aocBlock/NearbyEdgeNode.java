// NearbyEdgeNode.java
public class NearbyEdgeNode {
    private String secretKey;

    public NearbyEdgeNode(String secretKey) {
        this.secretKey = secretKey;
    }

    public Envelope releaseSubTask(String skg, String enT, String pki, String t1) {
        String senderSignature = sign(skg, enT, pki, t1);
        String encryptedContent = EncryptionUtils.encrypt(pki, "helper-content");
       System.out.println("Encrypted Content: "+ encryptedContent.toString());
        return new Envelope(senderSignature, pki, encryptedContent, "public@cooperativenode");
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private String sign(String... contents) {
        return SignatureUtils.sign(secretKey, contents);
    }

    public Envelope releaseEnvelopeForSamplingVerification(String skg, String enT, String pki, String t1, String sAnS,
            String ys1, String f1) {
        if (hasDuplicateRecords(sAnS) || getRecordCount(sAnS) != getNumberOfElements(ys1)) {
            return releaseSubTask(skg, enT, pki, t1);
        }

        String testcase = generateTestcase(sAnS, ys1, f1);

        String idForVerification = EncryptionUtils.hash(testcase);
        String t2 = "random data";

        String senderSignature = sign(skg, enT, idForVerification, t2, EncryptionUtils.hash(testcase));
        return new Envelope(senderSignature, idForVerification, EncryptionUtils.encrypt(pki, testcase),
                "public@cooperativenode" + idForVerification);
    }

    private boolean hasDuplicateRecords(String sAnS) {

        return false;
    }

    private int getRecordCount(String sAnS) {

        return sAnS.length();
    }

    private int getNumberOfElements(String ys1) {

        return ys1.length();
    }

    private String generateTestcase(String sAnS, String ys1, String f1) {

        return sAnS + ys1 + f1;
    }

 

  
}
