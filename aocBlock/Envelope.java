// Envelope.java

enum EnvelopeType {
    envrv,
    envcs,
    envvs,
    envcm,
    envch,
    envpr,
    envvt
}


public class Envelope {
    private EnvelopeType type;
    private String envId;
    private Node sentBy;
    private Node receivedBy;
    private SubTask subtask;
    private String result;
        



    
    
    public Envelope(EnvelopeType type, String envId, Node sentBy, SubTask subtask, String result) {
        this.type = type;
        this.envId = envId;
        this.sentBy = sentBy;
        this.subtask = subtask;
        this.result = result;
    }



    public Envelope(EnvelopeType type, String envId, Node sentBy, Node receivedBy, SubTask subtask, String result) {
        this.type = type;
        this.envId = envId;
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
        this.subtask = subtask;
        this.result = result;
    }



    public EnvelopeType getType() {
        return type;
    }



    public void setType(EnvelopeType type) {
        this.type = type;
    }



    public String getEnvId() {
        return envId;
    }



    public void setEnvId(String envId) {
        this.envId = envId;
    }



    public Node getSentBy() {
        return sentBy;
    }



    public void setSentBy(Node sentBy) {
        this.sentBy = sentBy;
    }



    public SubTask getSubtask() {
        return subtask;
    }



    public void setSubtask(SubTask subtask) {
        this.subtask = subtask;
    }



    public String getResult() {
        return result;
    }



    public void setResult(String result) {
        this.result = result;
    }



    public Node getReceivedBy() {
        return receivedBy;
    }



    public void setReceivedBy(Node receivedBy) {
        this.receivedBy = receivedBy;
    }


    

    

}
