package processor;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.PatientRepository;
import processor.db.StudyRepository;
import processor.entity.Patient;
import processor.entity.Study;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

/**
 * Created by EsterIBm on 11/10/2016.
 */

@Component
public class ExtractionHeader {

    @Autowired private PatientRepository repositoryP;
    @Autowired private StudyRepository repositoryS;

    @PostConstruct
    public void listHeader() {
        List<DicomObject> dicomObjectList = new ArrayList<>();
        DicomObject dObjec = null;

        try {
           /* DicomInputStream dis = new DicomInputStream
                    (              new File("C:\\Users\\EsterIBm\\Documents\\Dicom\\DICOM\\S00001\\SER00001\\I00003"));*/
            File file = new File("C:/images 20161014");
            File[] files = file.listFiles();
            files = file.listFiles();
            //lendo todos os arquivos da pasta
            for (int i = 0; i < files.length; i++) {
                DicomInputStream dis = new DicomInputStream(files[i]);
                dObjec = dis.readDicomObject();
                dicomObjectList.add(dObjec);
                if (i == (files.length - 1)) {
                    dis.close();
                }
            }
            //System.out.println(dicomObjectList.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        getHeader(dicomObjectList);
    }


    public void getHeader(List<DicomObject> dicomObjectList) {

        Set<Patient> patients = new HashSet<>();
        Set<Study> studies = new HashSet<>();
        Map<String, String> data = new HashMap<>();
        String key = null;
        String value = null;
        Set<Map> mapES = new HashSet<>();
        String anterior = null;
        for (DicomObject dObject : dicomObjectList) {
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
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setNamePatient(tagValue);
                                break;
                            case "(0010,0020)":
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setIdPatient(tagValue);
                                key = tagValue;
                                break;
                            case "(0010,0040)":
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setSexPatient(tagValue);
                                break;
                            case "(0010,0030)":
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setBirthDatePatient(tagValue);
                                break;
                            case "(0018,1030)":
                                //System.out.println(tagName + " = " + tagValue);
                                study.setProtocolName(tagValue);
                                break;
                            case "(0008,1030)":
                                //System.out.println(tagName + " = " + tagValue);
                                study.setStudyDescription(tagValue);
                                break;
                            case "(0008,0020)":
                                //System.out.println(tagName + " = " + tagValue);
                                study.setStudyDate(tagValue);
                                break;
                            case "(0008,0030)":
                                //System.out.println(tagName + " = " + tagValue);
                                study.setStudyTime(tagValue);
                                break;
                            case "(0018,0060)":
                                //System.out.println(tagName + " = " + tagValue);
                                study.setKvp(tagValue);
                                break;
                            case "(0020,000D)": //Study Instance UID
                                //System.out.println(tagName + " = " + tagValue);
                                study.setIdStudy(tagValue);
                                value=tagValue;
                                /*if(anterior==null){
                                    anterior= tagValue;
                                }
                                if(anterior != tagValue){
                                   Study study1= studies.get();
                                    if (study1.getDlp()==null){
                                        study1.setDlp("NA");
                                    }
                                }*/
                                break;
                            case "(00E1,1021)":
                                //System.out.println("DLP" + " = " + tagValue);
                                study.setDlp(tagValue);
                                //System.out.println(study.getDlp());
                            default:
                                break;
                        }
                        // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (study.getDlp() != null) {
                //if(!patients.contains(patient)) {
                    patients.add(patient);
                //System.out.println(patient);

               // }
                studies.add(study);
                data.put(key, value);
                mapES.add(data);

            }


        }

       System.out.println(mapES.size());
        System.out.println(data);
        //System.out.println(studies.size());

        /*for (Patient patient : patients) {
            System.out.println(patient);
        }*/
        // salvando objeto no banco

        for (Patient patient : patients) {
            for (Study study : studies){
                for(Map map: mapES){
                    if(map.containsKey(patient.getIdPatient())== map.containsValue(study.getIdStudy())){
                        study.setPatient(patient);
                    }
                }
            }
        }
    }
    //verify(studies);


    //repositoryP.deleteAll();
    //repositoryS.deleteAll();

    private void savePatientsDD(List<Patient> patients) {

        for (Patient patient : patients) {
            repositoryP.save(patient);
        }




    }

    private void saveStudiesDD(List<Study> studies) {

        for (Study study : studies) {
            repositoryS.save(study);
        }
    }




    //System.out.println(repository.findByIdPatient(""));
    //for (DataObject customer : repository.findAll()) {
    // System.out.println(repositoryP.findByIdPatient("1230310K"));
    // }*/
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // Agora o diretório está vazio, restando apenas deletá-lo.
        return dir.delete();
    }

    private void verify(List<Study> studies) {
        for (Study s : studies) {
            // if (s.getDlp().equals(null)) {
            studies.remove(s);
            //}
        }

    }
}
