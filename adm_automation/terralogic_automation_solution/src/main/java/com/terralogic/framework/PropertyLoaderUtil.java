package com.terralogic.framework;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyLoaderUtil{
	Map<String, String> propertyMap = new HashMap<String, String>();
			
	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public void loadProperties(String propertyFilePath) {
		 
		Properties prop = new Properties();
		InputStream input = null;
	
		//System.out.println(this.getPropertyMap());
		try {
			input = new FileInputStream(propertyFilePath);
	 		prop.load(input);
	 		Map<String, String> localMap = new HashMap<String, String>();
	 		Enumeration enuKeys = prop.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = prop.getProperty(key);
				//System.out.println(key + ": " + value);
				localMap.put(key, value);
				}
			this.setPropertyMap(localMap);
			//System.out.println(this.getPropertyMap());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

/*	public static void main(String[] args) {
		PropertyLoaderUsingMap obj = new PropertyLoaderUsingMap();
		System.out.println("Loading property and storing it in map");
		obj.loadProperties("");
		
	}*/
}


