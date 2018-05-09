package org.json;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class JSONTest {

	static XJSONArray xjsonObject;
	static JSONArray jsonObject;

	static final ExecutorService executor = Executors.newSingleThreadExecutor();


	public static int testJSON(String str) throws Exception {
		//String str = "{key1:value1, key2:42}";
		jsonObject = null;
		xjsonObject = null;

		final Runnable xJsonTask = new Thread() {
			@Override
			public void run() {
				xjsonObject = new XJSONArray(str);
			}
		};

		final Runnable jsonTask = new Thread() {
			@Override
			public void run() {
				jsonObject = new JSONArray(str);
			}
		};

		final Future future = executor.submit(xJsonTask);
		//executor.shutdown(); // This does not cancel the already-scheduled task.

		// Run xJsonTask for 1 minute
		try { 
			future.get(5, TimeUnit.SECONDS); 
		}
		/*catch (InterruptedException et)
		{
			//System.out.println("T");
			return -3;
		}*/
		catch (TimeoutException te) { 
			return -3;
		}

		catch (Exception ex)
		{
			return -1;
		}

		final Future future2 = executor.submit(jsonTask);
		// Run jsonTask for 1 minute

		try {
                        future2.get(5, TimeUnit.SECONDS);
			//XJSONArray xjsonObject = new XJSONArray(str);
			//JSONArray jsonObject = new JSONArray(str);
			//System.out.println("json " + jsonObject.toString());
			//System.out.println("xjson " + xjsonObject.toString());

			return (jsonObject.toString().equals(xjsonObject.toString())) == true ? 1 : 0; //{

                }
                /*catch (InterruptedException et)
                {
                        //System.out.println("t");
                        return -2;
                }*/
		catch (TimeoutException te) { 
			return -2;
		}
                catch (Exception ex)
                {
                        return -1;
                }



		//return (!jsonObject.toString().equals(xjsonObject.toString())); //{
	}
	public static void main(String[] args)
	{
		try
		{
			File file = new File(args[0]);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			int count = 0;
			System.out.println(args[1]);
			while ((line = bufferedReader.readLine()) != null) {
				//stringBuffer.append(line);
				//stringBuffer.append("\n");
				int testJsonResult = 0;
				//System.out.println(line);
				try
				{
					testJsonResult = testJSON("[" + line + "]");
				}
				catch (Exception ex)
				{
					testJsonResult = -1;
					//System.out.println(count + ",-1");
					//System.out.println("-1");
				}
				if (testJsonResult == 1)
				{
					//System.out.println(count + ",1");
					System.out.println("1");
				}
				else if (testJsonResult == -2)
				{
					System.out.println("t");
					//System.out.println(count + ",t");
				}
				else if (testJsonResult == -3)
				{
					System.out.println("T");
					//System.out.println(count + ",T");
				}
				else if (testJsonResult == -1)
				{
					//System.out.println(count + ",-1");
					System.out.println("-1");
				}
				else
				{
					//System.out.println(count + ",0");
					System.out.println("0");
				}

				count++;

			}
			fileReader.close();
			//System.out.println("Final count " + count);
			if (!executor.isTerminated())
			    executor.shutdownNow();

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		// Read the file of 100 inputs
		// 
		// call testJSon foreach line of inputs
		// print the value
	}
}
