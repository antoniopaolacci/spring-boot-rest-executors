package it.example.app.mappers;

import it.example.app.rest.exceptions.RestServiceCallException;

public interface IBusinessAssemblerGenerics<T1, T2, T3, T4> {

	public T3 doInputMapping(T1 sourceBean) throws RestServiceCallException;

	public Object doInputMappingUriMap(T1 sourceBean) throws RestServiceCallException;

	public T2 doOutputMapping(T4 response) throws RestServiceCallException;

	public String getVersion();

	public T2 doExceptionMapping();
	
}
