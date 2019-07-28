package barracksWars.core.commands;

import barracksWars.interfaces.CommandInterpreter;
import barracksWars.interfaces.Executable;
import barracksWars.interfaces.Repository;
import barracksWars.interfaces.UnitFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CommandInterpreterImpl implements CommandInterpreter {
    private static final String COMMANDS_PACKAGE_NAME = "barracksWars.core.commands.";

    private Repository repository;
    private UnitFactory unitFactory;

    public CommandInterpreterImpl(Repository repository, UnitFactory unitFactory) {
        this.repository = repository;
        this.unitFactory = unitFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Executable interpretCommand(String[] data, String commandName) {
        Executable executable = null;
        commandName = Character.toUpperCase(commandName.charAt(0)) + commandName.substring(1);

        try {
            Class commandClass = Class.forName(COMMANDS_PACKAGE_NAME + commandName);
            executable = (Executable) commandClass.getDeclaredConstructor(String[].class)
                    .newInstance((Object) data);
            this.injectFields(executable);
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return executable;
    }

    private void injectFields(Executable executable) {
        Field[] fields = executable.getClass().getDeclaredFields();
        Field[] currentFields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getAnnotations()[0].toString().contains("Inject")) {

                for (Field currentField : currentFields) {
                    if (currentField.getType().equals(field.getType())) {
                        field.setAccessible(true);

                        try {
                            field.set(executable, currentField.get(this));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
