import static spark.Spark.*;

import com.google.gson.Gson;
public class server3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		port(2222);
    	get("Buy/:id",( req,res)->{double ID = Double.parseDouble(req.params(":id"));
    	String path = "/home/raghad-al3/Desktop/SharedFolders/Books2.xlsx";
    		return gson.toJson(ExceleDataReader.LookUpFromExcel(path, ID));			
    	});

	}

}
