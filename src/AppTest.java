import javafx.scene.effect.Reflection;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AppTest extends Reflection {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, IOException, NoSuchMethodException {
        UseReflection useReflection = new UseReflection();
        useReflection.getReflection();
    }
}
