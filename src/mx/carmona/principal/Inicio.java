/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mx.sat.cfd33.Comprobante;

/**
 *
 * @author Edwin Carmona
 */
public class Inicio {
    public static void main(String[] args) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Comprobante.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("C:\\Users\\Sergio Flores\\Documents\\eds\\dolphin\\pruebas\\AET131112RQ2_P_P_0000000504.xml");
        Comprobante config = (Comprobante) unmarshaller.unmarshal(xml);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "file:///C:\\Users\\Sergio Flores\\Documents\\eds\\dolphin\\FirstXSD.xml");
        marshaller.marshal(config, System.out);
    }
}
