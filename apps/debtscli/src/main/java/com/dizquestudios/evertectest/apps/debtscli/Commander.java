package com.dizquestudios.evertectest.apps.debtscli;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;

import com.dizquestudios.evertectest.core.debts.api.ClientAPI;
import com.dizquestudios.evertectest.core.debts.api.DebtAPI;
import com.dizquestudios.evertectest.core.debts.api.ParameterAPI;
import com.dizquestudios.evertectest.core.debts.shared.API;

/**
 *
 * @author Sebastian
 */
@API
public class Commander implements CommandLineRunner {

    @FunctionalInterface
    interface Command {

        void run(String path) throws IOException;
    }

    public static final String HELP_ARG = "-h";
    public static final String PARAMETERS_ARG = "-p";
    public static final String DEBTS_ARG = "-d";

    private static Logger logger() {
        return Logger.getLogger(Commander.class.getSimpleName());
    }

    private final DebtAPI debtAPI;
    private final ParameterAPI parameterAPI;
    private final ClientAPI clientAPI;
    private final Map<String, Command> commands;

    public Commander(DebtAPI api, ParameterAPI parameterAPI, ClientAPI clientAPI) {
        this.debtAPI = api;
        this.parameterAPI = parameterAPI;
        this.clientAPI = clientAPI;
        this.commands = new HashMap<>();
        this.commands.put(PARAMETERS_ARG, this::loadParameters);
        this.commands.put(DEBTS_ARG, this::loadDebts);
    }

    @Override
    public void run(String... args) throws Exception {
        logger().log(Level.INFO, "EXECUTING : command line runner");
        logger().log(Level.INFO, "");

        try (Stream<String> arguments = Arrays.stream(args).parallel()) {
            String helpArg = arguments.filter((arg) -> {
                return arg.equalsIgnoreCase(HELP_ARG);
            }).findAny().orElse(null);

            if (helpArg != null) {
                String helpMenu = """
                                  
                                  AYUDA: 
                        
                                  Sintaxis: java --enable-preview -jar debtscli##0.0.1 [argumentos...]
                                                                    
                                  Argumentos disponibles:
                                      -h
                                      Lista la ayuda con los argumentos disponibles.
                                  
                                      -p <ruta absoluta del archivo>
                                      Permite cargar los parametros de configuracion desde una ubicacion.
                                  
                                      -d <ruta absoluta del archivo>
                                      Permite cargar todas las deudas desde un archivo.
                                  
                """;
                logger().log(Level.INFO, helpMenu);
                return;
            }

        }

        String current;
        String next;
        for (int i = 0; i < args.length; i++) {
            current = args[i];
            logger().log(Level.INFO, String.format("arg: %s", current));

            if (current.startsWith("-") && this.commands.containsKey(current)) {
                i++;
                next = args[i];

                this.commands.get(current).run(next);
            }
            logger().log(Level.INFO, "");
        }
    }

    private void loadParameters(String Path) throws IOException {
        logger().log(Level.INFO, String.format("Load paramters. Path: '%s'", Path));

        parameterAPI.saveFromFile(Paths.get(Path).toFile());
        logger().log(Level.INFO, "Results:");
        parameterAPI.findAll().forEach((parameter) -> {
            logger().log(Level.INFO, parameter.toString());
        });

    }

    private void loadDebts(String Path) throws IOException {
        logger().log(Level.INFO, String.format("Load debts. Path: '%s'", Path));

        debtAPI.loadDebsFromFile(Paths.get(Path).toFile(), parameterAPI, clientAPI);
        logger().log(Level.INFO, "Results:");
        debtAPI.findAll(clientAPI).forEach((debt) -> {
            logger().log(Level.INFO, debt.toString());
        });
    }
}
