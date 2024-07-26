package dev.patika.VeterinaryManagementSystem.core.config.ModelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {

    //Returns a ModelMapper instance configured for request mappings.
    ModelMapper forRequest();

    //Returns a ModelMapper instance configured for response mappings.
    ModelMapper forResponse();
}
