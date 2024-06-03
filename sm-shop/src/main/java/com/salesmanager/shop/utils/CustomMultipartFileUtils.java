package com.salesmanager.shop.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFileUtils  implements MultipartFile {
	private final File file;
    private final String originalFileName;

    public CustomMultipartFileUtils(File file, String originalFileName) {
        this.file = file;
        this.originalFileName = originalFileName;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        // You can implement content type detection logic here if needed
        return null;
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return toByteArray(file);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (InputStream inputStream = getInputStream();
             OutputStream outputStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private byte[] toByteArray(File file) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
             BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len;
            while ((len = in.read(buffer, 0, buf_size)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }
}

