package com.softserverinc.edu.forms;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * For fileImage uploading in form processing
 */

public class FileUploadForm {

    private long userId;

    private byte[] fileImage;

    private String fileName;

    private String encodedImage;

    public byte[] getFileImage() {
        return fileImage;
    }

    public void setFileImage(byte[] fileImage) {
        this.fileImage = fileImage;
    }

    public String getEncodedImage() {
        if (fileImage != null)
            encodedImage = new String(new Base64().encode(getFileImage()));
        else
            encodedImage = "";
        return encodedImage;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName.length() >= 64)
            fileName = fileName.substring(fileName.length() - (fileName.length() - 60), fileName.length() - 1);
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
