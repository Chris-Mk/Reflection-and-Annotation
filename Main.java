import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Class reflectionClass = Reflection.class;
//        printClasses(reflectionClass);
//        printMethods(reflectionClass);
        printMistakes(reflectionClass);
    }

    private static void printMistakes(Class reflectionClass) {
        Field[] declaredFields = reflectionClass.getDeclaredFields();
        Arrays.stream(declaredFields)
                .filter(field -> !Modifier.isPrivate(field.getModifiers()))
                .sorted(Comparator.comparing(Field::getName))
                .forEach(field -> System.out.println(field.getName() + " must be private!"));

        Method[] declaredMethods = reflectionClass.getDeclaredMethods();
        List<Method> getters = new ArrayList<>();
        List<Method> setters = new ArrayList<>();

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith("get")) {
                getters.add(declaredMethod);
            } else if (declaredMethod.getName().startsWith("set")) {
                setters.add(declaredMethod);
            }
        }

        getters.stream()
                .filter(method -> !Modifier.isPublic(method.getModifiers()))
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.println(method.getName() + " must be public!"));

        setters.stream()
                .filter(method -> !Modifier.isPrivate(method.getModifiers()))
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.println(method.getName() + " must be private!"));
    }

    private static void printMethods(Class reflectionClass) {
        Method[] declaredMethods = reflectionClass.getDeclaredMethods();

        List<Method> getters = new ArrayList<>();
        List<Method> setters = new ArrayList<>();

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith("get")) {
                getters.add(declaredMethod);
            } else if (declaredMethod.getName().startsWith("set")) {
                setters.add(declaredMethod);
            }
        }

        getters.stream()
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.printf("%s will return class %s%n",
                        method.getName(),
                        method.getReturnType().getSimpleName()));

        setters.stream()
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.printf("%s and will set field of class %s%n",
                        method.getName(),
                        method.getParameterTypes()[0].getSimpleName()));
    }

    private static void printClasses(Class reflectionClass) {
        System.out.println(reflectionClass);

        System.out.println(reflectionClass.getSuperclass());

        Class[] interfaces = reflectionClass.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println(anInterface);
        }

        try {
            Object reflection = reflectionClass.getDeclaredConstructor().newInstance();
            System.out.println(reflection);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
