package org.dru.car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JApplet {
    private World world;
    private Timer timer;

    @Override
    public void init() {
        super.init();
        setLayout(new BorderLayout(0, 0));
        world = new World();
        add(world, BorderLayout.CENTER);
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                world.updateWorld();
                world.repaint();
            }
        });
        timer.setInitialDelay(10);
        timer.setRepeats(true);
    }

    @Override
    public void start() {
        super.start();
        timer.start();
    }

    @Override
    public void stop() {
        super.stop();
        timer.stop();
    }
}
