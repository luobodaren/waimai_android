package com.life.base.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        /*Gson gson = new Gson();*/
        List<T> list = new ArrayList<T>();
        JsonArray array = JsonParser.parseString(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    // -------

    /**
     * 按章节点得到相应的内容
     *
     * @param jsonString json字符串
     * @param note       节点
     * @return 节点对应的内容
     */
    public static Integer getIntNoteJsonString(String jsonString, String note) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        if (TextUtils.isEmpty(note)) {
            throw new RuntimeException("note标签不能为空");
        }
        JsonElement element = JsonParser.parseString(jsonString);
        if (element.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        return element.getAsJsonObject().get(note) == null ?
                null : element.getAsJsonObject().get(note).getAsInt();
    }

    /**
     * 按章节点得到相应的内容
     *
     * @param jsonString json字符串
     * @param note       节点
     * @return 节点对应的内容
     */
    public static String getStringNoteJsonString(String jsonString, String note) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        if (TextUtils.isEmpty(note)) {
            throw new RuntimeException("note标签不能为空");
        }
        JsonElement element = JsonParser.parseString(jsonString);
        if (element.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        return element.getAsJsonObject().get(note) == null ?
                null : element.getAsJsonObject().get(note).toString();
    }

    /**
     * 按照json字符串获取JsonObject
     *
     * @param jsonString
     * @return
     */
    public static JsonObject parserJsonToJsonObject(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        if (!jsonElement.isJsonObject()) {
            throw new RuntimeException("json字符不是一个json对象");
        }
        return jsonElement.getAsJsonObject();
    }

    /**
     * 按照节点得到节点内容，然后传化为相对应的bean数组
     *
     * @param jsonString 原json字符串
     * @param note       节点标签
     * @param beanClazz  要转化成的bean class
     * @return 返回bean的数组
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString, String note, Class<T> beanClazz) {
        String noteJsonString = getStringNoteJsonString(jsonString, note);
        return parserJsonToArrayBeans(noteJsonString, beanClazz);
    }

    /**
     * 按照节点得到节点内容，转化为一个数组
     *
     * @param jsonString json字符串
     * @param beanClazz  集合里存入的数据对象
     * @return 含有目标对象的集合
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString, Class<T> beanClazz) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        if (!jsonElement.isJsonArray()) {
            throw new RuntimeException("json字符不是一个数组对象集合");
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<T> beans = new ArrayList<T>();
        for (JsonElement jsonElement2 : jsonArray) {
            T bean = gson.fromJson(jsonElement2, beanClazz);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 按照节点得到节点内容，转化为一个对象
     *
     * @param jsonString json字符串
     * @param note       json标签
     * @param clazzBean  要封装成的目标对象
     * @return 含有目标对象的集合
     */
    public static <T> T parserJsonToBean(String jsonString, String note, Class<T> clazzBean) {
        String noteJsonString = getStringNoteJsonString(jsonString, note);
        return parserJsonToBean(noteJsonString, clazzBean);
    }

    /**
     * 把相对应节点的内容封装为对象
     *
     * @param jsonString json字符串
     * @param clazzBean  要封装成的目标对象
     * @return 目标对象
     */
    public static <T> T parserJsonToBean(String jsonString, Class<T> clazzBean) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("json字符串为空");
        }
        if (!jsonElement.isJsonObject()) {
            throw new RuntimeException("json不是一个对象");
        }
        return gson.fromJson(jsonElement, clazzBean);
    }

}
