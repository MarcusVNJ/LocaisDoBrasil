package com.evoluum.localidades.helper.plugins;

import com.evoluum.localidades.dto.LocationResult;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class HttpResponseCsv {

    private static Logger logger = Logger.getLogger(HttpResponseCsv.class.getName());

    public static void requestLocationCsv(HttpServletResponse response, List<String> lines) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=Locations.csv;");

        logger.info("Criando arquivo Csv");

        ServletOutputStream os = response.getOutputStream();

        os.write(LocationResult.getHeader().toString().getBytes(StandardCharsets.UTF_8));

        lines.forEach(line -> {
            try {
                os.write(line.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erro ao realizar a inserção de dados no arquivo CSV");
                e.printStackTrace();
            }
        });

        response.flushBuffer();
    }

}
