/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;


import java.util.Arrays;

/**
 * Common math routines.
 */
public class Maths{

  /**
   * Returns the big-endian binary representation of an integer. 
   */
  public static String getBinaryRepr(int n){
    String bin = "";
    int x = 1;
    for(int i = 0; i < 32; i++){      
      bin = (((x << i) & n) == (x << i)? 1: 0) + bin; 
    }
    return bin;
  }
  
  public static String getBinaryRepr(byte n){
    String bin = "";
    int x = 1;
    for(int i = 0; i < 8; i++){      
      bin = (((x << i) & n) == (x << i)? 1: 0) + bin; 
    }
    return bin;    
  }
  
  /**
   * Returns the binary representation without the leading zeros
   */
  public static String getReducedBinaryRepr(int n) {
    if(n == 0) {
      return "0";
    }
    String bin = getBinaryRepr(n);
    int i = 0;
    for(; i < bin.length() && bin.charAt(i) == '0'; i++);
    return bin.substring(i);
  }
}