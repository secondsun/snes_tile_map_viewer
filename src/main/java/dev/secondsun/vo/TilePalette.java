package dev.secondsun.vo;

import java.util.ArrayList;
import java.util.List;

public class TilePalette {

    List<TilePaletteEntry > entries = new ArrayList<>();
    public void add(TilePaletteEntry tilePaletteEntry) {
        this.entries.add(tilePaletteEntry);
    }

    public int getRGB(int index) {
        return entries.get(index).toRGB();
    }

    public int entryCount() {
        return entries.size();
    }
}
