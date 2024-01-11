public class Transaction {
    private String TransactionId;
    private String TypeTx;
    private boolean negativelyVerified;
    private EdgeNode computedEdgeNode;
    private EdgeNode verifiedEdgeNode;


    

    public Transaction(String transactionId, String typeTx, boolean negativelyVerified, EdgeNode computedEdgeNode,
            EdgeNode verifiedEdgeNode) {
        TransactionId = transactionId;
        TypeTx = typeTx;
        this.negativelyVerified = negativelyVerified;
        this.computedEdgeNode = computedEdgeNode;
        this.verifiedEdgeNode = verifiedEdgeNode;
    }


    public String getTransactionId() {
        return TransactionId;
    }
    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }
    public String getTypeTx() {
        return TypeTx;
    }
    public void setTypeTx(String typeTx) {
        TypeTx = typeTx;
    }
    public boolean isNegativelyVerified() {
        return negativelyVerified;
    }
    public void setNegativelyVerified(boolean negativelyVerified) {
        this.negativelyVerified = negativelyVerified;
    }
    public EdgeNode getComputedEdgeNode() {
        return computedEdgeNode;
    }
    public void setComputedEdgeNode(EdgeNode computedEdgeNode) {
        this.computedEdgeNode = computedEdgeNode;
    }
    public EdgeNode getVerifiedEdgeNode() {
        return verifiedEdgeNode;
    }
    public void setVerifiedEdgeNode(EdgeNode verifiedEdgeNode) {
        this.verifiedEdgeNode = verifiedEdgeNode;
    }
    
    

}
