package stronghold.model.utils;

import stronghold.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Command {
    String baseCommand;
    ArrayList<String> subcommands;
    HashMap<String, String> argsAndParams;
    ArrayList<String> options;

    public Command(String input){
        String[] splittedInput = input.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        System.out.println(Arrays.toString(splittedInput));
        System.out.println(splittedInput[0]);
        for (int i = 0; i < splittedInput.length; i++) {
            String key = splittedInput[i];
            if (key.startsWith("-") && !key.startsWith("--") && i < splittedInput.length - 1) {
                String value = splittedInput[i + 1];
                if (!value.startsWith("-")) {
                    argsAndParams.put(key, value);
                } else {
                    argsAndParams.put(key, "-");
                }
            }
        }
    }

    public String getBaseCommand() {
        return this.baseCommand;
    }

    public ArrayList<String> getSubcommands() {
        return this.subcommands;
    }

    public HashMap<String, String> getArgsAndParams() {
        return this.argsAndParams;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public static void main(String[] args) {
        Command command = new Command("basecommand subcommand1 -arg1 \"params 1\" -arg2 -arg3 params2 --options");
        System.out.println(command.getBaseCommand());
        System.out.println(command.getSubcommands());
        System.out.println(command.getArgsAndParams());
        System.out.println(command.getOptions());

    }
}
