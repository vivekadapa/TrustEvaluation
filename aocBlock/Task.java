

public class Task {
    private String taskId;
    private int numberOfSubtasks;
    private Node assignedNearbyNode;



    public Task(String taskId, int numberOfSubtasks, Node assignedNearbyNode) {
        this.taskId = taskId;
        this.numberOfSubtasks = numberOfSubtasks;
        this.assignedNearbyNode = assignedNearbyNode;
    }


    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public int getNumberOfSubtasks() {
        return numberOfSubtasks;
    }
    public void setNumberOfSubtasks(int numberOfSubtasks) {
        this.numberOfSubtasks = numberOfSubtasks;
    }
    public Node getAssignedNearbyNode() {
        return assignedNearbyNode;
    }
    public void setAssignedNearbyNode(Node assignedNearbyNode) {
        this.assignedNearbyNode = assignedNearbyNode;
    }


    
}



