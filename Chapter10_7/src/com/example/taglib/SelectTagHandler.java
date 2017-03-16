package com.example.taglib;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/*
 * HTML select tag uses the JSP dynamic attributes mechanism to store all
 * of the pass-through HTML attributes in a hashmap
 */
public class SelectTagHandler extends SimpleTagSupport implements DynamicAttributes {
	// private Fields:
	private List optionsList = null;
	private String name;
	
	// Typically,the setter method will simply store each attribute name/value pair in a hashmap:
	private Map<String,Object> tagAttrs = new HashMap<String,Object>();
	
	// templates for String formating:
	private static final String ATTR_TEMPLATE = "%s='%s' ";
	private static final String OPTION_TEMPLATE = " <option value='%1s'> %1$s </option>";
		
	// store the 'optionsList' attribute:
	public void setOptionsList(List optionsList){
		this.optionsList = optionsList;
	}

	// store the 'name' attribute:
	public void setName(String name){
		this.name = name;
	}
	
	// store all other (dynamic) attributes:
	public void setDynamicAttribute(String uri, String name, Object value)  {
		tagAttrs.put(name, value);
	}
	
	// generate the <select> and <option> tags:
	public void doTag()throws JspException, IOException{
		// get PageContext:
		PageContext pageContext = (PageContext)getJspContext();
		// get JspWriter object out:
		JspWriter out = pageContext.getOut();
	
		// Start the HTML <select> tag:
		out.print("<select >");
		
		// add mandatory attributes:
		out.print(String.format(ATTR_TEMPLATE, "name", this.name));
		
		// add dynamic attributes:
		for(String attrName : tagAttrs.keySet()){ // retrieve the set of attributes from the map's keys. Each key
			// is the name of one of the dynamic attributes. The value of the attribute is stored in the map. The get()
			// method retrieves the value from the key ( the name of the attribute). 
			String attrDefinition = String.format(ATTR_TEMPLATE, attrName, tagAttrs.get(attrName));
			out.print(attrDefinition);
		}
		out.println(">");
		
		// Generate the <option> tags from the optionsList:
		for(Object option : optionsList){
			String optionTag = String.format(OPTION_TEMPLATE, option.toString());
			out.println(optionTag);
		}
		
		// End the HTML </select> tag:
		out.println(" </select>");
		
	} // END of doTag method
	
} // END of SelectTagHandler
