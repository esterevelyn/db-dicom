package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import processor.Processor;
//import processor.entity.DataObject;

public interface ProcessorRepository extends MongoRepository<Processor, String> {
}