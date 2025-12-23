package edu.nps.moves.dis7.pdus;

import java.util.LinkedHashMap;
import com.google.common.primitives.*;
import java.util.List;

/**
 * Wrapper used to create mapping structure when marshalling / unmarshalling map style PDU to and from byteBuffers.
 * <br>
 * <br>
 * {@link PduMap#values()} should be instances of following types:
 * <br> - Another layer in structure -> {@link PduMap}
 * <br> - List of records etc (OBJECT_LIST) -> {@link List}<{@link PduMap} / {@link Integer}>
 * <br> - {@link Enum} -> {@link Integer}
 * <br> - {@link DisBitSet} -> {@link Integer}
 * <br> - uint8 -> {@link Integer}
 * <br> - uint16 -> {@link Integer}
 * <br> - uint32 -> {@link UnsignedInteger}
 * <br> - uint64 -> {@link UnsignedLong}
 * <br> - int8 -> {@link Byte}
 * <br> - int16 -> {@link Short}
 * <br> - int32 -> {@link Integer}
 * <br> - int64 -> {@link Long}
 * <br> - float32 -> {@link Float}
 * <br> - float64 -> {@link Double}
 * <br> - primitive list -> byte[] / short[] / ...
 */
public class PduMap extends LinkedHashMap<String, Object> {
}
