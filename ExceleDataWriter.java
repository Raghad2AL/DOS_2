import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExceleDataWriter {

    
    public static Object LookUpFromExcel(String file, double ItemNumber) throws IOException
    {
    	FileInputStream file1 = new FileInputStream(new File(file));
        XSSFWorkbook myExcelBook = new XSSFWorkbook(file1);
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
        else 
        {
        	row = myExcelSheet.getRow(7);
        }
        
            double amount = row.getCell(3).getNumericCellValue();
            System.out.println("amount :" + amount);
        
            if(amount==0) {return "Out of this book"+name;}
        else{
            amount--;
           Cell newCell = row.getCell(3);
           newCell.setCellValue(newCell.getNumericCellValue()-1);
            System.out.println("amount :" + amount);
            String name = row.getCell(1).getStringCellValue();
        }

        file1.close();
        FileOutputStream outFile = new FileOutputStream(new File(file));
        myExcelBook.write(outFile);
        outFile.close();
        /*
        as the database are small and only excel files, I found out the best consistency to do is to copy the changes directly 
        to the other DB when one is edited
        */
        String file2 = "/home/raghad-al3/Desktop/SharedFolders/Books2.xlsx";
        copyFiles(file, file2);
        
        return "The remaining amount of this book ' "+name+" '"+": "+amount;
        
    }//look up function
    
    static void copyFiles(String path1, String path2) throws IOException
    {
    	
    	File OriginalWB = new File(path1);
    	File CopyWB = new File(path2);
    	CopyWB.delete();
    	Files.copy(OriginalWB.toPath(), CopyWB.toPath());

    }
    
}
