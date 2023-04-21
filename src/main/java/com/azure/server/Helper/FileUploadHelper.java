package com.azure.server.Helper;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class FileUploadHelper {
    @Autowired
    BlobServiceClient blobServiceClient;

    @Value("${blobimgendpoint}")
    private String host;

    @Autowired
    BlobContainerClient blobContainerClient;

    final String fileExtension = ".jpg";

    public String uploadFile(MultipartFile multipartFile) {
        String fileName = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
        Date date = new Date();

        fileName = dateFormat.format(date);

        try {

            if(!blobContainerClient.exists()) return "";
            BlobClient blob = blobContainerClient
                    .getBlobClient(fileName+fileExtension);
            blob.upload(multipartFile.getInputStream(),
                    multipartFile.getSize(), true);
        } catch (Exception e) {
            fileName = "";
            e.printStackTrace();
        }
        return fileName;
    }

    public byte[] getFile(String fileName) {
        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.download(outputStream);
        final byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    public List<String> listBlobs() {
        PagedIterable<BlobItem> items = blobContainerClient.listBlobs();
        List<String> names = new ArrayList<>();
        for (BlobItem item : items) {
            names.add(item.getName());
        }
        return names;

    }

    public Boolean deleteBlob(String blobName) {
        BlobClient blob = blobContainerClient.getBlobClient(blobName);
        blob.delete();
        return true;
    }
}