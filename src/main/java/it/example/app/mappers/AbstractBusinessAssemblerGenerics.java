package it.example.app.mappers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* @param <T5> MBean di input del BAssembler
* @param <T8> Input dell'operation del servizio
* @param <T9> Output dell'operation del servizio
* @param <T6> MBean di output del BAssembler
* @param <T>  Tipo delleccezione da intercettare
*
*/

public abstract class AbstractBusinessAssemblerGenerics<T1, T2, T3, T4> implements IBusinessAssemblerGenerics<T1, T2, T3, T4> {

	private static Logger log = LoggerFactory.getLogger(AbstractBusinessAssemblerGenerics.class);
	
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "AbstractBusinessAssemblerGenerics [version=" + version + "]";
	}

}