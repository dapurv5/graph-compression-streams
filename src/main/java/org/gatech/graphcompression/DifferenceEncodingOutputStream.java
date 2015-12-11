/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

import static java.lang.Math.floor;
import static java.lang.Math.max;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


/**
 * Adjacent Difference Encoding
 * Just take difference with adjacent element, the first element
 * takes a difference with the source vertex.
 */
public class DifferenceEncodingOutputStream implements GraphCompressionOutputStream{
  
  private int u = 1; //the current source vertex
  private final double log2 = Math.log(2);
  private final OutputStream out;
  private final BitOutputStream bitOut;
  
  public DifferenceEncodingOutputStream(OutputStream out) {
    this.out = out;
    this.bitOut = new BitOutputStream(out);
  }

  @Override
  public void close() throws IOException {
    flush();
    bitOut.close();
    out.close();
  }

  @Override
  public void flush() throws IOException {
    bitOut.flush();
    out.flush();
  }
  
  protected int[] getDiffWithAdjElem(int u, int[] adjacency) {
    int[] adjDiff = new int[adjacency.length];
    adjDiff[0] = adjacency[0] - u;
    for(int i = 1; i < adjacency.length; i++) {
      adjDiff[i] = adjacency[i] - adjacency[i-1];
    }
    return adjDiff;
  }
  
  protected double log2(int n) {
    return Math.log(n)/log2;
  }

  @Override
  public void writeAdjacency(int[] adj) throws IOException {
    int[] adjDiff = getDiffWithAdjElem(u, adj);
    int numBitsToEncode = 0;
    int signBit = 0; //sign of the first element in adjDiff array
    if(adjDiff[0] < 0) {
      signBit = 1;
      adjDiff[0] *= -1.0;
    }
    
    int greatestElem = -1;
    for(int i = 0; i < adj.length; i++) {
      greatestElem = max(greatestElem, adjDiff[i]);
    }
    
    numBitsToEncode = (int)floor(log2(greatestElem)) + 1;
    numBitsToEncode = max(1, numBitsToEncode);
    
    bitOut.writeInt(adj.length); //write the length of the adjacency
    bitOut.writeByte(numBitsToEncode);  //write the number of bits required to encode each value    
    bitOut.writeBit(signBit);

    for(int e : adjDiff) {      
      String binary = Maths.getBinaryRepr(e);
      binary = binary.substring(binary.length() - numBitsToEncode);
      assert(binary.equals(Maths.getReducedBinaryRepr(e)));
      
      for(char bit : binary.toCharArray()) {
        bitOut.writeBit(Integer.parseInt(bit+""));
      }
    }
    u++; //move to the new vertex
  }

  public static void main(String[] args) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DifferenceEncodingOutputStream deos = new DifferenceEncodingOutputStream(bos);
    int[] adj = new int[]{3,4,5,9};
    deos.writeAdjacency(adj);
    
    adj = new int[]{1,4,6,9};
    deos.writeAdjacency(adj);
    deos.close();
    
    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    DifferenceEncodingInputStream deis = new DifferenceEncodingInputStream(bis);
    adj = deis.readAdjacency();
    System.out.println(Arrays.toString(adj));
    adj = deis.readAdjacency();
    System.out.println(Arrays.toString(adj));
    deis.close();
  }
}
