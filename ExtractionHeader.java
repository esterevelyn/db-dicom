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

import java.io.File;
import java.util.*;

/**
 * Created by EsterIBm on 11/10/2016.
 */

@Component
public class ExtractionHeader {

    @Autowired private PatientRepository repositoryP;
    @Autowired private StudyRepository repositoryS;

    public void listHeader() {
        List<DicomObject> dicomObjectList = new ArrayList<>();
        DicomObject dObjec = null;

        try {
           /* DicomInputStream dis = new DicomInputStream
                    (              new File("C:\\Users\\EsterIBm\\Documents\\Dicom\\DICOM\\S00001\\SER00001\\I00003"));*/
            File file = new File("C:\\P1");
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
        String anterior = null;
        double tagValueDouble;
        double eDlp;
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
                                value = tagValue;
                                break;
                            case "(0010,0040)":
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setSexPatient(tagValue);
                                break;
                            case "(0010,0030)":
                                //System.out.println(tagName + " = " + tagValue);
                                patient.setBirthDatePatient(tagValue);
                                break;
                            case "(0010,1010)":
                                study.setPatientAge(tagValue);

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
                                key = tagValue;
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
                                if (tagValue != null) {
                                    tagValueDouble = Double.parseDouble(tagValue);
                                    study.setDlp(tagValueDouble);
                                }
                                boolean ok = false;

                                //ver(tagValue, patients,value,patient);
                                // System.out.println(study.getDlp());
                                //System.out.println(patient.getDlpTotal());

                            default:
                                break;
                        }
                        // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //patients.add(patient);
            if (study.getDlp() != 0) {
                patients.add(patient);
                studies.add(study);
                data.put(key, value);
                ///patient.setDlpTotal(study.getDlp());
                //System.out.println(value + ":" + patient.getDlpTotal());
            }


        }

        for (Study study : studies) {
            for (Patient patient : patients) {
                if (data.get(study.getIdStudy()).equals(patient.getIdPatient())) {
                    patient.setDlpTotal(study.getDlp());
                    //System.out.println(patient.getDlpTotal());

                    //System.out.println(patient.getIdPatient() + ":" + patient.getDlpTotal());
                    //System.out.println(study.getPatient());
                }
            }
        }

        System.out.println(data.size());
        System.out.println(data);

        //repositoryP.deleteAll();
        //repositoryS.deleteAll();

        //savePatientsDD(patients);

        for (Study study : studies) {
            for (Patient patient : patients) {
                if (data.get(study.getIdStudy()).equals(patient.getIdPatient())) {
                    study.setPatient(patient);
                    patient.setDlpTotal(study.getDlp());
                    String age = study.getPatientAge().replaceFirst("^0+(?!$)", "");
                    age = age.substring(0, age.length() - 1);
                    int ageInt = Integer.parseInt(age);
                    if (study.getStudyDescription().toLowerCase().contains("ABDOME")) {

                        if (study.getPatientAge().contains("Y")) {

                            if (1 <= ageInt && ageInt < 5) {
                                study.seteDl(study.getDlp() * 0.0300);

                            } else if (5 <= ageInt && ageInt < 10) {
                                study.seteDl(study.getDlp() * 0.0200);

                            } else if (10 <= ageInt && ageInt < 20) {
                                study.seteDl(study.getDlp() * 0.0150);

                            } else {
                                study.seteDl(study.getDlp() * 0.0150);

                            }
                        } else if (study.getPatientAge().contains("M")) {
                            study.seteDl(study.getDlp() * 0.0490);

                        }

                    } else if (study.getStudyDescription().toUpperCase().contains("TORAX")) {
                        if (study.getPatientAge().contains("Y")) {

                            if (1 <= ageInt && ageInt < 5) {
                                study.seteDl(study.getDlp() * 0.0260);

                            } else if (5 <= ageInt && ageInt < 10) {
                                study.seteDl(study.getDlp() * 0.0180);

                            } else if (10 <= ageInt && ageInt < 20) {
                                study.seteDl(study.getDlp() * 0.0130);

                            } else {
                                study.seteDl(study.getDlp() * 0.0140);

                            }
                        } else if (study.getPatientAge().contains("M")) {
                            study.seteDl(study.getDlp() * 0.0390);

                        } else if (study.getStudyDescription().toUpperCase().contains("CRANIO")) {
                            if (study.getPatientAge().contains("Y")) {

                                if (1 <= ageInt && ageInt < 5) {
                                    study.seteDl(study.getDlp() * 0.0067);

                                } else if (5 <= ageInt && ageInt < 10) {
                                    study.seteDl(study.getDlp() * 0.0040);

                                } else if (10 <= ageInt && ageInt < 20) {
                                    study.seteDl(study.getDlp() * 0.0032);

                                } else {
                                    study.seteDl(study.getDlp() * 0.0021);

                                }
                            } else if (study.getPatientAge().contains("M")) {
                                study.seteDl(study.getDlp() * 0.0110);

                            }

                        }

                        //System.out.println(patient.getIdPatient() + ":" + patient.getDlpTotal());
                        //System.out.println(study.getPatient());
                    }
                    System.out.println(study.geteDl());
                    patient.seteDlpTotal(study.geteDl());
                    System.out.println(patient.geteDlpTotal());

                }
            }

            // saveStudiesDD(studies);

            //repositoryS.

            //repositoryS.findByPacient(patient);

        }
    }


    private void savePatientsDD(Set<Patient> patients) {

        for (Patient patient : patients) {
            repositoryP.save(patient);
        }

    }

    private void saveStudiesDD(Set<Study> studies) {

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

    private void ver(String tagValue, Set<Patient>patients,String value, Patient patient){
        boolean ok=false;
        double tagValueDouble;
        for(Patient p:patients) {
            if (p.getIdPatient().equals(value)) {
                tagValueDouble = Double.parseDouble(tagValue);
                p.setDlpTotal(tagValueDouble);
                System.out.println(p.getIdPatient());
                ok=true;
            }
        }
        if(!ok){
            tagValueDouble = Double.parseDouble(tagValue);
            patient.setDlpTotal(tagValueDouble);

        }

    }
}
