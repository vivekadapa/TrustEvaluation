import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.*;
// import java.util.function.DoubleFunction;
import javax.crypto.Cipher;
// import org.xml.sax.EntityResolver;

interface StringFunction {
        String run(String str);
}

public class AocBlock {

        private List<Node> nodeList;
        private HashMap<Envelope, Envelope> DAG;

        public AocBlock(List<Node> nodeList) {
                this.nodeList = nodeList;
        }

        public List<Node> getNodeList() {
                return nodeList;
        }

        public void setNodeList(List<Node> nodeList) {
                this.nodeList = nodeList;
        }

        public HashMap<Envelope, Envelope> getDAG() {
                return DAG;
        }

        public void setDAG(HashMap<Envelope, Envelope> dag) {
                DAG = dag;
        }

        private static byte[] decryptWithPrivateKey(byte[] input, PrivateKey privateKey) throws Exception {
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(input);
        }

        public static boolean isPrime(int n, double sqrt) {

                if (n <= 1)
                        return false;

                if (n == 2 || n == 3)
                        return true;

                if (n % 2 == 0 || n % 3 == 0)
                        return false;

                for (int i = 5; i <= sqrt; i = i + 6)
                        if (n % i == 0 || n % (i + 2) == 0)
                                return false;

                return true;
        }

        public AocBlock blockGeneration() throws Exception {
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
                // Task T1 = new Task("t1", subtasks, nn1);
                // List<Envelope> envOfExectionAndVerification = new ArrayList<>();

                LinkedHashMap<Envelope, Envelope> DAG = new LinkedHashMap<>();
                int k = 0;
                for (int j = 1; j < matrix.length; j++) {
                        if (matrix[0][j] == 1) {
                                Node senderNode = aocBlock.nodeList.get(0);
                                Node receiverNode = aocBlock.nodeList.get(j);
                                SubTask subtask = new SubTask(senderNode.getNodeId() + receiverNode.getNodeId(),
                                                100, receiverNode, senderNode);
                                subtasks.add(subtask);
                                String range = "";
                                if (k == 0) {
                                        range = "1-10";
                                } else {
                                        range = "11-20";
                                }
                                k++;
                                Envelope envelope = Envelope.releaseSubTask(senderNode, receiverNode,
                                                range, "sqrt");
                                DAG.put(envelope, null);
                        }
                }

                // System.out.println(DAG);
                ArrayList<Envelope> subtaskEnvelopes = new ArrayList<>();
                for (Map.Entry<Envelope, Envelope> entry : new LinkedHashMap<>(DAG).entrySet()) {
                        Envelope e = entry.getKey();
                        Node sentBy = e.getReceivedBy();
                        Node receivedBy = e.getSentBy();
                        byte[] decryptedY = decryptWithPrivateKey(e.getEncryptedY(), sentBy.getPrivateKey());
                        byte[] decryptedF = decryptWithPrivateKey(e.getEncryptedF(), sentBy.getPrivateKey());
                        String decryptedYmessage = new String(decryptedY, StandardCharsets.UTF_8);
                        String decryptedFmessage = new String(decryptedF, StandardCharsets.UTF_8);
                        double start = Double.parseDouble(decryptedYmessage.split("-")[0]);
                        double end = Double.parseDouble(decryptedYmessage.split("-")[1]);
                        ArrayList<Double> sAns = new ArrayList<>();
                        if (decryptedFmessage.equals("sqrt")) {
                                sAns.add(0, 0.0);
                                for (double i = start; i <= end; i++) {
                                        sAns.add(Math.sqrt(i));
                                }
                        }
                        Envelope subtaskResultEnvelope = Envelope.subTaskResultEnvelope(sentBy, receivedBy,
                                        sAns.toString(), e.getPrevHash());
                        subtaskEnvelopes.add(subtaskResultEnvelope);
                        DAG.put(subtaskResultEnvelope, entry.getKey());
                }

                // for (Envelope e : DAG.keySet()) {
                // if (e.getType() == EnvelopeType.envcs) {
                // byte[] decryptedResult = decryptWithPrivateKey(e.getEncryptedResult(),
                // e.getReceivedBy().getPrivateKey());
                // String decryptedResultmessage = new String(decryptedResult,
                // StandardCharsets.UTF_8);
                // System.out.println(decryptedResultmessage);
                // }
                // }
                k = 0;
                List<Envelope> envOfExectionAndVerification1 = new ArrayList<>();
                // ArrayList<Integer> numberOfOnesPerRow = new ArrayList<>();
                int numberOfOnesPerRow[] = new int[matrix.length];
                // Iterate over each row of the matrix
                for (int i = 1; i < matrix.length; i++) {
                        int count = 0;
                        for (int j = 1; j < matrix[i].length; j++) {
                                if (matrix[i][j] == 1) {
                                        count++;
                                }
                        }
                        numberOfOnesPerRow[i] = count;
                }

                int q = 0;
                for (int i = 1; i < matrix.length; i++) {
                        for (int j = 1; j < matrix.length; j++) {
                                if (matrix[i][j] == 1) {
                                        for (Envelope envelope : subtaskEnvelopes) {
                                                if (envelope.getSentBy() == nodeList.get(i)) {
                                                        byte[] decryptedResult = decryptWithPrivateKey(
                                                                        envelope.getEncryptedResult(),
                                                                        envelope.getReceivedBy()
                                                                                        .getPrivateKey());
                                                        String decryptedResultmessage = new String(
                                                                        decryptedResult,
                                                                        StandardCharsets.UTF_8);
                                                        // System.out.println("\nDecrypted Result : "
                                                        // + decryptedResultmessage);
                                                        if (numberOfOnesPerRow[i] != 1) {
                                                                ArrayList<Double> resultList = new ArrayList<>();
                                                                String[] items = decryptedResultmessage.split(",");
                                                                int midPoint = (items.length / 2) + 1;

                                                                // System.out.println("Mid point : " + midPoint);
                                                                String[] elements = (q == 0)
                                                                                ? Arrays.copyOfRange(items, 0, midPoint)
                                                                                : Arrays.copyOfRange(items, midPoint,
                                                                                                items.length);
                                                                for (String element : elements) {

                                                                        element = element.trim().replaceAll("^\\[|]$",
                                                                                        "");
                                                                        resultList.add(Double.parseDouble(element));
                                                                }

                                                                // System.out.println("\nTask division result : "
                                                                // + resultList);
                                                                q++;
                                                                Envelope newEnvelope = Envelope.releaseSubTask(nn1,
                                                                                nodeList.get(j), resultList.toString(),
                                                                                "prime");
                                                                DAG.put(newEnvelope, envelope);
                                                                envOfExectionAndVerification1.add(newEnvelope);
                                                        } else {
                                                                Envelope newEnvelope = Envelope.releaseSubTask(nn1,
                                                                                nodeList.get(j), decryptedResultmessage,
                                                                                "prime");
                                                                DAG.put(newEnvelope, envelope);
                                                                envOfExectionAndVerification1.add(newEnvelope);

                                                        }
                                                }
                                        }
                                }
                        }
                }

                // System.out.println("This is a DAG " + DAG);

                // System.out.println(envOfExectionAndVerification1);
                ArrayList<Envelope> finalSubTaskEnvelopes = new ArrayList<>();
                int z = 1;
                for (Envelope e : envOfExectionAndVerification1) {
                        byte[] decryptedY = decryptWithPrivateKey(
                                        e.getEncryptedY(),
                                        e.getReceivedBy()
                                                        .getPrivateKey());
                        String decryptedYmessage = new String(
                                        decryptedY,
                                        StandardCharsets.UTF_8);
                        ArrayList<Double> arrayList = new ArrayList<>();
                        String[] elements = decryptedYmessage.substring(1, decryptedYmessage.length() - 1)
                                        .split(",\\s*");
                        for (String element : elements) {
                                arrayList.add(Double.parseDouble(element));
                        }
                        byte[] decryptedF = decryptWithPrivateKey(e.getEncryptedF(), e.getReceivedBy().getPrivateKey());
                        String decryptedFmessage = new String(
                                        decryptedF,
                                        StandardCharsets.UTF_8);
                        ArrayList<Boolean> isPrime = new ArrayList<>();
                        // System.out.println(decryptedYmessage);
                        if (decryptedFmessage.equals("prime")) {
                                for (int i = 0; i < arrayList.size(); i++) {
                                        if (arrayList.get(i) != 0.0) {
                                                isPrime.add(isPrime(z, arrayList.get(i)));
                                                z++;
                                        }
                                }
                        }
                        // System.out.println(e.getPrevHash());
                        Envelope env = Envelope.subTaskResultEnvelope(e.getReceivedBy(), nodeList.get(0),
                                        isPrime.toString(), e.getPrevHash());
                        finalSubTaskEnvelopes.add(env);
                        DAG.put(env, e);
                        // System.out.println(isPrime);
                }
                ArrayList<Boolean> finalResult = new ArrayList<>();
                for (Envelope e : finalSubTaskEnvelopes) {
                        byte[] decryptedResult = decryptWithPrivateKey(
                                        e.getEncryptedResult(),
                                        e.getReceivedBy()
                                                        .getPrivateKey());
                        String decryptedResultmessage = new String(
                                        decryptedResult,
                                        StandardCharsets.UTF_8);
                        ArrayList<Boolean> arrayList = new ArrayList<>();
                        String[] elements = decryptedResultmessage.substring(1, decryptedResultmessage.length() - 1)
                                        .split(",\\s*");
                        for (String element : elements) {
                                arrayList.add(Boolean.parseBoolean(element));
                        }
                        finalResult.addAll(arrayList);
                }

                System.out.println(finalResult);
                // envOfExectionAndVerification.addAll(envOfExectionAndVerification1);
                // // aocBlock.SubtaskEnvelopes = envOfExectionAndVerification;
                // // System.out.println(envOfExectionAndVerification);

                // ArrayList<Envelope> resultEnvelopes = new ArrayList<>();
                // for (int i = 0; i < envOfExectionAndVerification1.size(); i++) {
                // Node sentBy = envOfExectionAndVerification1.get(i).getReceivedBy();
                // Node receivedBy = envOfExectionAndVerification1.get(i).getSentBy();
                // Envelope subtaskResultEnvelope = new Envelope(EnvelopeType.envcs, sentBy,
                // receivedBy);
                // resultEnvelopes.add(subtaskResultEnvelope);
                // for (Map.Entry<Envelope, Envelope> entry : new
                // LinkedHashMap<>(DAG).entrySet()) {
                // if (entry.getKey() == envOfExectionAndVerification1.get(i)) {
                // DAG.put(subtaskResultEnvelope, entry.getKey());
                // }
                // }
                // }

                Envelope commitmentEnvelope = new Envelope(EnvelopeType.envcm, nn1, null);
                DAG.put(commitmentEnvelope, null);
                // // System.out.println(DAG);
                Scanner sc = new Scanner(System.in);

                ArrayList<Envelope> challengeEnvelopes = new ArrayList<>();
                for (int i = 1; i < aocBlock.nodeList.size(); i++) {
                        System.out.println("Does " + aocBlock.nodeList.get(i).getNodeId()
                                        + " want to challenge the result");
                        Boolean bool = sc.nextBoolean();
                        if (bool == true) {
                                Envelope envelope = new Envelope(EnvelopeType.envch,
                                                aocBlock.nodeList.get(i),
                                                aocBlock.nodeList.get(0));
                                challengeEnvelopes.add(envelope);
                                DAG.put(envelope, commitmentEnvelope);
                        }
                }

                ArrayList<Envelope> proofEnvelopes = new ArrayList<>();
                for (int i = 0; i < challengeEnvelopes.size(); i++) {
                        Envelope envelope = new Envelope(EnvelopeType.envpr,
                                        aocBlock.nodeList.get(0),
                                        challengeEnvelopes.get(i).getSentBy());
                        for (Map.Entry<Envelope, Envelope> entry : new LinkedHashMap<>(DAG).entrySet()) {
                                if (entry.getKey().getType() == EnvelopeType.envch) {
                                        DAG.put(envelope, entry.getKey());
                                }
                        }
                        proofEnvelopes.add(envelope);
                }

                ArrayList<Envelope> negativeVerificationEnvelopes = new ArrayList<>();

                for (int i = 0; i < proofEnvelopes.size(); i++) {
                        System.out.println("Does " +
                                        proofEnvelopes.get(i).getReceivedBy().getNodeId()
                                        + " want to negatively verify the proof");
                        Boolean bool = sc.nextBoolean();
                        if (bool == true) {
                                Envelope envelope = new Envelope(EnvelopeType.envvt,
                                                proofEnvelopes.get(i).getReceivedBy(),
                                                aocBlock.nodeList.get(0));
                                negativeVerificationEnvelopes.add(envelope);
                                for (Map.Entry<Envelope, Envelope> entry : new LinkedHashMap<>(DAG).entrySet()) {
                                        if (entry.getKey().getType() == EnvelopeType.envpr
                                                        && entry.getKey().getReceivedBy() == envelope.getSentBy()) {
                                                DAG.put(envelope, entry.getKey());
                                        }
                                }
                        }
                }

                for (Envelope i : negativeVerificationEnvelopes) {
                        System.out.println(i.getSentBy().getNodeId() + " sent the negative verification correct:");
                        Boolean n = sc.nextBoolean();
                        if (n) {
                                int score1 = i.getSentBy().getTrustScore().getCorrectnessOfVerificationSubTask();
                                score1++;
                                i.getSentBy().getTrustScore().setCorrectnessOfVerificationSubTask(score1);
                                int score2 = i.getReceivedBy().getTrustScore().getCorrectnessOfSubTaskResult();
                                score2--;
                                i.getReceivedBy().getTrustScore().setCorrectnessOfSubTaskResult(score2);
                        } else {
                                int score1 = i.getReceivedBy().getTrustScore().getCorrectnessOfSubTaskResult();
                                score1++;
                                i.getReceivedBy().getTrustScore().setCorrectnessOfSubTaskResult(score1);
                                int score2 = i.getSentBy().getTrustScore().getCorrectnessOfVerificationSubTask();
                                score2--;
                                i.getSentBy().getTrustScore().setCorrectnessOfVerificationSubTask(score2);
                        }
                }

                // System.out.println(DAG.size());
                // System.out.println(DAG);

                aocBlock.DAG = DAG;

                System.out.println("nn1's score " + scoreOfNN1);
                System.out.println("cn1's score " + scoreOfCN1);
                System.out.println("cn2's score " + scoreOfCN2);
                System.out.println("cn3's score " + scoreOfCN3);
                System.out.println("cn4's score " + scoreOfCN4);

                // sc.close();

                return aocBlock;
        }

        // public static void main(String[] args) {

        // aocBlock.NegativeVerificationEnvelopes = negativeVerificationEnvelopes;

        // System.out.println(negativeVerificationEnvelopes.toString());

        // Envelope envcm = new Envelope(EnvelopeType.envcm, nn1, cn1);
        // System.out.println(envcm);
        // aocBlock.commitmentOfResult = envcm;

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
        // }
        // else if (matrix[i][j] == 1 && j != 0) { // For directed edges from
        // cooperative Edge Nodes except when
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
        // }
}