package net.candorservices.java.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of a config from Candor's API.
 * @author Seailz
 * @version 1.0.0
 */
public class Config {

    private Map<String, Object> values;
    private JSONObject json;

    public Config(JSONObject in) {
        this.values = in.toMap();
        this.json = in;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * This method attempts to replicate the behavior of YAML or JS objects. Basically, you can access a value in any specific bit by using
     * a dot (.) to separate the keys. For example, if you have a config that looks like this:
     * <pre>
     *{
     *     "test": {
     *      "test2": {
     *          "test3": "Hello World!"
     *      }
     *    }
     * }
     *</pre>
     *
     * and you wanted to get the value of test3, you would use the following code:
     * <pre>
     *     Config config = new Config(json);
     *     String test3 = config.get("test.test2.test3");
     *     System.out.println(test3);
     *     // Output: Hello World!
     *  </pre>
     *
     *  You can also use this method to get values from arrays. For example, if you have a config that looks like this:
     *  <pre>
     *      {
     *          "test": {
     *              "test2": {
     *                  "test3": [
     *                      {
     *                          "test4": "Hello World!"
     *                      }
     *                  ]
     *               }
     *          }
     *      }
     * </pre>
     *
     * and you wanted to get the value of test4, you would use the following code:
     * <pre>
     *     Config config = new Config(json);
     *     String test4 = config.get("test.test2.test3.0.test4");
     *     System.out.println(test4);
     *     // Output: Hello World!
     * </pre>
     *
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        String[] split = key.split("\\.");

        Map<String, Object> values = this.values;
        List<Map<String, Object>> listValues = null;
        int index = 0;
        for (String s : split) {
            index++;
            if (values.get(s) instanceof Map) {
                values = (Map<String, Object>) values.get(s);
            } else if (values.get(s) instanceof List) {
                listValues = (List<Map<String, Object>>) values.get(s);
                int listValuesIndex = Integer.parseInt(split[index]);
                values = listValues.get(listValuesIndex);
            }
        }

        return values.get(split[split.length - 1]);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) get(key);
    }

    public double getDouble(String key) {
        return (double) get(key);
    }

    public float getFloat(String key) {
        return (float) get(key);
    }

    public long getLong(String key) {
        return (long) get(key);
    }

    public List<HashMap<String, Object>> getList(String key) {
        return (List<HashMap<String, Object>>) values.get(key);
    }

    public HashMap<String, Object> getObject(String key) {
        return (HashMap<String, Object>) values.get(key);
    }


    /**
     * You can also just retrieve the raw JSON object, if you prefer.
     */
    public JSONObject getJson() {
        return json;
    }
}
