package com.ballistic.velocity.repository;

import com.ballistic.velocity.model.pojo.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {

    @Query("{ ?0 : ?1}")
    public List<Document> getAllDocumentBySourceId(String fieldType, String sourceID);

}
