package it.example.app.executors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 *
 * Classe per le chiamate Rest
 *
 * Configurazione di spring:
 * 
 * <bean id="listaParametriService" class=
 * "it.generali.application.dna.business.service.portafoglio.ListaParametriService"
 * scope="singleton"> <property name="webAssembler" ref=
 * "listaParametriWAssembler" /> <property name="businessAssembler" ref=
 * "listaParametriBAssembler" /> <property name="serviceUrl" value=
 * "${GestoreProdotto.listaParametri.url}" /> <property name=
 * "isVisibilityDetails" value="false" /> </bean>
 *
 * @param <T3>
 *            BBean di input del WAssembler
 * @param <T4>
 *            BBean di output del WAssembler
 * @param <T5>
 *            MBean di input del BAssembler
 * @param <T6>
 *            MBean di output del BAssembler
 * @param <T8>
 *            Input del operation del servizio
 * @param <T9>
 *            Output del operation del servizio
 * @param <T10>
 *            Tipo del eccezione da intercettare
 */

public abstract class AbstractRestServiceExecutor<T3, T4, T5, T6, T8, T9, T10> {

	Logger log = LoggerFactory.getLogger(AbstractRestServiceExecutor.class);

	private IWebAssemblerGenerics<T3, T5, T6, T4, T10> webAssembler; // WebAssembler, hanno i Generics a loro volta

	private IBusinessAssemblerGenerics<T5, T8, T9, T6, T10> businessAssembler; // BusinessAssembler, hanno i Generics a loro volta

	protected String serviceUrl; // ServiceProxy
	
	protected String xIbmClientId; // Client ID per servizi FUSE
	
	protected String xIbmClientIdSoggetto; // Client ID per servizi FUSE Soggetto

	@Autowired
	@Qualifier("restTemplate")
	protected RestTemplate restTemplate;

	// Indica se il servizio utilizza la nuova o la vecchia visibilita'
	private Boolean visibilityDetails;
	private Boolean fuseVisibilita;
	private Boolean fuseDettaglioVisibilita;
	private Boolean gdmAuth;

	// Metodo richiamato dal ControllerBean per eseguire il servizio richiesto

	public T4 doService(T3 t3, String errorAreaId) throws Throwable {

		String flowId = "NO FLOW";

		long timeStart = Calendar.getInstance().getTimeInMillis();

		HttpEntity<?> request = null;
		// Service Response
		T9 t9 = null;

		String codiceSistema = this.businessAssembler.getCodiceSistema();
		if(codiceSistema == null || codiceSistema.isEmpty()) {
			codiceSistema = Constants.SISTEMA_DNA;
		}

		Map<String, String> uriMap = null;
		List<MockServiziMBean> mockAttivo = null;

		try {
			
			T5 t5 = this.webAssembler.doInputMapping(t3);

			// Recupero la chiave di cache dato l'input
			String cacheKey = this.businessAssembler.getCacheKey(t5);

			// Recupero dall'eventuale output in cache
			T6 t6 = this.businessAssembler.getCachedOutput(cacheKey);

			List<WarningMBean> listWarningMBean = null;

			// se null chiamo il servizio
			if(t6==null) {
				
				// Trasformazione da MBean a InputServizio
				T8 t8 = this.businessAssembler.doInputMapping(t5);
			
				// Da implementare nel BAssembler per i metodi http che aggiungono al URI la risorsa di riferimento o parametri in query string
				// restiuisce una mappa chiave valore da sostituire nel URL
				uriMap = beanToMap(this.businessAssembler.doInputMappingUriMap(t5));

				// Impostato nel header visibilita e log
				request = this.setHttpHeader(t8);

				if (log.isInfoEnabled()) {
					
					Gson gSon = new Gson();
					
					String logString = "WPS Rest Service URL ";
					
					if (uriMap == null) {
						logString += this.serviceUrl + " ClassName=" + this.getClass().getCanonicalName() + " flowId=" + flowId;
					} 
					else {
						String serviceUrlTradotto = this.serviceUrl;
						for (String key: uriMap.keySet()) {
							if (uriMap.get(key) != null) {
								serviceUrlTradotto = serviceUrlTradotto.replace("{" + key + "}", uriMap.get(key));
							}
						}
						logString += serviceUrlTradotto + " ClassName=" + this.getClass().getCanonicalName() + " flowId=" + flowId;
					}
					logString += " Request=" + gSon.toJson(request); 
					log.info(logString);
				}

				timeStart = Calendar.getInstance().getTimeInMillis();
				
				log.info(this.serviceUrl, " ClassName=" + this.getClass().getCanonicalName(), "flowId=" + flowId,
						"Start WPS Rest");
								
		
				// Chiamata al servizio
				t9 = this.callOperation(request, uriMap);
				
				long timeEnd = Calendar.getInstance().getTimeInMillis();
				
				log.info(this.serviceUrl, "ClassName=" + this.getClass().getCanonicalName(), "flowId=" + flowId,
						"Stop WPS Rest (elapsed time = " + Long.toString(timeEnd - timeStart) + ")");

				if (log.isInfoEnabled()) {
					try {
						Gson gSon = new Gson();
						log.info("[" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "][RESPONSE] " + this.serviceUrl + " ClassName=" + this.getClass().getCanonicalName() + " | " + gSon.toJson(t9));
					} catch (Exception ex) {
						log.error("WPS Rest Service URL " + this.serviceUrl + " ClassName=" + this.getClass().getCanonicalName(), ex);
					}
					
				}

				// Check del output
				this.businessAssembler.validateOutput(t9);

				// Trasformazione da OutputServizio a MBean
				t6 = this.businessAssembler.doOutputMapping(t9);

				listWarningMBean = this.businessAssembler.doWarningMapping(t9, codiceSistema);

				// cache eventuale dell'output
				this.businessAssembler.cacheOutput(cacheKey, t6);
				
			}

			this.webAssembler.validateOutput(t6);

			// Trasformazione da MBean a BBean
			T4 t4 = this.webAssembler.doOutputMapping(t6);

			if (listWarningMBean != null
					&& !listWarningMBean.isEmpty()) {
				this.webAssembler.showWarnings(listWarningMBean, errorAreaId);
			}

			return t4;
			
		} catch (Throwable e) {
			
			long timeEnd = Calendar.getInstance().getTimeInMillis();
			
			log.error(this.serviceUrl, "ClassName=" + this.getClass().getCanonicalName(), "flowId=" + flowId,
					"Stop WPS Rest in Fail (elapsed time = " + Long.toString(timeEnd - timeStart) + ")");

			// Log request
			try {
				
				Gson gSon = new Gson();
				String req = gSon.toJson(request);
				if (req != null && req.length() > Constants.LIMITE_REQUEST_JSON_LOG) {
					req = req.substring(0,Constants.LIMITE_REQUEST_JSON_LOG);
				}
				String res = gSon.toJson(t9);
				if (res != null && res.length() > Constants.LIMITE_REQUEST_JSON_LOG) {
					res = res.substring(0,Constants.LIMITE_REQUEST_JSON_LOG);
				}
				String logString = "WPS Rest Service URL ";
				if (uriMap == null) {
					logString += this.serviceUrl + " ClassName=" + this.getClass().getCanonicalName();
				} else {
					logString += uriMap.entrySet().stream().reduce(this.serviceUrl, (str, elem) -> str.replace("{" + elem.getKey() + "}", elem.getValue()),(str, str2) -> str) +
					" ClassName=" + this.getClass().getCanonicalName();
				}
				logString += " Request=" + req + " Response=" + res;
				log.error(logString);
			} catch(Exception ex) {
				log.error("WPS Rest Service URL " + this.serviceUrl + " ClassName=" + this.getClass().getCanonicalName(), ex);
			}

			//da rimuovere quando si passa alla versione con tre parametri
			Throwable t = (Throwable) this.businessAssembler.doExceptionMapping(e);
			if(t == null) {
				t = (Throwable)this.businessAssembler.doExceptionMapping(e, codiceSistema, errorAreaId);
			}
			
			Throwable toBeLaunched = (Throwable) this.webAssembler.doExceptionMapping(t);
			
			if (toBeLaunched == null) {
				if(e instanceof BusinessException) {
					this.setServiceNameInBusinessException((BusinessException) e);
				}
				throw e;
			} else {
				if(toBeLaunched instanceof BusinessException) {
					this.setServiceNameInBusinessException((BusinessException) toBeLaunched);
				}
				throw toBeLaunched;
			}
		}
	}

	protected abstract T9 callOperation(HttpEntity<?>  requestEntity, Map<String, String> uriMap) throws Throwable;
	
	public IWebAssemblerGenerics<T3, T5, T6, T4, T10> getWebAssembler() {
		return this.webAssembler;
	}

	public void setWebAssembler(IWebAssemblerGenerics<T3, T5, T6, T4, T10> webAssembler) {
		this.webAssembler = webAssembler;
	}

	public IBusinessAssemblerGenerics<T5, T8, T9, T6, T10> getBusinessAssembler() {
		return this.businessAssembler;
	}

	public void setBusinessAssembler(IBusinessAssemblerGenerics<T5, T8, T9, T6, T10> businessAssembler) {
		this.businessAssembler = businessAssembler;
	}

	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Boolean isVisibilityDetails() {
		return this.visibilityDetails;
	}

	public String getServiceUrl() {
		return this.serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setVisibilityDetails(Boolean visibilityDetails) {
		this.visibilityDetails = visibilityDetails;
	}

	public void setIsVisibilityDetails(Boolean visibilityDetails) {
		this.visibilityDetails = visibilityDetails;
	}

	public String getxIbmClientId() {
		return xIbmClientId;
	}

	public void setxIbmClientId(String xIbmClientId) {
		this.xIbmClientId = xIbmClientId;
	}

	public Boolean getFuseVisibilita() {
		return fuseVisibilita;
	}

	public void setFuseVisibilita(Boolean fuseVisibilita) {
		this.fuseVisibilita = fuseVisibilita;
	}

	public Boolean getFuseDettaglioVisibilita() {
		return fuseDettaglioVisibilita;
	}

	public void setFuseDettaglioVisibilita(Boolean fuseDettaglioVisibilita) {
		this.fuseDettaglioVisibilita = fuseDettaglioVisibilita;
	}

	public Boolean getGdmAuth() {
		return gdmAuth;
	}

	public void setGdmAuth(Boolean gdmAuth) {
		this.gdmAuth = gdmAuth;
	}

	protected HttpEntity<?> setHttpHeader(T8 t8) {
		
		// Set header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));

		// Set external header
		buildExternalHeaders(requestHeaders);
		
		// The body object as first parameter
		return new HttpEntity<Object>(t8, requestHeaders);
		
	}

	protected void buildExternalHeaders(HttpHeaders requestHeaders) {
		
	}

	private void setServiceNameInBusinessException(BusinessException businessException) {
		if (businessException != null) {
			if (businessException.getFatal() != null) {
				for (SignalMessage signalMessage : businessException.getFatal()) {
					signalMessage.setServizio(this.serviceUrl);
				}
			}

			if (businessException.getError() != null) {
				for (SignalMessage signalMessage : businessException.getError()) {
					signalMessage.setServizio(this.serviceUrl);
				}
			}

			if (businessException.getWarning() != null) {
				for (SignalMessage signalMessage : businessException.getWarning()) {
					signalMessage.setServizio(this.serviceUrl);
				}
			}

			if (businessException.getInfo() != null) {
				for (SignalMessage signalMessage : businessException.getInfo()) {
					signalMessage.setServizio(this.serviceUrl);
				}
			}

		}
	}
	

	protected Map<String,String> beanToMap(Object ob) {
		
		Map<String,String> parameters  = null;
		
		if (ob != null) {
			TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
			ObjectMapper mapper = new ObjectMapper();
			parameters = mapper.convertValue(ob, typeRef);
		}
		
		return parameters;
	}
	
	public String getEndpointUriMap(Map<String,String> uriMap) {
		
		String endPoint=this.serviceUrl+"?";
		
		for (Map.Entry<String, String> param : uriMap.entrySet()) {
			if(param.getValue()!=null){
				if(!endPoint.contains("{"+param.getKey()+"}"))
					endPoint = endPoint.concat(param.getKey()+"={"+param.getKey()+"}"+"&");		
			}				
		}
		
		return endPoint.endsWith("&") ? StringUtils.substring(endPoint, 0, endPoint.length()-1) : endPoint;	 
	
	}

}