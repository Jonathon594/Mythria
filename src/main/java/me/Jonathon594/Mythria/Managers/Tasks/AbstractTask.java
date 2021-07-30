package me.Jonathon594.Mythria.Managers.Tasks;

public abstract class AbstractTask {
    private int delay;

    protected AbstractTask(int delay) {
        this.delay = delay;
    }

    public abstract void execute();

    public boolean shouldExecute() {
        return delay <= 0;
    }

    public void update() {
        delay--;
    }
}
