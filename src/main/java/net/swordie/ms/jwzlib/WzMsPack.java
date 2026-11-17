package net.swordie.ms.jwzlib;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A .ms pack of the 64-bit client (Data\Packs\Mob_00000.ms, ...): an encrypted container of whole .img files.
 * <p>
 * Layout (format 4): random filler whose length comes from the file name, the version byte, a salt, then a ChaCha20
 * encrypted header (hash, entry count) and entry table (name, sizes, page offset, per-entry key). Entry data starts
 * at the next 1 KB page after the table; only the first 1 KB of each entry is encrypted. Keys are derived from the
 * lower-case file name, the salt and each entry's name and key. Unpacked images use no string key.
 */
final class WzMsPack {

	static final class Entry {
		final String name;
		final long start;
		final int size;
		final byte[] key;

		Entry(String name, long start, int size, byte[] key) {
			this.name = name;
			this.start = start;
			this.size = size;
			this.key = key;
		}

		/** @return the image name, without the pack's folder prefix (Mob/8930000.img -> 8930000.img) */
		String imageName() {
			return name.substring(name.lastIndexOf('/') + 1);
		}
	}

	private static final int[] KEY_MASK = {
			0x7B, 0x2F, 0x35, 0x48, 0x43, 0x95, 0x02, 0xB9, 0xAE, 0x91, 0xA6, 0xE1, 0xD8, 0xD6, 0x24, 0xB4,
			0x33, 0x10, 0x1D, 0x3D, 0xC1, 0xBB, 0xC6, 0xF4, 0xA5, 0xFE, 0xB3, 0x69, 0x6B, 0x56, 0xE4, 0x75
	};
	private static final int MAX_ENTRIES = 100_000;
	private static final int PAGE = 1024;

	private final Path path;
	private final ByteBuffer data;
	private final String salt;
	private final List<Entry> entries = new ArrayList<>();

	private WzMsPack(Path path, ByteBuffer data, String salt) {
		this.path = path;
		this.data = data;
		this.salt = salt;
	}

	/**
	 * @throws IOException if the file can't be read or isn't a pack this reader knows
	 */
	static WzMsPack open(Path path) throws IOException {
		ByteBuffer data;
		try (RandomAccessFile f = new RandomAccessFile(path.toFile(), "r")) {
			data = f.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f.length()).order(ByteOrder.LITTLE_ENDIAN);
		}
		String fileName = path.getFileName().toString().toLowerCase();
		int nameSum = 0;
		for (int i = 0; i < fileName.length(); i++) {
			nameSum += fileName.charAt(i);
		}
		int fillLen = nameSum % 312 + 30;
		if (data.capacity() < fillLen + 64) {
			throw new IOException("too small for a .ms pack");
		}
		int[] fill = new int[fillLen];
		for (int i = 0; i < fillLen; i++) {
			fill[i] = (data.get(i) >> 1) & 0xFF;
		}
		int pos = fillLen;
		int version = (data.get(pos++) & 0xFF) ^ fill[0];
		if (version != 4) {
			throw new IOException("pack format " + version + " (only format 4, used since v2xx, is supported)");
		}
		int saltLen = (data.get(pos) & 0xFF) ^ fill[0];
		pos += 4;
		if (saltLen <= 0 || saltLen > fillLen) {
			throw new IOException("bad salt length " + saltLen);
		}
		char[] saltChars = new char[saltLen];
		for (int i = 0; i < saltLen; i++) {
			int v = fill[i] ^ (data.get(pos + i * 2) & 0xFF);
			saltChars[i] = (char) (((v | 0x4B) << 1) - v - 75);
		}
		pos += saltLen * 2;
		WzMsPack pack = new WzMsPack(path, data, new String(saltChars));
		String keyText = fileName + pack.salt;

		byte[] headerKey = new byte[32];
		for (int i = 0; i < 32; i++) {
			headerKey[i] = (byte) ((keyText.charAt(i % keyText.length()) + i) ^ KEY_MASK[i]);
		}
		byte[] header = pack.bytes(pos, 8);
		new ChaCha20(headerKey, new byte[12], 0).apply(header, 0, header.length);
		ByteBuffer hb = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);
		hb.getInt(); // hash
		int count = hb.getInt();
		if (count < 0 || count > MAX_ENTRIES || count > (data.capacity() - pos) / 56) {
			throw new IOException("bad entry count " + count);
		}
		long tableStart = pos + 8 + (nameSum * 3 % 212 + 64);

		byte[] tableKey = new byte[32];
		for (int i = 0; i < 32; i++) {
			int c = keyText.charAt(keyText.length() - 1 - i % keyText.length());
			tableKey[i] = (byte) ((i + (i % 3 + 2) * c) ^ KEY_MASK[i]);
		}
		TableReader table = pack.new TableReader(tableKey, tableStart);
		List<long[]> raw = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			int nameLen = table.readInt();
			if (nameLen < 0 || nameLen > 4096) {
				throw new IOException("bad entry name length " + nameLen);
			}
			String name = new String(table.read(nameLen * 2), StandardCharsets.UTF_16LE);
			table.readInt(); // checksum
			table.readInt(); // flags
			int startPage = table.readInt();
			int size = table.readInt();
			int sizeAligned = table.readInt();
			table.readInt();
			table.readInt();
			byte[] entryKey = table.read(16);
			table.readInt();
			table.readInt();
			if (startPage < 0 || size < 0 || sizeAligned < size) {
				throw new IOException("bad entry " + name);
			}
			pack.entries.add(new Entry(name, startPage, size, entryKey));
		}
		long dataStart = (table.end() + PAGE - 1) & -PAGE;
		for (int i = 0; i < pack.entries.size(); i++) {
			Entry e = pack.entries.get(i);
			Entry placed = new Entry(e.name, dataStart + e.start * PAGE, e.size, e.key);
			if (placed.start + placed.size > data.capacity()) {
				throw new IOException("entry " + e.name + " runs past the end of the file");
			}
			pack.entries.set(i, placed);
		}
		return pack;
	}

	Path getPath() {
		return path;
	}

	List<Entry> getEntries() {
		return entries;
	}

	/** @return the entry's .img bytes, decrypted */
	byte[] read(Entry e) {
		byte[] out = bytes(e.start, e.size);
		long fnv = 0x811C9DC5L;
		for (int i = 0; i < salt.length(); i++) {
			fnv = ((fnv ^ salt.charAt(i)) * 0x01000193L) & 0xFFFFFFFFL;
		}
		String digits = Long.toString(fnv);
		int dl = digits.length();
		byte[] key = new byte[32];
		for (int j = 0; j < 32; j++) {
			int d0 = digits.charAt(j % dl) - '0';
			int d1 = digits.charAt((j + 1) % dl) - '0';
			int idx = (digits.charAt((j + 2) % dl) - '0' + j) % e.key.length;
			int c = e.name.charAt(j % e.name.length());
			key[j] = (byte) ((j + c * (d0 % 2 + (e.key[idx] & 0xFF) + (d1 + j) % 5)) ^ KEY_MASK[j]);
		}
		long half = fnv >>> 1;
		byte[] seed = new byte[12];
		ByteBuffer sb = ByteBuffer.wrap(seed).order(ByteOrder.LITTLE_ENDIAN);
		sb.putInt((int) fnv).putInt((int) half).putInt((int) (half ^ 0x6C));
		int a = 0, b = 0, c = 90, d = 0;
		for (int k = 0; k < 12; k++) {
			seed[k] ^= (byte) (d + 11 * (k / 11) + (c ^ (k >> 2)) + (a ^ b));
			d--;
			a += 8;
			b += 17;
			c += 43;
		}
		byte[] nonce = new byte[12];
		System.arraycopy(seed, 0, nonce, 4, 8);
		int counter = sb.getInt(8);
		new ChaCha20(key, nonce, counter).apply(out, 0, Math.min(out.length, PAGE));
		return out;
	}

	private byte[] bytes(long pos, int len) {
		byte[] out = new byte[len];
		data.get((int) pos, out, 0, len);
		return out;
	}

	/** The entry table's stream: its counter restarts whenever a read ends on a block boundary. */
	private final class TableReader {
		private final ChaCha20 cipher;
		private final byte[] block = new byte[64];
		private long next;
		private int used = 64;

		TableReader(byte[] key, long start) {
			cipher = new ChaCha20(key, new byte[12], 0);
			next = start;
		}

		byte[] read(int len) throws IOException {
			byte[] out = new byte[len];
			int done = 0;
			while (done < len) {
				if (used >= 64) {
					if (next + 64 > data.capacity()) {
						throw new IOException("entry table runs past the end of the file");
					}
					data.get((int) next, block, 0, 64);
					next += 64;
					cipher.apply(block, 0, 64);
					used = 0;
				}
				int n = Math.min(len - done, 64 - used);
				System.arraycopy(block, used, out, done, n);
				done += n;
				used += n;
			}
			if (used >= 64) {
				cipher.setCounter(0);
			}
			return out;
		}

		int readInt() throws IOException {
			return ByteBuffer.wrap(read(4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
		}

		long end() {
			return next;
		}
	}

	/** Plain ChaCha20 (20 rounds, 32-bit counter, 96-bit nonce). */
	static final class ChaCha20 {
		private final int[] state = new int[16];
		private final int[] work = new int[16];
		private final byte[] stream = new byte[64];

		ChaCha20(byte[] key, byte[] nonce, int counter) {
			state[0] = 0x61707865;
			state[1] = 0x3320646E;
			state[2] = 0x79622D32;
			state[3] = 0x6B206574;
			ByteBuffer kb = ByteBuffer.wrap(key).order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < 8; i++) {
				state[4 + i] = kb.getInt(i * 4);
			}
			state[12] = counter;
			ByteBuffer nb = ByteBuffer.wrap(nonce).order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < 3; i++) {
				state[13 + i] = nb.getInt(i * 4);
			}
		}

		void setCounter(int counter) {
			state[12] = counter;
		}

		/** XORs the key stream over buf[off, off + len), one fresh block per 64 bytes. */
		void apply(byte[] buf, int off, int len) {
			for (int p = 0; p < len; p += 64) {
				nextBlock();
				int n = Math.min(64, len - p);
				for (int i = 0; i < n; i++) {
					buf[off + p + i] ^= stream[i];
				}
			}
		}

		private void nextBlock() {
			System.arraycopy(state, 0, work, 0, 16);
			for (int i = 0; i < 10; i++) {
				quarter(0, 4, 8, 12);
				quarter(1, 5, 9, 13);
				quarter(2, 6, 10, 14);
				quarter(3, 7, 11, 15);
				quarter(0, 5, 10, 15);
				quarter(1, 6, 11, 12);
				quarter(2, 7, 8, 13);
				quarter(3, 4, 9, 14);
			}
			for (int i = 0; i < 16; i++) {
				int v = work[i] + state[i];
				stream[i * 4] = (byte) v;
				stream[i * 4 + 1] = (byte) (v >>> 8);
				stream[i * 4 + 2] = (byte) (v >>> 16);
				stream[i * 4 + 3] = (byte) (v >>> 24);
			}
			if (++state[12] == 0) {
				state[13]++;
			}
		}

		private void quarter(int a, int b, int c, int d) {
			int[] x = work;
			x[a] += x[b];
			x[d] = Integer.rotateLeft(x[d] ^ x[a], 16);
			x[c] += x[d];
			x[b] = Integer.rotateLeft(x[b] ^ x[c], 12);
			x[a] += x[b];
			x[d] = Integer.rotateLeft(x[d] ^ x[a], 8);
			x[c] += x[d];
			x[b] = Integer.rotateLeft(x[b] ^ x[c], 7);
		}
	}
}
