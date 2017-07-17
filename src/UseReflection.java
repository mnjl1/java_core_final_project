import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UseReflection {

    public void getReflection() throws FileNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        FillArray fillArray = new FillArray();
        SortArray sortArray = new SortArray();

        Class fillClass = fillArray.getClass();
        Class sortClass = sortArray.getClass();

        Method[] fillMethod = fillClass.getDeclaredMethods();
        Method[] sortMethod = sortClass.getDeclaredMethods();

            long startTime;
            long sortTime;

            Workbook sortBook = new HSSFWorkbook();

            int size = 10;//array size
            for (int i = 0; i < fillMethod.length; i++) {
                int[] fillResult = new int[0];
                    fillResult = ((int[]) fillMethod[i].invoke(fillClass.newInstance(), size));
                    String sheetName = fillMethod[i].getName();
                    Sheet sheet = sortBook.createSheet(sheetName);//creating xls sheet with fill method name

                for (int j = 0; j < sortMethod.length; j++) {
                    startTime = System.nanoTime();
                    int[] sortResult = ((int[]) sortMethod[j].invoke(sortClass.newInstance(), fillResult));

                    String sortName = sortMethod[j].getName();

                    Row row= sheet.createRow(j);
                    Cell cellM = row.createCell(0);
                    cellM.setCellValue(sortName);

                    sortTime = System.nanoTime() - startTime;//calculating sorting time
                    //Row rowTime = sheet.createRow(j);
                    Cell cellT = row.createCell(1);
                    cellT.setCellValue(sortTime);
                }
            }
        try(FileOutputStream fos = new FileOutputStream("SortTime.xls")) {
            sortBook.write(fos);
        }catch (IOException e){
            System.out.println(e);
        }
        }
}


