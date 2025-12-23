/**
 * Copyright (c) 2008-2023, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */

package edu.nps.moves.dis7.pdus;

import java.nio.ByteBuffer;

/**
 * EntityCapabilities is a marker interface to polymorphize Entity Capabilities (uid 55).
 */
public interface EntityCapabilities extends Marshaller
{
    int BIT_LENGTH = 32;
    int BYTE_LENGTH = (BIT_LENGTH + Byte.SIZE - 1) / Byte.SIZE;

    class GenericCapabilityBitSet extends DisBitSet implements EntityCapabilities {
        public GenericCapabilityBitSet() {
            super(BIT_LENGTH);
        }
    }

    static int unmarshallRawValue(ByteBuffer byteBuffer) throws Exception
    {
        byte[] bytes = new byte[BYTE_LENGTH];
        byteBuffer.get(bytes);
        return DisBitSet.bytesToInt(bytes);
    }

    static void marshallRawValue(int rawValue, ByteBuffer byteBuffer) throws Exception
    {
        byte[] bytes = DisBitSet.intToBytes(rawValue, BYTE_LENGTH);
        byteBuffer.put(bytes);
    }

    static int getByteLength() {
        return BYTE_LENGTH;
    }

}
