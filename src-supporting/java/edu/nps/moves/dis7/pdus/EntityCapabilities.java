/**
 * Copyright (c) 2008-2023, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */

package edu.nps.moves.dis7.pdus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * EntityCapabilities is a marker interface to polymorphize Entity Capabilities (uid 55).
 */
public interface EntityCapabilities extends Marshaller
{
    class GenericCapabilityBitSet extends DisBitSet {
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
    static Map<String, Object> fromBufferToMap(java.nio.ByteBuffer byteBuffer) throws Exception
    {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        try
        {
            // This is so dirty
            GenericCapabilityBitSet capabilities = new GenericCapabilityBitSet();
            capabilities.unmarshal(byteBuffer);
            map.put("entityCapabilities", capabilities);
        }
        catch (java.nio.BufferUnderflowException bue)
        {
            System.err.println("*** buffer underflow error while unmarshalling EntityCapabilities data.");
        }
        return map;
    }

}
