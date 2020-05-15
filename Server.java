//package com.code.spark;
import static spark.Spark.*;

import com.google.gson.Gson;
public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		port(7766);
		Gson gson = new Gson();
    	get("Lookup/:id",( req,res)->{double ID = Double.parseDouble(req.params(":id"));
    	String path = "/home/raghad-al2/Desktop/SharedFolders/Books.xlsx";
    		return gson.toJson(ExceleDataReader.LookUpFromExcel(path, ID));			
    	});
    	
    	get("Search/:topic",( req,res)->{String topic = req.params(":topic");
    	String path = "/home/raghad-al2/Desktop/SharedFolders/Books.xlsx";
    		return gson.toJson(ExceleDataReader.SearchFromExcel(path, topic));			
    	});

	}

}
