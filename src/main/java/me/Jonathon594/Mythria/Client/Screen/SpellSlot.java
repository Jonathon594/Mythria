package me.Jonathon594.Mythria.Client.Screen;

public class SpellSlot {
    private int x, y, index;

    public SpellSlot(int index, int x, int y) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x-1 && mouseX < x + 17 && mouseY >= y-1 && mouseY < y + 17;
    }
}
