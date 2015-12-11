/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/**
 * InputStream corresponding to {@link DifferenceEncodingOutputStream}
 */
public class DifferenceEncodingInputStream implements GraphCompressionInputStream {
  
  private int u = 1; //the current source vertex
  private final InputStream in;
  private final BitInputStream bitIn;
  
  public DifferenceEncodingInputStream(InputStream in) {
    this.in = in;
    this.bitIn = new BitInputStream(in);
  }

  @Override
  public void close() throws IOException {
    bitIn.close();
    in.close();
  }

  @Override
  public int[] readAdjacency() throws IOException {
    int L = bitIn.readInt(); //read the length of the adjacency
    int[] adjDiff = new int[L];
    int[] adj = new int[L];
    int numBitsToEncode = bitIn.readByte();    
    int signBit = bitIn.read(); //sign of the first element in adjDiff array
    
    for(int i = 0; i < L; i++) {
      int encodedVertex = 0;
      int j = 0;
      while(j < numBitsToEncode) {
        int bit = bitIn.read();
        encodedVertex = encodedVertex*2 + bit;
        j++;
      }
      adjDiff[i] = encodedVertex;
    }
    
    if(signBit == 1) {
      adjDiff[0] *= -1;
    }
    adj[0] = adjDiff[0] + u;
    for(int i = 1; i < L; i++) {
      adj[i] = adj[i-1] + adjDiff[i];
    }
    u++;
    return adj;
  }
}
