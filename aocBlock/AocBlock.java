import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;

public class AocBlock {

    public static void main(String[] args) {
        int[][] matrix = {
                { 0, 1, 0, 1, 0 },
                { 1, 0, 1, 0, 0 },
                { 1, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 1 },
                { 1, 0, 0, 0, 0 }
        };

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
            // other than cm
            for (int j = 0; j < matrix.length; j++) {
                if (i == 0 && matrix[i][j] == 1) {
                    // String sentId = nodeList.get(i).getNodeId();
                    Node recieverNode = nodeList.get(j);
                    String envId = nodeList.get(i).getNodeId() + nodeList.get(j).getNodeId();
                    Envelope e1 = new Envelope(EnvelopeType.envrv, envId, nodeList.get(i), recieverNode, null, null);
                    Envnn1.add(e1);
                } else if (matrix[i][j] == 1) {
                    // String sentId = nodeList.get(i).getNodeId();
                    String envId = nodeList.get(i).getNodeId() + nodeList.get(j).getNodeId();
                    Node reciverNode = nodeList.get(j);
                    Envelope e2 = new Envelope(EnvelopeType.envcs, envId, nodeList.get(i), reciverNode, null, null);
                    Envnn1.add(e2);
                }

            }
        }

        // for(int i=0;i<matrix.length;i++){
        //     if(matrix[i][0] == 1){
        //         Envelope e3 = new Envelope(null, null, cn4, null, null)
        //     }
        // }

        for (int i = 0; i < Envnn1.size(); i++) {
            System.out.println( Envnn1.get(i).getType()  + " " + Envnn1.get(i).getSentBy().getNodeId() + " " + Envnn1.get(i).getReceivedBy().getNodeId());
        }
    }
}
