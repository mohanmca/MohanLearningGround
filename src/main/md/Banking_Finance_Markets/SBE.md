## [Simple Binary Encoding](https://aeron.io/docs/simple-binary-encoding/basic-sample/)
1. SBE is build around flyweight pattern, it is all about reuse object to reduce memory pressure on JVM.
2. Class generated by SbeTool (we call them Flyweight)
3. SBE flyweights behaves a bit like a stencil, you position it over a wall (byte array) at the right place (offset) and then you can paint (encode) very quickly!
4. SBE is using array as underlying storage and fields are packed in it.

## What is basic Example in SBE
1. [Basic Sample](https://aeron.io/docs/simple-binary-encoding/basic-sample/)

## Order of encoding
1. Within the fields section, fields are encoded in the order specified by schema.
2. Then repeating groups, again in the order specified in the schema.
3. Finally variable length fields, in the order specified by the schema.

## Order of encoding - API   
1. Developer encode and decode in the order specified by the schema. Failing to do so could at best reduce performance, at worst return invalid data during decoding or corrupt data in the buffer during encoding.
1. API might let encode and decode out of order. But there are plans to improve that and throw errors if detected an invalid sequence
1. That constraint helps simplifying the flyweight design and make it more hardware friendly.

## Encoder / Decoder
1. Encoder/Decoder does no allocation or very less(i.e in case of String).
2. SBE recommends to use direct/offheap buffer to take GC completely out of picture.
3. Buffer can be allocated at thread level and can be used for decoding and encoding of message.
4. Decoder has to know very little metadata about message(i.e offset and size).

## What are 5 fields related to MessageHeaderEncoder/Decoder
1. static ByteOrder	BYTE_ORDER 
1. static int	ENCODED_LENGTH 
1. static int	SCHEMA_ID 
1. static int	SCHEMA_VERSION 
1. static String	SEMANTIC_VERSION 


## SBE Flyweights vs DTO
1. SBE does not work with DTO: the flyweight writes directly to the underlying buffer during encoding and reads directly from the buffer during decoding.
2. When we write orderId = 72 in the order flyweight, what it does is encode 72 in its byte representation (which depends of the orderId primitive type and of the endianess) and store it directly in the underlying buffer.
3. Flyweights can be reused indefinitely, to encode and decode different messages. But it is not threadsafe
4. When you decode a field of one of the primitive types, nothing is allocated, it’s only a stack operation.

## How to decode array?
1. when you decode a field of type array you do not get a new array allocated and given back you:
2. Provide your own buffer (that you can reuse on your side) and the flyweight will copy data to your buffer.
3. Again, this allows your system to not allocate.
    1. Why limiting or preventing allocation? To limit or suppress GCs, which will slow down your encoding and decoding operations

## Google Protocol Buffer vs SBE
1. SBE is significantly faster, but there is a more subtle aspect:
2. GPB allocates so it will trigger GCs and slow down the overall system. This is another big advantage for SBE.

## Fast array access
1. Reading integers of different sizes from a byte array in C++ is simple:  apply an offset to your byte pointer, cast the pointer to the type you need and dereference, job done. 
2. To work around those (performance) limitations Java uses the Unsafe class, which basically perform pointer operations under the hood and gets inlined (resulting in the same assembly code than C++)

## How to access different size of integers in C++

```cpp
#include <iostream>
#include <cstdint>

int main() {
    // Example byte array (could be filled with data from a file, network, etc.)
    uint8_t byteArray[] = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
    
    // Pointer to the beginning of the array
    uint8_t* ptr = byteArray;
    
    // Read an 8-bit integer (1 byte)
    uint8_t value8 = *reinterpret_cast<uint8_t*>(ptr);
    std::cout << "8-bit value: " << static_cast<uint32_t>(value8) << std::endl;
    
    // Advance the pointer by 1 byte to read the next value
    ptr += sizeof(uint8_t);
    
    // Read a 16-bit integer (2 bytes)
    uint16_t value16 = *reinterpret_cast<uint16_t*>(ptr);
    std::cout << "16-bit value: " << value16 << std::endl;
    
    // Advance the pointer by 2 bytes to read the next value
    ptr += sizeof(uint16_t);
    
    // Read a 32-bit integer (4 bytes)
    uint32_t value32 = *reinterpret_cast<uint32_t*>(ptr);
    std::cout << "32-bit value: " << value32 << std::endl;

    return 0;
}
```

## Endianess
1. Endianess specifies the order bytes are stored for a given primitive type. Most hardware use little endian and network historically use big endian.
2. In little-endian format, the least significant byte (LSB) is stored at the smallest memory address, and the most significant byte (MSB) is stored at the largest.
3. C++ uses a macro to apply endianess, which compiles to a single x86 instruction bswap.
4. The bswap instruction on x86 architectures is a highly efficient way to swap the byte order of a 16-bit, 32-bit, or 64-bit integer, converting it between little-endian and big-endian formats.
5. Java uses integer.reverse, which gets optimized away as well as bswap.

## SBE ENcoding
1. Header
2. Block Fields
3. Repeating Group
    1. Var length fields (sub-field of repeating group)
5. Var length fields (root fields)


## When Encoded Length Isn’t Encoded Length
```java
final int encodedLength = nosEncoder.encodedLength();
final byte[] bytes = new byte[encodedLength];
buffer.getBytes(0, bytes); //why we need this?
final DirectBuffer readBuffer = new UnsafeBuffer(bytes);
wrapDecoder(headerDecoder, nosDecoder, readBuffer, 0);
System.out.println(nosDecoder);
```
1. java.lang.IndexOutOfBoundsException: index=262 length=22 capacity=276
2. We get an exception because encoder.encodedLength() excludes the header length. The whole byte array is required to decode the message, not just the body.


## How to determine the encoded length if we only have the encoded buffer?
1. Unfortunately, the decoder has to traverse to the end of the message to get the encoded length.
2. One other way is to remember the encoded length at the time that the message was encoded and pass it along with the encoded buffer as a method parameter.
3. Sample code to find encodedLength
   ```java
      skipGroup(nosDecoder.allocations(), allocDec -> {
          skipGroup(allocDec.nestedParties(), partyDec -> {
                     partyDec.nestedPartyDescription();
          });
          allocDec.allocDescription();
      });
      nosDecoder.traderDescription();
      nosDecoder.orderDescription();
      //decoder encoded length at end of message = actual encoded Length
      encodedLengthFromDecoder = headerDecoder.encodedLength() + nosDecoder.encodedLength();
   ```

## The Moving Repeating Group
1. Don't invoke same method multiple times expecting same result (it is buffer reader, and moves pointer)
2. Unless the field is a fixed length field, every field subsequent to the mutated field needs to be encoded again.
3. Remember the limit just before encoding it, then use the limit to backtrack later.
4. ```java
      //I want to change trader description later so remember the limit here
      final int limit = nosEncoder.limit();
      nosEncoder.traderDescription("TRADER-1");
      nosEncoder.orderDescription("ORDER DESC");
      nosEncoder.limit(limit);
      nosEncoder.traderDescription("TRADER-00001");
      //Everything subsequent to the above needs to be encoded again
      nosEncoder.orderDescription("ORDER DESC");
   ```

## The Semi-Forbidden Schema Evolution
1. Code that uses SBE also tends to reuse the buffers to reduce allocations.
2. Even though we don’t care about the last field, the buffer may contains some bytes from the previous message that encroaches on the new field when we encode the new message.
3. Use base64 encoder to compare SBE strings
4. ```java
      final int encodedLength = headerEncoder.encodedLength() + nosEncoder.encodedLength();
   
      final byte[] bytes = new byte[encodedLength];
      buffer.getBytes(0, bytes);
      
      final String base64EncStr = Base64.getEncoder().encodeToString(bytes);
      System.out.println(base64EncStr);
      final byte[] decoderBytes = Base64.getDecoder().decode(base64EncStr);
      final DirectBuffer decoderBuffer = new UnsafeBuffer(decoderBytes);
      wrapDecoder(headerDecoder, nosDecoder, decoderBuffer, 0);
      final String decoderToString = nosDecoder.toString();
      System.out.println(decoderToString);
   ```

## Encoding int and float value in SBE

```java
public TradeEncoder customerId(final long value)
{    buffer.putLong(offset + 8, value, java.nio.ByteOrder.LITTLE_ENDIAN);    return this;}

public void writeFloat(float v) throws IOException {
    if (pos + 4 <= MAX_BLOCK_SIZE) {        Bits.putFloat(buf, pos, v);        pos += 4;    } else {        dout.writeFloat(v);    }
}
 
public void writeLong(long v) throws IOException {
    if (pos + 8 <= MAX_BLOCK_SIZE) {        Bits.putLong(buf, pos, v);        pos += 8;    } else {        dout.writeLong(v);    }
}
 
public void writeDouble(double v) throws IOException {
    if (pos + 8 <= MAX_BLOCK_SIZE) {        Bits.putDouble(buf, pos, v);        pos += 8;    } else {        dout.writeDouble(v);    }
}
```

## Sample public SBE interface
1. [Binance-Simple Binary Encoding (SBE) FAQ](https://developers.binance.com/docs/binance-spot-api-docs/faqs/sbe_faq#how-to-get-an-sbe-response)
2. ```curl -v -sX GET -H "Accept: application/sbe" -H "X-MBX-SBE: 1:0" 'https://api.binance.com/api/v3/exchangeInfo?symbol=BTCUSDT' > output.txt```

## Reference
1. [Design principles for SBE, the ultra-low latency marshaling API](https://weareadaptive.com/2013/12/15/design-principles-for-sbe-the-ultra-low-latency-marshaling-api/)
2. [SBE Gotchas](https://github.com/tommyqqt/sbe-gotchas/tree/master)
3. [iLink 3 - Simple Binary Encoding-Owned by CME Group](https://cmegroupclientsite.atlassian.net/wiki/spaces/EPICSANDBOX/pages/46439949/iLink+3+-+Simple+Binary+Encoding)

## Generate Anki
```bash
mdanki SBE.md sbe.apkg --deck "Mohan::DeepWork::Encoding::SBE"
```
