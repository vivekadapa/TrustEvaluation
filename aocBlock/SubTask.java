public class SubTask extends Task {
    private String subTaskId;
    private String preId;
    private String postId;
    private int timeToComplete;
    private Node assignedNode;
  
    

    public SubTask(String taskId, int numberOfSubtasks, Node assignedNearbyNode, String subTaskId, String preId,
            String postId, int timeToComplete, Node assignedNode) {
        super(taskId, numberOfSubtasks, assignedNearbyNode);
        this.subTaskId = subTaskId;
        this.preId = preId;
        this.postId = postId;
        this.timeToComplete = timeToComplete;
        this.assignedNode = assignedNode;
    }
    
    public String getSubTaskId() {
        return subTaskId;
    }
    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }
    public String getPreId() {
        return preId;
    }
    public void setPreId(String preId) {
        this.preId = preId;
    }
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public int getTimeToComplete() {
        return timeToComplete;
    }
    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }
    public Node getAssignedNode() {
        return assignedNode;
    }
    public void setAssignedNode(Node assignedNode) {
        this.assignedNode = assignedNode;
    }


    


}
