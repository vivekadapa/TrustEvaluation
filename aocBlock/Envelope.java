public class Envelope {
    private String id;
    private EdgeNode sender;
    private EdgeNode receiver;
    private long timestamp; 
    private VerificationResult verificationResult;

    public Envelope(String id, EdgeNode sender, EdgeNode receiver) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = System.currentTimeMillis(); 
      
    }

    public String getId() {
        return id;
    }

    public EdgeNode getSender() {
        return sender;
    }

    public EdgeNode getReceiver() {
        return receiver;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSender(EdgeNode sender) {
        this.sender = sender;
    }

    public void setReceiver(EdgeNode receiver) {
        this.receiver = receiver;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public VerificationResult getVerificationResult() {
        return verificationResult;
    }

    public void setVerificationResult(VerificationResult verificationResult) {
        this.verificationResult = verificationResult;
    }

    
}
