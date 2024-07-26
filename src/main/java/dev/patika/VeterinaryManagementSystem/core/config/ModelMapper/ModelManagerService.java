package dev.patika.VeterinaryManagementSystem.core.config.ModelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelManagerService implements IModelMapperService {

    private final ModelMapper modelMapper;

    @Autowired
    public ModelManagerService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

     // Configures the ModelMapper instance for request mappings.
    // Sets the ambiguity to be ignored and uses strict matching strategy.

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return this.modelMapper;
    }

    //Configures the ModelMapper instance for response mappings.
    // Sets the ambiguity to be ignored and uses strict matching strategy

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return this.modelMapper;
    }
}