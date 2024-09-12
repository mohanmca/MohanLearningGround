## What is the difference between BigEndian and LittleEndian
1. Order in which msb are stored is different between two
2. Higher significant stored in lower order bytes, hence they travel first in network
3. BigEndian is less commmon and LittleIndian is more widely used on ARM/Intel on MAC/Linux
4. PowerPC and Spark processor uses BigEndian
5. Java.class file and CD-ROW, Mp3 and JPEG also uses BigEndian

## Algorithms uses bigEndian
1. MD5
2. Sha-1

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
