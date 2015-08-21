package org.dru.car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class Car {
    private static final BufferedImage image;

    static {
        try {
            image = ImageIO.read(Track.class.getResource("/car1.png"));
        } catch (final Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    private Computer computer;

    private double maxSpeed;

    private double x;
    private double y;
    private double theta;

    private double speed;
    private double turn;

    private double velocityX;
    private double velocityY;


    public Car(final double x, final double y, final double theta, final Computer computer, final double maxSpeed) {
        this.computer = computer;
        this.maxSpeed = maxSpeed;
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(final double speed) {
        this.speed += (Math.max(0.0, Math.min(1, speed)) - this.speed) * (speed > this.speed ? 0.01 : 0.1);
    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(final double turn) {
        this.turn += (Math.max(-Math.PI, Math.min(Math.PI, turn)) - this.turn) * 0.05;
    }

    public void updateCar(final World world) {
        theta += turn * 0.01;
        velocityX += (Math.sin(theta) * speed * maxSpeed - velocityX) * 0.035;
        velocityY += (Math.cos(theta) * speed * maxSpeed - velocityY) * 0.035;
        x += velocityX;
        y -= velocityY;
        computer.process(this, world);
    }

    public void paintCar(final Graphics2D g) {
        g.translate(-6, -10);
        g.drawImage(image, 0, 0, null);
        g.translate(6, 10);
        computer.paint(g, this);
    }

    public void paintStats(final Graphics2D g) {
        computer.paintStats(g);
    }
}
