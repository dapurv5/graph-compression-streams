/**
 *  Copyright (c) 2013 Apurv Verma
 */
package org.gatech.graphcompression;

import java.io.IOException;
import java.io.OutputStream;


public class BitOutputStream extends OutputStream{
  
  private int buffer = 0;
  private int bufferPtr = BitUtils.BITS_PER_BYTE - 1;
  private OutputStream out;
  
  public BitOutputStream(OutputStream out){
    this.out = out;
        ;
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#write(int)
   */
  @Deprecated
  public void write(int b) throws IOException {
    writeBit(b);
  }
  
  public void writeBit(int b) throws IOException{
    buffer = setBit(buffer, bufferPtr, b);
    bufferPtr--;
    if(bufferPtr < 0){
      out.write(buffer);
      buffer = 0;
      bufferPtr = BitUtils.BITS_PER_BYTE - 1;
    }
  }
  
  public void writeBits(int...A) throws IOException{
    for(int a:A){
      writeBit(a);
    }
  }
  
  public void writeByte(int b) throws IOException {
    for(int i = 7; i >= 0; i--) {
      int bit = getBit(b, i);
      writeBit(bit);
    }
  }
  
  public void writeInt(int b) throws IOException {
    for(int i = 31; i >= 0; i--) {
      int bit = getBit(b, i);
      writeBit(bit);
    }
  }
  
  /**
   * Gets the nth bit from the right. 
   */
  private int getBit(int buffer, int n){
    return ((1 << n) & buffer) >> n;
  }
  
  /**
   * Sets the nth bit from right.
   */
  private int setBit(int x, int n, int flag){
    int mask = ~(1 << n);
    return (x & mask) | (flag << n);    
  }
  
  public void flush() throws IOException{
    if(bufferPtr != BitUtils.BITS_PER_BYTE-1){
      out.write(buffer);
    }
  }
  
  @Override
  public void close() throws IOException{
    flush();
    out.close();
  }  
}