package dev.secondsun.vo;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private TilePalette palette;

    public void setPalette(TilePalette palette) {
        this.palette = palette;
    }

    public TilePalette getPalette() {
        return palette;
    }

    public int entryCount() {
         return tileEntries.size();
    }

    public List<TileEntry> entries() {
        return tileEntries;
    }

    enum Size {SIZE_32x32, SIZE_64x32, SIZE_32x64, SIZE_64x64};


    Size size = Size.SIZE_32x32;
    List<TileEntry> tileEntries = new ArrayList<>(128);

    public void add(TileEntry tileEntry) {
        this.tileEntries.add(tileEntry);
    }


}
