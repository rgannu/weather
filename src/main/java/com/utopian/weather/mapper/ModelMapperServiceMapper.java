package com.utopian.weather.mapper;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.modelmapper.Converter;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ModelMapperServiceMapper implements ServiceMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ModelMapperServiceMapper() {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setCollectionsMergeEnabled(false);
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        try{
            if (source != null) {
                return modelMapper.map(source, destinationType);
            } else {
                return null;
            }
        }
        catch(MappingException me){
            throw convertException(me);
        }
    }

    private RuntimeException convertException(MappingException me) {
        if(me.getErrorMessages() != null){
            return me.getErrorMessages().stream()
                    .filter(errorMessage -> RuntimeException.class.isAssignableFrom(errorMessage.getCause().getClass()))
                    .findFirst()
                    .<RuntimeException>map(errorMessage -> (RuntimeException)errorMessage.getCause())
                    .orElse(me);
        }
        return me;
    }

    @Override
    public <D> D map(Object source, D destination) {
        try{
            if (source != null) {
                modelMapper.map(source, destination);
                return destination;
            } else {
                return null;
            }
        }
        catch(MappingException me){
            throw convertException(me);
        }
    }

    @Override
    public <D> D map(Object source, Type destination) {
        try {
            if (source != null) {
                return modelMapper.map(source, destination);
            } else {
                return null;
            }
        } catch (MappingException me) {
            throw convertException(me);
        }
    }

    @Override
    public <D> List<D> mapToList(Collection<?> source, Class<D> destination) {
        checkNotNull(destination);
        return ofNullable(source).map(Collection::stream).orElseGet(Stream::empty).map(s -> map(s, destination)).collect(Collectors.toList());
    }

    @Override
    public <S, D> Converter<S, D> getConverter(Class<S> source, Class<D> destinationType) {
        TypeMap<S, D> typeMap = modelMapper.getTypeMap(source, destinationType);
        if(typeMap != null) {
            return typeMap.getConverter();
        } else {
            return null;
        }
    }

    @Override
    public <S, D> Converter<S, D> getPostConverter(Class<S> source, Class<D> destinationType) {
        TypeMap<S, D> typeMap = modelMapper.getTypeMap(source, destinationType);
        if(typeMap != null) {
            return typeMap.getPostConverter();
        } else {
            return null;
        }
    }

}
