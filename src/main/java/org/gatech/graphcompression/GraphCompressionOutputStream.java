/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;

public interface GraphCompressionOutputStream extends Closeable, Flushable{
  
  
  /**
   * Writes the adjacency
   */
  public void writeAdjacency(int[] adj) throws IOException;
}
