package processor.entity;

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

    public void getImageOfPatient () throws InterruptedException {
        List<DicomObject> listP = new ArrayList<>();
        String hc = "HCRP113547";
        //
        DcmQR dcmqr = new DcmQR(hc);

        //servidor
        dcmqr.setCalledAET("CQCT", true); //remoto CQCT
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

        //String[] ts= new String[]{"1.2.840.10008.1.2"};
        dcmqr.setStoreDestination("C:/images");
        dcmqr.setMoveDest("HCRP113547");
        //dcmqr.addStoreTransferCapability("1.2.840.10008.5.1.4.1.1.2",ts);
        //dcmqr.addStoreTransferCapability(UID.CTImageStorage,new String[]{UID.ImplicitVRLittleEndian});
        dcmqr.addStoreTransferCapability(UID.SecondaryCaptureImageStorage, new String[]{UID.ImplicitVRLittleEndian});


        //ts[0]="1.2.840.10008.5.1.4.1.1.2";
        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.valueOf("PATIENT"));
        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
        dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);

        // dcmqr.addMatchingKey(Tag.toTagPath("PatientID"), "1230310K");

        //Altair - 8 estudos
        dcmqr.addMatchingKey(new int[]{Tag.PatientID}, "1330474K");

        //Roberto - 1 exame
        //dcmqr.addMatchingKey(new int[]{Tag.PatientID}, "0430620A");


        ///dcmqr.addMatchingKey(new int[]{Tag.StudyDate}, "20150904");
        //dcmqr.addMatchingKey(new int[]{Tag.StudyTime}, "152432.328000");
        //dcmqr.addMatchingKey(new int[]{Tag.SeriesInstanceUID},
        //"1.2.840.113704.1.111.4052.1475161513.7");
        dcmqr.configureTransferCapability(true);

        //dcmqr.addMatchingKey(Tag.toTagPath());
        //dcmqr.addMatchingKey(Tag.toTagPath("SOPClassUID"), "1.2.840.10008.5.1.4.1.1.2");


        //


        //dcmqr.addReturnKey(new int[]{Tag.PatientID});
        //dcmqr.addReturnKey(new int[]{Tag.SOPInstanceUID});
        //dcmqr.addReturnKey(new int[]{Tag.StudyTime});


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
            //System.out.println(e);
            //System.exit(1);
        } catch (org.dcm4che2.net.ConfigurationException e) {
            e.printStackTrace();
        }

        System.out.println("done");
        System.out.println("List Size = " + listP.size());
    }
}
