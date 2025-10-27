package com.example.neu.util;

import com.example.neu.dto.auditlog.AuditLogResponse;
import com.example.neu.dto.caseDto.CaseRequest;
import com.example.neu.dto.caseDto.CaseResponse;
import com.example.neu.dto.casecasetag.CaseCaseTagResponse;
import com.example.neu.dto.casefile.CaseFileResponse;
import com.example.neu.dto.caseperson.CasePersonResponse;
import com.example.neu.dto.casetag.CaseTagRequest;
import com.example.neu.dto.casetag.CaseTagResponse;
import com.example.neu.dto.category.CategoryRequest;
import com.example.neu.dto.category.CategoryResponse;
import com.example.neu.dto.person.PersonRequest;
import com.example.neu.dto.person.PersonResponse;
import com.example.neu.entity.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ValueMapper {
    ValueMapper MAPPER = Mappers.getMapper(ValueMapper.class);

    CategoryResponse convertToCategoryResponse(Category category);
    Category convertToCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> convertToCategoryResponseList(List<Category> categories);

    CaseTagResponse convertToCaseTagResponse(CaseTag caseTag);
    CaseTag convertToCaseTag(CaseTagRequest caseTagRequest);
    List<CaseTagResponse> convertToCaseTagResponseList(List<CaseTag> caseTagList);

    PersonResponse convertToPersonResponse(Person person);
    Person convertToPerson(PersonRequest personRequest);
    List<PersonResponse> convertToPersonResponseList(List<Person> persons);

    CaseResponse convertToCaseResponse(Case caseEntity);
    Case convertToCase(CaseRequest caseRequest);
    List<CaseResponse> convertToCaseResponseList(List<Case> cases);

    @Named("convertToCasePersonResponse")
    @Mapping(source = "caseEntity.id", target = "caseId")
    @Mapping(source = "person.id", target = "personId")
    CasePersonResponse convertToCasePersonResponse(CasePerson casePerson);

    @IterableMapping(qualifiedByName = "convertToCasePersonResponse")
    List<CasePersonResponse> convertToCasePersonResponseList(List<CasePerson> casePersons);

    @Named("convertToCaseCaseTagResponse")
    @Mapping(source = "caseEntity.id", target = "caseId")
    @Mapping(source = "tag.id", target = "caseTagId")
    CaseCaseTagResponse convertToCaseCaseTagResponse(CaseCaseTag caseCaseTag);

    @IterableMapping(qualifiedByName = "convertToCaseCaseTagResponse")
    List<CaseCaseTagResponse> convertToCaseCaseTagResponseList(List<CaseCaseTag> caseCaseTags);

    @Mapping(source = "caseEntity.id", target = "caseId")
    @Mapping(source = "uploadedBy.id", target = "uploadedBy")
    CaseFileResponse convertToCaseFileResponse(CaseFile caseFile);

    List<CaseFileResponse> convertToCaseFileResponseList(List<CaseFile> caseFiles);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "caseEntity.id", target = "caseId")
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AuditLogResponse convertToAuditLogResponse(AuditLog auditLog);

    List<AuditLogResponse> convertToAuditLogResponseList(List<AuditLog> auditLogs);
}
