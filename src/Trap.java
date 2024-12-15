import java.awt.Image;

public class Trap extends SolidSprite {
    public Trap(int x, int y, Image image) {
        super(x, y, image, 48, 48); // Assuming trap size is 48x48
    }

    public void trigger(DynamicSprite hero) {
        System.out.println("Trap triggered at (" + x + ", " + y + ")!");
        // Example: Reduce hero's health or respawn
    }
}