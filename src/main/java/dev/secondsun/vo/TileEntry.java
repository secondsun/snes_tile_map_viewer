package dev.secondsun.vo;

public class TileEntry {
    public final int tileIndex;
    public final boolean horizontalFlip;
    public final boolean verticallFlip;
    public final int paletteNumber;
    public final boolean priority;

    public TileEntry(int tileIndex, boolean horizontalFlip, boolean verticallFlip, int paletteNumber, boolean priority) {
        this.tileIndex = tileIndex;
        this.horizontalFlip = horizontalFlip;
        this.verticallFlip = verticallFlip;
        this.paletteNumber = paletteNumber;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TileEntry{" +
                "tileIndex=" + tileIndex +
                ", horizontalFlip=" + horizontalFlip +
                ", verticallFlip=" + verticallFlip +
                ", paletteNumber=" + paletteNumber +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileEntry tileEntry = (TileEntry) o;

        if (tileIndex != tileEntry.tileIndex) return false;
        if (horizontalFlip != tileEntry.horizontalFlip) return false;
        if (verticallFlip != tileEntry.verticallFlip) return false;
        if (paletteNumber != tileEntry.paletteNumber) return false;
        return priority == tileEntry.priority;
    }

    @Override
    public int hashCode() {
        int result = tileIndex;
        result = 31 * result + (horizontalFlip ? 1 : 0);
        result = 31 * result + (verticallFlip ? 1 : 0);
        result = 31 * result + paletteNumber;
        result = 31 * result + (priority ? 1 : 0);
        return result;
    }
}
