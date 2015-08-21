package org.dru.car;

import java.awt.*;

public final class Computer1 implements Computer {
    private final double[] distances = new double[45];
    private final double[] thetas = new double[distances.length];
    private double sum_distance;
    private double max_distance;
    private double min_distance;

    public Computer1() {
    }

    @Override
    public void process(final Car car, final World world) {
        double scale = 1.0 / (Math.pow(car.getSpeed() * car.getMaxSpeed(), 1) + 1.0);
        double theta = -Math.PI * 0.5 * scale;
        max_distance = 0;
        min_distance = Double.MAX_VALUE;
        sum_distance = 0;
        double sum_theta = 0;
        double max_theta = 0;
        for (int i = 0; i < distances.length; i++) {
            double distance = scan(car, world, theta);
            distances[i] = distance;
            thetas[i] = theta;
            if (max_distance < distance) {
                max_distance = distance;
                max_theta = theta;
            }
            if (min_distance > distance) {
                min_distance = distance;
            }
            sum_distance += distance;
            sum_theta += theta * distance;
            theta += Math.PI / (distances.length - 1) * scale;
        }
        car.setSpeed(distances[distances.length / 2] * 0.009);
        //car.setTurn(max_theta * 4);
        car.setTurn(sum_theta / max_distance);
    }

    @Override
    public void paint(final Graphics2D g, final Car car) {
        for (int i = 0; i < distances.length; i++) {
            drawLine(g, thetas[i], distances[i], i);
        }
    }

    public void paintStats(final Graphics2D g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, 160, 60);
        g.setColor(Color.gray);
        g.fillRect(0, 60, 160, 60);
        g.translate(80, 60);
        double left = thetas[0];
        double right = thetas[distances.length - 1];
        double range = right - left;
        for (int i = 0; i < distances.length - 1; i++) {
            int x1 = (int) (160 * (thetas[i] - left - range * 0.5) / range);
            int x2 = (int) (160 * (thetas[i + 1] - left - range * 0.5) / range);
            int xr = x2 - x1;
            double d1 = distances[i] / 20.0;//max_distance;
            double d2 = distances[i + 1] / 20.0;// max_distance;
            for (int x = 0; x < xr; x++) {
                double d = d1 + (d2 - d1) * (double) x / (double) xr;
                g.setColor(mix(Color.green, (1 - d / 20.0)));
                double y = 60 / (d + 1);
                g.fillRect(x1 + x, -(int) y, 1, (int) (y * 2));
            }
        }
    }

    private Color mix(final Color color, final double c) {
        double r = color.getRed() * c;
        double g = color.getGreen() * c;
        double b = color.getBlue() * c;
        return new Color((int) saturate(r), (int) saturate(g), (int) saturate(b));
    }

    private double saturate(final double v) {
        return Math.max(0, Math.min(255, v));
    }

    private void drawLine(final Graphics2D g, final double theta, final double length, final int i) {
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        double x1 = sin * 11;
        double y1 = -cos * 11;
        double x2 = sin * length;
        double y2 = -cos * length;
        drawLine(g, x1, y1, x2, y2, i);
    }

    private void drawLine(final Graphics2D g, final double x1, final double y1, final double x2, final double y2, final int i) {
        g.setColor(Color.red);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private double scan(final Car car, final World world, final double theta) {
        double sin = Math.sin(car.getTheta() + theta);
        double cos = Math.cos(car.getTheta() + theta);
        double x = car.getX() + sin * 11;
        double y = car.getY() - cos * 11;
        while (x >= 0 && x < world.getTrack().getWidth() && y >= 0 && y < world.getTrack().getHeight()) {
            if (isGreen(world.getTrack().pixel(x, y))) {
                break;
            }
            if (isCar(car, world, x, y)) {
                break;
            }
            x += sin;
            y -= cos;
        }
        final double dx = x - car.getX();
        final double dy = y - car.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private boolean isGreen(final int rgb) {
        final int r = (rgb >> 16) & 0xff;
        final int g = (rgb >> 8) & 0xff;
        final int b = rgb & 0xff;
        return (g > (r + 64) && g > (b + 64));
    }

    private boolean isCar(final Car car, final World world, final double x, final double y) {
        for (final Car other : world.getCars()) {
            if (other == car) {
                continue;
            }
            final double dx = x - other.getX();
            final double dy = y - other.getY();
            if (Math.sqrt(dx * dx + dy * dy) < 11) {
                return false;
            }
        }
        return false;
    }
}
