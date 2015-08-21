package org.dru.car;

import java.awt.*;

public interface Computer {
    void process(final Car car, final World world);

    void paint(final Graphics2D g, final Car car);

    void paintStats(final Graphics2D g);
}
