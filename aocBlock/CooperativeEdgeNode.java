// CooperativeEdgeNode.java
public class CooperativeEdgeNode {
    private String publicKey;

    public CooperativeEdgeNode(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void processEnvelope(Envelope envelope) {
        if (envelope.hasAuthority("public@cooperativenode")) {
            String decryptedContent = EncryptionUtils.decrypt(publicKey, envelope.getEncryptedContent());
            System.out.println("Decrypted Content: " + decryptedContent);
        } else {
            System.out.println("Unauthorized Envelope");
        }
    }


    public Envelope broadcastComputationResult(String skg, String saas, String pki) {
        String computationResult = calculateComputationResult(saas); 
        System.out.println(computationResult);
        String senderSignature = SignatureUtils.sign(skg, EncryptionUtils.hash(computationResult));
        return new Envelope(senderSignature, pki, EncryptionUtils.encrypt(pki, computationResult),
                "BroadcastRecipient");
    }

    private String calculateComputationResult(String saas) {
        return "Computed: " + saas;
    }

    public Envelope verifyAndExecuteSubTask(Envelope envelope, String ys2, String f2) {
        if (!verifyPreviousSubTask(envelope.getEncryptedContent(), envelope.getId(), envelope.getTestcase())) {
            System.out.println("Verification of previous sub-task failed.");
            return null; 
        }

     
        String result = executeSubTask(envelope.getTestcase(), ys2, f2);
        System.out.println("Computation Result: " + result);
        String senderSignature = SignatureUtils.sign(envelope.getSenderSignature(), EncryptionUtils.hash(result));
        Envelope computationResultEnvelope = new Envelope(senderSignature, envelope.getReceiverPublicKey(),
                EncryptionUtils.encrypt(envelope.getReceiverPublicKey(), result), "BroadcastRecipient");
        
        return computationResultEnvelope;
    }

    public boolean verifyPreviousSubTask(String encryptedContent, String envelopeId, String testcase) {
        return true;
    }
    
    public String executeSubTask(String testcase, String ys2, String f2) {
        return testcase + ys2 + f2;
    }
}
