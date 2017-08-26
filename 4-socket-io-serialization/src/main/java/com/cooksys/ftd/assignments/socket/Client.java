package com.cooksys.ftd.assignments.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     * @throws JAXBException 
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public static void main(String[] args){
    	
    	try{
       	//Read Config.xml    	
    	Config config = Utils.loadConfig("config/config.xml",Utils.createJAXBContext());
    	
    	//Create object of RemoteConfig class to get port and host from it
    	RemoteConfig rC = config.getRemote();
    	
    	//Create socket
    	Socket socket = new Socket(rC.getHost(), rC.getPort());
    	
    	//Unmarshal student object from socket	
    	Unmarshaller jaxbUnmarshaller = Utils.createJAXBContext().createUnmarshaller();
		Student studentRecieved = (Student) jaxbUnmarshaller.unmarshal(socket.getInputStream());
		
		//Print student object to console
		System.out.println(studentRecieved);

		socket.close();
		
    }catch(Exception e){
    	e.printStackTrace();
    }
    	
    }
}
