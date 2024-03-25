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

                for (int i = 1; i < matrix.length; i++) {
                        for (int j = 1; j < matrix.length; j++) {
                                if (matrix[i][j] == 1) {
                                        if (numberOfOnesPerRow[i] == 1) {
                                                for (int p = 0; p < subtaskEnvelopes.size(); p++) {
                                                        if (subtaskEnvelopes.get(p).getSentBy() == nodeList.get(i)) {
                                                                byte[] decryptedResult = decryptWithPrivateKey(
                                                                                subtaskEnvelopes.get(p)
                                                                                                .getEncryptedResult(),
                                                                                subtaskEnvelopes.get(p).getReceivedBy()
                                                                                                .getPrivateKey());
                                                                String decryptedResultmessage = new String(
                                                                                decryptedResult,
                                                                                StandardCharsets.UTF_8);
                                                                System.out.println(decryptedResultmessage);
                                                                Envelope envelope = Envelope.releaseSubTask(nn1,
                                                                                nodeList.get(j), decryptedResultmessage,
                                                                                "prime");
                                                                DAG.put(envelope, subtaskEnvelopes.get(p));
                                                        }
                                                }
                                        } else {
                                                int q = 0;
                                                for (int p = 0; p < subtaskEnvelopes.size(); p++) {
                                                        if (subtaskEnvelopes.get(p).getSentBy() == nodeList.get(i)) {
                                                                byte[] decryptedResult = decryptWithPrivateKey(
                                                                                subtaskEnvelopes.get(p)
                                                                                                .getEncryptedResult(),
                                                                                subtaskEnvelopes.get(p).getReceivedBy()
                                                                                                .getPrivateKey());
                                                                String decryptedResultmessage = new String(
                                                                                decryptedResult,
                                                                                StandardCharsets.UTF_8);
                                                                ArrayList<Double> resultList = new ArrayList<>();
                                                                int midPoint = decryptedResultmessage.length() / 2;
                                                                String[] elements;
                                                                if (q == 0) {
                                                                        elements = decryptedResultmessage
                                                                                        .substring(1, midPoint)
                                                                                        .split(", ");
                                                                } else {
                                                                        elements = decryptedResultmessage
                                                                                        .substring(midPoint + 1)
                                                                                        .split(", ");
                                                                }
                                                                for (String element : elements) {
                                                                        resultList.add(Double.parseDouble(element));
                                                                }
                                                                System.out.println(resultList);
                                                                q++;
                                                        }
                                                }
                                        }
                                }
                        }
                }

                aocBlock.DAG = DAG;

                // sc.close();

                return aocBlock;
                // for (int i = 1; i < matrix.length; i++) {
                // for (int j = 1; j < matrix.length; j++) {
                // if (matrix[i][j] == 1) {
                // if (numberOfOnesPerRow[i] == 1) {
                // for (int p = 0; p < subtaskEnvelopes.size(); p++) {
                // if (subtaskEnvelopes.get(p).getSentBy() == nodeList.get(i)) {
                // byte[] decryptedResult = decryptWithPrivateKey(
                // subtaskEnvelopes.get(p)
                // .getEncryptedResult(),
                // subtaskEnvelopes.get(p).getReceivedBy()
                // .getPrivateKey());
                // String decryptedResultmessage = new String(
                // decryptedResult,
                // StandardCharsets.UTF_8);
                // System.out.println(decryptedResultmessage);
                // Envelope envelope = Envelope.releaseSubTask(nn1,
                // nodeList.get(j), decryptedResultmessage,
                // "prime");
                // DAG.put(envelope, subtaskEnvelopes.get(p));
                // }
                // }
                // } else {
                // int q = 0;
                // for (int p = 0; p < subtaskEnvelopes.size(); p++) {
                // if (subtaskEnvelopes.get(p).getSentBy() == nodeList.get(i)) {
                // if (q == 0) {
                // byte[] decryptedResult = decryptWithPrivateKey(
                // subtaskEnvelopes.get(p)
                // .getEncryptedResult(),
                // subtaskEnvelopes.get(p)
                // .getReceivedBy()
                // .getPrivateKey());
                // String decryptedResultmessage = new String(
                // decryptedResult,
                // StandardCharsets.UTF_8);
                // ArrayList<Double> resultList = new ArrayList<>();
                // int midPoint = decryptedResultmessage.length()
                // / 2;
                // String[] elements = decryptedResultmessage
                // .substring(1, midPoint)
                // .split(", ");
                // for (String element : elements) {
                // resultList.add(Double
                // .parseDouble(element));
                // }
                // System.out.println(resultList);

                // } else {
                // byte[] decryptedResult = decryptWithPrivateKey(
                // subtaskEnvelopes.get(p)
                // .getEncryptedResult(),
                // subtaskEnvelopes.get(p)
                // .getReceivedBy()
                // .getPrivateKey());
                // String decryptedResultmessage = new String(
                // decryptedResult,
                // StandardCharsets.UTF_8);
                // ArrayList<Double> resultList = new ArrayList<>();
                // int midPoint = decryptedResultmessage.length()
                // / 2;
                // String[] elements = decryptedResultmessage
                // .substring(midPoint)
                // .split(", ");
                // for (String element : elements) {
                // resultList.add(Double
                // .parseDouble(element));
                // }
                // System.out.println(resultList);
                // }
                // q++;
                // }
                // }
                // }

                // }
                // }
                // }
                // // System.out.println("This is a DAG " + DAG);

                // // System.out.println(envOfExectionAndVerification1);
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

                // Envelope commitmentEnvelope = new Envelope(EnvelopeType.envcm, nn1, null);
                // DAG.put(commitmentEnvelope, null);
                // // System.out.println(DAG);
                // Scanner sc = new Scanner(System.in);

                // ArrayList<Envelope> challengeEnvelopes = new ArrayList<>();
                // for (int i = 1; i < aocBlock.nodeList.size(); i++) {
                // System.out.println("Does " + aocBlock.nodeList.get(i).getNodeId()
                // + " want to challenge the result");
                // Boolean bool = sc.nextBoolean();
                // if (bool == true) {
                // Envelope envelope = new Envelope(EnvelopeType.envch,
                // aocBlock.nodeList.get(i),
                // aocBlock.nodeList.get(0));
                // challengeEnvelopes.add(envelope);
                // DAG.put(envelope, commitmentEnvelope);
                // }
                // }

                // ArrayList<Envelope> proofEnvelopes = new ArrayList<>();
                // for (int i = 0; i < challengeEnvelopes.size(); i++) {
                // Envelope envelope = new Envelope(EnvelopeType.envpr,
                // aocBlock.nodeList.get(0),
                // challengeEnvelopes.get(i).getSentBy());
                // for (Map.Entry<Envelope, Envelope> entry : new
                // LinkedHashMap<>(DAG).entrySet()) {
                // if (entry.getKey().getType() == EnvelopeType.envch) {
                // DAG.put(envelope, entry.getKey());
                // }
                // }
                // proofEnvelopes.add(envelope);
                // }

                // // ArrayList<Envelope> negativeVerificationEnvelopes = new ArrayList<>();

                // for (int i = 0; i < proofEnvelopes.size(); i++) {
                // System.out.println("Does " +
                // proofEnvelopes.get(i).getReceivedBy().getNodeId()
                // + " want to negatively verify the proof");
                // Boolean bool = sc.nextBoolean();
                // if (bool == true) {
                // Envelope envelope = new Envelope(EnvelopeType.envvt,
                // aocBlock.nodeList.get(i),
                // aocBlock.nodeList.get(0));
                // for (Map.Entry<Envelope, Envelope> entry : new
                // LinkedHashMap<>(DAG).entrySet()) {
                // if (entry.getKey().getType() == EnvelopeType.envpr
                // && entry.getKey().getReceivedBy() == envelope.getSentBy()) {
                // DAG.put(envelope, entry.getKey());
                // }
                // }
                // }
                // }

                // System.out.println(DAG.size());
                // System.out.println(DAG);

        }
}
