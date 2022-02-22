package com.aura.nomina.parametryx.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.google.gson.JsonObject;
//import org.json.*;

public interface JSONUtil {

    Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    static final Gson gson = new Gson();

    public static Map<String, String> getAllPathWithValues(String jsonString){
        Map jsonPaths = new HashMap<String,String>();

        List<String> paths = JsonPath
                .using(Configuration.builder().options(Option.AS_PATH_LIST).build())
                .parse(jsonString)
                .read("$..*",List.class);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        //logger.info(("paths:" + paths);
        for (String jpath : paths){
            //logger.info(("jpath" + jpath);
            Object node = JsonPath.read(document,jpath);
            //logger.info(("node.getClass()" + node.getClass());
            if (node != null){
            //if (node instanceof java.util.Map) { //String jPathValue = evaluateJSONPath(jsonString,jpath)
                //logger.info(("node" + node);
                jsonPaths.put(new String(jpath), new String(node.toString()));
                //jsonPaths.putAll((Map) node);
            //}
            }

        }
        //logger.info("jsonPaths:" + jsonPaths);
        return jsonPaths;
    }

    public static Map<String, String> getAllPathWithValuesGSON(String jsonString){
        Map jsonPaths = new HashMap<String,String>();

        Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider()).build();

        List<String> paths = JsonPath
                .using(Configuration.builder().options(Option.AS_PATH_LIST).build())
                //.using(conf)
                .parse(jsonString)
                .read("$..*",List.class);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        //logger.info(("paths:" + paths);
        for (String jpath : paths){

            logger.info("jpath" + jpath);
            Object node = JsonPath.read(document,jpath);
            logger.info(("node.getClass()" + node.getClass()));
            if (node != null){
                if (node instanceof java.util.Map){
                    Object pathVal = evaluateJSONPathObject(jsonString,jpath);
                    jsonPaths.put(new String(jpath), new String(pathVal.toString()));
                }
                else
                //if (node instanceof java.util.Map) { //String jPathValue = evaluateJSONPath(jsonString,jpath)
                //logger.info(("node" + node);
                jsonPaths.put(new String(jpath), new String(node.toString()));
                //jsonPaths.putAll((Map) node);
                //}
            }

        }
        //logger.info("jsonPaths:" + jsonPaths);
        return jsonPaths;
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public static String evaluateJSONPathString(String jsonString, String jsonPath){
        String resultEvaluation = null;

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
        Object oResultEvaluation = JsonPath.read(document, jsonPath);
        //Object dataObject = JsonPath.parse(jsonString).read(jsonPath);
        //resultEvaluation = dataObject.toString();

        return oResultEvaluation.toString();
    }

    public static Object evaluateJSONPathObject(String jsonString, String jsonPath){
        String resultEvaluation = null;

        //Configuration conf = Configuration.defaultConfiguration();
        //conf.addOptions(Option.);
        //Configuration conf = Configuration.
        Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider()).build();

        //Object document = conf.jsonProvider().parse(jsonString);
        //Object oResultEvaluation = JsonPath.read(document, jsonPath);

        JsonObject jsonObject = JsonPath.using(conf).parse(jsonString).read(jsonPath);

        //Object dataObject = JsonPath.parse(jsonString).read(jsonPath);
        //resultEvaluation = dataObject.toString();
        //Object value = JsonPath.parse(jsonString.asNode().getLiteralLexicalForm())
        //        .limit(1).read(jsonpath.getString());

        return jsonObject;
    }

}
