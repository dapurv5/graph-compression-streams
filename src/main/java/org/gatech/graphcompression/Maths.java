/**
 *  Copyright (c) 2012 Apurv Verma
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 *  limitations under the License.
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