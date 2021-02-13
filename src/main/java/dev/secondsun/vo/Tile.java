package dev.secondsun.vo;

import java.awt.image.Raster;
import java.util.List;

public class Tile {
    private final List<PixelRow> rows;

    public Tile(List<PixelRow> rows) {
        this.rows = rows;
    }

    public PixelRow getRow(int row) {
        return rows.get(row);
    }
}
