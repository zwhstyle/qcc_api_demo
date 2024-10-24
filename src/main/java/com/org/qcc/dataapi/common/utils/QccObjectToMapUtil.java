package com.org.qcc.dataapi.common.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.util.ReflectionUtils;

public class QccObjectToMapUtil {

  /**
   * 将任何 Java 对象转换为 LinkedHashMap
   *
   * @param obj 需要转换的对象
   * @return 转换后的 LinkedHashMap
   */
  public static Map<String, Object> convertToMap(Object obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    return convertToMap(map, obj);
  }

  /**
   * 将任何 Java 对象转换为 LinkedHashMap
   *
   * @param obj 需要转换的对象
   * @return 转换后的 LinkedHashMap
   */
  public static Map<String, Object> convertToMap(Map<String, Object> map, Object obj) {
    // 获取对象的所有字段
    Field[] fields = obj.getClass().getDeclaredFields();

    for (Field field : fields) {
      // 设置可访问私有字段
      ReflectionUtils.makeAccessible(field);
      try {
        // 获取字段名和字段值
        String key = field.getName();
        Object value = field.get(obj);
        // 如果值不为空，则添加到 map 中
        if (value != null) {
          map.put(key, value.toString());
        }
      } catch (IllegalAccessException e) {
        // 处理异常
        e.printStackTrace();
      }
    }
    return map;
  }
}
