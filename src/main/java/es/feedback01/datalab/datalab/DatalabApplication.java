package es.feedback01.datalab.datalab;

import es.feedback01.datalab.datalab.service.CustomerPurchaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatalabApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatalabApplication.class, args);
    }

    @Bean
    CommandLineRunner loadCsvAtStartup(CustomerPurchaseService service) {
        return args -> {
            // al arrancar la aplicacion, cargamos el csv en memoria
            service.loadCsv();

            // mostramos cuantas compras se han cargado
            System.out.println(
                    "compras cargadas desde el csv: " + service.getPurchases().size()
            );
        };
    }
}
