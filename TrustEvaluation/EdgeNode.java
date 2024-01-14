public class EdgeNode {
    private String nodeId;
    private float computationScore;
    private float verficationScore;


    
    public EdgeNode(String nodeId, float computationScore, float verficationScore) {
        this.nodeId = nodeId;
        this.computationScore = computationScore;
        this.verficationScore = verficationScore;
    }

    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public float getComputationScore() {
        return computationScore;
    }
    public void setComputationScore(float computationScore) {
        this.computationScore = computationScore;
    }
    public float getVerficationScore() {
        return verficationScore;
    }
    public void setVerficationScore(float verficationScore) {
        this.verficationScore = verficationScore;
    }


    public float CalculateTrustScore(){
        float trust = (float)(0.65*this.computationScore + 0.35*this.verficationScore);
        return trust;
    }

    
}
