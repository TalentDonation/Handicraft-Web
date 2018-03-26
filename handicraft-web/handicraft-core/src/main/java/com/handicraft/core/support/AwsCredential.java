package com.handicraft.core.support;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:credential-amazon-s3.yml")
public class AwsCredential {
    @Value("${bucket}")
    private String bucket;

    @Value("${access-key}")
    private String accessKey;

    @Value("${secret-key}")
    private String secretKey;
}
