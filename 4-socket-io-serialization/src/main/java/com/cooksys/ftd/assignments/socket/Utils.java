package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     * @throws JAXBException 
     */
    public static JAXBContext createJAXBContext() {
    	
    	try {
			return JAXBContext.newInstance(Config.class,LocalConfig.class,RemoteConfig.class,Student.class);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
    	    	
    	try {
    		//Unmarshal Config.xml
    		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    		return (Config) jaxbUnmarshaller.unmarshal(new File(configFilePath));

    	  } catch (JAXBException e) {
    		e.printStackTrace();
    		System.out.println("Error reading from Config.xml");
    		return null;
    	  }
    	
    }
}
