package es.feedback01.datalab.datalab.service;

import es.feedback01.datalab.datalab.domain.CustomerPurchase;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerPurchaseService {

    // aqui guardo todas las compras que voy leyendo del csv
    // se cargan una sola vez al arrancar la aplicacion
    private final List<CustomerPurchase> purchases = new ArrayList<>();

    // este metodo se encarga de leer el csv y pasarlo a objetos java
    public void loadCsv() {

        try {
            // cargamos el archivo desde resources
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("customer_purchases_1000.csv");

            if (is == null) {
                throw new RuntimeException("no se encuentra el archivo csv");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                // la primera linea es la cabecera, asi que la salto
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                /*
                 aqui me encontre un problema leyendo el csv
                 el orden de las columnas no era el que yo habia pensado al principio
                 por eso el programa intentaba convertir textos como "electronics" en numeros
                 despues de revisar una linea real del csv por consola, he ajjustao los indices
                 para que cada campo coincida con su tipo correcto
                */

                CustomerPurchase purchase = new CustomerPurchase();

                // id de la compra
                purchase.setId(Long.parseLong(data[0]));

                // id del cliente
                purchase.setCustomerId(Long.parseLong(data[1]));

                // producto comprado
                purchase.setProduct(data[2]);

                // categoria del producto
                purchase.setCategory(data[3]);

                // importe de la compra (esta en la posicion 4)
                purchase.setAmount(Double.parseDouble(data[4]));

                // fecha y hora de la compra
                purchase.setTimestamp(LocalDateTime.parse(data[5]));

                // tipo de pago
                purchase.setPaymentType(data[6]);

                // region del cliente
                purchase.setRegion(data[7]);

                purchases.add(purchase);
            }

        } catch (Exception e) {
            throw new RuntimeException("error leyendo el csv", e);
        }
    }

    // devuelve todas las compras cargadas en memoria
    public List<CustomerPurchase> getPurchases() {
        return purchases;
    }
}
