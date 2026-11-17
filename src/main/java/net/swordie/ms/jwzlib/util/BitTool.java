/*
	This file is part of JWzLib: MapleStory WZ File Parser
	Copyright (C) 2019  Brenterino <brent@zygon.dev>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.swordie.ms.jwzlib.util;

/**
 *
 * @author Brenterino
 */
public final class BitTool {

	private BitTool() {
	}

	/**
	 * Converts the first two bytes to
	 * a short.
	 * @param in in input array.
	 * @return a short.
	 */
	public static short toInt16(byte[] in) {
		return (short) (((in[1] & 0xFF) << 8) | (in[0] & 0xFF));
	}

	/**
	 * Converts the first four bytes to
	 * an int-32.
	 * @param in in input array.
	 * @return an int-32.
	 */
	public static int toInt32(byte[] in) {
		return ((in[3] & 0xFF) << 24) | ((in[2] & 0xFF) << 16) | ((in[1] & 0xFF) << 8) | (in[0] & 0xFF);
	}

	/**
	 * Converts the first eight bytes to
	 * a long.
	 * @param in in input array.
	 * @return a long.
	 */
	public static long toInt64(byte[] in) {
		return ((long) (in[7] & 0xFF) << 56) | ((long) (in[6] & 0xFF) << 48) | ((long) (in[5] & 0xFF) << 40) | ((long) (in[4] & 0xFF) << 32)
				| ((long) (in[3] & 0xFF) << 24) | ((in[2] & 0xFF) << 16) | ((in[1] & 0xFF) << 8) | (in[0] & 0xFF);
	}
}
