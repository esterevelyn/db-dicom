package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import processor.entity.DataObject;

public interface DataObjectRepository extends MongoRepository<DataObject, String> {
}