package com.example.dataprocessor;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SparkTransferElastic implements SparkLoader {
    private final TransferConfiguration transferConfiguration;
    private final String connectionString;

    public SparkTransferElastic(TransferConfiguration transferConfiguration) {
        this.transferConfiguration = transferConfiguration;
        this.connectionString = transferConfiguration.getElasticHost() + ":" + transferConfiguration.getElasticPort();
    }

    @Override
    public void loadRoomsFromCsv() {
        SparkConf sparkConf = new SparkConf().setAppName("Spark CSV loader for Elastic");
        sparkConf.setMaster("local[" + transferConfiguration.getFlows() + "]");
        sparkConf.set("es.nodes", connectionString);

        SparkSession sesBuild = SparkSession.builder().config(sparkConf).getOrCreate();

        String[] columnsAr = {"id", "name", "description", "neighborhood_overview", "host_location", "host_about",
                "host_neighbourhood",
                "room_type",
                "price",
                "reviews_per_month"};

        Column[] superhero = new Column[columnsAr.length];
        for (int i = 0; i < columnsAr.length; i++) {
            superhero[i] = functions.col(columnsAr[i]);
        }

        JavaRDD<Map<String, String>> rdd = sesBuild.read()
                .format("com.databricks.spark.csv")
                .option("sep", "ஸ")
                .option("header", "true")
                .option("quote", "\"")
                .option("escape", "\\")
                .option("multiline", true)
                .option("inferSchema", "true")
                .load(transferConfiguration.getRoomsPath())
                .select(superhero)
                .withColumnRenamed("neighborhood_overview", "neighborhoodOverview")
                .withColumnRenamed("host_location", "location")
                .withColumnRenamed("host_about", "about")
                .withColumnRenamed("room_type", "type")
                .withColumnRenamed("reviews_per_month", "reviewsPerMonth")
                .withColumnRenamed("host_neighbourhood", "neighbourhood")
                .withColumn("price", functions.expr("substring(price, 2, length(price))"))
                .toJavaRDD()
                .map(row -> {
                    Map<String, String> resultMap = new HashMap<>();
                    for (int i = 0; i < columnsAr.length; i++) {
                        resultMap.put(columnsAr[i], row.getString(i));
                    }
                    return resultMap;
                })
                .filter(row -> (row.get("id") == null ? 0 : row.get("id").length()) < 50);

        JavaEsSpark.saveToEs(rdd, transferConfiguration.getIndex(), Collections.singletonMap("es.mapping.id", "id"));
        sesBuild.stop();
    }

    @Override
    public void loadClientsFromXml() {
        SparkConf sparkConf = new SparkConf().setAppName("Spark XML loader for Elastic");
        sparkConf.setMaster("local[" + transferConfiguration.getFlows() + "]");
        sparkConf.set("es.nodes", connectionString);
        sparkConf.set("es.http.timeout", "5m");
        sparkConf.set("es.http.retries", "10");
        sparkConf.set("es.batch.write.retry.count", "10");
        sparkConf.set("es.batch.write.retry.wait", "30s");

        SparkSession sesBuild = SparkSession.builder().config(sparkConf).getOrCreate();

        JavaRDD<Map<String, Object>> rdd = sesBuild
                .read()
                .format("com.databricks.spark.xml")
                .option("rowTag", "row")
                .load(transferConfiguration.getClientsPath())
                .toJavaRDD()
                .map(row -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("name", row.getAs("_DisplayName"));
                    resultMap.put("creationDate", row.getAs("_CreationDate"));
                    resultMap.put("_id", UUID.randomUUID().toString());
                    return resultMap;
                });

        JavaEsSpark.saveToEs(rdd, transferConfiguration.getIndex(), Collections.singletonMap("es.mapping.id", "_id"));
        sesBuild.stop();
    }
}

