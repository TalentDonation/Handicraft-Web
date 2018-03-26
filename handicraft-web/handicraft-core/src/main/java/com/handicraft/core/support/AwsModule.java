package com.handicraft.core.support;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class AwsModule {
    private final AmazonS3 amazonS3;
    private final AwsCredential awsCredential;
    private final String dir = "furniture";

    @Autowired
    public AwsModule(AwsCredential awsCredential) {
        this.awsCredential = awsCredential;
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsCredential.getAccessKey(), awsCredential.getSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion(Regions.AP_NORTHEAST_2).build();
    }

    public long upload(long fid, String fileName, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        StringBuffer path = new StringBuffer(dir)
                .append("/").append(fid)
                .append("/").append(fileName);
        PutObjectResult putObjectResult = amazonS3.putObject(awsCredential.getBucket(), path.toString(), inputStream, objectMetadata);
        return putObjectResult.getMetadata().getContentLength();
    }

    public InputStream load(long fid, String fileName) {
        StringBuffer path = new StringBuffer(dir)
                .append("/").append(fid)
                .append("/").append(fileName);
        return amazonS3.getObject(awsCredential.getBucket(), path.toString()).getObjectContent();
    }

    public long change(long fid, String originalFileName, String newFileName, InputStream inputStream) {
        remove(fid, originalFileName);
        return upload(fid, newFileName, inputStream);
    }

    public void remove(long fid, String fileName) {
        StringBuffer path = new StringBuffer(dir)
                .append("/").append(fid)
                .append("/").append(fileName);
        amazonS3.deleteObject(awsCredential.getBucket(), path.toString());
    }

    private void resize() {
        ;
    }
}
