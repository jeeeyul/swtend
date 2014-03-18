package net.jeeeyul.swtend.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a lightweight resource. It is not has to be disposed.
 * 
 * @since 1.2
 */
public abstract class LightWeightResource {
	private Map<String, Object> dataMap;
	private Object data;

	public LightWeightResource() {
	}

	public Object getData() {
		return data;
	}

	private Map<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
		}
		return dataMap;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) getDataMap().get(key);
	}

	public void setData(String key, Object data) {
		getDataMap().put(key, data);
	}
	
	abstract LightWeightResource getCopy();
}
