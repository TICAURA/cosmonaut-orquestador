package com.aura.nomina.dao;

import com.aura.nomina.domain.DaoResult;
import com.aura.nomina.domain.Response;
import com.aura.nomina.parametryx.Field;
import com.aura.nomina.parametryx.StoreProcedure;

import com.aura.nomina.parametryx.util.JSONUtil;
import org.apache.avro.LogicalTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.postgresql.util.PGobject;

@Singleton
public class SuperDAO {

    @Inject
    DataSource dataSource;

    Logger logger = LoggerFactory.getLogger(SuperDAO.class);

    public SuperDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DaoResult executeSP(StoreProcedure storeProcedure, Map<String,String> valueMap){
        DaoResult daoResult = new DaoResult();
    //public Response executeSP(StoreProcedure storeProcedure, Map<String,String> valueMap){
        Response response = null;
        //ResponseHeader responseHeader = new ResponseHeader();
        String resultJSON = "";
        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("Executing Procedure ..." + storeProcedure.toString());
        logger.info("storeProcedure:" + storeProcedure.toString());
        logger.info("valueMap:" + valueMap.toString());

        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT " + storeProcedure.getProcedureName() + " ");

            //Fill input params
            for (Field field : storeProcedure.getFields()){
                logger.info("Field iterate:" + field.toString());
                if (valueMap.containsKey(field.getMapedValue())){
                    logger.info("Field mapped on map");
                }
                else{
                    logger.info("Field unmapped on map");
                    //continue;
                }

                logger.info("field.getIndex():" + field.getIndex());
                logger.info("field.getName():" + field.getName());
                logger.info("valueMap.get(field.getMapedValue()):" + valueMap.get(field.getMapedValue()));

                switch (field.getType()){
                    case "Integer":
                        logger.info("Set Integer");
                        Integer valueInjectedInt = valueMap.get(field.getMapedValue()) != null ? Integer.parseInt(valueMap.get(field.getMapedValue())) : null;
                        if (valueInjectedInt == null) pStmt.setObject(field.getIndex(), valueInjectedInt);
                        else pStmt.setInt(field.getIndex(), valueInjectedInt);
                        break;
                    case "Float":
                        logger.info("Set Float");
                        Float valueInjectedFloat = valueMap.get(field.getMapedValue()) != null ? Float.parseFloat(valueMap.get(field.getMapedValue())) : null;
                        if (valueInjectedFloat == null) pStmt.setObject(field.getIndex(), valueInjectedFloat);
                        else pStmt.setFloat(field.getIndex(), valueInjectedFloat);
                        break;
                    case "BigDecimal":
                        logger.info("Set Decimal");
                        BigDecimal valueInjectedBD = valueMap.get(field.getMapedValue()) != null ? new BigDecimal(valueMap.get(field.getMapedValue())) : null;
                        if (valueInjectedBD == null) pStmt.setObject(field.getIndex(), valueInjectedBD);
                        else pStmt.setBigDecimal(field.getIndex(), valueInjectedBD);
                        break;
                    case "String":
                        logger.info("Set String");
                        pStmt.setString(
                                field.getIndex(),
                                //valueMap.get(field.getIndex())
                                valueMap.get(field.getMapedValue())
                        );
                        break;
                    case "LocalDate":
                        logger.info("Set LocalDate");
                        LocalDate localdate = LocalDate.parse(valueMap.get(field.getMapedValue()));
                        logger.info("localdate:" + localdate);
                        pStmt.setObject(
                                field.getIndex(), localdate);
                        break;
                    case "Boolean":
                        logger.info("Set Boolean");
                        Boolean valueInjectedBoolean = valueMap.get(field.getMapedValue()) != null ? Boolean.parseBoolean(valueMap.get(field.getMapedValue())) : null;
                        if (valueInjectedBoolean == null) pStmt.setObject(field.getIndex(), valueInjectedBoolean);
                        else pStmt.setBoolean(field.getIndex(), valueInjectedBoolean);
                        break;
                    case "JSON": //TODO Check me!
                        logger.info("Set JSON");

                        PGobject jsonObject = new PGobject();
                        jsonObject.setType("json");
                        jsonObject.setValue(valueMap.get(field.getMapedValue()));
                        pStmt.setObject(field.getIndex(),jsonObject);
                        /**pStmt.setString(
                                field.getIndex(),
                                //valueMap.get(field.getIndex())
                                valueMap.get(field.getMapedValue())
                        );*/
                        break;
                    default:
                        break;
                }

            }
            //pStmt.execute();

            //Get result
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            if (storeProcedure.isArray() == true) resultJSON += "[";
            while (rs.next()) {
                //logger.info("row" + rs.toString());
                //logger.info("c1" + rs.getString(1));
                //logger.info("c2" + rs.getString(2));
                resultJSON += rs.getString(1);
                if (storeProcedure.isArray() == true) resultJSON += ",";
                //logger.info(execProcedureResult);
            }
            logger.info("PURE resultJSON:" + resultJSON);
            if (storeProcedure.isArray() == true){
                if (resultJSON.length() > 2) {
                    logger.info("PURE resultJSON:" + resultJSON);
                    resultJSON = resultJSON.substring(0, resultJSON.length() - 1);
                    //resultJSON += "]";
                }
            }
            if (storeProcedure.isArray() == true) resultJSON += "]";
            logger.info("resultJSON:" + resultJSON);
            daoResult = new DaoResult(resultJSON, null,null);

            //TODO FIXME
            //response = new Response(resultJSON,null,1,"Operacion Completada");
            //response = new Response(null,1,"Operacion Completada");

            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("message", "Hello \"World\"");
            //responseHeader.setMensaje(resultJSON.toString());
            //responseHeader.setMensaje(resultJSON);
            //responseHeader.setRespCode(1);



        } catch (Exception e) {
            String errDao = "DAO Exception resultJSON:" + e.getMessage();
            daoResult = new DaoResult(null, -11,"Excepcion en DAO: " + e.getMessage());
            logger.error(errDao);
            e.printStackTrace();
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (Exception eSt) {
                String errDaoSt = "Statement Closure Exception:" + eSt.getMessage();
                daoResult = new DaoResult(null, -12,"Excepcion en DAO (Cierre sentencia): " + eSt.getMessage());
                logger.error(errDaoSt);
                eSt.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception eCon) {
                String errDaoCon = "Connection Closure Exception:" + eCon.getMessage();
                daoResult = new DaoResult(null, -13,"Excepcion en DAO (Cierre Conexion): " + eCon.getMessage());
                logger.error(errDaoCon);
                eCon.printStackTrace();
            }
        }
        //response.setResultado(resultJSON);
        //response.setResponseHeader(responseHeader);
        //return responseHeader;
        return daoResult;
        //return response;
    }
}
