import java.util.ArrayList;
import java.util.List;


public class AocBlock {
    private List<Envelope> envelopes;
    private long timestamp;

    public AocBlock(long timestamp) {
        this.envelopes = new ArrayList<>();
        this.timestamp = timestamp;
    }

    public void addEnvelope(Envelope envelope) {
        envelopes.add(envelope);
    }

    public void processEnvelopes() {
        for (Envelope envelope : envelopes) {
            VerificationResult verificationResult = verify(envelope);
            envelope.setVerificationResult(verificationResult);

            if (verificationResult.isSuccess()) {
                executeTask(envelope);
            }
        }
        System.out.println("\n");
    }

    public List<Envelope> getEnvelopes() {
        return envelopes;
    }

    public void setEnvelopes(List<Envelope> envelopes) {
        this.envelopes = envelopes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private VerificationResult verify(Envelope envelope) {
        return new VerificationResult(true, "Verification successful");
    }

    private void executeTask(Envelope envelope) {
        System.out.println("Execution of task for envelope " + envelope.getId());
    }

}
