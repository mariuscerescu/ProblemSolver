package org.example.problemMetaData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLDeserializer {
    public static ProblemMetaData getPOSOutput(String textToAnalyze) {
        String filePath = "src/main/java/org/example/problemMetaData/bin_PosRo/outputuri/intrebaria.xml";
        ProblemMetaData posOutput = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProblemMetaData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            File xmlFile = new File(filePath);
            posOutput = (ProblemMetaData) unmarshaller.unmarshal(xmlFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        assert posOutput != null;
        posOutput.problem = textToAnalyze;
        return posOutput;
    }
}
