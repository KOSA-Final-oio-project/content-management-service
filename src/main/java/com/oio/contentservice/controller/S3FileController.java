package com.oio.contentservice.controller;

import com.oio.contentservice.dto.upload.UploadFileDto;
import com.oio.contentservice.service.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class S3FileController {

    private final S3FileService s3FileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Map<String, String>> upload(UploadFileDto uploadFileDto) {

        log.info(uploadFileDto);

        if (uploadFileDto.getFiles() != null) {

            final List<Map<String,String>> list = new ArrayList<>();

            uploadFileDto.getFiles().forEach(multipartFile -> {

                try {
                    Map<String, String> map =  s3FileService.uploadS3File(multipartFile);
                    list.add(map);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }); //end forEach
            return list;
        } // end if

        return null;
    }

    @DeleteMapping("/remove/{fileName}")
    public void removeFile(@PathVariable("fileName") String fileName) {

        s3FileService.removeS3File(fileName);

    }

}
