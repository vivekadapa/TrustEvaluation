import java.util.ArrayList;

public class Task {
    private String taskId;
    private ArrayList<SubTask> subtasks;
    private Node assignedNearbyNode;
 

    public Task(String taskId, ArrayList<SubTask> subtasks, Node assignedNearbyNode) {
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

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public Node getAssignedNearbyNode() {
        return assignedNearbyNode;
    }

    public void setAssignedNearbyNode(Node assignedNearbyNode) {
        this.assignedNearbyNode = assignedNearbyNode;
    }

}



