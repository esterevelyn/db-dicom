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


    @Override
    public String toString() {
        return String.format(
                //"Customer[id=%s, idPatient='%s']",
                // id, idPatient);
                "Patient[Patient ID= %s,Patient’s Name= %s, Patient’s Sex= %s, Patient’s Birth Date= %s ]",
                idPatient, namePatient, sexPatient, birthDatePatient);
    }


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
