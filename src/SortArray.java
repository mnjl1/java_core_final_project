import java.util.Arrays;

public class SortArray {
    public void ArraysToSort(int[] a) {
        Arrays.sort(a);
    }
    public void BubbleSort(int[] a) {
        int temp;
        for (int i = 0; i <a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[j]>a[i]){
                    temp = a[i];
                    a[i]=a[j];
                    a[j]=temp;
                }
            }
        }
    }
}
