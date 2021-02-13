package dev.secondsun.vo;

public class TilePaletteEntry {
    final int blue,green,red;

    public TilePaletteEntry(int blue, int green, int red) {
        this.blue = blue;
        this.green = green;
        this.red = red;
    }

    @Override
    public String toString() {
        return "TilePaletteEntry{" +
                "blue=" + blue +
                ", green=" + green +
                ", red=" + red +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TilePaletteEntry that = (TilePaletteEntry) o;

        if (blue != that.blue) return false;
        if (green != that.green) return false;
        return red == that.red;
    }

    @Override
    public int hashCode() {
        int result = blue;
        result = 31 * result + green;
        result = 31 * result + red;
        return result;
    }

    public int toRGB() {
        return ((red * 8) << 16) | ((green * 8) << 8) | (blue * 8);
    }
}
