package parameters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ReadExcel {
    private static final String parameterFileLocation = "src/main/resources/Parameters.xlsx";


    public static void getCollegeNames(ArrayList<String> collegeCities, ArrayList<String> collegeNames){

        try {
                FileInputStream excelFile = new FileInputStream(new File(parameterFileLocation));
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet urlList = workbook.getSheetAt(0);
                Iterator<Row> iterator = urlList.iterator();
                int r = 0;

                while(iterator.hasNext()){

                    Row currentRow = iterator.next();

                    Cell cell1 = currentRow.getCell(1);
                    Cell cell2 = currentRow.getCell(2);

                    String cell1Value = "", cell2Value = "";

                    try {
                         cell1Value = cell1.getStringCellValue();

                         cell2Value = cell2.getStringCellValue().replaceAll("\\s", "\\+");
                            if(cell2Value.contains(",")){
                                cell2Value = cell2Value.replaceAll(",", "");
                            }
                        System.out.println(cell2Value + "***********************");
                     } catch (Exception ignored) {}

                    boolean notEmpty = cell1Value.length() > 5;

                    if(notEmpty && !(r == 0)) {
                        collegeCities.add(cell1Value);
                        collegeNames.add(cell2Value);

                    }

                    r++;

                }

            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();

        }


    }




}
