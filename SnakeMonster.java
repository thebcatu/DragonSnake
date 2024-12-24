import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeMonster extends JFrame {
    private int stepPhase = 0; // Phase of leg movement (0 to 20)
    private boolean movingForward = true; // Direction of motion
    private int bodyOffsetX = 0; // Small horizontal movement for the body
    private int bodyOffsetY = 0; // Small vertical movement for the body
    private int bodyPositionX = 350; // Starting X position
    private int bodyPositionY = 200; // Starting Y position

    public SnakeMonster() {
        // Set up the JFrame
        setTitle("Walking Snake Monster");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Timer for animation
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMovement();
                repaint();
            }
        });
        timer.start();
    }

    // Update movement logic for legs and body
    private void updateMovement() {
        // Update step phase for legs
        stepPhase = (stepPhase + 1) % 20;

        // Update body position for forward motion
        if (movingForward) {
            bodyPositionX += 1; // Move forward horizontally
        }

        // Slight sway in body for realism
        bodyOffsetX = (int) (5 * Math.sin(Math.toRadians(stepPhase * 18))); // Horizontal sway
        bodyOffsetY = (int) (5 * Math.cos(Math.toRadians(stepPhase * 18))); // Vertical sway
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        // Set background color to black
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Head of the snake (triangle)
        g2d.setColor(Color.GREEN);
        int[] headX = {bodyPositionX + bodyOffsetX, bodyPositionX - 50 + bodyOffsetX, bodyPositionX + 50 + bodyOffsetX};
        int[] headY = {bodyPositionY + bodyOffsetY, bodyPositionY + 100 + bodyOffsetY, bodyPositionY + 100 + bodyOffsetY};
        g2d.fillPolygon(headX, headY, 3);

        // Eyes on the head
        g2d.setColor(Color.WHITE);
        g2d.fillOval(bodyPositionX - 20 + bodyOffsetX, bodyPositionY + 40 + bodyOffsetY, 20, 20);
        g2d.fillOval(bodyPositionX + bodyOffsetX, bodyPositionY + 40 + bodyOffsetY, 20, 20);

        g2d.setColor(Color.BLACK);
        g2d.fillOval(bodyPositionX - 12 + bodyOffsetX, bodyPositionY + 48 + bodyOffsetY, 8, 8);
        g2d.fillOval(bodyPositionX + 8 + bodyOffsetX, bodyPositionY + 48 + bodyOffsetY, 8, 8);

        // Backbone with curved bones
        int x = bodyPositionX + bodyOffsetX, y = bodyPositionY + 100 + bodyOffsetY;
        for (int i = 0; i < 8; i++) {
            g2d.setColor(Color.GREEN);
            g2d.drawArc(x - 25, y, 50, 30, 0, 180);
            g2d.drawLine(x, y + 15, x, y + 45);
            y += 60;
        }

        // Tail
        g2d.setColor(Color.GREEN);
        g2d.drawLine(x, y, x, y + 50);

        // Limbs connected to backbone with small feet
        g2d.setColor(Color.DARK_GRAY);
        int[][] limbPositions = {
            // Upper limbs
            {x - 25, bodyPositionY + 130 + stepPhase / 2, x - 50, bodyPositionY + 160 + stepPhase / 2, x - 60, bodyPositionY + 170 + stepPhase / 2},
            {x + 25, bodyPositionY + 130 - stepPhase / 2, x + 50, bodyPositionY + 160 - stepPhase / 2, x + 60, bodyPositionY + 170 - stepPhase / 2},

            // Lower limbs
            {x - 25, bodyPositionY + 250 + stepPhase / 2, x - 50, bodyPositionY + 280 + stepPhase / 2, x - 60, bodyPositionY + 290 + stepPhase / 2},
            {x + 25, bodyPositionY + 250 - stepPhase / 2, x + 50, bodyPositionY + 280 - stepPhase / 2, x + 60, bodyPositionY + 290 - stepPhase / 2},
        };

        for (int[] limb : limbPositions) {
            g2d.drawLine(limb[0], limb[1], limb[2], limb[3]);
            g2d.drawLine(limb[2], limb[3], limb[4], limb[5]);

            // Draw small feet (toes)
            g2d.drawLine(limb[4], limb[5], limb[4] - 10, limb[5] + 10);
            g2d.drawLine(limb[4], limb[5], limb[4] + 10, limb[5] + 10);
        }
    }

    public static void main(String[] args) {
        new SnakeMonster();
    }
}
