/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

public class BitUtils {
  public final static int BITS_PER_BYTE = 8;
  
  /**
   * Returns the nth byte in an integer x.
   * n <= 3
   */
  public static int getByte(int x, int n){
    assert(n <= 3);
    int s = BITS_PER_BYTE * n;
    int t = (1<< (BITS_PER_BYTE -1));
    t = t | t-1;
    t = t << s;
    x = x & t;
    x = x >> s;
    return x;
  }
  
  /**
   * Sets the nth byte in an integer x to b.
   * The rest of the bytes remain the same.
   */
  public static int setByte(int x, int n, int b){
    assert(n<=3);
    assert(b<=255);
    int s = BITS_PER_BYTE * n;
    int t = (1 << (BITS_PER_BYTE-1));
    t = t | t-1;
    t = t << s;
    t = ~t;
    x = x & t;
    x = x | (b<<s);
    return x;
  }  
}