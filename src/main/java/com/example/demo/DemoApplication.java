package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        File[] listOfFiles = new File("xsd_files").listFiles();
        Arrays.stream(listOfFiles).forEach(directory -> {
            final String configFile = "config_" + directory.getName() + ".yaml";
            try {
                System.out.println("Processing file: " + directory.getName());
                runProcess("java -cp . -jar xml-avro-all-1.8.2.jar -c ./xsd_files/" + directory.getName() + "/" + configFile, directory.getName());
            } catch (Exception e) {
                System.out.println("Error processing file");
                throw new RuntimeException(e);
            }
        });
    }

    private static void runProcess(String command, String file) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
		if (pro.exitValue() == 1) {
			System.out.println("Error processing file:" + file);
		}
    }
}