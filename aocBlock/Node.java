import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

enum NodeType {
    nn,
    cn
}

public class Node {

    private String nodeId;
    private NodeType nodeType;
    private Trust trustScore;
    private int amount;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Node(String nodeId, NodeType nodeType, Trust trustScore, int amount) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.trustScore = trustScore;
        this.amount = amount;
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Node(String nodeId, NodeType nodeType, Trust trustScore, int amount, PrivateKey privateKey,
            PublicKey publicKey) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.trustScore = trustScore;
        this.amount = amount;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Trust getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Trust trustScore) {
        this.trustScore = trustScore;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Node(String nodeId, NodeType nodeType) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
    }

    public Node(String nodeId, NodeType nodeType, int amount) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.amount = amount;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public int getamount() {
        return amount;
    }

    public void setamount(int amount) {
        this.amount = amount;
    }

    public int giveStake(int stake) {
        this.amount = this.amount - stake;
        return stake;
    }

    public int giveAmount(int amount) {
        this.amount = this.amount + amount;
        return this.amount;
    }

    // public String toString() {
    //     return "\n{\nNode Id:" + this.nodeId + "\n" + "Node Type: " + this.nodeType + "\n}\n";

    // }
    public String toString(){
        return " " + this.nodeId;
    }

    public static void main(String[] args) {
        Node node = new Node("node1", NodeType.nn, null, 100);
        PrivateKey privateKey = node.getPrivateKey();
        PublicKey publicKey = node.getPublicKey();

        System.out.println(privateKey);
        System.out.println(publicKey);
    }

}
