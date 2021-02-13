package dev.secondsun.controller;


import dev.secondsun.vo.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TileViewer {
    enum BackGroundMode {MODE_0, MODE_1, MODE_2, MODE_3}

    ;

    private static final String exampleLocation = "/home/summerspittman/Projects/SNES_demoscene/libSFX/examples/Hello/Data/";
    private static final String image = exampleLocation + "SNES.png";
    private static final String map = exampleLocation + "SNES.png.map";
    private static final String tiles = exampleLocation + "SNES.png.tiles";
    private static final String palette = exampleLocation + "SNES.png.palette";


    @FXML
    private ImageView originalImage;

    @FXML
    private ImageView renderedPalette;

    @FXML
    private ImageView renderedImage;

    @FXML
    private GridPane tilesView;

    @FXML
    public void initialize() throws IOException {
        var set = new TileSet(image, tiles, palette, map);
        originalImage.setImage(new Image(new File(set.getImage()).toURL().toString()));
        var map = readMap(set);
        List<Tile> tiles = readTiles(set, BackGroundMode.MODE_1);

        for (int index = 0; index < tiles.size(); index++) {
            var image = renderTile(tiles.get(index), map.getPalette());
            var colours = new HashSet<Integer>();
            for (int i = 0; i < 8; i++) {
                for (int i2 = 0; i2 < 8; i2++) {
                    colours.add((int) tiles.get(index).getRow(i).getPixel(i2));
                }
            }
            tilesView.add(new ImageView(SwingFXUtils.toFXImage(image, null)), index % 8, index / 8);
        }
        tilesView.requestLayout();
        tilesView.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
        tilesView.prefHeight(600);
        tilesView.prefWidth(600);

        renderedPalette.setImage(SwingFXUtils.toFXImage(map.getPalette().draw(), null));

        BufferedImage image = assembleImage(map, tiles);
        renderedImage.setImage(SwingFXUtils.toFXImage(image, null));
    }

    private BufferedImage assembleImage(TileMap map, List<Tile> tiles) {
        var image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                if ((y * 32 + x) < map.entryCount()) {
                    var tileEntry = map.entries().get(y * 32 + x);
                    try {
                        image.getGraphics().drawImage(renderTile(tileEntry, tiles, map.getPalette()), x * 8, y * 8, null);
                    } catch (Exception swallow) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, swallow.getMessage(), swallow);
                    }
                }
            }
        }
        return image;
    }

    private BufferedImage renderTile(TileEntry entry, List<Tile> tiles, TilePalette palette) {
        var tile = tiles.get(entry.tileIndex);
        var image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                var pixel = tile.getRow(row).getPixel(column);

                int correctRow = column;
                int correctColumn = row;

                if (entry.horizontalFlip) {
                    correctColumn = 7 - correctColumn;
                }
                if (entry.verticallFlip) {
                    correctRow = 7 - correctRow;
                }
                try {
                    image.setRGB(correctRow, correctColumn, toRGB(pixel, palette, entry));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Logger.getAnonymousLogger().log(Level.SEVERE, String.format("%d,%d out of bounds", correctRow, correctColumn));
                }
            }
        }
        return image;
    }

    private int toRGB(byte pixel, TilePalette palette, TileEntry entry) {

        return palette.getRGB(entry.paletteNumber * 16 + pixel);
    }

    private BufferedImage renderTile(Tile tile, TilePalette palette) {
        var image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                var pixel = tile.getRow(row).getPixel(column);
                image.setRGB(row, column, palette.getRGB(pixel));
            }
        }
        return image;
    }

    private List<Tile> readTiles(TileSet set, BackGroundMode mode) {
        var tiles = new ArrayList<Tile>(64);
        try {
            var stream = new PushbackInputStream(new FileInputStream(set.getTiles()));
            int read = 0;
            while ((read = stream.read()) != -1) {
                stream.unread(read);
                switch (mode) {

                    case MODE_0:
                        throw new IllegalStateException("Not implemented");
                    case MODE_1: {
                        //read bp1 and 2
                        List<PixelRow> rows = allocatePixelRows(8);
                        for (int row = 0; row < 8; row = row + 1) {
                            var bp1 = stream.read();
                            var bp2 = stream.read();
                            rows.get(row).applyBitplane(bp1, 0);
                            rows.get(row).applyBitplane(bp2, 1);
                        }
                        //read bp3
                        for (int row = 0; row < 8; row = row + 1) {
                            var bp3 = stream.read();
                            var bp4 = stream.read();
                            rows.get(row).applyBitplane(bp3, 2);
                            rows.get(row).applyBitplane(bp4, 3);
                        }
                        tiles.add(new Tile(rows));
                    }
                    break;
                    case MODE_2:
                        throw new IllegalStateException("Not implemented");
                    case MODE_3:
                        throw new IllegalStateException("Not implemented");
                }
            }
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(String.format("Found %d tiles entries", tiles.size()));
        return tiles;
    }

    private List<PixelRow> allocatePixelRows(int size) {
        var list = new ArrayList<PixelRow>();
        for (int i = 0; i < size; i++) {
            list.add(new PixelRow());
        }
        return list;
    }

    private TilePalette readPalette(TileSet set) {
        try {
            var stream = new FileInputStream(set.getPallette());
            var palette = new TilePalette();
            var count = 0;
            int highbyte, lowbyte;
            while ((lowbyte = stream.read()) != -1) {
                highbyte = stream.read();
                var word = highbyte << 8 | lowbyte;
                var blue = (word & 0b0111110000000000) >> 10;
                var green = (word & 0b0000001111100000)  >> 5;
                var red = word & 0b0000000000011111;
                palette.add(new TilePaletteEntry(blue, green, red));

            }
            System.out.println(String.format("Found %d palette entries", palette.entryCount()));
            return palette;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TileMap readMap(TileSet set) {
        HashMap<Integer, Integer> indexCount = new HashMap<>();
        var tileMap = new TileMap();
        try {
            var stream = new FileInputStream(set.getMap());
            var reading = true;
            var count = 0;
            int highbyte;
            while ((highbyte = stream.read()) != -1) {
                var lowbyte = stream.read();
                var word = (lowbyte << 8) | highbyte;
                var tileIndex = word & 0b001111111111;
                var horizontalFlip = (word & 0b0100000000000000) > 0;
                var verticallFlip = (word & 0b1000000000000000) > 0;
                var paletteNumber = (word & 0b0001110000000000) >> 10;
                var priority = (word & 0b0010000000000000) != 0;

                var tileEntry = new TileEntry(tileIndex, horizontalFlip, verticallFlip, paletteNumber, priority);
                indexCount.compute(paletteNumber, (index, ct) -> ct == null ? 1 : index + ct);
                tileMap.add(tileEntry);
            }
            tileMap.setPalette(readPalette(set));
            System.out.println(String.format("Found %d tile map entries", tileMap.entryCount()));
            System.out.println(("Entry Stats :" + indexCount));

            return tileMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
