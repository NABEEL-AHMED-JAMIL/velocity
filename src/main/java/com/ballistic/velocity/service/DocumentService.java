package com.ballistic.velocity.service;

import com.ballistic.velocity.model.pojo.Document;
import com.ballistic.velocity.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document) { return this.documentRepository.save(document); }

    public List<Document> saveDocuments(List<Document> documents) { return this.documentRepository.saveAll(documents); }

    public List<Document> getAllDocumentBySourceId(String fieldType, String sourceID) { return this.documentRepository.getAllDocumentBySourceId(fieldType, sourceID); }

    public Page<Document> findPaginated(int page, int size) { return documentRepository.findAll(new PageRequest(page, size)); }

}