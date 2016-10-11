package processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.DataObjectRepository;
import processor.entity.Header;
import processor.entity.ImageOfPatient;
import processor.entity.Report;
import processor.entity.Worklist;

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
        Worklist wl = new Worklist("20161012");
        List idPatients= new ArrayList();
        idPatients=wl.getWL();

        //pegar imagens dos pacientes da worklist
        ImageOfPatient im = new ImageOfPatient();
        im.getImageOfPatient(idPatients);

        //ler cabeçalho das imagens e  salvar informações no banco
        Header header = new Header();
       // header.listHeader();

        //gerar relatório
        Report report = new Report();

    }
}
