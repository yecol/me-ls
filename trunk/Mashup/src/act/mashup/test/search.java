package act.mashup.test;

import java.net.*;
import java.io.*;

public class search
{
  public static void main ( String[] args ) throws IOException 
  {
    try 
    {
        URL url = new URL("http://www.baidu.com/s?wd=ÁùÒ»");

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;

        while ((str = in.readLine()) != null) 
        {
          System.out.println(str);
        }
        System.out.println("all");
        in.close();
    } 
    catch (MalformedURLException e) {
    	e.printStackTrace();
    } 
    catch (IOException e) {
    	e.printStackTrace();
    }
  }
}