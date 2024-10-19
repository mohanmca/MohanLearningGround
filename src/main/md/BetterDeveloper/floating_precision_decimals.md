## What is the difference between BigEndian and LittleEndian
1. Order in which msb are stored is different between two
2. Higher significant stored in lower order bytes, hence they travel first in network
3. BigEndian is less commmon and LittleIndian is more widely used on ARM/Intel on MAC/Linux
4. PowerPC and Spark processor uses BigEndian
5. Java.class file and CD-ROW, Mp3 and JPEG also uses BigEndian

## Algorithms uses bigEndian
1. MD5
2. Sha-1

## In Avro, Hive - what is precision and scale
1. Precision refers to the total number of digits that can be stored, regardless of the decimal point's position.
2. Scale indicates the number of digits that can be stored to the right of the decimal point. The scale must be less than or equal to the precision
3. DECIMAL(precision, scale)

## Avro - decimal type
1. Avro type for decimal is specified as bytes. (It has underlying type and logicaltype)
2. Logical Type: This is set to "decimal".

## How is BigInteger is saved internall?
1. let us say N = 2**70+5 == 1180591620717411303425 == 1000000000000000000000000000000000000000000000000000000000000000101
2. N would be split into 32-bit chunks {10000000000000000000000000000000 (32 bits), 00000000000000000000000000000000 (32 bits), 00000000000000000000000000000101 (32 bits)} 
3. int[] value = { 5, 0, 1073741824 };  // base-2^32 chunks
4. signum = 1
5. ```java
  // Define the large number 2^70 + 5
        BigInteger bigInt = BigInteger.valueOf(2).pow(70).add(BigInteger.valueOf(5));
        // Print the value
        System.out.println("BigInteger value: " + bigInt);        
        // Print the internal representation
        System.out.println("BigInteger internal array: " + java.util.Arrays.toString(bigInt.toByteArray()));
```
6. [0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x05] //Buffere representation, 0x40 ~= 64xx``x``

## How to store 128 BigDecimal in java
```
    import java.nio.*;

    // toDecimal(1958l, 0l, (short) -3).equals(  new BigDecimal("1.958"))

    public static BigDecimal toDecimal(long mantissaLow, long mantissaHigh, short exponent) {        
        var buffer = ByteBuffer.allocate(128).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(0, mantissaLow);
        buffer.putLong(8, mantissaHigh);
        byte[] mantissaBytes = buffer.array();
        reverse(mantissaBytes);
        BigInteger mantissa = new BigInteger(1, mantissaBytes);
        return new BigDecimal(mantissa, (int)-exponent);
    }


    public static void reverse(byte[] array) {
        int left = 0, right = array.length - 1;        
        while (left < right) {
            // Swap elements at left and right indices
            byte temp = array[left];
            array[left++] = array[right];
            array[right--] = temp;            
        }
    }
```



## How to create MD anki
mdanki src/main/md/BetterDeveloper/daily_practice.md dp.apkg --deck "Mohan::DeepWork::Java::BigDecimal"
