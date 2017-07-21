import java.util.Comparator;

//object for sort result
public class SortResultObject {

    String fillMethodName;
    String sortMethodName;
    int collectionSize;
    long sortTime;

    public SortResultObject(String fillMethodName, String sortMethodName,
                            int collectionSize, long sortTime) {
        this.fillMethodName = fillMethodName;
        this.sortMethodName = sortMethodName;
        this.collectionSize = collectionSize;
        this.sortTime = sortTime;
    }
//just for testing "print collection"
    @Override
    public String toString() {
        return "SortResultObject{" +
                "fillMethodName='" + fillMethodName + '\'' +
                ", sortMethodName='" + sortMethodName + '\'' +
                ", collectionSize=" + collectionSize +
                ", sortTime=" + sortTime +
                '}';
    }
}

