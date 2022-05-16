package com.utopian.weather.mapper;


import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.modelmapper.Converter;

public interface ServiceMapper {

    <D> D map(Object source, Class<D> destinationType);

    <D> D map(Object source, D destination);

    <D> D map(Object source, Type destination);

    <D> List<D> mapToList(Collection<?> source, Class<D> destination);

    <S, D> Converter<S, D> getConverter(Class<S> source, Class<D> destinationType);

    <S, D> Converter<S, D> getPostConverter(Class<S> source, Class<D> destinationType);

}
