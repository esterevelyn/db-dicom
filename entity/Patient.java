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
    public double dlpTotal;
    public double eDlpTotal;


   /* @Override
    public String toString() {
        return String.format(
                //"Customer[id=%s, idPatient='%s']",
                // id, idPatient);
                "Patient[Patient ID= %s,Patient’s Name= %s, Patient’s Sex= %s, Patient’s Birth Date= %s, DLPtotal= %s ]",
                idPatient, namePatient, sexPatient, birthDatePatient,dlpTotal);
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

    public double getDlpTotal() {
        return dlpTotal;
    }

    public double geteDlpTotal() {
        return eDlpTotal;
    }

    public void seteDlpTotal(double eDlpTotal) {
        this.eDlpTotal += eDlpTotal;
    }

    public void setDlpTotal(double dlpTotal) {
        //System.out.println(this.dlpTotal + " + " + dlpTotal );
        this.dlpTotal += dlpTotal;
        //System.out.println("depois" + this.dlpTotal + " + " + dlpTotal );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return idPatient.equals(patient.idPatient);

    }

    @Override
    public int hashCode() {
        return idPatient.hashCode();
    }
}
