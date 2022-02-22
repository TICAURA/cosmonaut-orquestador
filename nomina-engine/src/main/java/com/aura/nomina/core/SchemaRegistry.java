package com.aura.nomina.core;

import com.aura.nomina.parametryx.StoreProcedure;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;

@Singleton
public class SchemaRegistry {
    Logger logger = LoggerFactory.getLogger(SchemaRegistry.class);

    private Map<String,Schema> schemaMap = new HashMap<String, Schema>();

    public Map<String, Schema> getSchemaMap() {
        return schemaMap;
    }

    public SchemaRegistry(){
        //Fixme Replace handy with DB Query
        logger.info("Init Schema Registry Singleton...");
        Map<String,String> schemasDB = new HashMap<String, String>();

        logger.info("Load Procedure Specs ...");
        String schemaDef1 = "{\"namespace\": \"com.aura.nomina.schema\",\n" +
                " \"type\": \"record\",\n" +
                " \"name\": \"f_lista_empleados_nomina\",\n" +
                " \"fields\": [\n" +
                "     {\"name\": \"clienteId\", \"type\": \"int\" },\n" +
                "     {\"name\": \"fechaContrato\",  \"type\": \"string\"},\n" +
                "     {\"name\": \"personaId\", \"type\": \"int\"},\n" +
                "     {\"name\": \"politicaId\", \"type\": \"int\"},\n" +
                "     {\"name\": \"grupoNomina\", \"type\": \"int\"},\n" +
                "     {\"name\": \"tipoCompensacion\", \"type\": \"int\"},\n" +
                "     {\"name\": \"sbmImss\", \"type\": \"int\"},\n" +
                "     {\"name\": \"fecIniPeriodo\", \"type\": \"string\"}\n" +
                " ]\n" +
                "}";
        String schemaDef2 = "{\"namespace\": \"com.aura.nomina.schema\",\n" +
                " \"type\": \"record\",\n" +
                " \"name\": \"f_lista_empleados_pago_nomina\",\n" +
                " \"fields\": [\n" +
                "     {\"name\": \"nominaXperiodoId\", \"type\": \"int\"},\n" +
                "     {\"name\": \"estatusPago\", \"type\": [\"null\",\"int\"]}\n" +
                " ]\n" +
                "}";
        String schemaDef41 = "{\"namespace\": \"com.aura.nomina.schema\",\n" +
                " \"type\": \"record\",\n" +
                " \"name\": \"cuarentayuno\",\n" +
                " \"fields\": [\n" +
                "     {\"name\": \"nominaXperiodoId\", \"type\": \"int\" }\n" +
                " ]\n" +
                "}";
        String schemaDef42 = "{\"namespace\": \"com.aura.nomina.schema\",\n" +
                " \"type\": \"record\",\n" +
                " \"name\": \"cuarentaydos\",\n" +
                " \"fields\": [\n" +
                "     {\"name\": \"nominaXperiodoId\", \"type\": \"int\" },\n" +
                "     {\"name\": \"fechaContrato\",  \"type\": \"string\"},\n" +
                "     {\"name\": \"personaId\", \"type\": \"int\"},\n" +
                "     {\"name\": \"clienteId\", \"type\": \"int\"}\n" +
                " ]\n" +
                "}";

        logger.info("Adding Procedure Specs ...");
        //schemasDB.put("1",schemaDef1);
        //schemasDB.put("2",schemaDef2);
        schemasDB.put("42",schemaDef42);
        //schemasDB.put("41",schemaDef41);

        logger.info("Serializing Procedure Specs ...");
        for (String schemaKey : schemasDB.keySet()){
            //Gson g = new Gson();
            //StoreProcedure sp = g.fromJson(proceduresDB.get(procedureKey), StoreProcedure.class);
            //logger.info("Serialization OK  [" + procedureKey + "] ...");
            Schema schema = new Schema.Parser().parse(schemasDB.get(schemaKey));
            schemaMap.put(schemaKey,schema);
            schema = null;
            logger.info("Load Serialized OK [" + schemaKey + "]");
        }
        logger.info("schemaMap:" + schemaMap);
        //logger.info("Procedure Load Complete.");
        logger.info("Init Schema Registry Singleton  DONE");
    }
}
