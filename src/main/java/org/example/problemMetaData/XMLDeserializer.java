package org.example.problemMetaData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLDeserializer {
    public static MathWordProblem getPOSOutput() {
        String filePath = "src/main/java/org/example/problemMetaData/bin_PosRo/outputuri/intrebaria.xml";
        MathWordProblem posOutput = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MathWordProblem.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            File xmlFile = new File(filePath);
            posOutput = (MathWordProblem) unmarshaller.unmarshal(xmlFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return posOutput;
    }
}
