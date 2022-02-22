package com.aura.nomina.core;

import com.aura.nomina.dao.ParameterDAO;
import com.aura.nomina.parametryx.StoreProcedure;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class FunctionRegistry {

    @Inject
    ParameterDAO parameterDAO;

    Logger logger = LoggerFactory.getLogger(FunctionRegistry.class);

    //private Map<String,StoreProcedure> procedureMap = new HashMap<String, StoreProcedure>();
    private Map<String,StoreProcedure> procedureMap = null;

    public Map<String, StoreProcedure> getProcedureMap() {
        return procedureMap;
    }

    public FunctionRegistry(){
        //Fixme Replace handy with DB Query
        //FIXME Get store procedure definition from DB Table!!!!
        logger.info("Init Function Registry Singleton...");
        Map<String,String> proceduresDB = new HashMap<String, String>();

        //logger.info("Load Procedure Specs ...");

        //reloadMap();

        //logger.info("Adding Procedure Specs ...");

        //3.1
        //proceduresDB.put("40",callDef40);

        //logger.info("proceduresDB",proceduresDB);

        //TODO Kill me!
        //Map proceduresDB2 = parameterDAO.getFunctionRegistry();

        /**
        logger.info("Serializing Procedure Specs ...");
        for (String procedureKey : proceduresDB.keySet()){
            Gson g = new Gson();
            StoreProcedure sp = g.fromJson(proceduresDB.get(procedureKey), StoreProcedure.class);
            logger.info("Serialization OK  [" + procedureKey + "] ...");
            procedureMap.put(procedureKey,sp);
            sp = null;
            logger.info("Load Serialized OK [" + procedureKey + "]");
        }
        logger.info("procedureMap:" + procedureMap);
        //logger.info("Procedure Load Complete.");
        logger.info("Init Function Registry Singleton  DONE");
         */
    }

    public void reloadMap(){
        procedureMap = new HashMap<String, StoreProcedure>();
        //procedureMap.clear();
        Map<String,String> dbFunctionRegistryMap = parameterDAO.getFunctionRegistry();
        logger.info("Serializing Procedure Specs ...");
        for (String procedureKey : dbFunctionRegistryMap.keySet()){
            Gson g = new Gson();
            StoreProcedure sp = g.fromJson(dbFunctionRegistryMap.get(procedureKey), StoreProcedure.class);
            logger.info("Serialization OK  [" + procedureKey + "] ...");
            procedureMap.put(procedureKey,sp);
            sp = null;
            logger.info("Load Serialized OK [" + procedureKey + "]");
        }
        logger.info("procedureMap:" + procedureMap);
        //logger.info("Procedure Load Complete.");
        logger.info("Init Function Registry Singleton  DONE");
    }
}
