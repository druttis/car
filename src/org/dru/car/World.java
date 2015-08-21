package org.dru.car;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class World extends JComponent {
    private final Track track;
    private final List<Car> cars;

    public World() {
        track = new Track();
        cars = new ArrayList<Car>();
//        add(new Car(50, 200, 0, new Computer1(), 1));
//        add(new Car(50, 300, 0, new Computer1(), 1.25));
//        add(new Car(50, 400, 0, new Computer1(), 1.75));
        add(new Car(70, 300, 0, new Computer1(), 2.0));
    }

    public Track getTrack() {
        return track;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public void add(final Car car) {
        cars.add(car);
    }

    public void updateWorld() {
        for (final Car car : cars) {
            car.updateCar(this);
        }
    }

    public void paintWorld(final Graphics2D g) {
        track.paintTrack(g);
        final AffineTransform saf = g.getTransform();
        for (final Car cars : this.cars) {
            g.translate(cars.getX(), cars.getY());
            g.rotate(cars.getTheta());
            cars.paintCar(g);
            g.setTransform(saf);
        }
        int x = 120;
        int y = 140;
        for (final Car cars :this.cars) {
            g.translate(x, y);
            cars.paintStats(g);
            g.setTransform(saf);
            x += 164;
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        paintWorld((Graphics2D) g);
    }
}
