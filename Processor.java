package processor;

import org.springframework.stereotype.Component;

import javax.annotation.*;
import java.io.IOException;
import java.util.List;


@Component
public class Processor {
    @PostConstruct
    public void init() throws IOException, InterruptedException {

        //pegar pacientes da worklist
        String date = "20161015";
        Worklist wl = new Worklist(date);
        List<String> idPatients=wl.getWL();

        //System.out.println(idPatients.size());

        //pegar imagens dos pacientes da worklist
        ImageOfPatient im = new ImageOfPatient();
        im.getImageOfPatient(idPatients, date);

        //ler cabeçalho das imagens e  salvar informações no banco
        ExtractionHeader eHeader = new ExtractionHeader();
       // eHeader.listHeader();
    }

}

