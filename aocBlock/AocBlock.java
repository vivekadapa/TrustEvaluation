import java.util.*;

public class AocBlock {

        private List<Node> nodeList;

        public AocBlock(List<Node> nodeList) {
                this.nodeList = nodeList;
        }

        public static void main(String[] args) {

                int[][] matrix = {
                        { 0, 1, 0, 1, 0 },
                        { 1, 0, 1, 0, 0 },
                        { 1, 0, 0, 0, 0 },
                        { 0, 1, 0, 0, 1 },
                        { 1, 0, 0, 0, 0 }
                };

                Trust scoreOfNN1 = new Trust(1, 1, 0, 0, 0, 0);
                Node nn1 = new Node("nn1", NodeType.nn, scoreOfNN1, 0);
                Trust scoreOfCN1 = new Trust(0, 1, 0, 0, 0, 0);
                Node cn1 = new Node("cn1", NodeType.cn, scoreOfCN1, 0);
                Trust scoreOfCN2 = new Trust(0, 1, 0, 0, 0, 0);
                Node cn2 = new Node("cn2", NodeType.cn, scoreOfCN2, 0);
                Trust scoreOfCN3 = new Trust(0, 1, 0, 0, 0, 0);
                Node cn3 = new Node("cn3", NodeType.cn, scoreOfCN3, 0);
                Trust scoreOfCN4 = new Trust(0, 1, 0, 0, 0, 0);
                Node cn4 = new Node("cn4", NodeType.cn, scoreOfCN4, 0);

                List<Node> nodeList = new ArrayList<>();
                AocBlock aocBlock = new AocBlock(nodeList);
                aocBlock.nodeList.add(nn1);
                aocBlock.nodeList.add(cn1);
                aocBlock.nodeList.add(cn2);
                aocBlock.nodeList.add(cn3);
                aocBlock.nodeList.add(cn4);

                ArrayList<SubTask> subtasks = new ArrayList<>();
                Task T1 = new Task("t1", subtasks, nn1);
                List<Envelope> envOfExectionAndVerification = new ArrayList<>();

                System.out.println("Released Subtasks:");

                for (int i = 0; i < matrix.length; i++) {
                        for (int j = 1; j < matrix.length; j++) {
                                if (matrix[i][j] == 1) {
                                        Node senderNode = aocBlock.nodeList.get(0);
                                        Node receiverNode = aocBlock.nodeList.get(j);
                                        // System.out.println(senderNode.getPublicKey());
                                        SubTask subtask = new SubTask(senderNode.getNodeId() + receiverNode.getNodeId(),
                                                        100, receiverNode, senderNode);
                                        subtasks.add(subtask);
                                        Envelope envelope = Envelope.releaseSubTask(senderNode, receiverNode, subtask);
                                        // System.out.println(envelope);
                                        envOfExectionAndVerification.add(envelope);
                                }
                        }
                }

                System.out.println(envOfExectionAndVerification);

                // System.out.println(T1.getSubtasks().toString());

                // for (int i = 0; i < matrix.length; i++) {
                // for (int j = 0; j < matrix.length; j++) {

                // if (i == 0 && matrix[i][j] == 1) { // For directed edges from nearby node
                // which creates envelope of the
                // // type envrv
                // Node recieverNode = aocBlock.nodeList.get(j);
                // String envId = aocBlock.nodeList.get(i).getNodeId() +
                // aocBlock.nodeList.get(j).getNodeId();
                // Envelope e1 = new Envelope(EnvelopeType.envrv, envId,
                // aocBlock.nodeList.get(i), recieverNode, null,
                // null);

                // Envnn1.add(e1);
                // } else if (matrix[i][j] == 1 && j != 0) { // For directed edges
                // fromcooperative Edge Nodes except when
                // // the reciever edge node is nn14
                // String envId1 = aocBlock.nodeList.get(i).getNodeId() +
                // aocBlock.nodeList.get(0).getNodeId();
                // Node reciverNode = aocBlock.nodeList.get(j);
                // String envId2 = aocBlock.nodeList.get(0).getNodeId() +
                // aocBlock.nodeList.get(i).getNodeId();
                // Envelope e2 = new Envelope(EnvelopeType.envcs, envId1,
                // aocBlock.nodeList.get(i),
                // aocBlock.nodeList.get(0), null,
                // null);
                // Envelope e4 = new Envelope(EnvelopeType.envrv, envId2,
                // aocBlock.nodeList.get(0), reciverNode, null,
                // null);
                // Envnn1.add(e2);
                // Envnn1.add(e4);
                // }

                // }
                // }

                // for (int i = 0; i < matrix.length; i++) {
                // if (matrix[i][0] == 1) {
                // Node recieverNode = aocBlock.nodeList.get(0);
                // String envId = aocBlock.nodeList.get(i).getNodeId() +
                // aocBlock.nodeList.get(0).getNodeId();
                // Envelope e3 = new Envelope(EnvelopeType.envcs, envId,
                // aocBlock.nodeList.get(i), recieverNode, null,
                // null);
                // Envnn1.add(e3);
                // }
                // }

                // System.out.println("EnvelopeType" + " " + "Sent By" + " " + "Sent To" + " ");
                // for (int i = 0; i < Envnn1.size(); i++) {
                // System.out.println(Envnn1.get(i).getType() + " " +
                // Envnn1.get(i).getSentBy().getNodeId()
                // + " " + Envnn1.get(i).getReceivedBy().getNodeId());
                // }

                // Scanner sc = new Scanner(System.in);

                // ArrayList<Envelope> ChallengeEnvelopes = new ArrayList<>();
                // System.out.println("Challenge Envelopes:");
                // ArrayList<Node> challengedNodes = new ArrayList<>();

                // for (int i = 1; i < aocBlock.nodeList.size(); i++) {
                // System.out.println("Does " + aocBlock.nodeList.get(i).getNodeId() + "wantsto
                // challenge the result");
                // Boolean n = sc.nextBoolean();
                // if (n) {
                // challengedNodes.add(aocBlock.nodeList.get(i));
                // String id = EnvelopeType.envch.toString() +
                // aocBlock.nodeList.get(i).getNodeId()
                // + aocBlock.nodeList.get(0).getNodeId();
                // Envelope newEnvelope = new Envelope(EnvelopeType.envch, id,
                // aocBlock.nodeList.get(i),
                // aocBlock.nodeList.get(0));
                // ChallengeEnvelopes.add(newEnvelope);
                // } else {
                // int score = nodeList.get(i).getTrustScore().getCorrectnessOfSubTaskResult();
                // score++;
                // nodeList.get(i).getTrustScore().setCorrectnessOfSubTaskResult(score);
                // }
                // }

                // System.out.println("Challenge Envelopes:");
                // System.out.println("EnvelopeType" + " " + "Sent By" + " " + "Sent To" + " ");
                // for (int i = 0; i < ChallengeEnvelopes.size(); i++) {
                // System.out.println(ChallengeEnvelopes.get(i).getType() + " "
                // + ChallengeEnvelopes.get(i).getSentBy().getNodeId()
                // + " " + ChallengeEnvelopes.get(i).getReceivedBy().getNodeId());
                // }

                // ArrayList<Envelope> proofEnvelopes = new ArrayList<>();
                // System.out.println("Envelope of Proofs");
                // for (int i = 0; i < ChallengeEnvelopes.size(); i++) {
                // String id = EnvelopeType.envpr.toString() +
                // aocBlock.nodeList.get(0).getNodeId()
                // + ChallengeEnvelopes.get(i).getSentBy().getNodeId();
                // Envelope newEnvelope = new Envelope(EnvelopeType.envpr, id,
                // aocBlock.nodeList.get(0),
                // ChallengeEnvelopes.get(i).getSentBy());
                // proofEnvelopes.add(newEnvelope);
                // }

                // System.out.println("EnvelopeType" + " " + "Sent By" + " " + "Sent To" + " ");
                // for (int i = 0; i < proofEnvelopes.size(); i++) {
                // System.out.println(proofEnvelopes.get(i).getType() + " "
                // + proofEnvelopes.get(i).getSentBy().getNodeId()
                // + " " + proofEnvelopes.get(i).getReceivedBy().getNodeId());
                // }

                // ArrayList<Envelope> negativelyVerifiedList = new ArrayList<>();
                // HashMap<Node, Boolean> hMap = new HashMap<>();
                // for (int i = 0; i < proofEnvelopes.size(); i++) {
                // String id = EnvelopeType.envvt.toString() +
                // proofEnvelopes.get(i).getReceivedBy().getNodeId()
                // + proofEnvelopes.get(i).getSentBy().getNodeId();
                // Envelope newEnvelope = new Envelope(EnvelopeType.envvt, id,
                // proofEnvelopes.get(i).getReceivedBy(),
                // proofEnvelopes.get(i).getSentBy());
                // System.out.println("Does " +
                // proofEnvelopes.get(i).getReceivedBy().getNodeId()
                // + " want to negatively verify the proof:");
                // Boolean n = sc.nextBoolean();
                // hMap.put(proofEnvelopes.get(i).getReceivedBy(), n);
                // if (n) {
                // negativelyVerifiedList.add(newEnvelope);
                // }
                // }

                // for (Envelope i : negativelyVerifiedList) {
                // System.out.println(i.getSentBy().getNodeId() + " sent the negative
                // verification correct:");
                // Boolean n = sc.nextBoolean();
                // if (n) {
                // int score1 =
                // i.getSentBy().getTrustScore().getCorrectnessOfVerificationSubTask();
                // score1++;
                // i.getSentBy().getTrustScore().setCorrectnessOfVerificationSubTask(score1);
                // int score2 =
                // i.getReceivedBy().getTrustScore().getCorrectnessOfSubTaskResult();
                // score2--;
                // i.getReceivedBy().getTrustScore().setCorrectnessOfSubTaskResult(score2);
                // } else {
                // int score1 =
                // i.getReceivedBy().getTrustScore().getCorrectnessOfSubTaskResult();
                // score1++;
                // i.getReceivedBy().getTrustScore().setCorrectnessOfSubTaskResult(score1);
                // int score2 =
                // i.getSentBy().getTrustScore().getCorrectnessOfVerificationSubTask();
                // score2--;
                // i.getSentBy().getTrustScore().setCorrectnessOfVerificationSubTask(score2);
                // }
                // }

                // System.out.println("EnvelopeType" + " " + "Sent By" + " " + "Sent To" + " ");
                // for (int i = 0; i < negativelyVerifiedList.size(); i++) {
                // System.out.println(negativelyVerifiedList.get(i).getType() + " "
                // + negativelyVerifiedList.get(i).getSentBy().getNodeId()
                // + " " + negativelyVerifiedList.get(i).getReceivedBy().getNodeId());
                // }
                // sc.close();

                // System.out.println("Trust Score of nn1 " + scoreOfNN1.calculateTrustScore());
                // System.out.println("Trust Score of cn1 " + scoreOfCN1.calculateTrustScore());
                // System.out.println("Trust Score of cn2 " + scoreOfCN2.calculateTrustScore());
                // System.out.println("Trust Score of cn3 " + scoreOfCN3.calculateTrustScore());
                // System.out.println("Trust Score of cn4 " + scoreOfCN4.calculateTrustScore());
        }
}
