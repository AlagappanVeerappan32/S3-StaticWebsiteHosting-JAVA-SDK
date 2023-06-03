package org.example;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


import com.amazonaws.services.s3.model.*;

import java.io.File;



public class S3creation {

    /**
     * Creating client using the credential and region
     */

    public AmazonS3 s3Credential(AWSCredentials credentials)
    {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.CA_CENTRAL_1)
                .build();
        return s3;
    }

    public Bucket createBucket(AmazonS3 s3, String bucketName) {
        Bucket bucket = null;
        if (s3.doesBucketExistV2(bucketName)) {
            System.out.println("Bucket " + bucketName + " already exists.");
        } else {
            try {
                bucket = s3.createBucket(bucketName);
                System.out.println("Bucket " + bucketName + " is created.");
                String bucketLocation = s3.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);

            } catch (AmazonS3Exception e) {
                System.out.println(e.getErrorMessage());
            }
        }
        return bucket;
    }

    /**
     * PutObject takes 3 input - bucket name , key and file to upload
     * @param s3
     * @param bucketName, filePath
     */
    public void putFiles(AmazonS3 s3, String bucketName, String keyName, String filePath) {

        if (s3.doesBucketExistV2(bucketName)) {
            try {
                s3.putObject(bucketName, keyName, new File(filePath));
            } catch (AmazonServiceException e) {
                System.out.println(e.getErrorMessage());
            }
            System.out.println("File uploaded successfully!");
        }else {
            System.out.println("Bucket doesn't exist");
        }
    }

    public void enableHosting(AmazonS3 s3, String bucketName) {
        BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration(Constants.KEY_NAME);
        s3.setBucketWebsiteConfiguration(bucketName, configuration);
        System.out.println("bucket hosting enabled");
    }

    public void turnOffPublicAccess(AmazonS3 s3, String bucketName)   {
        s3.setPublicAccessBlock(new SetPublicAccessBlockRequest()
                .withBucketName(bucketName)
                .withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration()
                        .withBlockPublicAcls(false)
                        .withIgnorePublicAcls(false)
                        .withBlockPublicPolicy(false)
                        .withRestrictPublicBuckets(false)));
        System.out.println("bucket public access turned off");
    }

    public void changeBucketPolicy(AmazonS3 s3, String bucketName) {
        s3.setBucketPolicy(bucketName,Constants.BUCKET_POLICY);
        System.out.println("bucket policy changed");
    }

    public void deleteBucket(AmazonS3 s3, String bucketName) {
        try {
            ListObjectsV2Request listRequest = new ListObjectsV2Request().withBucketName(bucketName);
            ListObjectsV2Result listResult = s3.listObjectsV2(listRequest);

            while (listResult.getObjectSummaries().size() > 0) { // checking size
                DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName)  // to delete multiple objects
                        .withKeys(listResult.getObjectSummaries().stream()
                                .map(S3ObjectSummary::getKey)  // passing object keys
                                .toArray(String[]::new));

                s3.deleteObjects(deleteRequest);  // deletes the object
                listResult = s3.listObjectsV2(listRequest); // gets next object
            }

            s3.deleteBucket(bucketName);  // delete the bucket
            System.out.println("Bucket emptied and deleted successfully!");

        } catch (AmazonServiceException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}






