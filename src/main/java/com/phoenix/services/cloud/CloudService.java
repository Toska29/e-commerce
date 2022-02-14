package com.phoenix.services.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudService {
//    Map<?, ?> upload(File file, Map<?, ?> params) throws IOException;
//    Map<?, ?> upload(MultipartFile file, Map<?, ?> params) throws IOException;
    Map<?, ?> upload(byte[] bytes, Map<?, ?> params) throws IOException;

}
