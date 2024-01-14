public class Main {

    private static void displayResults(AocBlock aocBlock) {
        System.out.println("AocBlock Timestamp: " + aocBlock.getTimestamp());

        for (Envelope envelope : aocBlock.getEnvelopes()) {
            System.out.println("Envelope ID: " + envelope.getId());
            System.out.println("Sender: " + envelope.getSender().getNodeId());
            System.out.println("Receiver: " + envelope.getReceiver().getNodeId());

            VerificationResult verificationResult = envelope.getVerificationResult();
            System.out.println("Verification Result: " + verificationResult.isSuccess());
            System.out.println("Verification Message: " + verificationResult.getMessage());

            System.out.println("-------------------------------------------------------------------");
        }
    }

    public static void main(String[] args) {
        AocBlock aocBlock = new AocBlock(System.currentTimeMillis());

        EdgeNode nn = new EdgeNode("nn", 0.5f, 0.8f);
        EdgeNode cn1 = new EdgeNode("cn1", 0.3f, 0.9f);
        EdgeNode cn2 = new EdgeNode("cn2", 0.4f, 0.6f);

        Envelope envrv1 = new Envelope("envrv1", nn, cn1);
        Envelope envrv2 = new Envelope("envrv2", nn, cn2);
        Envelope envcs1 = new Envelope("envcs1", cn1, nn);
        Envelope envcs2 = new Envelope("envcs2", cn2, nn);

        aocBlock.addEnvelope(envrv1);
        aocBlock.addEnvelope(envrv2);
        aocBlock.addEnvelope(envcs1);
        aocBlock.addEnvelope(envcs2);

        aocBlock.processEnvelopes();
        displayResults(aocBlock);

    }

}
