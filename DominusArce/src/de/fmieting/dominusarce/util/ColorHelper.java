/**
 * 
 */
package de.fmieting.dominusarce.util;

/**
 * @author fmieting
 * 
 */
public class ColorHelper {

	public static int mul(int color, double factor) {
		return muladd(color, 0, -1 * factor, 0);
	}

	public static int add(int color, int offset) {
		return muladd(color, 0, -1.0, offset);
	}

	public static int muladd(int color, int maxval, double factor, int offset) {
		int result = 0, buf = 0;
		buf = ((int) (maxval - ((color & 0xff000000) >> 24) * factor + offset) << 24);
		result |= (buf >= 0 ? buf : 0);

		buf = ((int) (maxval - ((color & 0x00ff0000) >> 16) * factor + offset) << 16);
		result |= (buf >= 0 ? buf : 0);

		buf = ((int) (maxval - ((color & 0x0000ff00) >> 8) * factor + offset) << 8);
		result |= (buf >= 0 ? buf : 0);

		buf = ((int) (maxval - ((color & 0x000000ff) >> 0) * factor + offset) << 0);
		result |= (buf >= 0 ? buf : 0);

		return result;
	}
}
