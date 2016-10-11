package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import processor.entity.Patient;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public interface PatientRepository extends MongoRepository<Patient, String> {

        public Patient findByIdPatient(String idPatient);
        // public List<DataObject> findByLastName(String lastName);

}