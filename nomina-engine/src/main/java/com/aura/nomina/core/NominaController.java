package com.aura.nomina.core;

import com.aura.nomina.dao.SuperDAO;
import com.aura.nomina.domain.*;
import com.aura.nomina.parametryx.StoreProcedure;
import com.aura.nomina.parametryx.util.JSONUtil;
import com.aura.nomina.parametryx.util.SchemaUtil;
import io.micronaut.core.annotation.Nullable;
//import io.micronaut.core.util.StringUtils;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.avro.Schema;

import com.google.common.base.CaseFormat;

@Controller("/nomina")
public class NominaController {
    //@Inject
    //NominaDAO nominaDAO;
    @Inject
    SuperDAO superDAO;
    @Inject
    FunctionRegistry functionRegistry;
    @Inject
    SchemaRegistry schemaRegistry;
    @Inject
    ResponsePathsRegistry responsePathsRegistry;

    Logger logger = LoggerFactory.getLogger(NominaController.class);

    @Post("/getCalc")
    public String getCalc(Object payload, @Nullable @QueryValue String cve) { //Fixme replace concrete Object by OBJECT
        String resultJSON = null;
        logger.info("Executing Service Key :" + cve);
        logger.info("Executing payload:" + payload.toString());

        //Validation Block
        Boolean valid = false;
        Map<String,Schema> schemaMap = schemaRegistry.getSchemaMap();
        logger.info("schemaMap:" + schemaMap.toString());

        //Schema Validation
        Schema valSchema = schemaMap.get(cve);
        logger.info("valSchema:" + valSchema);
        if (valSchema != null) { //TODO Remove me once migrated all Schemas
            try {
                valid = SchemaUtil.validateJson(payload.toString(), valSchema);
            } catch (Exception e) {
                logger.error("Schema validation Exception:" + e.getMessage());
                e.printStackTrace();
                //String resSE =  assembleResponse("Entrada incorrecta: " + e.getMessage(),"Operacion No Completada", "[]", -1);
                String resSE = assembleFullResponse(cve, "Entrada incorrecta: " + e.getMessage(), "[]", -1);
                return resSE;
            }
        }
        //TODO Validate Date Formats (Separate Map)
        //logger.info("valid:" + valid);*/

        //Exec Block
        Map<String, StoreProcedure> procedureMap = functionRegistry.getProcedureMap();
        //logger.info("procedureMap:"+ procedureMap);
        if (procedureMap == null) {
            functionRegistry.reloadMap();
            //procedureMap = functionRegistry.getProcedureMap();
        }
        procedureMap = functionRegistry.getProcedureMap();
        logger.info("procedureMap:"+ procedureMap);

        logger.info("Retrieving Procedure Spec:" + cve + "...");
        StoreProcedure storeProcedure = procedureMap.get(cve);
        //Val extract
        logger.info("Retrieved Procedure Spec; storeProcedure:" + storeProcedure);
        //Map<String,String> mapVal= JSONUtil.getAllPathWithValues(payload.toString());
        Map<String,String> mapVal= JSONUtil.getAllPathWithValuesGSON(payload.toString());

        logger.info("mapVal:" + mapVal);
        //Exec
        logger.info("Executing procedure ...");
        //Response responseDB = superDAO.executeSP(storeProcedure,mapVal);
        DaoResult daoResult = superDAO.executeSP(storeProcedure,mapVal);
        String responseDB = daoResult.getResult();
        logger.info("responseDB:" + responseDB);

        //DaoResult daoResult1 = responseFilter(responseDB);

        String responseSrv = "";
        if (daoResult.getErrCode() != null){ //DAO Error propagated
            responseSrv = assembleFullResponse(cve,daoResult.getErrDescription(), daoResult.getResult(), daoResult.getErrCode());
        }
        else {
            if (responseDB == null) {
                responseSrv = assembleFullResponse(cve,"No se obtuvieron Datos (Nulo)", null, -1); }
            else if (responseDB.isEmpty()){
                responseSrv = assembleFullResponse(cve,"No se obtuvieron Datos (Vacio)","[]", -2); }
            else {
                if (storeProcedure.isArray()){
                    responseSrv = assembleFullResponse(cve,"Consulta Completada",responseDB,1); }
                else responseSrv = assembleResponse(responseDB);
            }
        }

        logger.info("Executing procedure DONE");
        logger.info("Executed Service Key :" + cve + " DONE");

        logger.info("responseSrv:" + responseSrv);

        //Check if JSON valid FIXME!!!!
        if (!JSONUtil.isJSONValid(responseSrv)){
            logger.error("Error en el mensaje de BackEnd [JSON No valido]:[" + responseSrv + "]");
            return  assembleFullResponse(null,"Error en el mensaje de BackEnd",null,-20);
        }

        //Snake 2 Camel
        Map<String,String> mapPaths = JSONUtil.getAllPathWithValues(responseSrv);
        for (String path : mapPaths.keySet()){
            //logger.info("path:" + path);
            //String[] split = path.substring(1).split("[");
            StringTokenizer st = new StringTokenizer(path, "['");
            for (int i = 0; st.hasMoreTokens(); i++){
                String pathName = st.nextToken();
                if ( !(pathName.contains("[")) &&
                        !(pathName.contains("]") &&
                                !(pathName.contains("$") ) ) ){
                    //logger.info("pathName:" + pathName);
                    String fixedPath = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, pathName);
                    //logger.info("fixedPath:" + fixedPath);
                    responseSrv = responseSrv.replace(pathName,fixedPath);
                }
            }
            //System.out.println("#" + i + ": "
            //        + st.nextToken());
        }
        //int ini = path.lastIndexOf("['");
        //int fin = path.lastIndexOf("']");
        //String node
        //logger.info("path redux:" + path.substring(ini+2,fin));

        return responseSrv;
    }

    private String assembleResponse(String jsonResult){
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"response\":" + jsonResult + "");
        sb.append("}");
        return sb.toString();
    }

    private String assembleFullResponse(String functionId, String sysMsg,String jsonResult, Integer resCode){
        StringBuilder sb = new StringBuilder("{");

        logger.info("CHECK jsonResult:" + jsonResult);
        //FIXME Check response Paths!!!!

        //String dbPayload = "null";
        Object dbPayload = jsonResult;
        String dbCode = "-99";
        String dbMsg = "No Disponible";
        Map<String,Map> responsesMap = responsePathsRegistry.getResponsesMap();//All
        if (responsesMap != null){
            Map<String,String> responsesFuncMap = responsesMap.get(functionId); //Concrete
            if (responsesFuncMap != null) {
                String jpPayload = responsesFuncMap.get("payload");
                String jpCode = responsesFuncMap.get("code");
                String jpMessage = responsesFuncMap.get("message");

                logger.info("jpPayload:" + jpPayload);
                logger.info("jpCode:" + jpCode);
                logger.info("jpMessage:" + jpMessage);

                //Overrride
                dbPayload = JSONUtil.evaluateJSONPathObject(jsonResult, jpPayload);
                dbCode = JSONUtil.evaluateJSONPathString(jsonResult, jpCode);
                dbMsg = JSONUtil.evaluateJSONPathString(jsonResult, jpMessage);
            }
        }

        logger.info("dbPayload:" + dbPayload);
        logger.info("dbCode:" + dbCode);
        logger.info("dbMsg:" + dbMsg);

        //sb.append("\"response\":" + jsonResult + "");
        sb.append("\"response\":" + dbPayload + "");
        sb.append(",");
        sb.append("\"response_detail\":{");
        sb.append("\"code\":\"" + resCode + "\",");
        sb.append("\"message\":\"" + sysMsg + "\"");
        //sb.append("\"code\":\"" + dbCode + "\",");
        //sb.append("\"message\":\"" + dbMsg + "\"")
        // ;
        //sb.append("\"message\":\"" + dbMsg != null || !dbMsg.isEmpty() ? dbMsg : sysMsg + "\"");
        //sb.append("\"userMsg\":\"" + userMsg + "\"");
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }

    /**
    private DaoResult responseFilter(String responseJSON){
        DaoResult daoResult = new DaoResult();
        String filteredResponse = null;

        JSONUtil.



        return daoResult;
    }*/

}
