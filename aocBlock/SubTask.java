// import java.time.Instant;

public class SubTask {

    private String subTaskId;
    private long timeliness;
    private Node assignedTo;
    private Node assignedBy;

    public SubTask(String subTaskId, long timeliness, Node assignedTo, Node assignedBy) {
        this.subTaskId = subTaskId;
        this.timeliness = timeliness;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
    }

    public String getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }

    public long getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(long timeliness) {
        this.timeliness = timeliness;
    }

    public Node getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Node assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Node getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Node assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String toString() {
        return "SubTaskId: " + this.subTaskId + "\nAssigned By: " + this.getAssignedBy() + "\nAssgined To: "
                + this.getAssignedTo();
    }

}