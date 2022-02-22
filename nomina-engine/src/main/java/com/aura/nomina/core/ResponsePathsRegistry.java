package com.aura.nomina.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ResponsePathsRegistry {
    Logger logger = LoggerFactory.getLogger(ResponsePathsRegistry.class);

    private Map<String,Map> responsesMap = new HashMap<String, Map>();

    public Map<String, Map> getResponsesMap() {
        return responsesMap;
    }

    public ResponsePathsRegistry(){
        //Fixme Replace handy with DB Query
        logger.info("Init Response Registry Singleton...");
        Map<String,String> responses = new HashMap<String, String>();

        logger.info("Load Response Paths ...");
        Map map2 = new HashMap();
        map2.put("payload","$[0]['salario']['salario_neto']");
        map2.put("code","$[0]['salario']['response_detail']['code']"); //$..['response_detail'].['code']
        map2.put("message","$[0]['salario']['response_detail']['message']"); //$..['response_detail'].['message']

        Map map200 = new HashMap();
        map200.put("payload","$[0]['nueva_nomina']['nomina_ordinaria']");
        map200.put("code","$[0]['nueva_nomina']['response_detail']['code']");
        map200.put("message","$[0]['nueva_nomina']['response_detail']['message']");

        Map map30 = new HashMap();
        map30.put("payload","$[0]['nomina_aguinaldo_calculada']['nomina_extraordinaria']");
        map30.put("code","$[0]['nomina_aguinaldo_calculada']['response_detail']['code']");
        map30.put("message","$[0]['nomina_aguinaldo_calculada']['response_detail']['message']");

        logger.info("Adding Procedure Specs ...");
        responsesMap.put("2",map2);
        responsesMap.put("200",map200);
        responsesMap.put("30",map30);

        logger.info("responseMap:" + responsesMap);
        //logger.info("Procedure Load Complete.");
        logger.info("Init Response Path Registry Singleton  DONE");
    }
}
