package com.ttcsolutions.kwapp.commons.thirdparty.lxfile;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FormDataConverter {

    private static final String BOUNDARY = Long.toString(System.currentTimeMillis());
    public static final String FORM_DATA_CONTENT_TYPE = String.format("multipart/form-data; boundary=%s", BOUNDARY);
    private static final byte[] DOUBLE_NEW_LINE = "\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] SEPARATOR = ("--" + BOUNDARY + "\r\nContent-Disposition: form-data; name=")
            .getBytes(StandardCharsets.UTF_8);
    private static final byte[] END = ("--" + BOUNDARY + "--").getBytes(StandardCharsets.UTF_8);
    private static final String NEW_LINE = "\r\n";

    public static List<byte[]> convert(Map<String, Object> data) {
        var byteArrays = new ArrayList<byte[]>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            byteArrays.add(SEPARATOR);
            if (entry.getValue() instanceof MultipartFile) {
                byteArrays.add(getFileSeparator(entry.getKey(), ((MultipartFile) entry.getValue()).getOriginalFilename()));
                byteArrays.add(Objects.requireNonNull(((MultipartFile) entry.getValue()).getContentType()).getBytes(StandardCharsets.UTF_8));
                byteArrays.add(DOUBLE_NEW_LINE);
                try {
                    byteArrays.add(((MultipartFile) entry.getValue()).getBytes());
                }catch (IOException e){
                    throw new BusinessException(ErrorCode.UPLOAD_FILE_ERROR, "Error uploading file");
                }
                byteArrays.add(NEW_LINE.getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(
                        ("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + NEW_LINE).getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays.add(END);
        return byteArrays;
    }
    private static byte[] getFileSeparator(String fileField, String fileName) {
        return ("\"" + fileField + "\"; filename=\""
                + fileName + "\"\r\nContent-Type: ").getBytes(StandardCharsets.UTF_8);
    }
}
