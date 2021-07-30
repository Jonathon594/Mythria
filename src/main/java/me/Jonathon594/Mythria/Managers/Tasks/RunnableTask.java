package me.Jonathon594.Mythria.Managers.Tasks;

public class RunnableTask extends AbstractTask {


    private final Runnable runnable;

    public RunnableTask(Runnable runnable, int delay) {
        super(delay);
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        runnable.run();
    }
}
