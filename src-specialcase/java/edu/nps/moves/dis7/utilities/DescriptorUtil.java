package edu.nps.moves.dis7.utilities;

import com.google.common.primitives.UnsignedLong;
import edu.nps.moves.dis7.pdus.*;

import java.nio.ByteBuffer;

/**
 * Helpers for {@link Descriptor}
 */
public class DescriptorUtil {

    /**
     * Creates a {@link Descriptor}
     */
    public static Descriptor descriptor(EntityType munitionType, MunitionDescriptorFields fields) throws Exception {
        return createDescriptor(munitionType, fields);
    }

    /**
     * Creates a {@link Descriptor}
     */
    public static Descriptor descriptor(EntityType explodingObjectType, ExplosionDescriptorFields fields) throws Exception {
        return createDescriptor(explodingObjectType, fields);
    }

    /**
     * Creates a {@link Descriptor}
     */
    public static Descriptor descriptor(EntityType expendableType, ExpendableDescriptorFields fields) throws Exception {
        return createDescriptor(expendableType, fields);
    }

    private static Descriptor createDescriptor(EntityType entityType, Marshaller fields) throws Exception {
        Descriptor descriptor = new Descriptor();
        descriptor.setEntityType(entityType);
        ByteBuffer buffer = ByteBuffer.allocate(fields.getMarshalledSize());
        fields.marshal(buffer);
        buffer.rewind();
        descriptor.setDescriptorRecordFields(UnsignedLong.fromLongBits(buffer.getLong()));
        return descriptor;
    }

    /**
     * Returned object is an instance of {@link MunitionDescriptorFields} or {@link ExpendableDescriptorFields}
     * depending on what is indicated by PDU status.
     *
     * @param pdu
     * @return
     * @throws Exception
     */
    public static Object getDescriptorSpecificFields(FirePdu pdu) throws Exception {
        byte status = pdu.getPduStatus().getValue();
        long rawFieldsValue = pdu.getDescriptor().getDescriptorRecordFields().longValue();

        if ((status & PduStatus.FTI_EXPENDABLE) != 0) {
            return unmarshall(new ExpendableDescriptorFields(), rawFieldsValue);
        }
        else {
            return unmarshall(new MunitionDescriptorFields(), rawFieldsValue);
        }
    }

    /**
     * Returned object is an instance of {@link MunitionDescriptorFields}, {@link ExpendableDescriptorFields} or
     * {@link ExplosionDescriptorFields} depending on what is indicated by PDU status.
     *
     * @param pdu
     * @return
     * @throws Exception
     */
    public static Object getDescriptorSpecificFields(DetonationPdu pdu) throws Exception {
        byte status = pdu.getPduStatus().getValue();
        long rawFieldsValue = pdu.getDescriptor().getDescriptorRecordFields().longValue();

        if ((status & PduStatus.DTI_NON_MUNITION_EXPLOSION) != 0) {
            return unmarshall(new ExplosionDescriptorFields(), rawFieldsValue);
        }
        else if ((status & PduStatus.DTI_EXPENDABLE) != 0) {
            return unmarshall(new ExpendableDescriptorFields(), rawFieldsValue);
        }
        else {
            return unmarshall(new MunitionDescriptorFields(), rawFieldsValue);
        }
    }


    private static Object unmarshall(Marshaller fieldsInstance, long fieldsValue) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(fieldsValue);
        buffer.rewind();
        fieldsInstance.unmarshal(buffer);
        return fieldsInstance;
    }
}
