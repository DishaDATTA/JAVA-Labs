import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;
    private boolean isShiftPressed = false;  // Track if Shift key is pressed

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;  // Shift key is pressed
        }

        // Set direction based on arrow key presses
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP :
                hero.setDirection(Direction.NORTH);
                if (isShiftPressed) {
                    hero.setSpeed(10);  // Sprint speed
                } else {
                    hero.setSpeed(5);   // Normal speed
                }
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                if (isShiftPressed) {
                    hero.setSpeed(10);  // Sprint speed
                } else {
                    hero.setSpeed(5);   // Normal speed
                }
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                if (isShiftPressed) {
                    hero.setSpeed(10);  // Sprint speed
                } else {
                    hero.setSpeed(5);   // Normal speed
                }
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                if (isShiftPressed) {
                    hero.setSpeed(10);  // Sprint speed
                } else {
                    hero.setSpeed(5);   // Normal speed
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = false;  // Shift key is released
        }

        // Reset to normal speed when no direction key is pressed and Shift is not held
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
                e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!isShiftPressed) {
                hero.setSpeed(5);  // Reset to normal speed
            }
        }
    }
}
