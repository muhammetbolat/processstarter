package org.csystem.app;

import org.csystem.util.console.Console;

import java.io.*;


import static org.csystem.util.console.CommandLineArgsUtil.checkForLengthEqual;


public final class ProcessStarterApp {
    private ProcessStarterApp() {}

    public static void run(String [] args) {

        try {
            checkForLengthEqual(args, 2, "Wrong number of argument", 1);
            ProcessBuilder processBuilder = new ProcessBuilder(args[0]);

            // If the process has standard error, it is directed to our process.
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(args[1], true));


            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Console.writeLine(line);
                bufferedWriter.write(line + "\r\n");
            }

            bufferedWriter.flush();

        } catch (IOException ignore) {
            Console.Error.write("Problem occurs while starting process");
        } catch (Throwable ex) {
            Console.Error.writeLine("%s", ex.getMessage());
        }



    }
}
