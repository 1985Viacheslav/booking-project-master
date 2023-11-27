package com.example.dataprocessor;

import com.mongodb.spark.MongoSpark;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.bson.Document;

import java.util.UUID;

public class SparkTransferMongo implements SparkLoader {
    private final TransferConfiguration transferConfiguration;
    private final String connectionString;

    public SparkTransferMongo(TransferConfiguration transferConfiguration) {
        this.transferConfiguration = transferConfiguration;
        this.connectionString = "mongodb://" + transferConfiguration.getMongoHost() + ":" + transferConfiguration.getMongoPort() + "/" + transferConfiguration.getMongoDatabase();
    }

    @Override
    public void loadClientsFromXml() {
        SparkConf sparkConf = new SparkConf().setAppName("Spark XML loader for Mongo");
        sparkConf.setMaster("local[" + transferConfiguration.getFlows() + "]");
        sparkConf.set("spark.mongodb.output.uri", connectionString + ".Clients");
        sparkConf.set("spark.mongodb.output.database", transferConfiguration.getMongoDatabase());
        sparkConf.set("spark.mongodb.output.collection", "Clients");
        sparkConf.set("spark.mongodb.output.maxBatchSize", "1024");

        SparkSession sesBuild = SparkSession.builder().config(sparkConf).getOrCreate();

        JavaRDD<Document> rdd = sesBuild
                .read()
                .format("com.databricks.spark.xml")
                .option("rowTag", "row")
                .load(transferConfiguration.getClientsPath())
                .toJavaRDD()
                .map(row -> new Document(
                        "name", row.getAs("_DisplayName")
                ));

        MongoSpark.save(rdd);
        sesBuild.stop();
    }

    @Override
    public void loadRoomsFromCsv() {
        SparkConf sparkConf = new SparkConf().setAppName("Spark CSV loader for Mongo");
        sparkConf.setMaster("local[" + transferConfiguration.getFlows() + "]");
        sparkConf.set("spark.mongodb.output.uri", connectionString + ".Rooms");
        sparkConf.set("spark.mongodb.output.database", transferConfiguration.getMongoDatabase());
        sparkConf.set("spark.mongodb.output.collection", "Rooms");
        sparkConf.set("spark.mongodb.output.maxBatchSize", "1024");

        SparkSession sesBuild = SparkSession.builder().config(sparkConf).getOrCreate();

        String[] columnsAr = {
                "id", "name", "description", "neighborhood_overview", "host_location", "host_about",
                "host_neighbourhood", "room_type", "price", "reviews_per_month"
        };

        Column[] superhero = new Column[columnsAr.length];
        for (int i = 0; i < columnsAr.length; i++) {
            superhero[i] = functions.col(columnsAr[i]);
        }

        Dataset<Row> df = sesBuild.read()
                .format("com.databricks.spark.csv")
                .option("sep", "ஸ")
                .option("header", "true")
                .option("quote", "\"")
                .option("escape", "\\")
                .option("multiline", true)
                .option("inferSchema", "true")
                .load(transferConfiguration.getRoomsPath())
                .select(superhero)
                .withColumnRenamed("id", "_id")
                .withColumnRenamed("neighborhood_overview", "neighborhoodOverview")
                .withColumnRenamed("host_location", "location")
                .withColumnRenamed("host_about", "about")
                .withColumnRenamed("room_type", "type")
                .withColumnRenamed("reviews_per_month", "reviewsPerMonth")
                .withColumnRenamed("host_neighbourhood", "neighbourhood")
                .withColumn("price", functions.expr("substring(price, 2, length(price))"));
                df.show();

        MongoSpark.save(df);
        sesBuild.stop();
    }
}

