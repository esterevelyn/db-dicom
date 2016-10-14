package processor;

import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.tool.dcmmwl.DcmMWL;

import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public class Worklist {

    private String date;


    public Worklist(String date) {
        this.date = date;
    }

    public List<String> getWL() throws IOException {
        List queryListP = new ArrayList<>();
        List<String> idPatients = new ArrayList<>();
        String aet = "HCRP113547";
        DcmMWL wl = new DcmMWL(aet);

        //servidor
        wl.setCalledAET("LYRIA_WL_HC_OFF");
        wl.setRemoteHost("10.165.0.100");
        wl.setRemotePort(11155);

        //local
        wl.setCalling(aet);
        wl.setLocalHost("143.107.141.242");

        wl.setTransferSyntax(new String[]{UID.ImplicitVRLittleEndian});
        //wl.addMatchingKey(new int[]{Tag.ScheduledProcedureStepStartDate}, "20161015");
        wl.addMatchingKey(new int[]{Tag.Modality}, "CT");
        //wl.addMatchingKey(new int[]{Tag.StudyDate}, "20161012");
        //wl.addMatchingKey(new int[]{Tag.ScheduledProcedureStepStartDate}, "20161014");
        //wl.addReturnKey(new int[]{Tag.CTAcquisitionTypeSequence});
        //wl.;


        try{
            wl.open();

            System.out.println("opened");
            queryListP = wl.query();

            System.out.println("queried");

            System.out.println("List Size = " + queryListP.size());
           // System.out.println(listP);
           wl.close();

        } catch (EOFException e) {
            //System.out.println(e);
            //System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.dcm4che2.net.ConfigurationException e) {
            e.printStackTrace();
        }

        //pegar somente o Patient ID

        idPatients=getOnlyIDPatient(queryListP, idPatients);

        System.out.println(idPatients.size());

        //salvar IDs dos pacientes em um txt
        salveTXT(idPatients);

        return idPatients;

    }

    private List<String> getOnlyIDPatient(List listP, List<String> idPatients){

        for(int i=0; i<listP.size(); i++) {
            String regImg = listP.get(i).toString();
            String[] inf;
            inf = regImg.split("\n");
            String id = inf[4].split(" ")[3];
            id=id.replace("[", "");
            id=id.replace("]", "");
            idPatients.add(id);
            // String studyDate = inf[1].split(" ")[3];
            // String studyTime = inf[2].split(" ")[3];
            //String SOPInstanc = inf[0].split(" ")[3];
            //System.out.println(studyInsta);
        }
        //List to List<String>

        // List<String> idPatients = new ArrayList<>();
        //for (Object object : listP) {
        // idPatients.add(Objects.toString(object, null));
        // }

        return idPatients;

    }

    private void salveTXT (List<String> idPatients) throws IOException {

        FileWriter arq = new FileWriter("C:\\Users\\EsterIBm\\Documents\\ID Patient HOJE.txt");
        PrintWriter gravarArq = new PrintWriter(arq);

        for (String object : idPatients) {
            gravarArq.println(object);
        }
        arq.close();

    }
}
