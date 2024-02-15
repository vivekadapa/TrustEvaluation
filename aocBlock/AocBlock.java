import java.util.*;

public class AocBlock {

    public static void main(String[] args) {
        int[][] matrix = {
                { 0, 1, 0, 1, 0 },
                { 1, 0, 1, 0, 0 },
                { 1, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 1 },
                { 1, 0, 0, 0, 0 }
        }; // Input Graph Based on the how the sub tasks are released

        Node nn1 = new Node("nn1", NodeType.nn);
        Node cn1 = new Node("cn1", NodeType.cn);
        Node cn2 = new Node("cn2", NodeType.cn);
        Node cn3 = new Node("cn3", NodeType.cn);
        Node cn4 = new Node("cn4", NodeType.cn);

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(nn1);
        nodeList.add(cn1);
        nodeList.add(cn2);
        nodeList.add(cn3);
        nodeList.add(cn4);

        List<Envelope> Envnn1 = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == 0 && matrix[i][j] == 1) { // For directed edges from nearby node which creates envelope of the
                                                   // type envrv
                    Node recieverNode = nodeList.get(j);
                    String envId = nodeList.get(i).getNodeId() + nodeList.get(j).getNodeId();
                    Envelope e1 = new Envelope(EnvelopeType.envrv, envId, nodeList.get(i), recieverNode, null, null);
                    Envnn1.add(e1);
                } else if (matrix[i][j] == 1 && j != 0) { // For directed edges from cooperative Edge Nodes except when
                                                          // the reciever edge node is nn1
                    String envId1 = nodeList.get(i).getNodeId() + nodeList.get(0).getNodeId();
                    Node reciverNode = nodeList.get(j);
                    String envId2 = nodeList.get(0).getNodeId() + nodeList.get(i).getNodeId();
                    Envelope e2 = new Envelope(EnvelopeType.envcs, envId1, nodeList.get(i), nodeList.get(0), null,
                            null);
                    Envelope e4 = new Envelope(EnvelopeType.envrv, envId2, nodeList.get(0), reciverNode, null, null);
                    Envnn1.add(e2);
                    Envnn1.add(e4);
                }

            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 1) {
                Node recieverNode = nodeList.get(0);
                String envId = nodeList.get(i).getNodeId() + nodeList.get(0).getNodeId();
                Envelope e3 = new Envelope(EnvelopeType.envcs, envId, nodeList.get(i), recieverNode, null, null);
                Envnn1.add(e3);
            }
        }

        System.out.println("EnvelopeType" + "  " + "Sent By" + "   " + "Sent To" + "   ");
        for (int i = 0; i < Envnn1.size(); i++) {
            System.out.println(Envnn1.get(i).getType() + "            " + Envnn1.get(i).getSentBy().getNodeId()
                    + "       " + Envnn1.get(i).getReceivedBy().getNodeId());
        }

        Scanner sc = new Scanner(System.in);

        ArrayList<Envelope> ChallengeEnvelopes = new ArrayList<>();
        System.out.println("Challenge Envelopes:");
        for (int i = 1; i < nodeList.size(); i++) {
            System.out.println("Does " + nodeList.get(i).getNodeId() + " wants to challenge the result");
            Boolean n = sc.nextBoolean();
            if (n) {
                String id = EnvelopeType.envch.toString() + nodeList.get(i).getNodeId() + nodeList.get(0).getNodeId();
                Envelope newEnvelope = new Envelope(EnvelopeType.envch, id, nodeList.get(i), nodeList.get(0));
                ChallengeEnvelopes.add(newEnvelope);
            }
        }

        System.out.println("EnvelopeType" + "  " + "Sent By" + "   " + "Sent To" + "   ");
        for (int i = 0; i < ChallengeEnvelopes.size(); i++) {
            System.out.println(ChallengeEnvelopes.get(i).getType() + "            "
                    + ChallengeEnvelopes.get(i).getSentBy().getNodeId()
                    + "       " + ChallengeEnvelopes.get(i).getReceivedBy().getNodeId());
        }

        ArrayList<Envelope> proofEnvelopes = new ArrayList<>();
        System.out.println("Envelope of Proofs");
        for (int i = 0; i < ChallengeEnvelopes.size(); i++) {
            String id = EnvelopeType.envpr.toString() + nodeList.get(0).getNodeId()
                    + ChallengeEnvelopes.get(i).getSentBy().getNodeId();
            Envelope newEnvelope = new Envelope(EnvelopeType.envpr, id, nodeList.get(0),
                    ChallengeEnvelopes.get(i).getSentBy());
            proofEnvelopes.add(newEnvelope);
        }

        System.out.println("EnvelopeType" + "  " + "Sent By" + "   " + "Sent To" + "   ");
        for (int i = 0; i < proofEnvelopes.size(); i++) {
            System.out.println(proofEnvelopes.get(i).getType() + "            "
                    + proofEnvelopes.get(i).getSentBy().getNodeId()
                    + "       " + proofEnvelopes.get(i).getReceivedBy().getNodeId());
        }

        ArrayList<Envelope> negativelyVerifiedList = new ArrayList<>();

        for (int i = 0; i < proofEnvelopes.size(); i++) {
            String id = EnvelopeType.envvt.toString() + proofEnvelopes.get(i).getReceivedBy().getNodeId()
                    + proofEnvelopes.get(i).getSentBy().getNodeId();
            Envelope newEnvelope = new Envelope(EnvelopeType.envvt, id, proofEnvelopes.get(i).getReceivedBy(),
                    proofEnvelopes.get(i).getSentBy());
            System.out.println("Does " + proofEnvelopes.get(i).getReceivedBy().getNodeId()
                    + " want to negatively verify the proof:");
            Boolean n = sc.nextBoolean();
            if (n) {
                negativelyVerifiedList.add(newEnvelope);
            }
        }

        System.out.println("EnvelopeType" + "  " + "Sent By" + "   " + "Sent To" + "   ");
        for (int i = 0; i < negativelyVerifiedList.size(); i++) {
            System.out.println(negativelyVerifiedList.get(i).getType() + "            "
                    + negativelyVerifiedList.get(i).getSentBy().getNodeId()
                    + "       " + negativelyVerifiedList.get(i).getReceivedBy().getNodeId());
        }
        sc.close();

    }
}
