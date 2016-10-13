package processor;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.tool.dcmqr.DcmQR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public class ImageOfPatient {

    public void getImageOfPatient(List<String> idPatients, String data) throws InterruptedException {
        String hc = "HCRP113547";
        String path = "C:/images"+ " " + data + "new";
        DcmQR dcmqr = new DcmQR(hc);

        //servidor
        dcmqr.setCalledAET("CQCT", true); //remoto CQCT
        dcmqr.setRemoteHost("10.165.14.191"); // IP CQCT 10.165.14.191
        dcmqr.setRemotePort(104); //104

        //Local
        dcmqr.setCalling("HCRP113547");
        dcmqr.setLocalHost("143.107.141.242");
        dcmqr.setLocalPort(11104);

        //dcmqr.setDateTimeMatching(true);
        dcmqr.setCFind(true);
        dcmqr.setCGet(true);

        dcmqr.setStoreDestination(path);
        dcmqr.setMoveDest("HCRP113547");
        //dcmqr.addStoreTransferCapability("1.2.840.10008.5.1.4.1.1.2",ts);
        //dcmqr.addStoreTransferCapability(UID.CTImageStorage,new String[]{UID.ImplicitVRLittleEndian});
        dcmqr.addStoreTransferCapability(UID.SecondaryCaptureImageStorage,
                new String[]{UID.ImplicitVRLittleEndian});


        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.valueOf("PATIENT"));
        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
        dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
        //String id = idPatients.get(0);

        for (String idPatient : idPatients) {
            consultDB(idPatient, dcmqr);
        }

    }
    private void consultDB(String idPatient, DcmQR dcmqr) {
        List<DicomObject> listP = new ArrayList<>();
        dcmqr.addMatchingKey(new int[]{Tag.PatientID}, idPatient);
        //System.out.println(idPatient);
        dcmqr.configureTransferCapability(true);

        try {

            dcmqr.start();
            System.out.println("started");
            dcmqr.open();
            System.out.println("opened");
            listP = dcmqr.query();
            dcmqr.move(listP);
            System.out.println("queried");

            System.out.println("List Size = " + listP.size());
            System.out.println(listP);

            dcmqr.stop();
            dcmqr.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (org.dcm4che2.net.ConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("done");
        System.out.println("List Size = " + listP.size());
        //}
    }
}

