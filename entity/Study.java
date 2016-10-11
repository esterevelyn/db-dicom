package processor.entity;
import org.springframework.data.annotation.Id;
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
    public String dlp;

    @Override
    public String toString() {
        return String.format(
                //"Customer[id=%s, idPatient='%s']",
                // id, idPatient);
                "Image[Patient ID= %s,Study Date= %s, Study Time= %s, Study Description= %s, Protocol Name= %s, DLP= %s ]",
                idStudy, studyDate, studyTime, studyDescription, protocolName, kvp, dlp);
    }

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

    public void setDlp(String dlp) {
        this.dlp = dlp;
    }
}






