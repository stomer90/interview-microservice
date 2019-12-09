package com.stormer.common.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RequestLog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLog {

    @Id
    private String id;

    private String path;

    private String method;

    private String clazz;

    private String request;

    private String header;

    private String response;

    private long responseTime;
}
