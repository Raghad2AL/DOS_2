import java.io.InputStream;

public class InMemoryCache {

	private String ID;
	private String RequestType;
	private String Topic;
	private InputStream Data;
	private boolean Invalid;
	
	public InMemoryCache()
	{
		ID="";
		RequestType="";
		Data=null;
		Invalid=false;
		Topic ="";
	}//Default constructor 
	
	public InMemoryCache(String id, String requestType, InputStream data, String topic,boolean invalid)
	{
		this.ID=id;
		this.RequestType=requestType;
		this.Data=data;
		this.Topic=topic;
		this.Invalid=invalid;
	}//second constructor 
	
	public void setID(String ID1) { ID=ID1;}
	public String getID() {return this.ID;}
	
	public void setRequest(String requestType1) { RequestType=requestType1;}
	public String getRequest() {return this.RequestType;}
	
	public void setData(InputStream data1) { Data=data1;}
	public InputStream getData() {return this.Data;}
	
	public void setInvalid(boolean invalid) { Invalid=invalid;}
	public boolean getInvalid() {return this.Invalid;}
	
	public void setTopic(String topic) {Topic =topic;}
	public String getTopic() {return this.Topic;}
	
}//class
