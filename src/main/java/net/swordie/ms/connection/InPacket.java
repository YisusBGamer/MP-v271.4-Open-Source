package net.swordie.ms.connection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.swordie.ms.connection.crypto.MapleCrypto;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;

import java.util.Arrays;

import static net.swordie.ms.util.FileTime.fromDate;

/**
 * Created on 2/18/2017.
 */
public class InPacket extends Packet {
    /** Where the second crypto block starts in a packet the client sent with a 1452-byte first block. */
    public static final int SHORT_FIRST_BLOCK = 1452;
    private ByteBuf byteBuf;
    private boolean loopback;
    private byte[] cipherCopy;
    private byte[] cipherIv;
    private MapleCrypto cipherCrypto;
    /**
     * Creates a new InPacket with a given buffer.
     * @param byteBuf The buffer this InPacket has to be initialized with.
     */
    public InPacket(ByteBuf byteBuf) {
        super(byteBuf.array());
        this.byteBuf = byteBuf.copy();
    }

    /**
     * Creates a new InPacket with no data.
     */
    public InPacket(){
        this(Unpooled.buffer());
    }

    /**
     * Creates a new InPacket with given data.
     * @param data The data this InPacket has to be initialized with.
     */
    public InPacket(byte[] data) {
        this(Unpooled.copiedBuffer(data));
    }

    /** Keeps the encrypted bytes of a long packet, for {@link #useShortFirstBlock()}. */
    public void keepCipher(byte[] cipher, byte[] iv, MapleCrypto crypto) {
        cipherCopy = cipher;
        cipherIv = iv;
        cipherCrypto = crypto;
    }

    /**
     * Decrypts everything from byte 1452 on again as sent with a 1452-byte first block (v271 "Skill System" attacks);
     * the bytes before that are the same either way.
     */
    public boolean useShortFirstBlock() {
        if (cipherCopy == null || cipherCopy.length <= SHORT_FIRST_BLOCK) {
            return false;
        }
        byte[] dec = cipherCrypto.crypt(cipherCopy, cipherIv, SHORT_FIRST_BLOCK);
        byteBuf.setBytes(SHORT_FIRST_BLOCK, dec, SHORT_FIRST_BLOCK, dec.length - SHORT_FIRST_BLOCK);
        cipherCopy = null;
        return true;
    }

    @Override
    public int getLength() {
        return byteBuf.readableBytes();
    }

    @Override
    public byte[] getData() {
        return byteBuf.array();
    }

    @Override
    public InPacket clone() {
        return new InPacket(byteBuf);
    }

    /**
     * Reads a single byte of the ByteBuf.
     * @return The byte that has been read.
     */
    public byte decodeByte() {
        return byteBuf.readByte();
    }

    public short decodeUByte() {
            return byteBuf.readUnsignedByte();
    }

    /**
     * Reads an <code>amount</code> of bytes from the ByteBuf.
     * @param amount The amount of bytes to read.
     * @return The bytes that have been read.
     */
    public byte[] decodeArr(int amount) {
        byte[] arr = new byte[amount];
        for(int i = 0; i < amount; i++) {
            arr[i] = byteBuf.readByte();
        }
        return arr;
    }

    /**
     * Reads an integer from the ByteBuf.
     * @return The integer that has been read.
     */
    public int decodeInt() {
        return byteBuf.readIntLE();
    }

    /**
     * Reads a short from the ByteBuf.
     * @return The short that has been read.
     */
    public short decodeShort() {
        return byteBuf.readShortLE();
    }

    /**
     * Reads a char array of a given length of this ByteBuf.
     * @param amount The length of the char array
     * @return The char array as a String
     */
    public String decodeString(int amount) {
        byte[] bytes = decodeArr(amount);
        char[] chars = new char[amount];
        for(int i = 0; i < amount; i++) {
            chars[i] = ClientText.decode(bytes[i]);
        }
        return String.valueOf(chars);
    }

    /**
     * Reads a String, by first reading a short, then reading a char array of that length.
     * @return The char array as a String
     */
    public String decodeString() {
        int amount = decodeShort();
        return decodeString(amount);
    }

    @Override
    public String toString() {
        return Util.readableByteArray(Arrays.copyOfRange(getData(), getData().length - getUnreadAmount(), getData().length)); // Substring after copy of range xd
    }


    /**
     * Reads and returns a long from this net.swordie.ms.connection.packet.
     * @return The long that has been read.
     */
    public long decodeLong() {
        return byteBuf.readLongLE();
    }

    public double decodeDouble() {
        return Double.longBitsToDouble(decodeLong());
    }

    public FileTime decodeFT(){
        long clientTime =  byteBuf.readLongLE();
        return new FileTime(FileTime.fromClientFileTime(clientTime));
    }
    /**
     * Reads a position (short x, short y) and returns this.
     * @return The position that has been read.
     */
    public Position decodePosition() {
        return new Position(decodeShort(), decodeShort());
    }

    /**
     * Reads a position (int x, int y) and returns this.
     * @return The position that has been read.
     */
    public Position decodePositionInt() {
        return new Position(decodeInt(), decodeInt());
    }

    /**
     * Reads a rectangle (short l, short t, short r, short b) and returns this.
     * @return The rectangle that has been read.
     */
    public Rect decodeShortRect() {
        return new Rect(decodePosition(), decodePosition());
    }

    /**
     * Reads a rectangle (int l, int t, int r, int b) and returns this.
     * @return The rectangle that has been read.
     */
    public Rect decodeIntRect() {
        return new Rect(decodePositionInt(), decodePositionInt());
    }


    /**
     * Returns the amount of bytes that are unread.
     * @return The amount of bytes that are unread.
     */
    public int getUnreadAmount() {
        return byteBuf.readableBytes();
    }

    public void skip(int n) {
        byteBuf.skipBytes(n);
    }

    /**
     * v271 puts a salt in front of a few requests (USER_RUN_SCRIPT 836, MOB_REQUEST 1351, 1105, 1355; client
     * 0x140e3bd30): int seed, then (mt19937(seed) % 10) + 1 random bytes. Skips it when at least {@code minBody}
     * bytes follow and the short after it is at most {@code maxFirstShort}; otherwise leaves the packet as it was
     * (bots and older clients send no salt).
     */
    public boolean skipClientSalt(int minBody, int maxFirstShort) {
        int start = byteBuf.readerIndex();
        if (byteBuf.readableBytes() < 5 + minBody) {
            return false;
        }
        int n = (int) (Integer.toUnsignedLong(mt19937First(byteBuf.getIntLE(start))) % 10) + 1;
        int body = byteBuf.readableBytes() - 4 - n;
        if (body < minBody || (body >= 2 && (byteBuf.getShortLE(start + 4 + n) & 0xFFFF) > maxFirstShort)) {
            return false;
        }
        byteBuf.readerIndex(start + 4 + n);
        return true;
    }

    /** The first output of std::mt19937 seeded with {@code seed}. */
    private static int mt19937First(int seed) {
        int[] mt = new int[624];
        mt[0] = seed;
        for (int i = 1; i < 624; i++) {
            mt[i] = 0x6c078965 * (mt[i - 1] ^ (mt[i - 1] >>> 30)) + i;
        }
        int y = (mt[0] & 0x80000000) | (mt[1] & 0x7fffffff);
        y = mt[397] ^ (y >>> 1) ^ ((y & 1) != 0 ? 0x9908b0df : 0);
        y ^= y >>> 11;
        y ^= (y << 7) & 0x9d2c5680;
        y ^= (y << 15) & 0xefc60000;
        y ^= y >>> 18;
        return y;
    }

    public void release() {
        byteBuf.release();
    }

    public boolean isLoopback() {
        return loopback;
    }

    public void setLoopback(boolean loopback) {
        this.loopback = loopback;
    }

    public byte[] decodeEncryptedData() {
        int key = decodeInt();
        int size = decodeInt();
        byte[] buffer = decodeArr(size);

        byte[] resBuf = new byte[buffer.length];

        int i = 0;
        for (; i < size - (size % 4); i += 4) {
            byte[] curInt = new byte[4];
            System.arraycopy(buffer, i, curInt, 0, 4);

            int toInt = Util.toInt(curInt);
            toInt ^= key;
            toInt += 0x369F144D;
            toInt += key >>> 7;
            toInt ^= 0xAAAABBBB;

            resBuf[i] = (byte) (toInt & 0xFF);
            resBuf[i + 1] = (byte) ((toInt >>> 8) & 0xFF);
            resBuf[i + 2] = (byte) ((toInt >>> 16) & 0xFF);
            resBuf[i + 3] = (byte) ((toInt >>> 24) & 0xFF);
        }

        for (; i < size; i++) {
            byte curByte = buffer[i];
            curByte ^= key >>> 1;
            curByte += 55;
            curByte += key >>> 7;
            curByte ^= 0xAB;

            resBuf[i] = curByte;
        }

        return resBuf;
    }
}
