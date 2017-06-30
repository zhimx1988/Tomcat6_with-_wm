package com.onceas.wm.console.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.tdojo.components.Include;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Heartbeat;

public class IncludeWrapper extends Include {

	@Inject
	private ComponentResources resources;
	
	/**
	 * connect to another widget's id
	 */
	@Parameter(defaultPrefix = BindingConstants.COMPONENT)
	private ClientElement sliderValueUpdater;

	@Parameter(value = "$sliderValueUpdater", defaultPrefix = BindingConstants.LITERAL)
	private String placeholder;

	/**
	 * connect to another widget's id
	 */
	@Parameter(defaultPrefix = BindingConstants.COMPONENT)
	private ClientElement sliderValueInput;

	@Parameter(value = "$sliderValueInput", defaultPrefix = BindingConstants.LITERAL)
	private String sliderInputPlaceholder;
	
	@Environmental
	private Heartbeat heartbeat;
	
	private Map<String, String> actoinBinings;
	
	void beginRender(MarkupWriter writer) {
		if (!resources.isBound("sliderValueUpdater")) {
			return;
		}
		
//		ComponentResources holderResources =((Component)holder).getComponentResources();

		List<String> informalParameters = resources.getInformalParameterNames();
		actoinBinings = new HashMap<String, String>();
		for (String name : informalParameters) {
			try {
				String value = resources.getInformalParameter(name, String.class);
				if (value.indexOf(placeholder) >= 0 || value.indexOf(sliderInputPlaceholder) >= 0 ) {
					actoinBinings.put(name, value);
				}
			} catch (Exception e) {
				// swallow it
			}
		}

		if (actoinBinings.size() > 0) {

			Runnable command = new Runnable() {
				public void run() {
					String observedId = sliderValueUpdater.getClientId();
					String inputId = sliderValueInput.getClientId();

					if (actoinBinings != null) {
						Element element = getDojoElement();

						for (Map.Entry<String, String> entry : actoinBinings.entrySet()) {
							String value = (String) entry.getValue();
							value = value.replace(placeholder, observedId);
							value = value.replace(sliderInputPlaceholder, inputId);
							element.forceAttributes(entry.getKey(), value);
						}
					}
				}
			};

			heartbeat.defer(command);
		}
	}
}
