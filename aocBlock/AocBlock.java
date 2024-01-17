import java.util.ArrayList;
import java.util.List;

public class AocBlock {
    private List<Envelope> envelopes;

    public AocBlock() {
        this.envelopes = new ArrayList<>();
    }

    public void addEnvelope(Envelope envelope) {
        this.envelopes.add(envelope);
    }

    public List<Envelope> getEnvelopes() {
        return envelopes;
    }

    public static void main(String[] args) {
        AocBlock aocBlock = new AocBlock();
        NearbyEdgeNode nearbyEdgeNode = new NearbyEdgeNode("secret@a#12#b"); // pass secret key
        CooperativeEdgeNode cooperativeEdgeNode = new CooperativeEdgeNode("public@cooperativenode");// pass public key
     
        Envelope envelope1 = nearbyEdgeNode.releaseSubTask("secret@a#12#b", "Task", "public@cooperativenode", "123");

        cooperativeEdgeNode.processEnvelope(envelope1);
        aocBlock.addEnvelope(envelope1);

        Envelope envelope2 = cooperativeEdgeNode.broadcastComputationResult("secret@a#12#b",
                "I am the task", "public@cooperativenode");


        aocBlock.addEnvelope(envelope2);

        List<Envelope> blockEnvelopes = aocBlock.getEnvelopes();
        for (Envelope envelope : blockEnvelopes) {
            System.out.println("Envelope ID: " + envelope.getId());
        }
    }
}
