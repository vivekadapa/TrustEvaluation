public class Trust {
    private int taskCompletion;
    private int timeliness;
    private int correctnessOfTaskResult;
    private int correctnessOfSubTaskResult;
    private int correctnessOfVerficationTask;
    private int correctnessOfVerificationSubTask;

    public Trust(int taskCompletion, int timeliness, int correctnessOfTaskResult, int correctnessOfSubTaskResult,
            int correctnessOfVerficationTask, int correctnessOfVerificationSubTask) {
        this.taskCompletion = taskCompletion;
        this.timeliness = timeliness;
        this.correctnessOfTaskResult = correctnessOfTaskResult;
        this.correctnessOfSubTaskResult = correctnessOfSubTaskResult;
        this.correctnessOfVerficationTask = correctnessOfVerficationTask;
        this.correctnessOfVerificationSubTask = correctnessOfVerificationSubTask;
    }

    public int getTaskCompletion() {
        return taskCompletion;
    }

    public void setTaskCompletion(int taskCompletion) {
        this.taskCompletion = taskCompletion;
    }

    public int getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(int timeliness) {
        this.timeliness = timeliness;
    }

    public int getCorrectnessOfTaskResult() {
        return correctnessOfTaskResult;
    }

    public void setCorrectnessOfTaskResult(int correctnessOfTaskResult) {
        this.correctnessOfTaskResult = correctnessOfTaskResult;
    }

    public int getCorrectnessOfSubTaskResult() {
        return correctnessOfSubTaskResult;
    }

    public void setCorrectnessOfSubTaskResult(int correctnessOfSubTaskResult) {
        this.correctnessOfSubTaskResult = correctnessOfSubTaskResult;
    }

    public int getCorrectnessOfVerficationTask() {
        return correctnessOfVerficationTask;
    }

    public void setCorrectnessOfVerficationTask(int correctnessOfVerficationTask) {
        this.correctnessOfVerficationTask = correctnessOfVerficationTask;
    }

    public int getCorrectnessOfVerificationSubTask() {
        return correctnessOfVerificationSubTask;
    }

    public void setCorrectnessOfVerificationSubTask(int correctnessOfVerificationSubTask) {
        this.correctnessOfVerificationSubTask = correctnessOfVerificationSubTask;
    }

    public float calculateTrustScore() {
        float score = (float) (0.25 * this.getTaskCompletion() + 0.15 * this.getTimeliness()
                + 0.15 * this.getCorrectnessOfSubTaskResult()
                + 0.15 * this.getCorrectnessOfTaskResult() + 0.15 * this.getCorrectnessOfVerficationTask()
                + 0.15 * this.getCorrectnessOfVerificationSubTask());
        return score;
    }

    public String toString() {
        float finalScore = this.calculateTrustScore();
        return "Total Score: " + finalScore + " tc=" + this.taskCompletion + " ts=" + this.timeliness + " ct="
                + this.correctnessOfTaskResult + " cs="
                + this.correctnessOfSubTaskResult + " vt=" + this.correctnessOfVerficationTask + " vs="
                + this.correctnessOfVerificationSubTask;
    }

}
