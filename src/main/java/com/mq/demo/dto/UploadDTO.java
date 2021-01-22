package com.mq.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadDTO {
    private String id;
    private String path;
    private String type;

}
