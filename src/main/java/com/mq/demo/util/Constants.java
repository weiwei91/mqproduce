package com.mq.demo.util;

/**
 * @author： weiwei10
 * @date： 2020/2/19 12:09
 * @description：
 * @modifiedBy：
 * @version: 1.0.0
 */
public class Constants {



    public static final String PARAM_CAMERA_INDEX_CODES = "cameraIndexCodes";
    public static final String PARAM_TRANS_MODE = "transmode";
    public static final String PARAM_STREAM_TYPE = "streamType";
    public static final String DEFAULT_VALUE_TRANS_MODE = "0";
    public static final String DEFAULT_VALUE_STREAM_TYPE = "0";

    public interface Protocol {
        String HTTP = "http";
        String HTTPS = "https";
    }
    //缓存存储key值
    public static final String STORE_KEY = "storeInfo";

    //队列相关常量
    public static final String QUEUE_KEY_UPLOAD_PICTURE = "uploadPicture";
    public static final String QUEUE_KEY_UPLOAD_OBJECT = "uploadObject";
    public static final String QUEUE_KEY_IMPORT_DEVICE = "importDevice";









}
