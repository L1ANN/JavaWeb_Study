package json;

import domain.Person;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author:L1ANN
 * @Description: JSON的转换和解析
 * @Date:Created in 12:18 2017/11/10
 * @Modified By:
 */
public class JsonDataConvert {

    /**
     * 将数据转换成JSON字符串
     *
     * @param key   字符串标识
     * @param value 要转换的对象
     * @return JSON字符串
     */
    public static String createJsonString(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject.toString();
    }

    /**
     * 将Array解析成Json串，使用JSONArray解析
     */
    @Test
    public void convert_Arr(){

        String[] str = {"Jack","Tom","90","true"};
        JSONArray json = JSONArray.fromObject(str);
        System.out.println(json.toString());

    }

    /**
     * 将Object解析成Json串，使用JSONObject解析
     */
    @Test
    public void convert_Object() {
        /**
         * 使用JSONObject静态方法转换成JSON（不带标识）
         * 使用JSONObject方法转换成JSON（带标识）
         */
        Person person = new Person();
        JSONObject jsonObject = JSONObject.fromObject(person);
        System.out.println(jsonObject);
        System.out.println((createJsonString("person", person)));

        //用户对象集合转换
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person(1, "aab");
        Person person2 = new Person(2, "abb");
        personList.add(person1);
        personList.add(person2);
        System.out.println((createJsonString("personList", personList)));

        //字符串集合转换
        List<String> stringList = new ArrayList<>();
        stringList.add("X-rapido");
        stringList.add("niuyue");
        System.out.println((createJsonString("stringList", stringList)));

        //list中map集合转换
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String,String> map = new HashMap<>();
            map.put("编号","id_"+i);
            map.put("地址","Name_"+i);
            mapList.add(map);
        }
        System.out.println((createJsonString("mapList",mapList)));

    }

    /**
     * 使用JsonConfig来过滤属性，适用于Object
     */
    @Test
    public void convert_Config(){

        JsonConfig config = new JsonConfig();
        //指定在转换时不包括哪些属性
        config.setExcludes(new String[]{"name"});
        Person person = new Person(001,"Jack");
        //转换时传入JsonConfig对象
        JSONObject jsonObject = JSONObject.fromObject(person,config);
        System.out.println(jsonObject);
    }

    /**
     * 将Json串转换成Array
     */
    @Test
    public void reconvert_Arr(){
        //1.首先用jsonArray接收Json字符串
        JSONArray jsonArray = JSONArray.fromObject("[89,90,99]");
        Object array = JSONArray.toArray(jsonArray);
        System.out.println(array);
        System.out.println(Arrays.asList((Object[])array));
    }

    /**
     * 将Json串转成JavaBean/Map
     */
    @Test
    public void reconvert_Object(){
        /**
         * 将Json形式的字符串转换成Map
         */
        String str = "{\"name\":\"Tom\",\"age\":90}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Map<String,Object> map = (Map<String,Object>) JSONObject.toBean(jsonObject,Map.class);
        System.out.println(map);

        /**
         * 将Json形式的字符串转换成JavaBean
         */
        Person person = (Person)JSONObject.toBean(jsonObject,Person.class);
        System.out.println(person);
    }
}
