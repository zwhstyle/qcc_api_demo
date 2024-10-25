package com.org.qcc.dataapi.config.customer.impl;

import com.org.qcc.dataapi.config.customer.AbstractErrorCodeLoader;
import com.org.qcc.dataapi.config.customer.ApiStatusCode;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

@Getter
public class DefaultErrorCodeLoader extends AbstractErrorCodeLoader {

  protected static final Logger logger = LoggerFactory.getLogger(DefaultErrorCodeLoader.class);
  private final Map<String, ApiStatusCode> errorCodeMap = new LinkedHashMap<>();

  public DefaultErrorCodeLoader(String path) {
    loadErrorCodes(path);
  }

  private void loadErrorCodes(String path) {
    try (BufferedReader input = new BufferedReader(new InputStreamReader(getFileInputStream(path), StandardCharsets.UTF_8))) {
      List<String> lines = readLines(new ArrayList<>(), input);
      for (String line : lines) {
        if (!StringUtils.isEmpty(line)) {
          ApiStatusCode statusCode = parseErrorCode(line);
          if (Objects.nonNull(statusCode)) {
            errorCodeMap.put(statusCode.getSubCode(), statusCode);
          }
        }
      }
    } catch (IOException e) {
      logger.error("An error occurred while trying to load the error codes from file: {}", path, e);
    }
  }

  private ApiStatusCode parseErrorCode(String line) {
    if ((line.startsWith("Y=") || line.startsWith("N=")) && !line.startsWith("#")) {
      String[] values = line.split("=");
      if (values.length <= 5) {
        logger.error("Invalid error code format: {}", line);
        return null;
      }
      String codeStatus = values[0].trim();
      String mainCode = values[1].trim();
      String mainCodeMsg = values[2].trim();
      String subCode = values[3].trim();
      String subMsg = values[4].trim();
      if (values.length == 6) {
        String needAlertConfig = values[5].trim();
        boolean needAlert = needAlertConfig.equals("Y");
        return new ApiStatusCode(mainCode, mainCodeMsg, subCode, subMsg, codeStatus, needAlert);
      } else {
        return new ApiStatusCode(mainCode, mainCodeMsg, subCode, subMsg, codeStatus);
      }
    }
    return null;
  }

  public <T extends Collection<String>> T readLines(T collection, BufferedReader reader) throws IOException {
    String line;
    while (true) {
      line = reader.readLine();
      if (line == null) {
        break;
      }
      collection.add(line);
    }
    return collection;
  }

  public static InputStream getFileInputStream(String filePath) throws IOException {
    ClassPathResource resource = new ClassPathResource("classpath".concat(filePath));
    if (!resource.exists()) {
      File file = ResourceUtils.getFile("classpath".concat(filePath));
      if (!file.exists()) {
        Resource fileResource = getFileInputStreamByJarFile(filePath);
        return fileResource.getInputStream();
      } else {
        return new FileSystemResource(file).getInputStream();
      }
    } else {
      return resource.getInputStream();
    }
  }

  public static Resource getFileInputStreamByJarFile(String filePath) {
    return new ClassPathResource(filePath);
  }

  @Override
  public ApiStatusCode getCode(String code) {
    return errorCodeMap.get(code);
  }

  @Override
  public Map<String, ApiStatusCode> getApiStatusCodeMap() {
    return Collections.unmodifiableMap(errorCodeMap);
  }
}
