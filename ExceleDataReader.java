import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This program read date values from XLSX file in Java using Apache POI.
 * 
 * @author WINDOWS 8
 *
 */
public class ExceleDataReader {

    
    public static Object LookUpFromExcel(String file, double ItemNumber) throws IOException
    {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
        XSSFRow row = myExcelSheet.getRow(0);
        //According to the ID number choose which row you want to get info from.
        if(ItemNumber==1)
        {
         row = myExcelSheet.getRow(1);
         }
        else if (ItemNumber==2) 
        {
        	row = myExcelSheet.getRow(2);
        }
        else if (ItemNumber==3) 
        {
        	row = myExcelSheet.getRow(3);
        }
        else if (ItemNumber==4) 
        {
        	row = myExcelSheet.getRow(4);
        }
        else if (ItemNumber==5) 
        {
        	row = myExcelSheet.getRow(5);
        }
        else if (ItemNumber==6) 
        {
        	row = myExcelSheet.getRow(6);
        }
        else if (ItemNumber==7) 
        {
        	row = myExcelSheet.getRow(7);
        }
        else 
        {
        	return "There is only 7 books in the store";
        }
        
        
      
            double ID = row.getCell(0).getNumericCellValue();
            System.out.println("ID :" + ID);
            String name = row.getCell(1).getStringCellValue();
            System.out.println("NAME : " + name);
            double price = row.getCell(2).getNumericCellValue();
            System.out.println(" price:" + price);
            double amount = row.getCell(3).getNumericCellValue();
            System.out.println("amount :" + amount);
            String topic = row.getCell(4).getStringCellValue();
            System.out.println("topic : " + topic);
        String data = new String(ID+'\n'+name+'\n'+price+'\n'+amount+'\n'+topic);
        
       
        myExcelBook.close();
		return data;
        
    }//look up function
    
    public static Object SearchFromExcel(String file, String topic) throws IOException
    {
    	 XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
         XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
         XSSFRow row = myExcelSheet.getRow(0);
         //topic=topic.trim();
        // System.out.println(topic);
    	if(topic.equals("DistributedSystems"))
    	{
    		row = myExcelSheet.getRow(1);
            double ID1 = row.getCell(0).getNumericCellValue();
            String name1 = row.getCell(1).getStringCellValue();
            
    		row = myExcelSheet.getRow(2);
    		 double ID2 = row.getCell(0).getNumericCellValue();
             String name2 = row.getCell(1).getStringCellValue();
             
             row = myExcelSheet.getRow(7);
    		 double ID3 = row.getCell(0).getNumericCellValue();
             String name3 = row.getCell(1).getStringCellValue();
             
             String book1 = ID1+'\n'+name1;
             String book2 = ID2+'\n'+name2;
             String book3 = ID3+'\n'+name3;
             String data = "DS"+'\n'+book1 +'\n'+book2 +'\n'+book3;
             
             return data;
             

    	}
    	else if (topic.equals("GraduateSchool"))
    	{
    		row = myExcelSheet.getRow(3);
            double ID1 = row.getCell(0).getNumericCellValue();
            String name1 = row.getCell(1).getStringCellValue();
            
    		row = myExcelSheet.getRow(4);
    		 double ID2 = row.getCell(0).getNumericCellValue();
             String name2 = row.getCell(1).getStringCellValue();

     		row = myExcelSheet.getRow(5);
     		 double ID3 = row.getCell(0).getNumericCellValue();
              String name3 = row.getCell(1).getStringCellValue();
              

      		row = myExcelSheet.getRow(6);
      		 double ID4 = row.getCell(0).getNumericCellValue();
               String name4 = row.getCell(1).getStringCellValue();
               
             
             String book1 = ID1+'\n'+name1;
             String book2 = ID2+'\n'+name2;
             String book3 = ID3+'\n'+name3;
             String book4 = ID4+'\n'+name4;
             String data = "GS"+'\n'+book1 +'\n' +book2 +'\n' +book3 +'\n' +book4;
             return data;
    		
    	}
    	else {
    	
		return "This topic does not exist";
    	}
    	
    	
    }//Search function
    
}