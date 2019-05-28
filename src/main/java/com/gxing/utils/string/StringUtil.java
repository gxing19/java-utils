package com.gxing.utils.string;

import org.springframework.util.StringUtils;

import java.util.*;

public class StringUtil {

    /**
     * 将字符串转换成set集合类
     * 分隔符是任意空白字符
     */
    public static Set<String> parseParameterList(String values) {
        Set<String> result = new TreeSet<String>();
        if (values != null && values.trim().length() > 0) {
            // the spec says the scope is separated by spaces
            String[] tokens = values.split("[\\s+]");//匹配任意空白字符
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }

    /**
     * 把集合转化成指定形式的字符串
     */
    public static String formatParameterList(Collection<String> value) {
        return value == null ? null : StringUtils.collectionToDelimitedString(value, ",");//指定分隔符
    }

    /**
     * 从query的字符串中抽取需要的键值对存入map中
     * query的形式name=god&password=111&method=up
     */
    public static Map<String, String> extractMap(String query) {
        Map<String, String> map = new HashMap<String, String>();
        Properties properties = StringUtils.splitArrayElementsIntoProperties(
                StringUtils.delimitedListToStringArray(query, "&"), "=");
        if (properties != null) {
            for (Object key : properties.keySet()) {
                map.put(key.toString(), properties.get(key).toString());
            }
        }
        return map;
    }

    /**
     * 比较两个集合是否相等
     */
    public static boolean containsAll(Set<String> target, Set<String> members) {
        target = new HashSet<String>(target);
        target.retainAll(members);//取两个集合的交集
        return target.size() == members.size();
    }
}
