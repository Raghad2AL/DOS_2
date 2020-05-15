import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
public class FrontEndServer {
	
	private static Integer postion =0;
	private static Integer postionS =0;	
	private static Integer postionB =0;
	private static Integer limit=4;//cache limit is 4
	private static Integer count=0;//counter for the arraylist below
	private static ArrayList<InMemoryCache> Mem = new ArrayList<InMemoryCache>();



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		port(2225);
	 	//Gson gson = new Gson();
		get("lookup/:id",(request,response)
	->{ String id =request.params(":id");
	//check the cache using ID
	    InputStream data = CheckCacheUsingID(id, "lookup");
	       if(data == null)
	       {
	    	   System.out.println("Cache does not have this data");
	       } 
	       //if the cache doesnt have the data, the server will hanlde it
	     String url = RoundRobin("lookup")+ id;
	     URL obj = new URL (url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	    String inputLine;
	    String topic="";
	    StringBuffer datain = new StringBuffer();

	    while ((inputLine = in.readLine()) != null) {
	        datain.append(inputLine);
	        topic=inputLine;
	    }
		 topic = topic.replaceAll("\\s", "");
	     New(id,"lookup",con.getInputStream(),topic,false);
   return datain;
		//return con.getInputStream();
	 });
		
		
		
		get("search/:topic",(request,response)
			->{ //String topic =request.params(":topic");
			String topic2 =request.params(":topic");
			//the below line is a must in case the user out %20 in the URL
			 topic2 = topic2.replaceAll("\\s", "");
			 //same, check the cache first but this time using the topic
		    InputStream data1 = CheckCacheUsingTopic(topic2, "search");
		    if(data1 == null)
		       {
		    	   System.out.println("Cache does not have this data");
		       } 
		    //let the server handle it if it is not in the cache
		 	  String url = RoundRobin("search")+ topic2;
			 URL obj = new URL (url);
		 	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 	  BufferedReader in = new BufferedReader(
			    new InputStreamReader(con.getInputStream()));
			    String inputLine;
			    boolean flag = true;
			    String id="";
			    StringBuffer data = new StringBuffer();

			    while ((inputLine = in.readLine()) != null) {
			        data.append(inputLine);
			        data.append('\n');
			        if(flag) { id=inputLine; flag = false;}
			    }
			   
			     New(id,"search",con.getInputStream(),topic2,false);
		   return data;});
		
		//the buy is handled by the server automatically and it sends
		//invalidate request to delete the data.
		get("buy/:id",(request,response)
				->{ String id =request.params(":id");
				     String url = RoundRobin("buy")+ id;
				     URL obj = new URL (url);
				     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				     SendInvalidate(id);//to delete data
					return con.getInputStream();});
		
					
		
	}//main
	
	/*RR is based on balancing requests in order as we don't know what the
	user might request, I have seperated them into three Arraylists each one
	for a request and then I balance between them by giving the first reuqest to the first
	server and the second one to the second server.
	
	*/ 
	static String RoundRobin(String hint)
	{
		ArrayList<String> IPsL =new ArrayList<String>();
		IPsL.add("http://192.168.1.124:7755/Lookup/");
		IPsL.add("http://192.168.1.117:7766/Lookup/");//forclone2
		
		ArrayList<String> IPsS =new ArrayList<String>();
		IPsS.add("http://192.168.1.124:7755/Search/");
		IPsS.add("http://192.168.1.117:7766/Search/");//forclone2
		
		
		ArrayList<String> IPsB =new ArrayList<String>();
		IPsB.add("http://192.168.1.123:2222/Buy/");
		IPsB.add("http://192.168.1.118:4444/Buy/");//forclone3
		
		String url = null;
		if(hint.equals("lookup"))
		{
		synchronized(postion)
		{
			if(postion>=IPsL.size())
			{
				postion =0;
			}
			
				//System.out.println(postion);
				url=IPsL.get(postion);
				postion++;
				//System.out.println(url);
			
		}
		}//if lookup
		else if (hint.equals("search"))
		{
			synchronized(postionS)
			{
				if(postionS>=IPsS.size())
				{
					postionS =0;
				}
				
					//System.out.println(postionS);
					url=IPsS.get(postionS);
					postionS++;
					//System.out.println(url);
				
			}
		}
		
		else if (hint.equals("buy"))
		{
			synchronized(postionB)
			{
				if(postionB>=IPsS.size())
				{
					postionB =0;
				}
				
					//System.out.println(postionB);
					url=IPsB.get(postionB);
					postionB++;
					//System.out.println(url);
				
			}
		}
		
		return url;
		
	}//RRfun
	
	public static InputStream CheckCacheUsingID(String ID, String Requesttype)
	{
		InputStream data =null;
		int index = -1;
		for(int i=0;i<Mem.size(); i++)
		{
	     if(Mem.get(i).getID().equals(ID) && Mem.get(i).getRequest().equals(Requesttype))
	     {
	    	 index =i;
	     }
		}//for
		
		if(index==-1)
		{
			return null;
			
		}
		else
		{
			data = Mem.get(index).getData();
		}
		
		return data;
		
	}//check
	public static InputStream CheckCacheUsingTopic(String topic, String Requesttype)
	{

		InputStream data =null;
		int index = -1;
		for(int i=0;i<Mem.size(); i++)
		{
	     if(Mem.get(i).getTopic().equals(topic) && Mem.get(i).getRequest().equals(Requesttype))
	     {
	    	 index =i;
	     }
		}//for
		
		if(index==-1)
		{
			return null;
			
		}
		else
		{
			data = Mem.get(index).getData();
		}
		
		return data;
		
	}
	
	public static void New(String ID, String RequestType, InputStream inputStream, String topic,Boolean invalid)
	{
		int flag =0;
	  if(Mem.size()<limit) {
		for(int i=0;i<Mem.size(); i++)
	  {
		  if((Mem.get(i).getID().equals(ID)) && (Mem.get(i).getRequest().equals(RequestType)) && 
				(Mem.get(i).getInvalid()==false))
		{
		  flag++;
		}
	  }//for loop
		if(flag!=0)
		{
		  System.out.println("In the cache");
		}
			
		else	
		{
			InMemoryCache mem = new InMemoryCache();
			mem.setID(ID);
			mem.setRequest(RequestType);
			mem.setData(inputStream);
			mem.setInvalid(invalid);
			mem.setTopic(topic);
			Mem.add(count,mem);
			count++;
			 System.out.println("Added");
			

		}
		
	  
     }//if
	  else {
		  System.out.println("Cache is full");
	  }
	}//new

	public static void SendInvalidate(String ID)
	{
		int index= -1;
		for(int i=0; i<Mem.size();i++)
		{
			if(Mem.get(i).getID().equals(ID))
			{
				index=i;
			}//if
		}//for loop
		if(index==-1)
		{
			System.out.println("This info doesnt exist in cache");
		}
		else
		{
			Mem.remove(index);
			count--;
			System.out.println("Invalidated");
		}
		
	}//invalidate fun
	
}//class
