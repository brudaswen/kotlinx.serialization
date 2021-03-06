package kotlinx.serialization.features

import kotlinx.serialization.*
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class BinaryPayloadExampleTest {
    @Serializable
    class BinaryPayload(val req: ByteArray, val res: ByteArray) {
        @Serializer(forClass = BinaryPayload::class)
        companion object : KSerializer<BinaryPayload> {
            override val descriptor: SerialDescriptor = object : SerialClassDescImpl("BinaryPayload") {
                init {
                    addElement("req")
                    addElement("res")
                }
            }

            override fun serialize(encoder: Encoder, obj: BinaryPayload) {
                val compositeOutput = encoder.beginStructure(descriptor)
                compositeOutput.encodeStringElement(descriptor, 0, HexConverter.printHexBinary(obj.req))
                compositeOutput.encodeStringElement(descriptor, 1, HexConverter.printHexBinary(obj.res))
                compositeOutput.endStructure(descriptor)
            }

            override fun deserialize(decoder: Decoder): BinaryPayload {
                val dec: CompositeDecoder = decoder.beginStructure(descriptor)
                var req: ByteArray? = null // consider using flags or bit mask if you
                var res: ByteArray? = null // need to read nullable non-optional properties
                loop@ while (true) {
                    when (val i = dec.decodeElementIndex(descriptor)) {
                        CompositeDecoder.READ_DONE -> break@loop
                        0 -> req = HexConverter.parseHexBinary(dec.decodeStringElement(descriptor, i))
                        1 -> res = HexConverter.parseHexBinary(dec.decodeStringElement(descriptor, i))
                        else -> throw SerializationException("Unknown index $i")
                    }
                }
                dec.endStructure(descriptor)
                return BinaryPayload(
                    req ?: throw MissingFieldException("req"),
                    res ?: throw MissingFieldException("res")
                )
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as BinaryPayload

            if (!req.contentEquals(other.req)) return false
            if (!res.contentEquals(other.res)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = req.contentHashCode()
            result = 31 * result + res.contentHashCode()
            return result
        }
    }

    @Test
    fun payloadEquivalence() {
        val payload1 = BinaryPayload(byteArrayOf(0, 0, 0), byteArrayOf(127, 127))
        val s = Json.stringify(BinaryPayload.serializer(), payload1)
        val payload2 = Json.parse(BinaryPayload.serializer(), s)
        assertEquals(payload1, payload2)
    }
}
