/**
 *  Copyright (c) 2015 Apurv Verma
 */
package org.gatech.graphcompression;

import java.io.Closeable;
import java.io.IOException;

public interface GraphCompressionInputStream extends Closeable {
  
  public int[] readAdjacency() throws IOException;
}
