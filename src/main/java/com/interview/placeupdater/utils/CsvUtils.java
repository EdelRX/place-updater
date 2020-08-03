package com.interview.placeupdater.utils;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.interview.placeupdater.model.PlaceEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@Component
public class CsvUtils {
    private static final CsvMapper mapper = new CsvMapper();
    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

    public static void write(PrintWriter writer, List<PlaceEntity> placeEntities) {
        writer.write("id,name,is_active,created_at,updated_at,UNLOCODE,place_identity_id,vendor_place_id \n");
        for (PlaceEntity placeEntity : placeEntities) {
            writer.write(placeEntity.getId() + "," + placeEntity.getName() + "," + placeEntity.getIs_active() +
                    "," + placeEntity.getCreated_at() + "," + placeEntity.getUpdated_at() + "," +
                    placeEntity.getUnlocode() + "," + placeEntity.getPlace_identity_id() + "," + placeEntity.getVendor_place_id() +
                    "\n");
        }
    }
}
