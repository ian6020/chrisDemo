package com.example.apps.filelog;

import java.util.List;

public interface Maskable {
	
	/**
	 * Return an immutable list of parameter names
	 * for masking
	 * 
	 * @return
	 */
	List<String> getParamListForMasking();
}
