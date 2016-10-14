package processor;

import org.springframework.stereotype.Component;


@Component
public class Processor {

    public void init() {

        //pegar pacientes da worklist
        /*String date = "20161015";
        Worklist wl = new Worklist(date);
        List<String> idPatients = new ArrayList<>();
        idPatients=wl.getWL();*/

        //System.out.println(idPatients.size());

        //pegar imagens dos pacientes da worklist
        //ImageOfPatient im = new ImageOfPatient();
       // im.getImageOfPatient(idPatients, date);

        //ler cabeçalho das imagens e  salvar informações no banco
        ExtractionHeader eHeader = new ExtractionHeader();
        eHeader.listHeader();
    }

}

   // }
//}
