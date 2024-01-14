public class SubTask {
    private String subTaskId;
    private String computationResult;


    public SubTask(String subTaskId, String computationResult) {
        this.subTaskId = subTaskId;
        this.computationResult = computationResult;
    }


    public String getSubTaskId() {
        return subTaskId;
    }


    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }


    public String getComputationResult() {
        return computationResult;
    }


    public void setComputationResult(String computationResult) {
        this.computationResult = computationResult;
    }
    
    
    
}
