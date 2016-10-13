package processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.DataObjectRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class Processor {

    @Autowired
    private DataObjectRepository repository;

    @PostConstruct
    public void init() throws IOException, InterruptedException {

        //pegar pacientes da worklist
        String date = "20161015";
        Worklist wl = new Worklist(date);
        List<String> idPatients = new ArrayList<>();
       idPatients=wl.getWL();

        //System.out.println(idPatients.size());

        //pegar imagens dos pacientes da worklist
        //ImageOfPatient im = new ImageOfPatient();
       // im.getImageOfPatient(idPatients, date);

        //ler cabeçalho das imagens e  salvar informações no banco
        Header header = new Header();
        header.listHeader();

        //gerar relatório
        Report report = new Report();

    }
}
