// This file is part of OpenTSDB.
// Copyright (C) 2017  The OpenTSDB Authors.
//
// This program is free software: you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 2.1 of the License, or (at your
// option) any later version.  This program is distributed in the hope that it
// will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
// of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
// General Public License for more details.  You should have received a copy
// of the GNU Lesser General Public License along with this program.  If not,
// see <http://www.gnu.org/licenses/>.
package net.opentsdb.utils;

import java.util.Random;

/**
 * Helper class for working with strings.
 * @since 3.0
 */
public class StringUtils {

  /** A random number generator. NOT SECURE! */
  private final static Random GENERATOR = new Random(System.currentTimeMillis());
  
  /** A simple upper and lower case basic ASCII character set. */
  private final static int[] ASCII_CHARS;
  static {
    ASCII_CHARS = new int[52];
    int c = 65;
    for (int i = 0; i < ASCII_CHARS.length; i++) {
      ASCII_CHARS[i] = c++;
      // skip carrots, braces and such.
      if (c == 90) {
        c = 97;
      }
    }
  }
  
  /**
   * Utility to generate a random string of upper and lower case basic ASCII
   * characters.
   * @param length A length for the returned string, must be greater than zero. 
   * @return A string of length characters.
   * @throws IllegalArgumentException if the string is less than 1.
   */
  public static String getRandomString(final int length) {
    if (length < 1) {
      throw new IllegalArgumentException("Length must be greater than zero.");
    }
    
    final StringBuilder buffer = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      buffer.append(
          (char) ASCII_CHARS[GENERATOR.nextInt(ASCII_CHARS.length - 1)]);
    }
    return buffer.toString();
  }

  /**
   * Optimized version of {@code String#split} that doesn't use regexps.
   * This function works in O(5n) where n is the length of the string to
   * split.
   * @param s The string to split.
   * @param c The separator to use to split the string.
   * @return A non-null, non-empty array.
   * @since 1.0
   */
  public static String[] splitString(final String s, final char c) {
    final char[] chars = s.toCharArray();
    int num_substrings = 1;
    for (final char x : chars) {
      if (x == c) {
        num_substrings++;
      }
    }
    final String[] result = new String[num_substrings];
    final int len = chars.length;
    int start = 0;  // starting index in chars of the current substring.
    int pos = 0;    // current index in chars.
    int i = 0;      // number of the current substring.
    for (; pos < len; pos++) {
      if (chars[pos] == c) {
        result[i++] = new String(chars, start, pos - start);
        start = pos + 1;
      }
    }
    result[i] = new String(chars, start, pos - start);
    return result;
  }
}
