package edu.nps.moves.dis7.pdus;

public class BitFieldElement {
    private final int elementBitPosition;
    private final int bitLength;

    public BitFieldElement(int elementBitPosition, int bitLength) {
        this.elementBitPosition = elementBitPosition;
        this.bitLength = bitLength;
    }

    public int getElementBitPosition() {
        return elementBitPosition;
    }

    public int getBitLength() {
        return bitLength;
    }
}
