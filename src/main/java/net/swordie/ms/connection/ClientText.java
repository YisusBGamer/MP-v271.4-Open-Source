package net.swordie.ms.connection;

/**
 * Text bytes as the GMS client reads and writes them: one byte per character, Windows-1252.
 * Bytes 1252 leaves undefined (0x81, 0x8D, 0x8F, 0x90, 0x9D) map to U+0080+n so every byte survives a round trip.
 */
public final class ClientText {
    private static final char[] HIGH = {
            '€', '\u0081', '‚', 'ƒ', '„', '…', '†', '‡',
            'ˆ', '‰', 'Š', '‹', 'Œ', '\u008D', 'Ž', '\u008F',
            '\u0090', '‘', '’', '“', '”', '•', '–', '—',
            '˜', '™', 'š', '›', 'œ', '\u009D', 'ž', 'Ÿ',
    };

    private ClientText() {
    }

    public static char decode(byte b) {
        int v = b & 0xFF;
        return v >= 0x80 && v < 0xA0 ? HIGH[v - 0x80] : (char) v;
    }

    public static byte encode(char c) {
        if (c < 0x100) {
            return (byte) c;
        }
        if (c >= 0xFF80) {
            // older decoding sign-extended each byte (0xB3 -> U+FFB3); scripts still carry raw bytes that way
            return (byte) c;
        }
        for (int i = 0; i < HIGH.length; i++) {
            if (HIGH[i] == c) {
                return (byte) (0x80 + i);
            }
        }
        return '?';
    }

    /** @return true if every character is one a player can type and the client can draw (printable 1252) */
    public static boolean isPrintable(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0x20 && c < 0x7F || c >= 0xA0 && c <= 0xFF) {
                continue;
            }
            if (c < 0x100 || !isHigh(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isHigh(char c) {
        for (char h : HIGH) {
            if (h == c) {
                return true;
            }
        }
        return false;
    }
}
