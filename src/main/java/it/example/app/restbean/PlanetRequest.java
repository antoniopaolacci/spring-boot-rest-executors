package it.example.app.restbean;

public class PlanetRequest {

	private String traceId;
	private String clientName;
	
	public PlanetRequest() { }
	
	public PlanetRequest(String traceId, String clientName) {
		this.traceId = traceId;
		this.clientName = clientName;
	}
	
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return "PlanetRequest [traceId=" + traceId + ", clientName=" + clientName + "]";
	}
	
}
