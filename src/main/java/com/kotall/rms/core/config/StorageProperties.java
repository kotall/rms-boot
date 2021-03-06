package com.kotall.rms.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "litemall.storage")
@Data
public class StorageProperties {
    private String active;
    private Local local;
    private Aliyun aliyun;
    private Tencent tencent;
    private Qiniu qiniu;

    @Data
    public static class Local {
        private String address;
        private String storagePath;
    }

    @Data
    public static class Tencent {
        private String secretId;
        private String secretKey;
        private String region;
        private String bucketName;
    }

    @Data
    public static class Aliyun {
        private  String endpoint;
        private  String accessKeyId;
        private  String accessKeySecret;
        private  String bucketName;
    }

    @Data
    public static class Qiniu {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucketName;
    }
}
