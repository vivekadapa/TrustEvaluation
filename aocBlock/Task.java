

public class Task {
    private String taskId;
    private SubTask[] subtasks;
    private Node assignedNearbyNode;
 

    public Task(String taskId, SubTask[] subtasks, Node assignedNearbyNode) {
        this.taskId = taskId;
        this.subtasks = subtasks;
        this.assignedNearbyNode = assignedNearbyNode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public SubTask[] getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(SubTask[] subtasks) {
        this.subtasks = subtasks;
    }

    public Node getAssignedNearbyNode() {
        return assignedNearbyNode;
    }

    public void setAssignedNearbyNode(Node assignedNearbyNode) {
        this.assignedNearbyNode = assignedNearbyNode;
    }

}



