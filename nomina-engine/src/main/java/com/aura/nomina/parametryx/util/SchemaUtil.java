package com.aura.nomina.parametryx.util;

import com.aura.nomina.core.NominaController;
import org.apache.avro.AvroTypeException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

public interface SchemaUtil {
    Logger logger = LoggerFactory.getLogger(SchemaUtil.class);

    public static boolean validateJson(String json, Schema schema) throws Exception {
        InputStream input = new ByteArrayInputStream(json.getBytes());
        DataInputStream din = new DataInputStream(input);

        try {
            logger.info("Schema:" + schema);
            DatumReader reader = new GenericDatumReader(schema);
            Decoder decoder = DecoderFactory.get().jsonDecoder(schema, din);
            reader.read(null, decoder);
            return true;
        } catch (AvroTypeException e) {
            logger.error(e.getMessage());
            throw new Exception(e);
            //return false;
        }
    }
}
