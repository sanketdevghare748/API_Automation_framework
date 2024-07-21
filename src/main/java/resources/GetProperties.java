package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {

	public static String getProperty(String property)
	{
		Properties prop = new Properties();
		try {

		    
        FileInputStream fis= new FileInputStream("E:\\Data\\eclipse\\Books_movies_APIs\\Config.properties");
		    // load a properties file
		    prop.load(fis);	

		} catch (IOException ex) {
		    ex.printStackTrace();
		
		    }
		   return prop.getProperty(property);
		}
	}

