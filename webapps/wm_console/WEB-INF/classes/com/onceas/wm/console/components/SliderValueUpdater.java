package com.onceas.wm.console.components;

import net.sourceforge.tdojo.base.ClientScript;
import net.sourceforge.tdojo.components.TJSONRequester;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Heartbeat;

@IncludeJavaScriptLibrary("SliderValueUpdater.js")
public class SliderValueUpdater extends ClientScript{
	@Inject
	private RenderSupport renderSupport;
	
	@Environmental
	private Heartbeat heartbeat;
	
	@Parameter(required=true, defaultPrefix=BindingConstants.COMPONENT)
	private TJSONRequester tjsonrequester;
	
	@Parameter(required=true, defaultPrefix=BindingConstants.PROP)
	private String key;
	
	void beginRender(MarkupWriter writer) {
		
		Runnable command = new Runnable() {

			public void run() {
				StringBuilder builder = new StringBuilder();
				builder.append(getClientId()).append("= new SliderValueUpdater(");
				builder.append("'").append(key).append("',");
				builder.append(tjsonrequester.getClientId()).append(");");

				renderSupport.addScript(builder.toString());
			}
		};

		heartbeat.defer(command);
	}
}
