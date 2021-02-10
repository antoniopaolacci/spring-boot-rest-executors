package it.example.app.mappers;

public interface IBusinessAssemblerGenerics<T1, T2, T3, T4> {

	public T3 doInputMapping(T1 sourceBean) throws Throwable;

	public Object doInputMappingUriMap(T1 sourceBean) throws Throwable;

	public T2 doOutputMapping(T4 response) throws Throwable;

	public String getVersion();
	
}
