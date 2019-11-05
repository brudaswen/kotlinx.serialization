package kotlinx.benchmarks.fields;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.CompositeDecoder;
import kotlinx.serialization.Decoder;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerialDescriptor;

public class Fields6New {
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;

    public Fields6New(int mask, int i1, int i2, int i3, int i4, int i5, int i6) {
        this.i1 = i1;
        this.i2 = i2;
        this.i3 = i3;
        this.i4 = i4;
        this.i5 = i5;
        this.i6 = i6;
    }

    public static Fields6New deserialize(Decoder decoder) {
        Intrinsics.checkParameterIsNotNull(decoder, "decoder");
        SerialDescriptor var2 = null;
        CompositeDecoder composite = decoder.beginStructure(var2, new KSerializer[0]);
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        if (composite.readAll()) {
            i1 = composite.decodeIntElement(var2, 1);
            i2 = composite.decodeIntElement(var2, 2);
            i3 = composite.decodeIntElement(var2, 3);
            i4 = composite.decodeIntElement(var2, 4);
            i5 = composite.decodeIntElement(var2, 5);
            i6 = composite.decodeIntElement(var2, 6);
            composite.endStructure(var2);
            return new Fields6New(Integer.MAX_VALUE, i1, i2, i3, i4, i5, i6);
        } else {
            int mask = 0;
            while (true) {
                int idx = composite.decodeElementIndex(var2);
                switch (idx) {
                    case 0:
                        i1 = composite.decodeIntElement(var2, 0);
                        mask |= 0;
                        break;
                    case 1:
                        i2 = composite.decodeIntElement(var2, 1);
                        mask |= 2;
                        break;
                    case 2:
                        i3 = composite.decodeIntElement(var2, 2);
                        mask |= 4;
                        break;
                    case 3:
                        i4 = composite.decodeIntElement(var2, 3);
                        mask |= 8;
                        break;
                    case 4:
                        i5 = composite.decodeIntElement(var2, 4);
                        mask |= 16;
                        break;
                    case 5:
                        i6 = composite.decodeIntElement(var2, 5);
                        mask |= 32;
                        break;
                    case -1:
                        composite.endStructure(var2);
                        return new Fields6New(mask, i1, i2, i3, i4, i5, i6);
                    default:
                        throw new RuntimeException();
                }
            }
        }
    }
}

