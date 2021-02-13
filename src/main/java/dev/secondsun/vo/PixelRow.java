package dev.secondsun.vo;

public class PixelRow {
    public byte[] pixels = new byte[8];

    public void applyBitplane(int bitPlane, int position) {

        for (int shift = 0; shift < 8; shift++) {
            var pixel = pixels[shift];

            //get the bit of the bitplane for the pixel
            var bitplaneMask = 1 << (7-shift);
            var bitplaneBit = ((bitPlane & bitplaneMask) != 0)?1:0;

            var resetPixelMask = ~(1<<position);
            pixel &= resetPixelMask;
            pixel |= bitplaneBit << position;
            pixels[shift] = pixel;

        }
    }

    public byte getPixel(int column) {
        return pixels[column];
    }
}
