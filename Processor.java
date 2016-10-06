package processor;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.tool.dcmqr.DcmQR;
import org.dcm4che2.util.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.DataObjectRepository;
import processor.entity.DataObject;

import javax.annotation.PostConstruct;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Component
public class Processor {

    @Autowired
   private DataObjectRepository repository;
    //private ProcessorRepository repository;

    @PostConstruct
    public void init() throws IOException, InterruptedException {
        System.out.println("==========================================================");
        System.out.println("Processador de texto para uma estrutura de banco de dados.");
        System.out.println("==========================================================");
        List<DicomObject> listP = new ArrayList<>();
        List<DicomObject> listTemp = new ArrayList<>();
        String hc = "HCRP113547";
        //
        DcmQR dcmqr =  new DcmQR(hc);

        //servidor
        dcmqr.setCalledAET("CQCT",true); //remoto CQCT
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
        dcmqr.addStoreTransferCapability(UID.SecondaryCaptureImageStorage,new String[]{UID.ImplicitVRLittleEndian});




        //ts[0]="1.2.840.10008.5.1.4.1.1.2";
        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.valueOf("PATIENT"));
        //dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
       dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
        //dcmqr.addMatchingKey(Tag.toTagPath("PatientID"), "1230310K");
        //dcmqr.addMatchingKey(new int[]{Tag.PatientID}, "1330474K");

        //Roberto - 1 exame
        dcmqr.addMatchingKey(new int[]{Tag.PatientID}, "0430620A");
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


        try{
            dcmqr.start();
            System.out.println("started");
            dcmqr.open();
            System.out.println("opened");
            listP = dcmqr.query();
            //DicomObject serie=listP.get(0);
            //listTemp.add(serie);
            //dcmqr.move(listTemp);
            dcmqr.move(listP);
            listTemp.clear();
            //dcmqr.get(listP);
            System.out.println("queried");

            System.out.println("List Size = " + listP.size());
            System.out.println(listP);

            dcmqr.stop();
            dcmqr.close();

        } catch (EOFException e) {
            //System.out.println(e);
            //System.exit(1);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        System.out.println("done");
        System.out.println("List Size = " + listP.size());

        //Collections.sort(serv);

       /* for(int i=0; i<listP.size(); i++) {

            String regImg = listP.get(i).toString();
            //System.out.println(imgF.getIdentifier());
            //System.out.println(regImg);
            String[] inf;
            inf = regImg.split("\n");

            //String patientID = inf[4].split(" ")[3];
           // String patientNam = "";
            String studyInsta = inf[5].split(" ")[3];
            studyInsta=studyInsta.replace("[", "");
            studyInsta=studyInsta.replace("]", "");
           // String studyDate = inf[1].split(" ")[3];
           // String studyTime = inf[2].split(" ")[3];
            //String SOPInstanc = inf[0].split(" ")[3];

            System.out.println(studyInsta);

        }*/


        //dcmqr.get(listP);
        //dcmqr.move(listP);
        Processor list = new Processor();
        //list.listHeader(listP.get(0));

        DicomObject dObject = null;

        try {
            DicomInputStream dis = new DicomInputStream
                    (new File("C:\\Users\\EsterIBm\\Documents\\Dicom\\DICOM\\S00001\\SER00001\\I00003"));
            dObject = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        //Processor list = new Processor();
        //list.listHeader(dObject);


       // try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        // DataObject object = new DataObject();

           // String line;
           // while ((line = br.readLine()) != null) {
                // Se for uma linha vazia, guarda o objeto populado e prepara um novo
              //  if (line.isEmpty()) {
                    //dataObjects.add(object);
                  //  object = new DataObject();
              //  } else { // Se é linha de dados, preenche no objeto atual
                    //String[] lineKeyValue = line.split("\t");
                   // object.insertData(lineKeyValue[0], lineKeyValue[1]);
             // }
          //  }

            // Essa linha imprimiria todos os objetos processados
            // dataObjects.forEach(System.out::println);

            //System.out.println("Total de objetos processados e estruturados: " + dataObjects.size());

            // Salva todos os objetos no banco
            //for (DataObject data : dataObjects) {
               // repository.save(data);
           // }

       // } catch (IOException e) {
          //  System.out.println("Nome de arquivo de entrada inválido.");
          //  e.printStackTrace();
       // }
    }

    public void listHeader(DicomObject dObject) {
       List<DataObject> dataObjects = new ArrayList<>();
        // Stack pilha = new Stack();
        ArrayList patient = new ArrayList();
        ArrayList study = new ArrayList();
        ArrayList tPatient = new ArrayList();
        Map<String, String> dataPatient = new HashMap<>();
        Map<String, String> dataStudy = new HashMap<>();
        Iterator<DicomElement> iter = dObject.datasetIterator();
        DataObject object = new DataObject();
        System.out.println(" ");
        while (iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dObject.nameOf(tag);
                System.out.println(tagName);
                String tagAddr = TagUtils.toString(tag);
                String tagValue;
                String tagVR = dObject.vrOf(tag).toString();
                if (!(tagVR.equals("SQ"))) {
                    tagValue = dObject.getString(tag);
                    //dataObjects.add(object);
                    switch (tagAddr) {
                        case "(0010,0010)":
                            System.out.println(tagName + " = " + tagValue);
                            dataPatient.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);

                            break;
                        case "(0010,0020)":

                            System.out.println(tagName + " = " + tagValue);
                            dataPatient.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);


                            break;
                        case "(0008,0020)":
                            System.out.println(tagName + " = " + tagValue);
                            //System.out.println(tagAddr+ tagName + " = " + tagValue);
                            dataStudy.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);

                            break;
                        case "(0008,0030)":
                            System.out.println(tagName + " = " + tagValue);
                            dataStudy.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,0040)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,0030)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0018,1030)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0008,1030)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0020,0010)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,1010)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0020,000D)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(00E1,1021)":
                            dataStudy.put("DLP", tagValue);
                            System.out.println("DLP" + " = " + tagValue);
                            object.insertData("DLP", tagValue);
                        default:
                            break;
                    }
                   // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
                }
               // dataObjects.forEach(System.out::println);


            } catch (Exception e) {
                e.printStackTrace();

            }
            //dataObjects.add(object);
        }
       // object.Nam("DLP");
       // dataObjects.add(object);
        //object = new DataObject();
        dataObjects.add(object);
        //object.get();
        patient.add(dataPatient);
        study.add(dataStudy);
        patient.add(study);
        tPatient.add(patient);
        //System.out.print("LISTA = ");
       // tPatient.forEach(System.out::println);
        //System.out.print(dataObjects.get(0));
        //Essa linha imprimiria todos os objetos processados
        //System.out.println();
        //dataObjects.forEach(System.out::println);
        //dataObjects.get(1);
     // for (DataObject data : dataObjects) {
        //System.out.println(dataObjects);
//       repository.save(dataObjects.get(0));

           //System.out.println(data);
      // }
       // dataObjects.
        // System.out.println(tPatient.size());

    }
}
