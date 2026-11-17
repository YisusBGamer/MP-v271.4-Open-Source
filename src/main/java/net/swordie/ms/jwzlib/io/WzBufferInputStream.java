package net.swordie.ms.jwzlib.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Reads from bytes in memory, e.g. an image unpacked from a .ms pack.
 */
public final class WzBufferInputStream extends WzInputStream {

	private final ByteBuffer data;

	public WzBufferInputStream(byte[] bytes) {
		data = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
	}

	@Override
	public int read(int off) {
		return 0xFF & data.get(off);
	}

	@Override
	public byte readByte(int off) {
		return data.get(off);
	}

	public int length() {
		return data.capacity();
	}
}
