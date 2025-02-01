package vera.tasks;

public class Task {
    protected String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); //mark done task with X
    }

    public void markFeature() {
        System.out.println("  Nice! I've marked this task as done:");
        this.isDone = true;
        System.out.println("   " + this.toString());
    }

    public void unmarkFeature() {
        System.out.println("  OK, I've marked this task as not done yet:");
        this.isDone = false;
        System.out.println("   " + this.toString());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }

    public String toFileString() {
        return String.format("%s | %s", isDone ?"1" :"0", description);
    }
}
