package com.onceas.wm.console.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author syk
 * 
 */
public class CellChart {
	// <td width=\"4%\" ><img src=\"images/legend_clear.gif\" /></td>

//	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "%4", allowNull = false)
//	private String width;

	@Parameter(required = true, allowNull = false)
	private double value;

	@Inject
	@Path("context:images/legend_blank.gif")
	private Asset blank;
	
	@Inject
	@Path("context:images/legend_clear.gif")
	private Asset clear;
	
	@Inject
	@Path("context:images/legend_critical.gif")
	private Asset critical;
	
	@Inject
	@Path("context:images/legend_warning.gif")
	private Asset warning;
	

	@BeginRender
	void doBeginRender(MarkupWriter writer) {
//		writer.element("td", "width", width);
		draw(writer);
//		writer.end();
	}


	private void draw(MarkupWriter writer) {
		if(value < 0){
			throw new RuntimeException("Value passed to CellChart must not be negative, current value is " + value);
		}
		
		if(value == 0){
			drawAllBlanks(writer);
		}else if(value < 70){
			internalDraw(writer,clear);
		}else if(value < 90){ 
			internalDraw(writer,warning);
		}else {
			internalDraw(writer, critical);
		}
		
	}

	private void drawAllBlanks(MarkupWriter writer) {
		for(int i = 0; i < NUM_GIF; i++){
			write(writer,blank);
		}
	}

	private static final int NUM_GIF = 20;
	private void internalDraw(MarkupWriter writer, Asset image) {
		
		if(value >= 100){
			drawHundredPercent(writer);
			return;
		}
		
		int mod = (int)(value / 5);
		int remain = (int)(value % 5);
		int numOfBlank = NUM_GIF -(mod + (remain > 0 ? 1 :0));
		
		for(int i = 0; i < mod; i++){
			write(writer, image);
		}
		
		if(remain > 0){
			write(writer, image);
		}
		
		//blank
		for(int k = 0; k < numOfBlank; k++){
			write(writer, blank);
		}
		
	}

	private void drawHundredPercent(MarkupWriter writer) {
		for(int i = 0; i < NUM_GIF; i++){
			write(writer, critical);
		}
	}


	private void write(MarkupWriter writer, Asset image) {
		writer.element("img", "src",image, "height" ,"12px");
		writer.end();
	}
}
