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
    class GenericCapabilityBitSet extends DisBitSet implements EntityCapabilities {
        public GenericCapabilityBitSet() {
            super(32);
        }
    }

    /**
     * Unpacks a Pdu into a map from the underlying data.
     * @throws java.nio.BufferUnderflowException if byteBuffer is too small
     * @see java.nio.ByteBuffer
     * @see <a href="https://en.wikipedia.org/wiki/Marshalling_(computer_science)" target="_blank">https://en.wikipedia.org/wiki/Marshalling_(computer_science)</a>
     * @param byteBuffer The ByteBuffer at the position to begin reading
     * @return marshalled serialized size in bytes
     * @throws Exception ByteBuffer-generated exception
     */
    static PduMap fromBufferToMap(ByteBuffer byteBuffer) throws Exception
    {
        PduMap map = new PduMap();

        // This is so dirty
        GenericCapabilityBitSet capabilities = new GenericCapabilityBitSet();
        capabilities.unmarshal(byteBuffer);
        map.put("entityCapabilities", capabilities);

        return map;
    }

    /**
     * Packs a Pdu represented in map into the ByteBuffer.
     * @throws java.nio.BufferOverflowException if byteBuffer is too small
     * @throws java.nio.ReadOnlyBufferException if byteBuffer is read only
     * @see java.nio.ByteBuffer
     * @param byteBuffer The ByteBuffer at the position to begin writing
     * @throws Exception ByteBuffer-generated exception
     */
    static void fromMapToBuffer(PduMap map, ByteBuffer byteBuffer) throws Exception
    {
        ((EntityCapabilities) map.get("entityCapabilities")).marshal(byteBuffer);
    }

    /**
     * Returns size of this serialized (marshalled) object in bytes
     * @see <a href="https://en.wikipedia.org/wiki/Marshalling_(computer_science)" target="_blank">https://en.wikipedia.org/wiki/Marshalling_(computer_science)</a>
     * @return serialized size in bytes
     */
    static int getMarshalledSize(PduMap map) {
        return ((EntityCapabilities) map.get("entityCapabilities")).getMarshalledSize();
    }

}
