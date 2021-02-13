package dev.secondsun.vo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TilePalette {

    List<TilePaletteEntry> entries = new ArrayList<>();

    public void add(TilePaletteEntry tilePaletteEntry) {
        this.entries.add(tilePaletteEntry);
    }

    public int getRGB(int index) {
        return entries.get(index).toRGB();
    }

    public int entryCount() {
        return entries.size();
    }

    public BufferedImage draw() {
        var image = new BufferedImage(256, 128, BufferedImage.TYPE_INT_RGB);

        IntStream.range(0, entries.size()).forEach(index -> {
            int y = (index / 16) * 16;
            int x = (index % 16) * 16;
            var gfx = image.getGraphics();
            gfx.setColor(new Color(getRGB(index)));
            gfx.fillRect(x,y,16,16);
        });

        return image;

    }
}
