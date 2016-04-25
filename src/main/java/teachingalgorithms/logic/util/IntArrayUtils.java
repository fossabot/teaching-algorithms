package teachingalgorithms.logic.util;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class IntArrayUtils.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class IntArrayUtils {

    /**
     * Copy array.
     *
     * @param src the src
     * @return the int[]
     */
    public static int[] copyArray(int[] src) {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    /**
     * Returns a copy of the array with a new int-value added at its end.
     *
     * @param src        the source
     * @param number2add the number2add
     * @return the int[]
     */
    public static int[] copyAdd2IntArray(int[] src, int number2add) {
        int[] dest = new int[src.length + 1];
        System.arraycopy(src, 0, dest, 0, src.length);
        dest[dest.length - 1] = number2add;
        return dest;
    }

}
