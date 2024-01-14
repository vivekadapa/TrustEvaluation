import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EdgeNode nn1 = new EdgeNode("nn1", 0, 0);
        EdgeNode cc1 = new EdgeNode("cc1", 0, 0);
        EdgeNode cc2 = new EdgeNode("cc2", 0, 0);
        EdgeNode cc3 = new EdgeNode("cc3", 0, 0);

        List<EdgeNode> CENodes = new ArrayList<>();
        CENodes.add(cc1);
        CENodes.add(cc2);
        CENodes.add(cc3);

        Transaction T1 = new Transaction("t1", "Txas", true, cc1, cc2);
        Transaction T2 = new Transaction("t2", "Txav", true, nn1, null);

        List<Transaction> transactionList1 = new ArrayList<>();
        transactionList1.add(T1);
        transactionList1.add(T2);

        Transaction T3 = new Transaction("t3", "Txas", false, cc2, cc3);
        Transaction T4 = new Transaction("t4", "Txas", true, cc1, nn1);

        List<Transaction> transactionList2 = new ArrayList<>();
        transactionList2.add(T3);
        transactionList2.add(T4);
        Block block1 = new Block(transactionList1, null, null);
        Block block2 = new Block(transactionList2, null, null);
        List<Block> Blocks = new ArrayList<>();
        Blocks.add(block1);
        Blocks.add(block2);

        for (int i = 0; i < Blocks.size(); i++) {
            for (int j = 0; j < Blocks.get(i).getTransactions().size(); j++) {
                if (Blocks.get(i).getTransactions().get(j).getTypeTx() == "Txas") {
                    EdgeNode computedNode = Blocks.get(i).getTransactions().get(j).getComputedEdgeNode();
                    EdgeNode verifiedEdgeNode = Blocks.get(i).getTransactions().get(j).getVerifiedEdgeNode();
                    if (Blocks.get(i).getTransactions().get(j).isNegativelyVerified()) {
                        float compScore = computedNode.getComputationScore();
                        computedNode.setComputationScore(compScore - 1);
                        float verifyScore = verifiedEdgeNode.getVerficationScore();
                        verifiedEdgeNode.setComputationScore(verifyScore + 1);
                    } else {
                        float compScore = computedNode.getComputationScore();
                        computedNode.setComputationScore(compScore + 1);
                        float verifyScore = verifiedEdgeNode.getVerficationScore();
                        verifiedEdgeNode.setComputationScore(verifyScore - 1);
                    }
                } else if (Blocks.get(i).getTransactions().get(j).getTypeTx() == "Txav") {
                    EdgeNode computedNode = Blocks.get(i).getTransactions().get(j).getComputedEdgeNode();
                    float compScore = computedNode.getComputationScore();
                    if (Blocks.get(i).getTransactions().get(j).isNegativelyVerified()) {
                        for (int k = 0; k < CENodes.size(); k++) {
                            EdgeNode verifiedEdgeNode = CENodes.get(i);
                            float verifyScore = verifiedEdgeNode.getVerficationScore();
                            verifiedEdgeNode.setComputationScore(verifyScore + 1);
                        }
                        computedNode.setComputationScore(compScore - 1);
                    } else {
                        computedNode.setComputationScore(compScore + 1);
                    }
                }
            }
        }
        
        System.out.println("Trust Score of nn1 = " + nn1.CalculateTrustScore());
        System.out.println("Trust Score of cc1 = " + cc1.CalculateTrustScore());
        System.out.println("Trust Score of cc2 = " + cc2.CalculateTrustScore());
        System.out.println("Trust Score of cc3 = " + cc3.CalculateTrustScore());

    }
}