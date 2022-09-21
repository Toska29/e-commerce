package com.phoenix.services.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Slf4j
class CloudServiceImplTest {

    @Autowired
    @Qualifier("cloudinary-service")
    CloudService cloudService;

    @Autowired
    Cloudinary cloudinary;

    @BeforeEach
    void setUp() {

    }
    @Test
    @DisplayName("")
    void cloudinaryObjectInstanceTest(){
        assertThat(cloudinary).isNotNull();
    }

    @Test
    void uploadToCloudinaryTest() throws IOException {
        Path file = Paths.get("src/test/resources/ahmad.jpeg");
        assertThat(file.toFile().exists()).isTrue();
        assertThat(file).isNotNull();
        Map<?, ?> uploadResult = cloudService.upload(Files.readAllBytes(file), ObjectUtils.emptyMap());
        log.info("upload result to cloud -> {}", uploadResult);
        assertThat(uploadResult.get("url")).isNotNull();
    }

    @Test
    void uploadMultipartToCloudinaryTest() throws IOException{
        //load the file
        Path path = Paths.get("src/test/resources/ahmad.jpeg");
        assertThat(path.toFile().exists()).isTrue();
        assertThat(path.getFileName().toString()).isEqualTo("ahmad.jpeg");

        //convert to Multipart
        MultipartFile multipartFile = new MockMultipartFile(path.getFileName().toString(),
                path.getFileName().toString(), "img/jpeg", Files.readAllBytes(path));
        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();

        //upload to cloud
        Map<?, ?> uploadResult = cloudService.upload(multipartFile.getBytes(),
                ObjectUtils.asMap("overwrite", true
        ));
        assertThat(uploadResult.get("url")).isNotNull();

    }
}