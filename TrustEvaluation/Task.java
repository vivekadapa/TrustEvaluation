import java.util.List;

public class Task {
    private String taskId;
    private List<SubTask> subTasks;


    public Task(String taskId, List<SubTask> subTasks) {
        this.taskId = taskId;
        this.subTasks = subTasks;
    }
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public List<SubTask> getSubTasks() {
        return subTasks;
    }
    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }


}
