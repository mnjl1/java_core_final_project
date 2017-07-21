import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class UseReflection {

    //getting fill/sort classes into action by reflection
    public void getReflection() throws  IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        FillArray fillArray = new FillArray();
        SortArray sortArray = new SortArray();

        Class fillClass = fillArray.getClass();
        Class sortClass = sortArray.getClass();

        Method[] fillMethod = fillClass.getDeclaredMethods();
        Method[] sortMethod = sortClass.getDeclaredMethods();

        //collection for holding sort results
        SortResultCollectionHolder collection = new SortResultCollectionHolder();
        collection.arrayList = new ArrayList();

        //loop for adding sort results to collection
        for (int k = 10; k <100; k=k+10) { // 'k' is array size
            for (int i = 0; i < fillMethod.length; i++) {
                int[] fillResult = ((int[]) fillMethod[i].invoke(fillClass.newInstance(), k));

                for (int j = 0; j < sortMethod.length; j++) {
                    long startTime = System.nanoTime();
                    int[] sortResult = ((int[]) sortMethod[j].invoke(sortClass.newInstance(), fillResult));
                    long sortTime = System.nanoTime() - startTime;//calculating sorting time

                    //creating object for each fill/sort result and adding it to collection
                    SortResultObject sortResultObject = new SortResultObject(fillMethod[i].getName(), sortMethod[j].getName(), k, sortTime);
                    collection.arrayList.add(sortResultObject);
                }
            }
        }
        // creating excel sheet with sort results
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        int count=0;
        Iterator iterator = collection.arrayList.iterator();
        while (iterator.hasNext()){
            Object ob = iterator.next();
            Class obClass  =ob.getClass();
            Field fillMethodName = obClass.getDeclaredField("fillMethodName");
            Field sortMethodName = obClass.getDeclaredField("sortMethodName");
            Field collectionSize = obClass.getDeclaredField("collectionSize");
            Field sortTime = obClass.getDeclaredField("sortTime");

            Object fillValue = fillMethodName.get(ob);
            Object sortValue = sortMethodName.get(ob);
            Object sizeValue = collectionSize.getInt(ob);
            Object timeValue = sortTime.get(ob);

            Row row = sheet.createRow(count);

            Cell fillCell = row.createCell(0);
            Cell sortCell = row.createCell(1);
            Cell sizeCell = row.createCell(2);
            Cell timeCell = row.createCell(3);

            fillCell.setCellValue(String.valueOf(fillValue));
            sortCell.setCellValue(String.valueOf(sortValue));
            sizeCell.setCellValue((Integer) sizeValue);
            timeCell.setCellValue((Long)timeValue);
            count++;
        }
        try(FileOutputStream fos = new FileOutputStream("SortTime.xls")) {
            wb.write(fos);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}



