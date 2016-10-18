package processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Component
public class Processor {
    @Autowired
    private ExtractionHeader eHeader;

    @PostConstruct
    public void init() throws IOException, InterruptedException {

        //pegar pacientes da worklist
        String date = "20161015";
        Worklist wl = new Worklist(date);
        //List<String> idPatients=wl.getWL();

        //System.out.println(idPatients.size());

        //pegar imagens dos pacientes da worklist
        //ImageOfPatient im = new ImageOfPatient();
        //im.getImageOfPatient(idPatients, date);

        //ler cabeçalho das imagens e  salvar informações no banco
        eHeader.listHeader();
    }

}

