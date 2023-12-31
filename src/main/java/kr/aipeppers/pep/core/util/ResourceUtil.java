package kr.aipeppers.pep.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import kr.aipeppers.pep.core.exception.BaseException;

public class ResourceUtil {

	private static final String CLASSPATH_PREFIX = "classpath:";

	/**
	 * Gets the url.
	 *
	 * @param resourceLocation the resource location
	 * @return the url
	 * @throws FileNotFoundException the file not found exception
	 * @throws MalformedURLException the malformed url exception
	 */
	public static URL getURL(String resourceLocation) {
    	if(resourceLocation == null) {
    		new FileNotFoundException("Resource location must not be null");
    	}
    	try {
    		if(resourceLocation.startsWith(CLASSPATH_PREFIX)) {
                String path = resourceLocation.substring(CLASSPATH_PREFIX.length());
                URL url = ResourceUtil.class.getResource(path);
                if(url == null) {
                    String description = (new StringBuilder()).append("class path resource [").append(path).append("]").toString();
                    throw new FileNotFoundException((new StringBuilder()).append(description).append(" cannot be resolved to URL because it does not exist").toString());
                } else {
                    return url;
                }
        	}
        	try {
        		return new URL(resourceLocation);
        	} catch(MalformedURLException ex) {
        		try {
        			return (new File(resourceLocation)).toURI().toURL();
    			} catch (MalformedURLException e) {
    				throw new MalformedURLException((new StringBuilder()).append("Resource location [").append(resourceLocation).append("] is neither a URL not a well-formed file path").toString());
    			}
        	}
    	} catch (Exception e) {
    		throw new BaseException(e);
    	}
    }

	/**
	 * Gets the path.
	 *
	 * @param resourceLocation the resource location
	 * @return the path
	 * @throws FileNotFoundException the file not found exception
	 * @throws MalformedURLException the malformed url exception
	 */
	public static String getPath(String resourceLocation) {
		URL url = null;
		String path = null;
		try {
			url = ResourceUtil.getURL(resourceLocation);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		if(url != null) {
			path = url.getPath();
		}
		return path;
	}

	/**
	 * Gets the resource stream.
	 *
	 * @param resourceLocation the resource location
	 * @return the resource stream
	 * @throws FileNotFoundException the file not found exception
	 */
	public static InputStream getResourceStream(String resourceLocation) throws FileNotFoundException {
		InputStream inputStream = null;
    	if(resourceLocation == null) {
    		new FileNotFoundException("Resource location must not be null");
    	}
    	if(resourceLocation.startsWith(CLASSPATH_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_PREFIX.length());
            inputStream = ResourceUtil.class.getResourceAsStream(path);
    	}
    	return inputStream;
    }

	/**
	 * Gets the resource.
	 *
	 * @param resourceLocation the resource location
	 * @return the resource
	 */
	public static Resource getResource(String resourceLocation) {
		if(resourceLocation.startsWith(CLASSPATH_PREFIX)) {
			resourceLocation = resourceLocation.substring(CLASSPATH_PREFIX.length());
	    	return new ClassPathResource(resourceLocation);
	    } else {
	    	return new FileSystemResource(resourceLocation);
	    }
	}
	
	
	/**
	 * Gets the config path.
	 *
	 * @param resourcePath the resource path
	 * @return the config path
	 */
	public static String getConfigPath(String resourcePath) {
		String path = null;
		try {
			if(StringUtil.isEmpty(resourcePath)) {
				resourcePath = "";
			}
			Resource resource = ResourceUtil.getConfigResource(resourcePath);
			URL url = resource.getURL();
			if(url != null) {
				path = url.getPath();
			}
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return path;
	}
	
	/**
	 * Gets the config path.
	 *
	 * @return the config path
	 */
	public static String getConfigPath() {
		return ResourceUtil.getConfigPath(null);
	}

	/**
	 * Gets the lupin config resource.
	 *
	 * @param resourcePath the resource path
	 * @return the lupin config resource
	 */
	public static Resource getConfigResource(String resourcePath) {
		String configPath = System.getProperty("config.path");
		String resourceLocation = null;
		if(StringUtil.isEmpty(configPath)) {
			resourceLocation = CLASSPATH_PREFIX + "/config";
		} else {
			resourceLocation = configPath;
		}
		resourceLocation += resourcePath;
		return ResourceUtil.getResource(resourceLocation);
	}
	

}
