package processor.entity;

import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.tool.dcmmwl.DcmMWL;

import java.io.EOFException;
import java.io.IOException;
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

    public List<String> getWL(){
        List listP = new ArrayList<>();
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
       wl.addMatchingKey(new int[]{Tag.Modality}, "CT");
        //wl.addReturnKey(new int[]{Tag.CTAcquisitionTypeSequence});


        try{
            wl.open();

            System.out.println("opened");
            listP = wl.query();

            System.out.println("queried");

            System.out.println("List Size = " + listP.size());
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


        for(int i=0; i<listP.size(); i++) {
            String regImg = listP.get(i).toString();
            //System.out.println(imgF.getIdentifier());
            //System.out.println(regImg);
            String[] inf;
            inf = regImg.split("\n");
            //String patientID = inf[4].split(" ")[3];
            // String patientNam = "";
            String studyInsta = inf[4].split(" ")[3];
            studyInsta=studyInsta.replace("[", "");
            studyInsta=studyInsta.replace("]", "");
            // String studyDate = inf[1].split(" ")[3];
            // String studyTime = inf[2].split(" ")[3];
            //String SOPInstanc = inf[0].split(" ")[3];
            System.out.println(studyInsta);
        }

        return listP;

    }
}
