package processor.entity;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.PatientRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EsterIBm on 11/10/2016.
 */
@Component
public class Header {
    @Autowired
    private PatientRepository repository;

    public void listHeader() {

       DicomObject dObject = null;

        try {
        DicomInputStream dis = new DicomInputStream
                (              new File("C:\\Users\\EsterIBm\\Documents\\Dicom\\DICOM\\S00001\\SER00001\\I00003"));
        dObject = dis.readDicomObject();
        dis.close();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.exit(0);
    }
    //Processor list = new Processor();
    //list.listHeader(dObject);
    // }

    // public void listHeader(DicomObject dObject) {
    List<Patient> patients = new ArrayList<>();
    Iterator<DicomElement> iter = dObject.datasetIterator();
    Patient patient = new Patient();
        Study study = new Study();

        while (iter.hasNext()) {
        DicomElement element = iter.next();
        int tag = element.tag();
        try {
            String tagName = dObject.nameOf(tag);
            String tagAddr = TagUtils.toString(tag);
            String tagValue;
            String tagVR = dObject.vrOf(tag).toString();
            if (!(tagVR.equals("SQ"))) {
                tagValue = dObject.getString(tag);

                switch (tagAddr) {
                    case "(0010,0010)":
                        System.out.println(tagName + " = " + tagValue);
                        //object.insertData(tagName, tagValue);
                        patient.setNamePatient(tagValue);
                        break;
                    case "(0010,0020)":

                        System.out.println(tagName + " = " + tagValue);
                        //object.insertData(tagName, tagValue);
                        patient.setIdPatient(tagValue);
                        break;
                    case "(0008,0020)":
                        System.out.println(tagName + " = " + tagValue);
                        study.setStudyDate(tagValue);

                        break;
                    case "(0008,0030)":
                        System.out.println(tagName + " = " + tagValue);
                        study.setStudyTime(tagValue);
                        break;
                    case "(0010,0040)":

                        System.out.println(tagName + " = " + tagValue);
                        //object.insertData(tagName, tagValue);
                        patient.setSexPatient(tagValue);
                        break;
                    case "(0010,0030)":
                        System.out.println(tagName + " = " + tagValue);
                        //object.insertData(tagName, tagValue);
                        patient.setBirthDatePatient(tagValue);
                        break;
                    case "(0018,1030)":
                        System.out.println(tagName + " = " + tagValue);
                        patient.insertData(tagName, tagValue);
                        break;
                    case "(0008,1030)":
                        System.out.println(tagName + " = " + tagValue);
                        patient.insertData(tagName, tagValue);
                        break;
                    case "(0020,0010)":
                        System.out.println(tagName + " = " + tagValue);
                        patient.insertData(tagName, tagValue);
                        break;
                    case "(0010,1010)":
                        System.out.println(tagName + " = " + tagValue);
                        //object.insertData(tagName, tagValue);
                        patient.setAgePatient(tagValue);
                        break;
                    case "(0020,000D)":
                        System.out.println(tagName + " = " + tagValue);
                        patient.insertData(tagName, tagValue);
                        break;
                    case "(00E1,1021)":
                        System.out.println("DLP" + " = " + tagValue);
                        //patient.insertData("DLP", tagValue);
                        study.setDlp(tagValue);
                    default:
                        break;
                }
                // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
        patients.add(patient);

      /* for (Patient data : patients) {
            repository.save(data);
        }
    //System.out.println(repository.findByIdPatient(""));
    //for (DataObject customer : repository.findAll()) {
           System.out.println(repository.findByIdPatient("1230310K"));
    // }*/
    }
}
