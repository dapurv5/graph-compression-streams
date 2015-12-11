package org.gatech.graphcompression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class DifferenceEncodingStreamTest {

  @Test
  public void test() throws IOException {
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
