import static spark.Spark.*;

import com.google.gson.Gson;
public class server3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		port(4444);
    	get("Buy/:id",( req,res)->{double ID = Double.parseDouble(req.params(":id"));
    	String path = "/home/raghad-al3/Desktop/SharedFolders/Books.xlsx";
    		return gson.toJson(ExceleDataReader.LookUpFromExcel(path, ID));			
    	});

	}

}
