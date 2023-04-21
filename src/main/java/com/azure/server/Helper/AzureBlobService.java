package com.azure.server.Helper;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AzureBlobService {
    @Autowired
    BlobServiceClient blobServiceClient;

    @Autowired
    FileUploadHelper fileUploadHelper;

    @Autowired
    BlobContainerClient blobContainerClient;

    public String upload(MultipartFile multipartFile)
            throws IOException {
// String fileName = fileUploadHelper.uploadFile(multipartFile);
// String msg = "";
// if (fileName.equals("")) {
// msg = "Unable to load book img, please try again latter. Although, ";
// } else {
// msg="no";
// }

        InputStream bufferedIn = new BufferedInputStream(multipartFile.getInputStream());
// Todo UUID
        BlobClient blob = blobContainerClient
                .getBlobClient(multipartFile.getOriginalFilename());
        blob.upload(bufferedIn,
                multipartFile.getSize(), true);

        return multipartFile.getOriginalFilename();
    }

    public byte[] getFile(String fileName)
            throws URISyntaxException {

        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.download(outputStream);
        final byte[] bytes = outputStream.toByteArray();
        return bytes;

    }

    public List<String> listBlobs() {

        PagedIterable<BlobItem> items = blobContainerClient.listBlobs();
        List<String> names = new ArrayList<String>();
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
