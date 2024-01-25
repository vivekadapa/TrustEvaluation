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
        };  //Input Graph Based on the how the sub tasks are released

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
                if (i == 0 && matrix[i][j] == 1) { //For directed edges from nearby node which creates envelope of the type envrv
                    Node recieverNode = nodeList.get(j);
                    String envId = nodeList.get(i).getNodeId() + nodeList.get(j).getNodeId();
                    Envelope e1 = new Envelope(EnvelopeType.envrv, envId, nodeList.get(i), recieverNode, null, null);
                    Envnn1.add(e1);
                } else if (matrix[i][j] == 1 && j != 0) { // For directed edges from cooperative Edge Nodes except when the edge  
                    String envId1 = nodeList.get(i).getNodeId() + nodeList.get(0).getNodeId();
                    Node reciverNode = nodeList.get(j);
                    String envId2 = nodeList.get(0).getNodeId() + nodeList.get(i).getNodeId();
                    Envelope e2 = new Envelope(EnvelopeType.envcs, envId1, nodeList.get(i), nodeList.get(0), null,
                            null);
                    Envelope e4 = new Envelope(EnvelopeType.envrv, envId2, nodeList.get(0),reciverNode, null, null);
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
    }
}
