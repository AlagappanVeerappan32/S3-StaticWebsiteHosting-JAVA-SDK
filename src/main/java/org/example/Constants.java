package org.example;

public class Constants {
    public static final String BUCKET_NAME = "my-alex-bucket-32";
    public static final String KEY_NAME = "index.html";
    public static final String LOCAL_FILE_PATH = "/Users/alagappanveerappan/Downloads/Serverless/Assignment1/Code/StorageAWS/src/main/java/org/example/index.html";

    public static final String BUCKET_POLICY = "{\n" +
            "  \"Id\": \"Policy1685142350409\",\n" +
            "  \"Version\": \"2012-10-17\",\n" +
            "  \"Statement\": [\n" +
            "    {\n" +
            "      \"Sid\": \"Stmt1685142348339\",\n" +
            "      \"Action\": [\n" +
            "        \"s3:GetObject\"\n" +
            "      ],\n" +
            "      \"Effect\": \"Allow\",\n" +
            "      \"Resource\": \"arn:aws:s3:::" + BUCKET_NAME + "/*\",\n" +
            "      \"Principal\": \"*\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
