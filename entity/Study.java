package processor.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public class Study {

    @Id
    public String idStudy;
    public String studyDate;
    public String studyTime;
    public String studyDescription;
    public String protocolName;
    public String kvp;
    public double dlp;
    private String patientAge;
    private double eDl;
    @DBRef
    Patient patient;

    /*@Override
    public String toString() {
        return String.format(
                //"Customer[id=%s, idPatient='%s']",
                // id, idPatient);
                "Image[Patient ID= %s,Study Date= %s, Study Time= %s, Study Description= %s, Protocol Name= %s, DLP= %s ]",
                idStudy, studyDate, studyTime, studyDescription, protocolName, kvp, dlp);
    }*/

    public void setIdStudy(String idStudy) {
        this.idStudy = idStudy;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public void setKvp(String kvp) {
        this.kvp = kvp;
    }

    public void setDlp(double dlp) {
        this.dlp = dlp;
    }

    public String getIdStudy() {
        return idStudy;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public String getKvp() {
        return kvp;
    }

    public double getDlp() {
        return dlp;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double geteDl() {
        return eDl;
    }

    public void seteDl(double eDl) {
        this.eDl=eDl;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Study study = (Study) o;

        return idStudy != null ? idStudy.equals(study.idStudy) : study.idStudy == null;

    }

    @Override
    public int hashCode() {
        return idStudy != null ? idStudy.hashCode() : 0;
    }
}






