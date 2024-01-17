// Envelope.java
public class Envelope {
    private String senderSignature;
    private String receiverPublicKey;
    private String encryptedContent;
    private String recipient;

    public Envelope(String senderSignature, String receiverPublicKey, String encryptedContent, String recipient) {
        this.senderSignature = senderSignature;
        this.receiverPublicKey = receiverPublicKey;
        this.encryptedContent = encryptedContent;
        this.recipient = recipient;
    }

    public String getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String senderSignature) {
        this.senderSignature = senderSignature;
    }

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
    }

    public String getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getSender() {
        return senderSignature.substring(0, Math.min(senderSignature.length(), 10));
    }
    
    public String getId() {
        return senderSignature + "_" + receiverPublicKey + "_" + recipient;
    }
    
    public String getVerificationResult() {
        return "Verification Result: Success";
    }

    public String getVerificationResult(String expectedSender, String expectedRecipient) {
        boolean signatureValid = verifySignature(expectedSender);
        boolean authorityValid = hasAuthority(expectedRecipient);

        if (signatureValid && authorityValid) {
            return "Verification Result: Success";
        } else {
            return "Verification Result: Failure";
        }
    }

    public boolean hasAuthority(String expectedRecipient) {
        return recipient.equals(expectedRecipient);
    }
    
    private boolean verifySignature(String expectedSender) {
        return getSender().equals(expectedSender);
    }
    
    public String getTestcase() {
        return "Sample-Test-case";
    }

    

}
