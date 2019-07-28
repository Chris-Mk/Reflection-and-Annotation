package blackBoxInteger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BlackBoxInt blackBoxInt = null;
        Field innerValue = null;
        try {
            Constructor constructor = BlackBoxInt.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            blackBoxInt = (BlackBoxInt) constructor.newInstance();

            innerValue = blackBoxInt.getClass().getDeclaredField("innerValue");
            innerValue.setAccessible(true);
        } catch (NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);
        String input;

        while (!"END".equals(input = scan.nextLine())) {
            String command = input.substring(0, input.indexOf("_"));
            int value = Integer.parseInt(input.substring(input.indexOf("_") + 1));

            try {
                Method method = switch (command) {
                    case "add" -> BlackBoxInt.class.getDeclaredMethod("add", int.class);
                    case "subtract" -> BlackBoxInt.class.getDeclaredMethod("subtract", int.class);
                    case "multiply" -> BlackBoxInt.class.getDeclaredMethod("multiply", int.class);
                    case "divide" -> BlackBoxInt.class.getDeclaredMethod("divide", int.class);
                    case "rightShift" -> BlackBoxInt.class.getDeclaredMethod("rightShift", int.class);
                    case "leftShift" -> BlackBoxInt.class.getDeclaredMethod("leftShift", int.class);
                    default -> null;
                };

                if (method != null && blackBoxInt != null && innerValue != null) {
                    method.setAccessible(true);
                    method.invoke(blackBoxInt, value);
                    System.out.println(innerValue.get(blackBoxInt));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
