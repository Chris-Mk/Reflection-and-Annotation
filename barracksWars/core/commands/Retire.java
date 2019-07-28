package barracksWars.core.commands;

import barracksWars.interfaces.Repository;

public class Retire extends Command {
    @Inject
    private Repository repository;

    public Retire(String[] data) {
        super(data);
    }

    @Override
    public String execute() {
        String result = this.getData()[1];
        try {
            this.repository.removeUnit(result);
            result += " retired!";
        } catch (IllegalArgumentException ex) {
            result = ex.getMessage();
        }
        return result;
    }
}
