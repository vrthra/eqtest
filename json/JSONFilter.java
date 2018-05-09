package org.json;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.*;

import java.util.Scanner;

public class JSONFilter {

  static XJSONArray xjsonObject;

  static final ExecutorService executor = Executors.newSingleThreadExecutor();

  public static int testJSON(String str) throws Exception {
    xjsonObject = null;

    final Runnable xJsonTask = new Thread() {
      @Override
      public void run() {
        xjsonObject = new XJSONArray(str);
      }
    };

    final Future xjsonFuture = executor.submit(xJsonTask);
    try { 
      xjsonFuture.get(5, TimeUnit.SECONDS); 
    }
    catch (TimeoutException te) { 
      return -3;
    } 
    catch(ExecutionException xe)
    {
	Throwable cause = xe.getCause();
	if (cause instanceof XJSONException)
	{
		return -1;
	}
      	xe.printStackTrace();
	return -1;
    }
    catch (Exception ex) {
      	ex.printStackTrace();
	return -1;
    }
    return 0;
  }

  public static void main(String[] args) {
    try {
      String line;
      Scanner sc = new Scanner(System.in);
      /*File file = new File();
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuffer stringBuffer = new StringBuffer();*/
      int count = 0;
      //System.out.println(args[1]);
      while (sc.hasNextLine()) {
	 line = sc.nextLine();
	System.err.println(line);
        int testJsonResult = 0;
        try {
          testJsonResult = testJSON("[" + line + "]");
        } catch (Exception ex) {
	  ex.printStackTrace();
          testJsonResult = -1;
        }
        if (testJsonResult < 0) {
          System.err.println(line);
        } else {
          System.out.println(line);
        }
        count++;
      }
      //fileReader.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
