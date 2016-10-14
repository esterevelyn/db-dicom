package processor.entity;
import org.springframework.data.annotation.Id;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public class Patient {

    @Id
    // private String id;
    public String idPatient;
    public String namePatient;
    public String birthDatePatient;
    public String sexPatient;


    //public List<String> DLP = new ArrayList<>();

    @Override
    public String toString() {
        return String.format(
                //"Customer[id=%s, idPatient='%s']",
                // id, idPatient);
                "Patient[Patient ID= %s,Patient’s Name= %s, Patient’s Sex= %s, Patient’s Birth Date= %s ]",
                idPatient, namePatient, sexPatient, birthDatePatient);
    }


    /*Map<String, String> study = new HashMap<>();

    public void insertData(String key, String value) {
        // Se for uma chave ainda inexistente
        // if (data.get(key) == null) {
        study.put(key, value);


        // } else { // Se j� existir chave, adiciona na lista j� existente
        // data.get(key).add(value);
        //   }


    }*/



    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public void setBirthDatePatient(String birthDatePatient) {
        this.birthDatePatient = birthDatePatient;
    }

    public void setSexPatient(String sexPatient) {
        this.sexPatient = sexPatient;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public String getBirthDatePatient() {
        return birthDatePatient;
    }

    public String getSexPatient() {
        return sexPatient;
    }

}
