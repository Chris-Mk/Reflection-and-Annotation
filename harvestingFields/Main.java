package harvestingFields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Class soilLandClass = RichSoilLand.class;
		Field[] fields = soilLandClass.getDeclaredFields();

		String input;
		while (!"HARVEST".equals(input = scan.nextLine())) {

			if ("all".equals(input)) {
				for (Field field : fields) {
					System.out.printf("%s %s %s%n",
							Modifier.toString(field.getModifiers()),
							field.getType().getSimpleName(),
							field.getName());
				}
			} else {
				for (Field field : fields) {
					String modifier = Modifier.toString(field.getModifiers());

					if (modifier.equals(input)) {
						System.out.printf("%s %s %s%n", modifier, field.getType().getSimpleName(), field.getName());
					}
				}
			}
		}
	}
}
