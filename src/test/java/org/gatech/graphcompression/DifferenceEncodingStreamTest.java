package org.gatech.graphcompression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class DifferenceEncodingStreamTest {
  
  private int[] getSorted(String line) {
    String[] vertices = line.split(" ");
    int[] adj = new int[vertices.length];
    for(int i = 0; i < vertices.length; i++) {
      adj[i] = Integer.parseInt(vertices[i]);
    }
    Arrays.sort(adj);
    return adj;
  }

  @Test
  public void test() throws IOException {
    //TODO: you need to write this too, so while decompression, the compressed file is 
    //all you need
    int numVertices = 0;
    
    DifferenceEncodingOutputStream deos = new DifferenceEncodingOutputStream(
        new FileOutputStream("/tmp/preferentialAttachment.dz"));
    
    InputStream in = getClass().getResourceAsStream("/preferentialAttachment.graph");    
    BufferedReader bin = new BufferedReader(new InputStreamReader(in));
    
    String line = null;
    while((line = bin.readLine()) != null) {
      int[] adj = getSorted(line);
      deos.writeAdjacency(adj);
      numVertices++;
    }
    deos.close();
    bin.close();
    in = getClass().getResourceAsStream("/preferentialAttachment.graph");    
    bin = new BufferedReader(new InputStreamReader(in));
    
    DifferenceEncodingInputStream deis = new DifferenceEncodingInputStream(
        new FileInputStream("/tmp/preferentialAttachment.dz"));
    
    OutputStream out = new FileOutputStream("/tmp/preferentialAttachmentDecompressed.graph");
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
    for(int i = 0; i < numVertices; i++) {
      int[] adj = deis.readAdjacency();
      bw.write(""+adj[0]);
      
      int[] adjOrig = getSorted(bin.readLine());
      Assert.assertArrayEquals(adjOrig, adj);
      for(int j = 1; j < adj.length; j++) {
        bw.write(" ");
        bw.write(""+adj[j]);        
      }
      bw.write("\n");
    }
    deis.close();
    bin.close();
  }
}
