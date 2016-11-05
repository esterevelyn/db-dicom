package processor;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class Processor {
    @Autowired
    private ExtractionHeader eHeader;
    @Autowired
    private Report report;

    @PostConstruct
    public void init() throws IOException, InterruptedException, JRException {
        String date = "20161031 Teste";
        List<String> idPatients = new ArrayList<>();
        /*try {
            FileReader arq;
            arq = new FileReader("C:\\Users\\EsterIBm\\Documents\\IDfinal.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            idPatients.add(linha);
            while (linha != null) {
                System.out.printf("%s\n", linha);
                linha = lerArq.readLine(); // lê da segunda até a última linha
                 if(linha != null) {
                    idPatients.add(linha);
                }
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }

        //pegar pacientes da worklist

        //Worklist wl = new Worklist(date);
        //List<String> idPatients=wl.getWL();

        System.out.println(idPatients);
        System.out.println(idPatients.size());*/
        //idPatients.add("0117429I");
        //pegar imagens dos pacientes da worklist
       // ImageOfPatient im = new ImageOfPatient();
        //im.getImageOfPatient(idPatients, date);

        //ler cabeçalho das imagens e  salvar informações no banco
       eHeader.listHeader();

        //report.generateDynamicReport();

    }

}

