package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import processor.entity.Study;

/**
 * Created by EsterIBm on 11/10/2016.
 */
public interface StudyRepository extends MongoRepository<Study, String> {
    public Study findByIdStudy(String idStudy);
}