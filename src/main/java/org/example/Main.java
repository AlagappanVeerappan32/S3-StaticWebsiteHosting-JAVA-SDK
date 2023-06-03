package org.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;



public class Main {
    public static void main(String[] args) {

        /*
         Adding credentials
         */
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAYI7GUN6MNHHDCHFZ",
                "26NE7LwL1V01UWh4LnF/0NJtla5njoVmg1eAq48S"
        );

        S3creation s3creation = new S3creation();
        AmazonS3 s3Cred = s3creation.s3Credential(credentials);
        s3creation.createBucket(s3Cred,Constants.BUCKET_NAME);
        s3creation.putFiles(s3Cred,Constants.BUCKET_NAME,Constants.KEY_NAME,Constants.LOCAL_FILE_PATH);
        s3creation.enableHosting(s3Cred,Constants.BUCKET_NAME);
        s3creation.turnOffPublicAccess(s3Cred,Constants.BUCKET_NAME);
        s3creation.changeBucketPolicy(s3Cred,Constants.BUCKET_NAME);
//        s3creation.deleteBucket(s3Cred,Constants.BUCKET_NAME);
    }
}