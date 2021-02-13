package dev.secondsun.vo;

public class TileSet {
    private final String image, tiles, pallette, map;

    public TileSet(String image, String tiles, String pallette, String map) {
        this.image = String.valueOf(image);
        this.tiles = String.valueOf(tiles);
        this.pallette = String.valueOf(pallette);
        this.map = String.valueOf(map);
    }

    public String getImage() {
        return image;
    }

    public String getTiles() {
        return tiles;
    }

    public String getPallette() {
        return pallette;
    }

    public String getMap() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileSet tileset = (TileSet) o;

        if (!image.equals(tileset.image)) return false;
        if (!tiles.equals(tileset.tiles)) return false;
        if (!pallette.equals(tileset.pallette)) return false;
        return map.equals(tileset.map);
    }

    @Override
    public int hashCode() {
        int result = image.hashCode();
        result = 31 * result + tiles.hashCode();
        result = 31 * result + pallette.hashCode();
        result = 31 * result + map.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tileset{" +
                "image='" + image + '\'' +
                ", tiles='" + tiles + '\'' +
                ", pallette='" + pallette + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}
