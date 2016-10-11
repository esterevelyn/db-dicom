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

    public ArrayList<String> getWL(){
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

        

       // System.out.println(wl);

        /*dcmqr.setCalledAET("CQCT",true); //remoto CQCT
        dcmqr.setRemoteHost("10.165.14.191"); // IP CQCT 10.165.14.191
        dcmqr.setRemotePort(104); //104


        //Local
        dcmqr.setCalling("HCRP113547");
        dcmqr.setLocalHost("143.107.141.242");
        //dcmqr.setLocalHost("localhost");
        dcmqr.setLocalPort(11104);

        //dcmqr.setDateTimeMatching(true);
        dcmqr.setCFind(true);
        dcmqr.setCGet(true);
        */
        return null;

    }
}
