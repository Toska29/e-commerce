package com.phoenix.services.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Service("cloudinary-service")
public class CloudinaryServiceImpl implements CloudService {

    @Autowired
    Cloudinary cloudinary;

//    @Override
//    public Map<?, ?> upload(File file, Map<?, ?> params) throws IOException {
//
//        return cloudinary.uploader().upload(Files.readAllBytes(file.toPath()), params);
//
//    }

    @Override
    public Map<?, ?> upload(byte[] bytes, Map<?, ?> params) throws IOException {
        return cloudinary.uploader().upload(bytes, params);
    }
}
