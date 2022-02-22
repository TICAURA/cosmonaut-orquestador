package com.aura.nomina.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.aura.nomina.parametryx.StoreProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ParameterDAO {

    @Inject
    DataSource dataSource;

    Logger logger = LoggerFactory.getLogger(ParameterDAO.class);

    public ParameterDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //public ParameterDAO(){ logger.info("Init Function Registry DAO Singleton...");}

    private Map<String,StoreProcedure> procedureMap = new HashMap<String, StoreProcedure>();

    public Map<String, StoreProcedure> getProcedureMap() {
        return procedureMap;
    }

    public Map<String,String> getFunctionRegistry(){
        //String resultJSON = "";
        Map<String,String> functionRegistry = new HashMap<>();

        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT * FROM ORQ_FUNCTION_REGISTRY ");

            //pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                //System.out.println("c1" + rs.getString(1));
                //System.out.println("c2" + rs.getString(2));
                String frKey = rs.getString(1);
                String frVal = rs.getString(2);
                functionRegistry.put(new String(frKey), new String(frVal));
                //logger.info(execProcedureResult);
            }
            //logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO Function Registry:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return functionRegistry;
    }
}
