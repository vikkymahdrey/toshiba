package com.team.app.config;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.app.dao.FrameDao;
import com.team.app.domain.LoraFrame;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;

@Service
public class MqttBroker implements MqttCallback,MqttIntrf {
	
	private static final AtLogger logger = AtLogger.getLogger(MqttBroker.class);
	
		
	@Autowired
	private FrameDao frameDao;	
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
	
	MqttClient client;
	
	MqttMessage message;
	
	
	
	public void doDemo() {
	    /*try {
	    	logger.debug("/ INside MQTT Broker 	4786e6ed00490044 ");	
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490044/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490044/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	    
	    
	  try {
	    	logger.debug("/ INside MQTT Broker 4786e6ed00490190 ");
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490190/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490190/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	  try {
	    	logger.debug("/ INside MQTT Broker 4786e6ed00490191 ");
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490191/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490191/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	  try {
	    	logger.debug("/ INside MQTT Broker 4786e6ed00490192 ");
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490192/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490192/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	  
	  try {
	    	logger.debug("/ INside MQTT Broker 4786e6ed00490500 ");
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490500/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490500/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }*/
		
		 try {
		    	logger.debug("/ INside MQTT Broker 	4786e6ed00490050 ");	
		    	MqttConnectOptions connOpts = new MqttConnectOptions();
		        connOpts.setUserName("loragw");
		        connOpts.setPassword("loragw".toCharArray());
		        connOpts.setCleanSession(true);
		        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
		        
		        client.connect(connOpts);
		        client.setCallback(this);
		        client.subscribe("application/2/node/4786e6ed00490050/rx");
		        MqttMessage message = new MqttMessage();
		        message.setPayload("sending......."
		                .getBytes());
		        client.publish("application/2/node/4786e6ed00490050/tx", message);
		        System.out.println("Message printing here "+message);
		        //System.exit(0);
		    }catch (MqttException e) {
		        e.printStackTrace();
		    }
		  
		  try {
		    	logger.debug("/ INside MQTT Broker 	4786e6ed00490051 ");	
		    	MqttConnectOptions connOpts = new MqttConnectOptions();
		        connOpts.setUserName("loragw");
		        connOpts.setPassword("loragw".toCharArray());
		        connOpts.setCleanSession(true);
		        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
		        
		        client.connect(connOpts);
		        client.setCallback(this);
		        client.subscribe("application/2/node/4786e6ed00490051/rx");
		        MqttMessage message = new MqttMessage();
		        message.setPayload("sending......."
		                .getBytes());
		        client.publish("application/2/node/4786e6ed00490051/tx", message);
		        System.out.println("Message printing here "+message);
		        //System.exit(0);
		    }catch (MqttException e){
		        e.printStackTrace();
		    }
	  
	 /* try {
	    	logger.debug("/ INside MQTT Broker 4786e6ed00490077 ");
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/6/node/4786e6ed00490077/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/6/node/4786e6ed00490077/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
*/	         
	 /* try {
	    	logger.debug("/ INside MQTT Broker 	4786e6ed00490050 ");	
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/2/node/4786e6ed00490050/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/2/node/4786e6ed00490050/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	  
	  try {
	    	logger.debug("/ INside MQTT Broker 	4786e6ed00490051 ");	
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/2/node/4786e6ed00490051/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/2/node/4786e6ed00490051/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    }catch (MqttException e){
	        e.printStackTrace();
	    }*/
	}

	
	public void connectionLost(Throwable cause) {
	   

	}
	
	//04th Jan,2018
	/*@Transactional
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.debug("Inside messageArrived");
		try{
			LoraFrame frm=null;
			if(!message.toString().isEmpty()){
				Charset UTF8 = Charset.forName("UTF-8");
				  JSONObject json=null;
				  		json=new JSONObject();
				  		json=(JSONObject)new JSONParser().parse(message.toString());
				  		logger.debug("REsultant json",json);  			  		 
				  		 
				  	  	
				  		logger.debug("Data data",json.get("data").toString());
				  	  	
				  	  	if(json.get("data")!=null){	
				  	  		 
				     		 byte[] decoded=Base64.decodeBase64(json.get("data").toString());
				     		 			     		 					     		 	
				     		 	if(decoded!=null && decoded.length>0){
				     		 		frm=new LoraFrame();
							  		
				     		 		frm.setApplicationID(json.get("applicationID").toString());
				     		 		frm.setApplicationName(json.get("applicationName").toString());
				     		 		frm.setNodeName(json.get("nodeName").toString());
				     		 		frm.setDevEUI(json.get("devEUI").toString());
							  		logger.debug("applicationID",json.get("applicationID").toString());
										logger.debug("applicationName",json.get("applicationName").toString());
											logger.debug("nodeName",json.get("nodeName").toString());
												logger.debug("devEUI",json.get("devEUI").toString());											
												

										  		 JSONArray arr=(JSONArray) json.get("rxInfo");
										  		 
										  		 if(arr!=null && arr.size()>0){
							   						 for (int i = 0; i < arr.size(); i++) {
							   							 JSONObject jsonObj = (JSONObject) arr.get(i);
							   							frm.setGatewayMac(jsonObj.get("mac").toString());
							   							frm.setGatewayName(jsonObj.get("name").toString());
							   							 
								   							logger.debug("mac",jsonObj.get("mac").toString());
								   								logger.debug("name",jsonObj.get("name").toString());
							   						 }
										  		 }
										  		 
										  		
										  		logger.debug("fport",json.get("fPort").toString());  	
										  	  		
										  		frm.setfPort(json.get("fPort").toString().trim());
										  		//frm.setCreatedAt(new Date(System.currentTimeMillis()));
										  		//frm.setUpdatedAt(new Date(System.currentTimeMillis()));
				     		 		
								   LoraFrame frame=null;
								   		frame=new LoraFrame();
				     		 		int i=0;
				     		 		int n=0;
				     		 		String firstByte="";
				     		 		int flag=0;
				     		 		String merge="";
				     		 		String central="";
				     		 	   for(Byte b : decoded){
				     		 		   			logger.debug("frame:::: ",frame);
				     		 		   			if(n>0){
				     		 		   				frame=new LoraFrame();
				     		 		   			  firstByte="";
				     		 		   				n=0;
				     		 		   				flag=0;
				     		 		   							     		 		   				
				     		 		   			}
				     		 		   if(i==0 || i==7 || i==14 || i==21 || i==28 || i==35 || i==42 || i==49){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
					     		 			 	firstByte=String.format("%02X ", b);	
					     		 			 	logger.debug("firstbyte firstbyte",firstByte);
					     		 			} 	
				     		 			i++; 
				     		 			 
				     		 		   }else if(i==1 || i==8 || i==15 || i==22 || i==29 || i==36 || i==43 || i==50){
				     		 			   
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
				     		 				
					     		 			logger.debug("Binary data: ",decodeBinary);
					     		 			 if(decodeBinary.equals("0")){
					     		 				 if(firstByte.equals("00")){
					     		 					logger.debug("firstbyte firstbyte if ",firstByte);
					     		 					frame.setDeviceId("000"); 
					     		 					
					     		 				 }else{
					     		 					logger.debug("firstbyte firstbyte else ",firstByte);
					     		 					 String l=firstByte.trim()+"0";
					     		 					frame.setDeviceId(l);
					     		 						     		 										     		 					 
					     		 				 }
					     		 				 frame.setLed1("0");
				     		 					 frame.setLed2("0");
					     		 				
					     		 				
					     		 			 }else{
					     		 				logger.debug("2nd byte not 0: ",decodeBinary);
					     		 				  					     		 				   
					     		 				   String s=String.format("%02X ", b);
					     		 				   logger.debug("s valuee: ",s);
					     		 									     		 						
					     		 						String dId=s.substring(0, 1);
					     		 						frame.setDeviceId(firstByte.trim()+dId);
					     		 						
					     		 						String led12=s.substring(1);
					     		 						logger.debug("s valuee led12: ",led12);					     		 						
					     		 						
					     		 						int x=Character.getNumericValue(led12.charAt(0));
					     		 						logger.debug("x valuee led12: ",x);
					     		 						
					     		 						String binaryled12=Integer.toBinaryString(x);
					     		 						 logger.debug("binary led12: ",binaryled12);
					     		 						
					     		 						if(binaryled12.length()<4){
					     		 							//Naming convension logic implementation
					     		 							flag=1;
						     		 					
						     		 					}else{
						     		 						logger.debug("length ==2: else",s);						     		 						
						     		 						String led2 =binaryled12.substring(binaryled12.length()-1);
						     		 						String led1=binaryled12.substring(binaryled12.length()-2,binaryled12.length()-1);
						     		 						frame.setLed1(led1);
						     		 						frame.setLed2(led2);
						     		 					}
					     		 					
					     		 					//}
					     		 				   	
					     		 			 }						     		 				    
					     		 				
					     		 			
				     		 			 }	
				     		 			i++;
				     		 		   }else if(i==2 || i==9 || i==16 || i==23 || i==30 || i==37 || i==44 || i==51){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
				     		 				 if(flag==1){
				     		 					logger.debug("flag==1 i==2: ",merge);
				     		 					 merge=String.valueOf(Character.toChars(b));
				     		 				 }else{
						     		 			 int led3=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("LED3 AS i==2 : ",led3);
						     		 			 
						     		 			 frame.setLed3(String.valueOf(led3));
				     		 				 }
				     		 			 }	 
				     		 			 i++;
				     		 			
				     		 		   }else if(i==3 || i==10 || i==17 || i==24 || i==31 || i==38 || i==45 || i==52){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
				     		 				if(flag==1){
				     		 					logger.debug("flag==1 i==3: ",merge);
				     		 					 merge=merge+String.valueOf(Character.toChars(b));
				     		 				 }else{
						     		 			 int led4=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("LED4 AS i==3 : ",led4);
						     		 			 
						     		 			 frame.setLed4(String.valueOf(led4));
				     		 				 }
				     		 			 }	 
				     		 			 i++;
				     		 		   }else if(i==4 || i==11 || i==18 || i==25 || i==32 || i==39 || i==46 || i==53){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==4: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 				 }else{
							     		 			 int temp=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("Temperature AS i==4 : ",temp);
							     		 			 
							     		 			 frame.setTemperature(String.valueOf(temp));
					     		 				 }
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==5 || i==12 || i==19 || i==26 || i==33 || i==40 || i==47 || i==54){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==5: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 				 }else{
							     		 			 int pressure=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("pressure AS i==5 : ",pressure);
							     		 			 
							     		 			 frame.setPressure(String.valueOf(pressure));
					     		 				 }
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==6 || i==13 || i==20 || i==27 || i==34 || i==41 || i==48 || i==55){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==6: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 					List<LoraFrame> frmList=consumerInstrumentServiceImpl.getFramesByNodeNameAndID(frame.getDeviceId(),frm.getNodeName());
					     		 					if(frmList!=null && !frmList.isEmpty()){
					     		 						
					     		 						logger.debug("flag==1 i==6: size ",frmList.size());
					     		 						logger.debug("DeviceId id ",frame.getDeviceId().trim());
					     		 						logger.debug("nodeName ",frm.getNodeName().trim());
					     		 						logger.debug("merge ",merge.trim());
					     		 						if(i==6 && !merge.equalsIgnoreCase("")){					     		 							
					     		 							//merge="C_"+merge;
					     		 							central=merge;
					     		 							logger.debug("merge IF ",merge+"index "+i);
					     		 							
					     		 						}					     		 						
					     		 						consumerInstrumentServiceImpl.setUpdateLoraFrames(frame.getDeviceId(),frm.getNodeName(),central.trim(),merge.trim());
					     		 						
					     		 						
					     		 					}
					     		 				}else{
							     		 			 int humidity=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("Humidity AS i==6 : ",humidity);
							     		 			 
							     		 			 frame.setHumidity(String.valueOf(humidity));
							     		 			 frame.setApplicationID(frm.getApplicationID());
							     		 			 frame.setApplicationName(frm.getApplicationName());
							     		 			 frame.setNodeName(frm.getNodeName());
							     		 			 frame.setDevEUI(frm.getDevEUI());
							     		 			 frame.setGatewayMac(frm.getGatewayMac());
							     		 			 frame.setGatewayName(frm.getGatewayName());
							     		 			 frame.setfPort(frm.getfPort());
							     		 			 frame.setCreatedAt(new Date(System.currentTimeMillis()));
							     		 			 frame.setUpdatedAt(new Date(System.currentTimeMillis()));
							     		 			 LoraFrame lfrm=frameDao.save(frame);
							     		 			 if(lfrm!=null){
							     		 				logger.debug("Frame frame updated IF : ",lfrm.getId()); 
							     		 			 }else{
							     		 				logger.debug("Frame frame updated ELSE : ");  
							     		 			 }
							     		 			 
							     		 			
					     		 				 }
					     		 				n++;
					     		 			 }	 
					     		 			 i++;        
				     		 			 
					     		 	   }
				     		 		   
				     		 		   
				     		 		 
				     		 		   
				  	  				 	
				     		 	}// for loop end here		  	
				     		 		
				     	}//if end here
				     		 	
				     		 	
					
				  	  	}	
				  	  	
				  		//frameDao.save(frame);
				  		
				  		
				  		
				  		
		}
		
		}catch(Exception e){
			logger.error("Error",e);
			e.printStackTrace();
		}
	}*/
	
	
	@Transactional
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.debug("Inside messageArrived");
		try{
			LoraFrame frm=null;
			if(!message.toString().isEmpty()){
				Charset UTF8 = Charset.forName("UTF-8");
				  JSONObject json=null;
				  		json=new JSONObject();
				  		json=(JSONObject)new JSONParser().parse(message.toString());
				  		logger.debug("REsultant json",json);  			  		 
				  		 
				  	  	
				  		logger.debug("Data data",json.get("data").toString());
				  	  	
				  	  	if(json.get("data")!=null){	
				  	  		 
				     		 byte[] decoded=Base64.decodeBase64(json.get("data").toString());
				     		 			     		 					     		 	
				     		 	if(decoded!=null && decoded.length>0){
				     		 		frm=new LoraFrame();
							  		
				     		 		frm.setApplicationID(json.get("applicationID").toString());
				     		 		frm.setApplicationName(json.get("applicationName").toString());
				     		 		frm.setNodeName(json.get("nodeName").toString());
				     		 		frm.setDevEUI(json.get("devEUI").toString());
							  		logger.debug("applicationID",json.get("applicationID").toString());
										logger.debug("applicationName",json.get("applicationName").toString());
											logger.debug("nodeName",json.get("nodeName").toString());
												logger.debug("devEUI",json.get("devEUI").toString());											
												

										  		 JSONArray arr=(JSONArray) json.get("rxInfo");
										  		 
										  		 if(arr!=null && arr.size()>0){
							   						 for (int i = 0; i < arr.size(); i++) {
							   							 JSONObject jsonObj = (JSONObject) arr.get(i);
							   							frm.setGatewayMac(jsonObj.get("mac").toString());
							   							frm.setGatewayName(jsonObj.get("name").toString());
							   							 
								   							logger.debug("mac",jsonObj.get("mac").toString());
								   								logger.debug("name",jsonObj.get("name").toString());
							   						 }
										  		 }
										  		 
										  		
										  		logger.debug("fport",json.get("fPort").toString());  	
										  	  		
										  		frm.setfPort(json.get("fPort").toString().trim());
										  		//frm.setCreatedAt(new Date(System.currentTimeMillis()));
										  		//frm.setUpdatedAt(new Date(System.currentTimeMillis()));
				     		 		
								   LoraFrame frame=null;
								   		frame=new LoraFrame();
				     		 		int i=0;
				     		 		int n=0;
				     		 		String firstByte="";
				     		 		int flag=0;
				     		 		String merge="";
				     		 		String central="";
				     		 	   for(Byte b : decoded){
				     		 		   			logger.debug("frame:::: ",frame);
				     		 		   			if(n>0){
				     		 		   				frame=new LoraFrame();
				     		 		   			  firstByte="";
				     		 		   				n=0;
				     		 		   				flag=0;
				     		 		   							     		 		   				
				     		 		   			}
				     		 		   if(i==0 || i==6 || i==12 || i==18 || i==24 || i==30 || i==36 || i==42){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
					     		 			 	firstByte=String.format("%02X ", b);	
					     		 			 	logger.debug("firstbyte firstbyte",firstByte);
					     		 			} 	
				     		 			i++; 
				     		 			 
				     		 		   }else if(i==1 || i==7 || i==13 || i==19 || i==25 || i==31 || i==37 || i==43){
				     		 			   
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
				     		 				
					     		 			logger.debug("Binary data: ",decodeBinary);
					     		 			 if(decodeBinary.equals("0")){
					     		 				 if(firstByte.equals("00")){
					     		 					logger.debug("firstbyte firstbyte if ",firstByte);
					     		 					frame.setDeviceId("000"); 
					     		 					
					     		 				 }else{
					     		 					logger.debug("firstbyte firstbyte else ",firstByte);
					     		 					 String l=firstByte.trim()+"0";
					     		 					frame.setDeviceId(l);
					     		 						     		 										     		 					 
					     		 				 }
					     		 				 frame.setLed1("0");
				     		 					 frame.setLed2("0");
					     		 				
					     		 				
					     		 			 }else{
					     		 				logger.debug("2nd byte not 0: ",decodeBinary);
					     		 				  					     		 				   
					     		 				   String s=String.format("%02X ", b);
					     		 				   logger.debug("s valuee: ",s);
					     		 									     		 						
					     		 						String dId=s.substring(0, 1);
					     		 						frame.setDeviceId(firstByte.trim()+dId);
					     		 						
					     		 						String led12=s.substring(1);
					     		 						logger.debug("s valuee led12: ",led12);					     		 						
					     		 						
					     		 						int x=Character.getNumericValue(led12.charAt(0));
					     		 						logger.debug("x valuee led12: ",x);
					     		 						
					     		 						String binaryled12=Integer.toBinaryString(x);
					     		 						 logger.debug("binary led12: ",binaryled12);
					     		 						
					     		 						if(binaryled12.length()<4){
					     		 							//Naming convension logic implementation
					     		 							flag=1;
						     		 					
						     		 					}else{
						     		 						logger.debug("length ==2: else",s);						     		 						
						     		 						String led2 =binaryled12.substring(binaryled12.length()-1);
						     		 						String led1=binaryled12.substring(binaryled12.length()-2,binaryled12.length()-1);
						     		 						frame.setLed1(led1);
						     		 						frame.setLed2(led2);
						     		 					}
					     		 					
					     		 					//}
					     		 				   	
					     		 			 }						     		 				    
					     		 				
					     		 			
				     		 			 }	
				     		 			i++;
				     		 		   }else if(i==2 || i==8 || i==14 || i==20 || i==26 || i==32 || i==38 || i==44){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 logger.debug("LED34 Binary Value",decodeBinary);
				     		 			 if(!firstByte.equals("")){
				     		 				 if(flag==1){
				     		 					logger.debug("flag==1 i==2: ",merge);
				     		 					 merge=String.valueOf(Character.toChars(b));
				     		 				 }else{
						     		 			 //int led34=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("LED34 Binary AS i==2 : ",decodeBinary);
						     		 			 
						     		 			 if(decodeBinary.equals("0")){
						     		 				logger.debug("LED34 Binary AS 0 IF condition");
						     		 				frame.setLed3("0");
						     		 				frame.setLed4("0");
						     		 			 }else{
						     		 				logger.debug("LED34 Binary AS !0 ELSE condition");
						     		 				 if(decodeBinary.length()<5){
						     		 					logger.debug("LED34 Binary AS !0 ELSE condition length<4");
						     		 					frame.setLed3("0");
						     		 						int led4=Integer.parseInt(decodeBinary,2);
						     		 					frame.setLed4(String.valueOf(led4));
						     		 				 }else{
						     		 					 
						     		 					logger.debug("LED34 Binary AS !0 ELSE condition length>4"); 
						     		 					if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
							     		 					String led3=decodeBinary.substring(0, decodeBinary.length()-4);
							     		 					String led4=decodeBinary.substring(decodeBinary.length()-4);
							     		 					
							     		 					frame.setLed3(String.valueOf(Integer.parseInt(led3,2)));
								     		 				frame.setLed4(String.valueOf(Integer.parseInt(led4,2)));
						     		 					}else{
						     		 						frame.setLed3("15");
								     		 				frame.setLed4("15");
						     		 					}
						     		 				 }
						     		 			 }
						     		 			 
						     		 			 
				     		 				 }
				     		 			 }	 
				     		 			 i++;
				     		 							     		 		   
				     		 		   }else if(i==3 || i==9 || i==15 || i==21 || i==27 || i==33 || i==39 || i==45){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==3: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 				 }else{
							     		 			 int temp=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("Temperature AS i==3 : ",temp);
							     		 			 
							     		 			 frame.setTemperature(String.valueOf(temp));
					     		 				 }
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==4 || i==10 || i==16 || i==22 || i==28 || i==34 || i==40 || i==46){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==4: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 				 }else{
							     		 			 int pressure=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("pressure AS i==4 : ",pressure);
							     		 			 
							     		 			 frame.setPressure(String.valueOf(pressure));
					     		 				 }
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==5 || i==11 || i==17 || i==23 || i==29 || i==35 || i==41 || i==47){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111") && !firstByte.equals("")){
					     		 				if(flag==1){
					     		 					logger.debug("flag==1 i==5: ",merge);
					     		 					 merge=merge+String.valueOf(Character.toChars(b));
					     		 					List<LoraFrame> frmList=consumerInstrumentServiceImpl.getFramesByNodeNameAndID(frame.getDeviceId(),frm.getNodeName());
					     		 					if(frmList!=null && !frmList.isEmpty()){
					     		 						
					     		 						logger.debug("flag==1 i==5: size ",frmList.size());
					     		 						logger.debug("DeviceId id ",frame.getDeviceId().trim());
					     		 						logger.debug("nodeName ",frm.getNodeName().trim());
					     		 						logger.debug("merge ",merge.trim());
					     		 						if(i==5 && !merge.equalsIgnoreCase("")){					     		 							
					     		 							//merge="C_"+merge;
					     		 							central=merge;
					     		 							logger.debug("merge IF ",merge+"index "+i);
					     		 							
					     		 						}					     		 						
					     		 						consumerInstrumentServiceImpl.setUpdateLoraFrames(frame.getDeviceId(),frm.getNodeName(),central.trim(),merge.trim());
					     		 						
					     		 						
					     		 					}else{
					     		 						
					     		 					}
					     		 				}else{
					     		 					 LoraFrame namingList=consumerInstrumentServiceImpl.getNamingPacket(frame.getDeviceId(),frm.getNodeName());
						     		 					 if(namingList!=null){
						     		 						 logger.debug("Naming Packet Named");
						     		 						 logger.debug("Central Naming Packet Named",namingList.getCentral());
						     		 						 logger.debug("Pheripheral Naming Packet Named",namingList.getPeripheral());
						     		 						frame.setCentral(namingList.getCentral()); 
						     		 						frame.setPeripheral(namingList.getPeripheral());
						     		 					 }
					     		 					 
							     		 			 int humidity=Integer.parseInt(decodeBinary,2);
							     		 			 logger.debug("Humidity AS i==5 : ",humidity);
							     		 			 
							     		 			 frame.setHumidity(String.valueOf(humidity));
							     		 			 frame.setApplicationID(frm.getApplicationID());
							     		 			 frame.setApplicationName(frm.getApplicationName());
							     		 			 frame.setNodeName(frm.getNodeName());
							     		 			 frame.setDevEUI(frm.getDevEUI());
							     		 			 frame.setGatewayMac(frm.getGatewayMac());
							     		 			 frame.setGatewayName(frm.getGatewayName());
							     		 			 frame.setfPort(frm.getfPort());
							     		 			 frame.setCreatedAt(new Date(System.currentTimeMillis()));
							     		 			 frame.setUpdatedAt(new Date(System.currentTimeMillis()));
							     		 			 LoraFrame lfrm=frameDao.save(frame);
							     		 			 if(lfrm!=null){
							     		 				logger.debug("Frame frame updated IF : ",lfrm.getId()); 
							     		 			 }else{
							     		 				logger.debug("Frame frame updated ELSE : ");  
							     		 			 }
							     		 			 
							     		 			
					     		 				 }
					     		 				n++;
					     		 			 }	 
					     		 			 i++;        
				     		 			 
					     		 	   }
				     		 		   
				     		 		   
				     		 		 
				     		 		   
				  	  				 	
				     		 	}// for loop end here		  	
				     		 		
				     	}//if end here
				     		 	
				     		 	
					
				  	  	}	
				  	  	
				  		//frameDao.save(frame);
				  		
				  		
				  		
				  		
		}
		
		}catch(Exception e){
			logger.error("Error",e);
			e.printStackTrace();
		}
	}
	
	/*08th Dec,2017*/
	
	/*@Transactional
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.debug("Inside messageArrived");
		try{
			LoraFrame frm=null;
			if(!message.toString().isEmpty()){
				 
				  JSONObject json=null;
				  		json=new JSONObject();
				  		json=(JSONObject)new JSONParser().parse(message.toString());
				  		logger.debug("REsultant json",json);  		
				  		 
				  		 
				  	  	
				  		logger.debug("Data",json.get("data").toString());
				  	  	
				  	  	if(json.get("data")!=null){	
				  	  		 
				     		 byte[] decoded=Base64.decodeBase64(json.get("data").toString());
				     		 			     		 					     		 	
				     		 	if(decoded!=null && decoded.length>0){
				     		 		frm=new LoraFrame();
							  		
				     		 		frm.setApplicationID(json.get("applicationID").toString());
				     		 		frm.setApplicationName(json.get("applicationName").toString());
				     		 		frm.setNodeName(json.get("nodeName").toString());
				     		 		frm.setDevEUI(json.get("devEUI").toString());
							  		logger.debug("applicationID",json.get("applicationID").toString());
										logger.debug("applicationName",json.get("applicationName").toString());
											logger.debug("nodeName",json.get("nodeName").toString());
												logger.debug("devEUI",json.get("devEUI").toString());
												
												

										  		 JSONArray arr=(JSONArray) json.get("rxInfo");
										  		 
										  		 if(arr!=null && arr.size()>0){
							   						 for (int i = 0; i < arr.size(); i++) {
							   							 JSONObject jsonObj = (JSONObject) arr.get(i);
							   							frm.setGatewayMac(jsonObj.get("mac").toString());
							   							frm.setGatewayName(jsonObj.get("name").toString());
							   							 
								   							logger.debug("mac",jsonObj.get("mac").toString());
								   								logger.debug("name",jsonObj.get("name").toString());
							   						 }
										  		 }
										  		 
										  		
										  		logger.debug("fport",json.get("fPort").toString());  	
										  	  		
										  		frm.setfPort(json.get("fPort").toString().trim());
										  		//frm.setCreatedAt(new Date(System.currentTimeMillis()));
										  		//frm.setUpdatedAt(new Date(System.currentTimeMillis()));
				     		 		
								   LoraFrame frame=null;
								   		frame=new LoraFrame();
				     		 		int i=0;
				     		 		int n=0;
				     		 		
				     		 	   for(Byte b : decoded){
				     		 		   			logger.debug("frame:::: ",frame);
				     		 		   			if(n>0){
				     		 		   				frame=new LoraFrame();
				     		 		   				n=0;
				     		 		   			}
				     		 		   if(i==0 || i==7 || i==14 || i==21 || i==28 || i==35 || i==42 || i==49){
				     		 			 i++; 
				     		 			 
				     		 		   }else if(i==1 || i==8 || i==15 || i==22 || i==29 || i==36 || i==43 || i==50){
				     		 			   
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
					     		 			logger.debug("Binary data: ",decodeBinary);
					     		 			 if(decodeBinary.equals("0")){
					     		 				frame.setDeviceId("000");
					     		 				frame.setLed1("0");
					     		 				frame.setLed2("0");
					     		 				logger.debug("DeviceId: ",frame.getDeviceId());
					     		 			 }else{
					     		 				 	String led2="";
					     		 					String led1="";
					     		 				if(decodeBinary.length()<5){
					     		 					frame.setDeviceId("000");
						     		 					if(decodeBinary.length()<3){
						     		 						led2 ="0";
						     		 						led1="1";
						     		 					}else{
						     		 						led2 =decodeBinary.substring(0,decodeBinary.length()-2);
						     		 						led1=decodeBinary.substring(decodeBinary.length()-2,decodeBinary.length());
						     		 						int l1=Integer.parseInt(led1,2);
							     		 					int l2=Integer.parseInt(led2,2);
							     		 					led1=String.valueOf(l1);
							     		 					led2=String.valueOf(l2);
						     		 					}
						     		 					
						     		 					
					     		 				}else{
					     		 					String dId=decodeBinary.substring(0,decodeBinary.length()-4);
					     		 						int deviceId=Integer.parseInt(dId,2);
					     		 							frame.setDeviceId("00"+String.valueOf(deviceId));
					     		 					led1=decodeBinary.substring(decodeBinary.length()-4,decodeBinary.length()-2);
					     		 					led2= decodeBinary.substring(decodeBinary.length()-2,decodeBinary.length());
					     		 					
					     		 					int l1=Integer.parseInt(led1,2);
					     		 					int l2=Integer.parseInt(led2,2);
					     		 					led1=String.valueOf(l1);
					     		 					led2=String.valueOf(l2);
					     		 				}
					     		 				logger.debug("DeviceID AS i==1 : ",frame.getDeviceId());
					     		 				logger.debug("LED1 AS i==1 : ",led1);
					     		 				logger.debug("LED2 AS i==1 : ",led1);
					     		 				frame.setLed1(led1);
					     		 				frame.setLed2(led2);
					     		 				
					     		 			 }
					     		 			
				     		 			 }	
				     		 			i++;
				     		 		   }else if(i==2 || i==9 || i==16 || i==23 || i==30 || i==37 || i==44 || i==51){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
					     		 			 int led3=Integer.parseInt(decodeBinary,2);
					     		 			 logger.debug("LED3 AS i==2 : ",led3);
					     		 			 
					     		 			 frame.setLed3(String.valueOf(led3));
				     		 			 }	 
				     		 			 i++;
				     		 			
				     		 		   }else if(i==3 || i==10 || i==17 || i==24 || i==31 || i==38 || i==45 || i==52){
				     		 			 String decodeBinary = Integer.toBinaryString(b);
				     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
					     		 			 int led4=Integer.parseInt(decodeBinary,2);
					     		 			 logger.debug("LED4 AS i==3 : ",led4);
					     		 			 
					     		 			 frame.setLed4(String.valueOf(led4));
				     		 			 }	 
				     		 			 i++;
				     		 		   }else if(i==4 || i==11 || i==18 || i==25 || i==32 || i==39 || i==46 || i==53){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
						     		 			 int temp=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("Temperature AS i==4 : ",temp);
						     		 			 
						     		 			 frame.setTemperature(String.valueOf(temp));
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==5 || i==12 || i==19 || i==26 || i==33 || i==40 || i==47 || i==54){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
						     		 			 int pressure=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("pressure AS i==5 : ",pressure);
						     		 			 
						     		 			 frame.setPressure(String.valueOf(pressure));
					     		 			 }	 
					     		 			 i++;
					     		 	   }else if(i==6 || i==13 || i==20 || i==27 || i==34 || i==41 || i==48 || i==55){
					     		 			 String decodeBinary = Integer.toBinaryString(b);
					     		 			 if(!decodeBinary.equalsIgnoreCase("11111111111111111111111111111111")){
						     		 			 int humidity=Integer.parseInt(decodeBinary,2);
						     		 			 logger.debug("Humidity AS i==6 : ",humidity);
						     		 			 
						     		 			 frame.setHumidity(String.valueOf(humidity));
						     		 			 frame.setApplicationID(frm.getApplicationID());
						     		 			 frame.setApplicationName(frm.getApplicationName());
						     		 			 frame.setNodeName(frm.getNodeName());
						     		 			 frame.setDevEUI(frm.getDevEUI());
						     		 			 frame.setGatewayMac(frm.getGatewayMac());
						     		 			 frame.setGatewayName(frm.getGatewayName());
						     		 			 frame.setfPort(frm.getfPort());
						     		 			 frame.setCreatedAt(new Date(System.currentTimeMillis()));
						     		 			 frame.setUpdatedAt(new Date(System.currentTimeMillis()));
						     		 			 frameDao.save(frame);
						     		 			 
						     		 			n++;
					     		 			 }	 
					     		 			 i++;        
				     		 			 
					     		 	   }
				     		 		   
				     		 		   
				     		 		 
				     		 		   
				  	  				 	
				     		 	}// for loop end here		  	
				     		 		
				     	}//if end here
				     		 	
				     		 	
					
				  	  	}	
				  	  	
				  		//frameDao.save(frame);
				  		
				  		
				  		
				  		
		}
		
		}catch(Exception e){
			logger.error("Error",e);
			e.printStackTrace();
		}
	}*/

	
	
	/*@Transactional
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.debug("Inside messageArrived");
		try{
			LoraFrame frame=null;
			if(!message.toString().isEmpty()){
				 
				  JSONObject json=null;
				  		json=new JSONObject();
				  		json=(JSONObject)new JSONParser().parse(message.toString());
				  		logger.debug("REsultant json",json);
				  		
				  		 frame=new LoraFrame();
				  		
				  		 frame.setApplicationID(json.get("applicationID").toString());
				  		 frame.setApplicationName(json.get("applicationName").toString());
				  		 frame.setNodeName(json.get("nodeName").toString());
				  		 frame.setDevEUI(json.get("devEUI").toString());
				  		logger.debug("applicationID",json.get("applicationID").toString());
							logger.debug("applicationName",json.get("applicationName").toString());
								logger.debug("nodeName",json.get("nodeName").toString());
									logger.debug("devEUI",json.get("devEUI").toString());
				  		 
				  		 JSONArray arr=(JSONArray) json.get("rxInfo");
				  		 
				  		 if(arr!=null && arr.size()>0){
	   						 for (int i = 0; i < arr.size(); i++) {
	   							 JSONObject jsonObj = (JSONObject) arr.get(i);
	   							 frame.setGatewayMac(jsonObj.get("mac").toString());
	   							 frame.setGatewayName(jsonObj.get("name").toString());
	   							 
		   							logger.debug("mac",jsonObj.get("mac").toString());
		   								logger.debug("name",jsonObj.get("name").toString());
	   						 }
				  		 }
				  		 
				  		
				  		logger.debug("fport",json.get("fPort").toString());
				  	  		logger.debug("Data",json.get("data").toString());
				  	  		
				  	  	frame.setfPort(json.get("fPort").toString().trim());
				  	  	frame.setCreatedAt(new Date(System.currentTimeMillis()));
				  	  	frame.setUpdatedAt(new Date(System.currentTimeMillis()));
				  	  	
				  	  	
				  	  	
				  	  	if(json.get("data")!=null){	
				  	  		 logger.debug("Data not empty");
				     		 byte[] decoded=Base64.decodeBase64(json.get("data").toString());
				     		 
				     		 	if(decoded!=null && decoded.length>0){
				     		 		
				     		 		 String decodeBinary = Integer.toBinaryString(decoded[0]);
				     		 		 	logger.debug("decoded[0] binary : ",decodeBinary);
				     		 		 	
				     		 		  String led1 = decodeBinary.substring(decodeBinary.length()-1,decodeBinary.length());	
				     		 		  	logger.debug("led1 : ",led1);
				     		 		 	
				     		 		  String led2 = decodeBinary.substring(decodeBinary.length()-2,decodeBinary.length()-1);	
				     		 		  	logger.debug("led2 : ",led2);
				     		 		  	
				     		 		 
				     		 		 	
				     		 		 					     		 		 	
				     		 		  String devLed12 = decodeBinary.substring(0, decodeBinary.length()-2);
				     		 		  	logger.debug("devLed12 : ",devLed12);	
				     		 		  	
				     		 		 			     		 		  	
				     		 		  int dId=Integer.parseInt(devLed12,2);
				     		 		  			logger.debug("dId : ",dId);
				     		 			
				     		 		 	
				     		 		 
				     		if(decoded[1]!=0){				  	  					
				  	  				int led34deci=decoded[1] & 0xFF;	
				  	  					logger.debug("led34deci : ",led34deci);
				  	  				
				  	  				 String led34 = Integer.toBinaryString(led34deci);
				  	  				 	logger.debug("led34 : ",led34);
				  	  				 	
				  	  				String le3 = led34.substring(led34.length()-4, led34.length());	
			  	  				 		logger.debug("led3 : ",le3); 	
				  	  				 	
				  	  				 String le4= led34.substring(0, led34.length()-4);
				  	  				 	logger.debug("led4 : ",le4);
				  	  				 		
				  	  				 
				  	  				 		
				  	  				int led3=Integer.parseInt(le3,2)*6;
		     		 		  			logger.debug("dId : ",dId);	
		     		 		  			
		     		 		  		int led4=Integer.parseInt(le4,2)*6;
	     		 		  				logger.debug("dId : ",dId);	
	     		 		  				
	     		 		  			frame.setLed3(String.valueOf(led3));
			  	  					frame.setLed4(String.valueOf(led4));
				     		}	
				     		else{
				     					 		  				
 		 		  			frame.setLed3(String.valueOf("0"));
	  	  					frame.setLed4(String.valueOf("0"));
				     		}
				  	  				 	//frame.setDeviceId(String.valueOf(dId));
	     		 		  			frame.setDeviceId("00"+dId);
				  	  				 	frame.setLed1(led1);
					  	  				frame.setLed2(led2);
				  	  				 	
				  	  					
				  	  					
				  	  					frame.setTemperature(String.valueOf(decoded[2]));
				  	  					frame.setPressure(String.valueOf(decoded[3]*1000));
				  	  					frame.setHumidity(String.valueOf(decoded[4]));
				  	  					
				     		 		  	
				     		 		
				     		 	}
					
				  	  	}	
				  	  	
				  		frameDao.save(frame);
				  		
				  		
				  		
				  		
		}
		
		}catch(Exception e){
			logger.error("Error",e);
			e.printStackTrace();
		}
	}*/

	
	


	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub

	}

}
