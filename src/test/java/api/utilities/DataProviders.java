package api.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.testng.annotations.DataProvider;

public class DataProviders {

    public static Object[][] getAllData(String sheetName) throws IOException {
        String path=System.getProperty("user.dir")+"/testData/Userdata.xlsx";
        XLUtility xl=new XLUtility(path);

        int rownum=xl.getRowCount(sheetName);
        int colcount=xl.getCellCount(sheetName,1);

        Object apidata[][]=new Object[rownum][colcount];

        for(int i=1;i<=rownum;i++) {
            for(int j=0;j<colcount;j++) {
//                apidata[i-1][j]= xl.getCellData(sheetName,i, j);
                XSSFCell cell = xl.getCell(sheetName, i, j);

                switch (cell.getCellType()) {
                    case STRING:
                        apidata[i - 1][j] = cell.getStringCellValue();
                        break;

                    case NUMERIC:
                        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                            // Format date if needed
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            apidata[i - 1][j] = dateFormat.format(cell.getDateCellValue());
                        } else {
                            // Differentiate between integer and double
                            double numericValue = cell.getNumericCellValue();
                            if (numericValue == Math.floor(numericValue)) {
                                apidata[i - 1][j] = (int) numericValue;
                            } else {
                                apidata[i - 1][j] = numericValue;
                            }
                        }
                        break;

                    case BOOLEAN:
                        apidata[i - 1][j] = cell.getBooleanCellValue();
                        break;

                    case BLANK:
                        apidata[i - 1][j] = ""; // Handle blank cells as per your requirement
                        break;

                    default:
                        // Handle other cell types if needed
                        break;
                }
            }
        }

        return apidata;
    }


    public static String[] getUserNames(String sheetName) throws IOException {
        String path=System.getProperty("user.dir")+"/testData/Userdata.xlsx";
        XLUtility xl=new XLUtility(path);

        int rownum=xl.getRowCount(sheetName);

        String apidata[]=new String[rownum];

        for(int i=1;i<=rownum;i++)
        {
            apidata[i-1]= xl.getCellData(sheetName,i, 1);

        }

        return apidata;
    }

}