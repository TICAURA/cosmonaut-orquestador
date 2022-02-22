package com.aura.nomina.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import com.aura.nomina.domain.Dummy;
import com.aura.nomina.domain.Empleado;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
//@Requires(beans = DataSource.class)
//@Requires(property = "datasource.url")
public class NominaDAO {

    @Inject
    DataSource dataSource;
    //DataSource dataSource; // "default" will be injected

    public NominaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Logger logger = LoggerFactory.getLogger(NominaDAO.class);

    //public String getSnmXsbmImss(Dummy dummy) {
    public String getSnmXsbmImss(Empleado dummy) {
        String resultJSON = null;

        Connection con = null;
        //CallableStatement cStmt = null;
        PreparedStatement pStmt = null;
        //logger.info("fcontrato:" + dummy.getFecha_contrato());
        logger.info("fcontrato:" + dummy.getFecha_contrato());
        try {
            //DataSource dataSource = dataSourceConfig.getDataSource();
            con = dataSource.getConnection();
            //TODO Replaceme!
//            con = DriverManager.getConnection(
//                    "jdbc:postgresql://119.8.3.41:5432/apyn?noAccessToProcedureBodies=true",
//                    "apyn", "s1st3m4d3n0min4");

            pStmt = con.prepareCall(" SELECT api_so_f_calcula_snm_xsbm_imss(?,?,?,?,?,?,?,?) ");

            pStmt.setInt(1, dummy.getCliente_id());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            pStmt.setObject(2, dummy.getFecha_contrato());
            System.out.println("fecha_contrato:" + dummy.getFecha_contrato());

            pStmt.setInt(3, dummy.getPersona_id());
            pStmt.setInt(4, dummy.getPolitica_id());
            pStmt.setInt(5, dummy.getGrupo_nomina());
            pStmt.setInt(6, dummy.getTipo_compensacion());
            pStmt.setInt(7, dummy.getSbm_imss());

            System.out.println("fec_ini_periodo:" + dummy.getFec_ini_periodo());
            pStmt.setObject(8, dummy.getFec_ini_periodo());

            pStmt.execute();

            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                // do something with the results.
                System.out.println("row" + rs.toString());
                //System.out.println("c0" + rs.getString(0));
                System.out.println("c1" + rs.getString(1));
                //System.out.println("c2" + rs.getString(2));
                resultJSON = rs.getString(1);

                //System.out.println(execProcedureResult);
            }

            logger.info("Respuesta resultJSON:" + resultJSON);
        } catch (Exception e) {
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (Exception eSt) {
                logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception eCon) {
                logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
            }
        }
        return resultJSON;
    }
    public String getSbcXsbmImss(Empleado dummy){
        //public String getSnmXsbmImss2(Dummy dummy){
            String resultJSON = null;

            Connection con = null;
            //CallableStatement cStmt = null;
            PreparedStatement pStmt = null;
            logger.info("fcontrato:" + dummy.getFecha_contrato());
            try {
                //DataSource dataSource = dataSourceConfig.getDataSource();
                con = dataSource.getConnection();
                //TODO Replaceme!
//            con = DriverManager.getConnection(
//                    "jdbc:postgresql://119.8.3.41:5432/apyn?noAccessToProcedureBodies=true",
//                    "apyn", "s1st3m4d3n0min4");

                pStmt = con.prepareCall(" SELECT api_so_f_calcula_sbc_xsbm_imss(?,?,?,?,?,?,?,?) ");

                pStmt.setInt(1, dummy.getCliente_id());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                pStmt.setObject(2, dummy.getFecha_contrato());
                System.out.println("fecha_contrato:" + dummy.getFecha_contrato());

                pStmt.setInt(3, dummy.getPersona_id());
                pStmt.setInt(4, dummy.getPolitica_id());
                pStmt.setInt(5, dummy.getGrupo_nomina());
                pStmt.setInt(6, dummy.getTipo_compensacion());
                pStmt.setInt(7, dummy.getSbm_imss());

                System.out.println("fec_ini_periodo:" + dummy.getFec_ini_periodo());
                pStmt.setObject(8, dummy.getFec_ini_periodo());

                pStmt.execute();

                ResultSet rs = (ResultSet) pStmt.executeQuery();
                while (rs.next()) {
                    // do something with the results.
                    System.out.println("row" + rs.toString());
                    //System.out.println("c0" + rs.getString(0));
                    System.out.println("c1" + rs.getString(1));
                    //System.out.println("c2" + rs.getString(2));
                    resultJSON = rs.getString(1);

                    //System.out.println(execProcedureResult);
                }

                logger.info("Respuesta resultJSON:" + resultJSON);
            }
            catch (Exception e){
                logger.error("Excection en DAO resultJSON:" + e.getMessage());
                e.printStackTrace();
            }
            finally {
                try { if (pStmt != null) { pStmt.close(); }
                }
                catch (Exception eSt) {
                    logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage());
                }
                try { if (con != null) { con.close(); }
                }
                catch (Exception eCon) {
                    logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage());
                }
            }

            return resultJSON;
        }

    public String getListaEmpleados(Empleado empleado){
        String resultJSON = "[";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_empleados_nomina(?) ");

            pStmt.setInt(1, empleado.getNominaXperiodoId());

            pStmt.execute();

            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                // do something with the results.
                //System.out.println("row" + rs.toString());
                //System.out.println("c0" + rs.getString(0));
                resultJSON += rs.getString(1) != null ? rs.getString(1) + "," : "";
                //System.out.println("c2" + rs.getString(2));
                //System.out.println(execProcedureResult);
            }
            resultJSON = resultJSON.substring(0,resultJSON.length()-1);
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON + "]";
    }

    public String getListaEmpleadosPagoNomina(Empleado empleado){
        String resultJSON = "[";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_empleados_pago_nomina(?,?) ");

            pStmt.setInt(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getEstatusPago());
            //pStmt.setNull(2, Types.INTEGER);

            pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1) != null ? rs.getString(1) + "," : "";
                //System.out.println(execProcedureResult);
            }
            resultJSON = resultJSON.substring(0,resultJSON.length()-1);
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON + "]";
    }

    public String getListaGeneralNominas(Empleado empleado){
        String resultJSON = "[";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_general_nominas(?,?,?,?,?) ");

            pStmt.setObject(1, empleado.getClienteId());
            pStmt.setObject(2, empleado.getTipoNomina());
            pStmt.setBoolean(3, empleado.getEsActivo());
            pStmt.setBoolean(4, empleado.getEsExtraordinario());
            pStmt.setObject(5, empleado.getEstado());

            pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1) != null ? rs.getString(1) + "," : "";
                //System.out.println(execProcedureResult);
            }
            resultJSON = resultJSON.substring(0,resultJSON.length()-1);
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON + "]";
    }

    public String getListaEmpleadosTimbresNomina(Empleado empleado){
        String resultJSON = "[";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_empleados_timbres_nomina(?,?,?,?) ");

            pStmt.setObject(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getEstadoTimbre());
            pStmt.setObject(3, empleado.getMetodoPago());
            pStmt.setBoolean(4, empleado.getEsEstadoError());

            pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1) != null ? rs.getString(1) + "," : "";
                //System.out.println(execProcedureResult);
            }
            resultJSON = resultJSON.substring(0,resultJSON.length()-1);
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON + "]";
    }

    public String getDetalleNominaEmpleado(Empleado empleado){
        String resultJSON = null;

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_detalle_nomina_empleado(?,?,?,?) ");

            pStmt.setObject(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getFechaContrato());
            pStmt.setObject(3, empleado.getPersonaId());
            pStmt.setObject(4, empleado.getClienteId());

            pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON = rs.getString(1);
                //System.out.println(execProcedureResult);
            }
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON;
    }

    public String getPercepcionesNominaEmpleado(Empleado empleado){
        String resultJSON = "";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_percepciones_nomina_empleado(?,?,?,?) ");

            pStmt.setObject(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getFechaContrato());
            pStmt.setObject(3, empleado.getPersonaId());
            pStmt.setObject(4, empleado.getClienteId());

            pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1);
                //System.out.println(execProcedureResult);
            }
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON;
    }

    public String getDeduccionesNominaEmpleado(Empleado empleado){
        String resultJSON = "[";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_lista_deducciones_nomina_empleado(?,?,?,?) ");

            pStmt.setObject(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getFechaContrato());
            pStmt.setObject(3, empleado.getPersonaId());
            pStmt.setObject(4, empleado.getClienteId());

            //pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                //System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1) != null ? rs.getString(1) + "," : "";
                //System.out.println(execProcedureResult);
            }
            resultJSON = resultJSON.substring(0,resultJSON.length()-1);
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON + "]";
    }

    public String getCalculaNominaOrdinaria(Empleado empleado){
        String resultJSON = "";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_calcula_nomina_ordinaria(?,?) ");

            pStmt.setObject(1, empleado.getNominaXperiodoId());
            pStmt.setObject(2, empleado.getUsuarioId());

            //pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1);
                //System.out.println(execProcedureResult);
            }
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON;
    }

    public String getCreaNominaOrdinaria(Empleado empleado){
        String resultJSON = "";

        Connection con = null;
        PreparedStatement pStmt = null;
        logger.info("empleado:" + empleado.toString());
        try {
            con = dataSource.getConnection();

            pStmt = con.prepareCall(" SELECT f_crea_nomina_ordinaria(?,?,?,?,?,?,?) ");

            /*logger.info("empleado.getClienteId()" + empleado.getClienteId());
            logger.info("empleado.getGrupoNomina()" + empleado.getClienteId());
            logger.info("empleado.getUsuarioId()" + empleado.getUsuarioId());
            logger.info("empleado.getFecIniPeriodo()" + empleado.getFecIniPeriodo());
            logger.info("empleado.getFecFinPeriodo()" + empleado.getFecFinPeriodo());
            logger.info("empleado.getFecIniIncidencia()" + empleado.getFecIniIncidencia());
            logger.info("empleado.getFecFinIncidencia()" + empleado.getFecFinIncidencia());*/

            pStmt.setObject(1, empleado.getClienteId());
            pStmt.setObject(2, empleado.getGrupoNomina());
            pStmt.setObject(3, empleado.getUsuarioId());
            pStmt.setObject(4, empleado.getFecIniPeriodo());
            pStmt.setObject(5, empleado.getFecFinPeriodo());
            pStmt.setObject(6, empleado.getFecIniIncidencia());
            pStmt.setObject(7, empleado.getFecFinIncidencia());

            //pStmt.execute();
            ResultSet rs = (ResultSet) pStmt.executeQuery();
            while (rs.next()) {
                System.out.println("row" + rs.toString());
                System.out.println("c1" + rs.getString(1));
                resultJSON += rs.getString(1);
                //System.out.println(execProcedureResult);
            }
            logger.info("Respuesta resultJSON:" + resultJSON);
        }
        catch (Exception e){
            logger.error("Excection en DAO resultJSON:" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { if (pStmt != null) { pStmt.close(); } }
            catch (Exception eSt) { logger.error("Excepcion en Cierre de Sentencia:" + eSt.getMessage()); }
            try { if (con != null) { con.close(); } }
            catch (Exception eCon) { logger.error("Excepcion en Cierre de Conexion:" + eCon.getMessage()); }
        }

        return resultJSON;
    }
}
