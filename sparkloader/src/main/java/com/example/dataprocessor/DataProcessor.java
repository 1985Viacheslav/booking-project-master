package com.example.dataprocessor;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DataProcessor {

    public static void main(String[] args) {
//        if (args.length < 3) {
//            System.out.println("There are not enough command line arguments. The program cannot work.");
//            return;
//        }

        String fileWithPropertiesName = "src\\main\\resources\\application.properties";
                //= args[0];
        File fileWithProperties = new File(fileWithPropertiesName);

        if (!fileWithProperties.exists()) {
            System.out.println("File with properties " + fileWithProperties.getAbsoluteFile() + " doesn't exist. The program cannot work.");
            return;
        }

        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream(fileWithProperties)) {
            prop.load(input);
        } catch (IOException ex) {
            System.out.println("File with properties is not correct. " + ex.getLocalizedMessage() + ". The program cannot work.");
            return;
        }

        TransferConfiguration transferConfiguration;

        try {
            transferConfiguration = TransferConfiguration.toTransferConfiguration(prop);
        } catch (NullPointerException ex) {
            System.out.println("The program cannot find some needed field in the configuration file. " + ex.getLocalizedMessage());
            return;
        }

        String transferTarget = "mongo";
                //= args[1];

        SparkLoader sparkLoader;
        if ("mongo".equals(transferTarget)) {
            sparkLoader = new SparkTransferMongo(transferConfiguration);
        } else if ("elastic".equals(transferTarget)) {
            sparkLoader = new SparkTransferElastic(transferConfiguration);
        } else {
            throw new IllegalArgumentException("This transfer target (" + transferTarget + ") is not supported.");
        }

        String entityToTransfer = "clients";
                //= args[2];

        if ("clients".equals(entityToTransfer)) {
            sparkLoader.loadClientsFromXml();
        } else if ("rooms".equals(entityToTransfer)) {
            sparkLoader.loadRoomsFromCsv();
        } else {
            System.out.println("This option (" + entityToTransfer + ") is not supported");
        }
    }
}

