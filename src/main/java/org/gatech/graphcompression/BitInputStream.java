/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

import java.io.IOException;
import java.io.InputStream;


public class BitInputStream extends InputStream{  

  private final static int EOS = -1;
  private InputStream in;
  private int buffer;
  private int bufferPtr;


  public BitInputStream(InputStream in){
    this.in = in;
    bufferPtr = -1;
  }

  /* (non-Javadoc)
   * @see java.io.InputStream#read()
   */
  @Override
  public int read() throws IOException {
    int bit = -1;
    if(bufferPtr < 0){
      buffer = in.read();
      bufferPtr = BitUtils.BITS_PER_BYTE - 1;
    }
    bit = (buffer == EOS)? EOS: getBit(buffer, bufferPtr);
    bufferPtr--;
    return bit;
  }
  
  public int readByte() throws IOException {
    int result = 0;
    for(int i = 7; i >= 0; i--) {
      int bit = read();
      result = result * 2 + bit;
    }
    return result;
  }
  
  public int readInt() throws IOException {
    int result = 0;
    for(int i = 31; i >= 0; i--) {
      int bit = read();
      result = result * 2 + bit;
    }
    return result;
  }

  /**
   * Gets the nth bit from the right. 
   */
  private int getBit(int buffer, int n){
    return ((1 << n) & buffer) >> n;
  }

  @Override
  public void close() throws IOException{
    in.close();
  }
}