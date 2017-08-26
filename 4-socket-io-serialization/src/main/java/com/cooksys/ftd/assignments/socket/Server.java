package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
    	
    	try {
    		//Read Student.xml
    		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    		return (Student) jaxbUnmarshaller.unmarshal(new File(studentFilePath));

    	  } catch (JAXBException e) {
    		e.printStackTrace();
    		System.out.println("Error reading from Student.xml");
    		return null;
    	  }
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     * @throws JAXBException 
     * @throws IOException 
     * @throws UnknownHostException 
     * @throws InterruptedException 
     */
    public static void main(String[] args){
    	
    	
    	try{
    	//Read config.xml
    	Config config = loadConfig("config/config.xml",createJAXBContext());
    	
    	//Create server
    	ServerSocket server = new ServerSocket(config.getLocal().getPort());
    	
    	//Read student.xml    	
    	Student student = loadStudent(config.getStudentFilePath(),createJAXBContext());
    	
    	//Socket listening the port
    	Socket serverSocket = server.accept();
    	 	
    	//Marshal student object to socket
  		JAXBContext jaxbContext = createJAXBContext();
  		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
  		jaxbMarshaller.marshal(student, serverSocket.getOutputStream());
  		
  		serverSocket.close();
  		server.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
       }
 
    }
