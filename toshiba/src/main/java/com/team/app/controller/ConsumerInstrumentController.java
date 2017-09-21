package com.team.app.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.app.config.MqttIntrf;
import com.team.app.constant.AppConstants;
import com.team.app.domain.JwtToken;
import com.team.app.domain.LoraFrame;
import com.team.app.domain.User;
import com.team.app.dto.ResponseDto;
import com.team.app.dto.Status;
import com.team.app.dto.StatusDto;
import com.team.app.dto.UserLoginDTO;
import com.team.app.exception.AtAppException;
import com.team.app.logger.AtLogger;
import com.team.app.service.AtappCommonService;
import com.team.app.service.ConsumerInstrumentService;
import com.team.app.service.MqttFramesService;
import com.team.app.utils.JWTKeyGenerator;
import com.team.app.utils.JsonUtil;

/**
 * 
 * @author Vikky
 *
 */

@RestController
@RequestMapping(AppConstants.CONSUMER_API)
public class ConsumerInstrumentController {
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
	
	@Autowired
	private AtappCommonService atAppCommonService;
	
	@Autowired
	private MqttFramesService  mqttFramesService;
	
	@Autowired
	private MqttIntrf mqttIntrf;
		
	
	private static final AtLogger logger = AtLogger.getLogger(ConsumerInstrumentController.class);
	
	static {
	    //for testing only
		
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("139.59.84.50")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}


	@RequestMapping(value = "/mobileLoginAuth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> userLoginFromApp(@RequestBody String received){
		logger.info("Inside in /mobileLoginAuth ");

		JSONObject obj=null;
		ResponseEntity<String> responseEntity = null;
		HttpHeaders httpHeaders =null;
		UserLoginDTO userLoginDTO=null;
		
		try{		
				obj=new JSONObject();
				obj=(JSONObject)new JSONParser().parse(received);
		}catch(Exception e){
			return new ResponseEntity<String>("Empty received body /mobileLoginAuth", HttpStatus.BAD_REQUEST);
		}
		
				
		try {			
			
			if( obj.get("username").toString()!=null && !obj.get("username").toString().isEmpty() 
    				&& obj.get("password").toString()!=null && !obj.get("password").toString().isEmpty() ){
    					
    				logger.debug("username for /mobileLoginAuth :",obj.get("username").toString());
    				logger.debug("password for /mobileLoginAuth :",obj.get("password").toString());
			
    				httpHeaders=new HttpHeaders();
			
    				userLoginDTO = consumerInstrumentServiceImpl.mobileLoginAuth(obj.get("username").toString(),obj.get("password").toString());
    				
    				    				
    				httpHeaders.add(AppConstants.HTTP_HEADER_TOKEN_NAME, userLoginDTO.getAccessToken());
    				httpHeaders.add(AppConstants.HTTP_HEADER_BASE_TOKEN_NAME, userLoginDTO.getBaseToken());
    				
    						
					try {
						/*Epoch format for Access,Base Token Expiration Date*/
						httpHeaders.add("AccessTokenExpiration", String.valueOf(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
								.parse(userLoginDTO.getAccessTokenExpDate().toString()).getTime()));
						httpHeaders.add("BaseTokenExpiration", String.valueOf(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
												.parse(userLoginDTO.getBaseTokenExpDate().toString()).getTime()));
					
					}catch(Exception e){
						logger.error("Exception in controller for /mobileLoginAuth",e);
					}
										
					String response = JsonUtil.objToJson(userLoginDTO);
			
					responseEntity = new ResponseEntity<String>(response,httpHeaders, HttpStatus.OK);
			
			}else{
				responseEntity = new ResponseEntity<String>("Any or all in usertype/mobileNo/pwd is null",HttpStatus.EXPECTATION_FAILED);
			}
		}catch(AtAppException ae) {
			logger.error("IN contoller catch block /mobileLoginAuth",ae);
			userLoginDTO=new UserLoginDTO();
			userLoginDTO.setStatusDesc(ae.getMessage());
			userLoginDTO.setStatusCode(ae.getHttpStatus().toString());
			String response = JsonUtil.objToJson(userLoginDTO);
			responseEntity = new ResponseEntity<String>(response, ae.getHttpStatus());
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/getRefreshToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getRefreshTokenHandler(@RequestHeader(value = AppConstants.HTTP_HEADER_BASE_TOKEN_NAME) String refreshToken){
		logger.info("Inside /getRefreshToken");
		ResponseEntity<String> responseEntity = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		UserLoginDTO userLoginDTO=null;
		try {
			//Validate BASE-TOKEN Value
			JWTKeyGenerator.validateXToken(refreshToken);
			
			// Validate Expriy Date
			atAppCommonService.validateXToken(AppConstants.KEY_ATAPP_MOBILE, refreshToken);
			
			userLoginDTO = consumerInstrumentServiceImpl.getRefreshTokenOnBaseToken();
			String response = JsonUtil.objToJson(userLoginDTO);
			httpHeaders.add(AppConstants.HTTP_HEADER_TOKEN_NAME, userLoginDTO.getAccessToken());
			httpHeaders.add(AppConstants.HTTP_HEADER_BASE_TOKEN_NAME, userLoginDTO.getBaseToken());
			
			try{
			httpHeaders.add("BaseTokenExpiration", String.valueOf(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
				.parse(userLoginDTO.getBaseTokenExpDate().toString()).getTime()));	
			
			httpHeaders.add("AccessTokenExpiration", String.valueOf(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
			.parse(userLoginDTO.getAccessTokenExpDate().toString()).getTime()));
			}catch(Exception e){
				logger.error("Exception in controller for /getRefreshToken",e);
			}
			responseEntity = new ResponseEntity<String>(response,httpHeaders, HttpStatus.OK);
		}catch(AtAppException ae) {
			logger.error("IN contoller catch block /getRefreshToken",ae);
			userLoginDTO=new UserLoginDTO();
			userLoginDTO.setStatusDesc(ae.getMessage());
			userLoginDTO.setStatusCode(ae.getHttpStatus().toString());
			String response = JsonUtil.objToJson(userLoginDTO);
			responseEntity = new ResponseEntity<String>(response, ae.getHttpStatus());
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/loginAuth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loginAuthentication(@RequestBody String received){
		logger.info("Inside in /loginAuth ");

		JSONObject obj=null;
		ResponseEntity<String> responseEntity = null;
		//HttpHeaders httpHeaders =null;
					
		ResponseDto dto=null;
					dto=new ResponseDto();
		Status status=null;
			status=new Status();					
					
		
		
		try{		
				obj=new JSONObject();
				obj=(JSONObject)new JSONParser().parse(received);
		}catch(Exception e){
			return new ResponseEntity<String>("Empty received body /loginAuth", HttpStatus.BAD_REQUEST);
		}
		
				
		try {			
			
			if( obj.get("username").toString()!=null && !obj.get("username").toString().isEmpty() 
    				&& obj.get("password").toString()!=null && !obj.get("password").toString().isEmpty() ){
    					
					String username=obj.get("username").toString();
					String password=obj.get("password").toString();
				
    				logger.debug("username for /loginAuth :",username);
    				logger.debug("password for /loginAuth :",password);
    				
    				JSONObject jsonObj=null;
	    				jsonObj=new JSONObject();
	    				jsonObj.put("username",username);
	    				jsonObj.put("password",password);
	    				
	    			String user=jsonObj.toString(); 
	    			
	    			logger.debug("User /loginAuth :",user);
	    			
    				String url="https://139.59.84.50:8080/api/internal/login";
    				logger.debug("URLConn",url);
    				
    				URL obj1 = new URL(url);
    				HttpURLConnection con = (HttpURLConnection) obj1.openConnection();
    				con.setDoOutput(true);
    				con.setRequestMethod("POST");
    				con.setRequestProperty("accept", "application/json");
    				con.setRequestProperty("Content-Type", "application/json");
    				
    				OutputStream os = con.getOutputStream();
    		        os.write(user.getBytes());
    		        os.flush();
    		        os.close();
    		        
    				int responseCode = con.getResponseCode();
    					logger.debug("POST Response Code :: " + responseCode);
    						logger.debug("POST Response message :: " + con.getResponseMessage());
    				
    				if(responseCode == HttpURLConnection.HTTP_OK) {
    					logger.debug("Token valid,POST Response with 200");
    					
    					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    					String inputLine;
    					StringBuffer response = new StringBuffer();

    					while ((inputLine = in.readLine()) != null) {
    						response.append(inputLine);
    					}
    					
    					in.close();
    					
    					JSONObject json=null;
    						json=new JSONObject();
    					json=(JSONObject)new JSONParser().parse(response.toString());
    					String jwt=json.get("jwt").toString();
    					logger.debug("jwt result",jwt);
    			
        				
    					if(!jwt.isEmpty()){
    						List<JwtToken> token=consumerInstrumentServiceImpl.getJwtToken();
    						  if(token!=null && !token.isEmpty()){
    							  logger.debug("jwt existing");
    							  JwtToken jwtT=token.get(0);
	    							  jwtT.setJwt(jwt);
	    							  jwtT.setUpdatedAt(new Date(System.currentTimeMillis()));
	    							  consumerInstrumentServiceImpl.updateJwt(jwtT);
    						  }else{
    							  logger.debug("jwt new");
    							  JwtToken jwtObj=null;
    	    						jwtObj=new JwtToken();
    	    						jwtObj.setJwt(jwt);
    	    						jwtObj.setCreatedAt(new Date(System.currentTimeMillis()));
    	    						jwtObj.setUpdatedAt(new Date(System.currentTimeMillis()));
    	    						consumerInstrumentServiceImpl.updateJwt(jwtObj);
    						  }
    						 // httpHeaders=new HttpHeaders();  
    						  //httpHeaders.add(AppConstants.HTTP_HEADER_JWT_TOKEN,jwt);
    						  dto.setStatusDesc("Successfully login");
    						  dto.setJwt(jwt);
    						  String resp = JsonUtil.objToJson(dto);
    						  responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
    					 }else{
    						// httpHeaders=new HttpHeaders();  
    						//httpHeaders.add(AppConstants.HTTP_HEADER_JWT_TOKEN,null);
    						dto.setStatusDesc("JWT not generated");
    						String resp = JsonUtil.objToJson(dto);
    						responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
    					 }
    					
    					
    					
    					
    				}else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
    					dto.setStatusDesc("Invalid credentials");
						String resp = JsonUtil.objToJson(dto);
    					responseEntity = new ResponseEntity<String>(resp,HttpStatus.UNAUTHORIZED);
    				}else{
    					status.setStatusDesc(con.getResponseMessage());
    					status.setStatusCode(String.valueOf(con.getResponseCode()));
						String resp = JsonUtil.objToJson(status);
    					responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
    				}
    				
    				  				   				
			
					
			}else{
				dto.setStatusDesc("username and/or password is null");
				String resp = JsonUtil.objToJson(dto);
				responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
			}
		}catch(Exception e) {
			logger.error("IN contoller catch block /loginAuth",e);
			dto.setStatusDesc(e.getMessage());
			String response = JsonUtil.objToJson(dto);
			responseEntity = new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return responseEntity;
	}
	
	/*@RequestMapping(value = "/json", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void jsonTesting() {
		logger.info("Inside /jsonTesting");
				
		try{
							
				JSONObject json=null;
					json=new JSONObject();
				
				json=(JSONObject)new JSONParser().parse(new FileReader("C:\\Users\\Dell\\Desktop\\file\\json.txt"));
				JSONArray arr=(JSONArray) json.get("result");
				
				
				if(arr!=null && arr.size()>0){
					 for (int i = 0; i < arr.size(); i++) {
						JSONObject jsonObj = (JSONObject) arr.get(i);
						logger.debug("json body ",json);
					 }
				}

		
		}catch(Exception e){
			logger.error("Error in /jsonTesting",e);
		}
	
	}*/
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUserhandler(){
		logger.info("Inside in /user ");

		ResponseEntity<String> responseEntity = null;
		ResponseDto dto=null;
					dto=new ResponseDto();
		Status status=null;
		  status=new Status();
		
						
		try {			
					List<JwtToken> token=consumerInstrumentServiceImpl.getJwtToken();
					String jwt="";
						 if(token!=null && !token.isEmpty()){
							 jwt=token.get(0).getJwt();
						 }
					 
    				String url="https://139.59.84.50:8080/api/users?limit=100";
    				logger.debug("URLConn",url);
    				
    				URL obj1 = new URL(url);
    				HttpURLConnection con = (HttpURLConnection) obj1.openConnection();
    				con.setDoOutput(true);
    				con.setRequestMethod("GET");
    				con.setRequestProperty("accept", "application/json");
    				con.setRequestProperty("Content-Type", "application/json");
    				con.setRequestProperty("Grpc-Metadata-Authorization",jwt);
    				
    				    
    				int responseCode = con.getResponseCode();
    					logger.debug("POST Response Code :: " + responseCode);
    						    				
    				if(responseCode == HttpURLConnection.HTTP_OK) {
    					logger.debug("Token valid,POST Response with 200");
    					
    					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    					String inputLine;
    					StringBuffer response = new StringBuffer();

    					while ((inputLine = in.readLine()) != null) {
    						response.append(inputLine);
    					}
    					
    					in.close();
    					
    					JSONObject json=null;
    						json=new JSONObject();
    					json=(JSONObject)new JSONParser().parse(response.toString());
    					//String count=(String) json.get("totalCount");
    					JSONArray arr=(JSONArray) json.get("result");    					
    					
    					if(arr!=null && arr.size()>0){
    						 for (int i = 0; i < arr.size(); i++) {
    							 JSONObject jsonObj = (JSONObject) arr.get(i);
    							 User u=consumerInstrumentServiceImpl.getNSUserById(jsonObj.get("id").toString());
    							 if(u!=null){
    								 logger.debug("/existing NSuser");
    								 u.setId(jsonObj.get("id").toString());
	    							 u.setUsername(jsonObj.get("username").toString());
	    							 u.setSessionTtl(String.valueOf(jsonObj.get("sessionTTL")));
	    							 u.setIsAdmin(String.valueOf(jsonObj.get("isAdmin")));
	    							 u.setIsActive(String.valueOf(jsonObj.get("isActive")));
	    							 String[] createdat=jsonObj.get("createdAt").toString().split("\\.");
	    							 String[] updatedat=jsonObj.get("updatedAt").toString().split("\\.");
	    							
	    							 Date createdDt=(Date) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(createdat[0]); 
	    							 Date updatedDt=(Date) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(updatedat[0]); 
	    							
	    							 //java.util.Date dt=DateUtil.convertStringToDate(updatedat[0], "yyyy-MM-dd hh:mm::ss", "yyyy-MM-dd hh:mm::ss");
	    							 u.setCreatedAt(createdDt);
	    							 u.setUpdatedAt(updatedDt);
	    							 consumerInstrumentServiceImpl.updateNSUser(u);
    								 
    							 }else{
    								 logger.debug("/new NSuser");
    								 User usr=null;
    								 usr=new User();
	    							 usr.setId(jsonObj.get("id").toString());
	    							 usr.setUsername(jsonObj.get("username").toString());
	    							 usr.setSessionTtl(String.valueOf(jsonObj.get("sessionTTL")));
	    							 usr.setIsAdmin(String.valueOf(jsonObj.get("isAdmin")));
	    							 usr.setIsActive(String.valueOf(jsonObj.get("isActive")));
	    							 consumerInstrumentServiceImpl.updateNSUser(usr);
    							 }
    						 }
    			
    						  dto.setStatusDesc("Successfully added/update user");
    						  String resp = JsonUtil.objToJson(dto);
    						  responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
    					 }else{
    						
    						dto.setStatusDesc("Empty result");
    						String resp = JsonUtil.objToJson(dto);
    						responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
    					 }
    					
    				   					
    				}else{
    					status.setStatusDesc(String.valueOf(con.getResponseCode()));
    					status.setStatusCode(con.getResponseMessage());
						String resp = JsonUtil.objToJson(status);
    					responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
    			  	}
			
		}catch(Exception e) {
			logger.error("IN contoller catch block /user",e);
			dto.setStatusDesc(e.getMessage());
			String response = JsonUtil.objToJson(dto);
			responseEntity = new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/frame", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> frameHandler(){
		logger.info("Inside in /user ");

		ResponseEntity<String> responseEntity = null;
		ResponseDto dto=null;
					dto=new ResponseDto();
		Status status=null;
		  status=new Status();
		
						
		try {			
					List<JwtToken> token=consumerInstrumentServiceImpl.getJwtToken();
					String jwt="";
						 if(token!=null && !token.isEmpty()){
							 jwt=token.get(0).getJwt();
						 }
					 
    				String url="https://139.59.84.50:8080/api/nodes/4786e6ed00490044/frames?limit=1000000";
    				logger.debug("URLConn",url);
    				
    				URL obj1 = new URL(url);
    				HttpURLConnection con = (HttpURLConnection) obj1.openConnection();
    				con.setDoOutput(true);
    				con.setRequestMethod("GET");
    				con.setRequestProperty("accept", "application/json");
    				con.setRequestProperty("Content-Type", "application/json");
    				con.setRequestProperty("Grpc-Metadata-Authorization",jwt);
    				
    				    
    				int responseCode = con.getResponseCode();
    					logger.debug("GET Response Code :: " + responseCode);
    						    				
    				if(responseCode == HttpURLConnection.HTTP_OK) {
    					logger.debug("Token valid,POST Response with 200");
    					
    					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    					String inputLine;
    					StringBuffer response = new StringBuffer();

    					while ((inputLine = in.readLine()) != null) {
    						response.append(inputLine);
    					}
    					
    					in.close();
    					
    					JSONObject json=null;
    						json=new JSONObject();
    					json=(JSONObject)new JSONParser().parse(response.toString());
    					//String count=(String) json.get("totalCount");
    					JSONArray arr=(JSONArray) json.get("result");    					
    					
    					if(arr!=null && arr.size()>0){
    						 for (int i = 0; i < arr.size(); i++) {
    							 JSONObject jsonObj = (JSONObject) arr.get(i);
    							 User u=consumerInstrumentServiceImpl.getNSUserById(jsonObj.get("id").toString());
    							 if(u!=null){
    								 logger.debug("/existing NSuser");
    								 u.setId(jsonObj.get("id").toString());
	    							 u.setUsername(jsonObj.get("username").toString());
	    							 u.setSessionTtl(String.valueOf(jsonObj.get("sessionTTL")));
	    							 u.setIsAdmin(String.valueOf(jsonObj.get("isAdmin")));
	    							 u.setIsActive(String.valueOf(jsonObj.get("isActive")));
	    							 String[] createdat=jsonObj.get("createdAt").toString().split("\\.");
	    							 String[] updatedat=jsonObj.get("updatedAt").toString().split("\\.");
	    							
	    							 Date createdDt=(Date) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(createdat[0]); 
	    							 Date updatedDt=(Date) new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(updatedat[0]); 
	    							
	    							 //java.util.Date dt=DateUtil.convertStringToDate(updatedat[0], "yyyy-MM-dd hh:mm::ss", "yyyy-MM-dd hh:mm::ss");
	    							 u.setCreatedAt(createdDt);
	    							 u.setUpdatedAt(updatedDt);
	    							 consumerInstrumentServiceImpl.updateNSUser(u);
    								 
    							 }else{
    								 logger.debug("/new NSuser");
    								 User usr=null;
    								 usr=new User();
	    							 usr.setId(jsonObj.get("id").toString());
	    							 usr.setUsername(jsonObj.get("username").toString());
	    							 usr.setSessionTtl(String.valueOf(jsonObj.get("sessionTTL")));
	    							 usr.setIsAdmin(String.valueOf(jsonObj.get("isAdmin")));
	    							 usr.setIsActive(String.valueOf(jsonObj.get("isActive")));
	    							 consumerInstrumentServiceImpl.updateNSUser(usr);
    							 }
    						 }
    			
    						  dto.setStatusDesc("Successfully added/update user");
    						  String resp = JsonUtil.objToJson(dto);
    						  responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
    					 }else{
    						
    						dto.setStatusDesc("Empty result");
    						String resp = JsonUtil.objToJson(dto);
    						responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
    					 }
    					
    				   					
    				}else{
    					status.setStatusDesc(String.valueOf(con.getResponseCode()));
    					status.setStatusCode(con.getResponseMessage());
						String resp = JsonUtil.objToJson(status);
    					responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
    			  	}
			
		}catch(Exception e) {
			logger.error("IN contoller catch block /user",e);
			dto.setStatusDesc(e.getMessage());
			String response = JsonUtil.objToJson(dto);
			responseEntity = new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/getInfo", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfoHandler() {
		logger.info("Inside /getInfo");
		ResponseEntity<String> responseEntity = null;
		StatusDto statusDto=null;
				statusDto=new StatusDto();
		try{
			
			
			String url="https://139.59.84.50:8080/api/nodes/4786e6ed00490044/frames?limit=1000";
			logger.debug("URLConn",url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Grpc-Metadata-Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJsb3JhLWFwcC1zZXJ2ZXIiLCJleHAiOjE1MDQ0NTkxNDMsImlzcyI6ImxvcmEtYXBwLXNlcnZlciIsIm5iZiI6MTUwNDM3Mjc0Mywic3ViIjoidXNlciIsInVzZXJuYW1lIjoiYWRtaW4ifQ.RsKmA9lvrI_GmFphkVaa5fLWTIRj-ACt7B9RvT9Xy2c");
			con.setRequestProperty("accept", "application/json");
			int responseCode = con.getResponseCode();
			logger.debug("GET Response Code :: " + responseCode);
			logger.debug("GET Response message :: " + con.getResponseMessage());
			
						
			if(responseCode == HttpURLConnection.HTTP_OK) {
				logger.debug("Token valid,GET Response with 200");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				
				in.close();
				
				JSONObject json=null;
					json=new JSONObject();
				json=(JSONObject)new JSONParser().parse(response.toString());
				JSONArray arr=(JSONArray) json.get("result");
				//String count=(String) json.get("totalCount");
				
				if(arr!=null && arr.size()>0){
					 for (int i = 0; i < arr.size(); i++) {
						JSONObject jsonObj = (JSONObject) arr.get(i);
						 statusDto.setResp(jsonObj.toString());
						 //statusDto.setCount(count);
					 }
				}else{
					statusDto.setResp("Empty Result");	
					//statusDto.setCount(count);
				}
				statusDto.setStatusDesc("Success");
				statusDto.setStatusCode(HttpStatus.OK.toString());
				String res = JsonUtil.objToJson(statusDto);
				responseEntity = new ResponseEntity<String>(res, HttpStatus.OK);
			} else {
				statusDto.setStatusDesc("Not Expected");
				statusDto.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
				statusDto.setResp(String.valueOf(responseCode));
				String response = JsonUtil.objToJson(statusDto);
				responseEntity = new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
			}

		
		}catch(Exception e){
			logger.error("IN contoller catch block /getInfo",e);
			statusDto.setStatusDesc(e.getMessage());
			String response = JsonUtil.objToJson(statusDto);
			responseEntity = new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		
	return responseEntity;
	}
	
	
	
	@RequestMapping(value = "/init", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> test1MqttHandler() {
		logger.info("Inside /test");
		ResponseEntity<String> responseEntity = null;		
		try{							
			mqttIntrf.doDemo();
			responseEntity = new ResponseEntity<String>("Connected", HttpStatus.OK);
		 }catch(Exception me){
			 logger.error("Error in /mqtt testing",me);
			 me.printStackTrace();
	     }
		return responseEntity;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deviceInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deviceInfoHandler(@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /deviceInfo ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
		try{			
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				List<LoraFrame> frames=mqttFramesService.getFrameByDeviceId();
				
				if(frames!=null && !frames.isEmpty()){
				
					JSONArray arr=null;
						arr=new JSONArray();
					JSONObject result=null;
						result=new JSONObject();					
					for(LoraFrame f : frames){
						List<LoraFrame> frmList=mqttFramesService.getFrameByDevId(f.getDeviceId(),f.getNodeName());
						if(frmList!=null && !frmList.isEmpty()){
							LoraFrame frm=frmList.get(0);
							JSONObject json=null;
								json=new JSONObject();								
								json.put("id", frm.getId());
								json.put("led1", frm.getLed1());
								json.put("led2", frm.getLed2());
								json.put("led3", frm.getLed3());
								json.put("led4", frm.getLed4());
								json.put("humidity", frm.getHumidity());
								json.put("pressure", frm.getPressure());
								json.put("temperature", frm.getTemperature());
								json.put("nodeName", frm.getNodeName());
								//json.put("loraId", frm.getLoraId());
								json.put("deviceId", frm.getDeviceId());
								json.put("devAdd", frm.getDeviceId());
								
								try{
									json.put("date", String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
										.parse(frm.getCreatedAt().toString()).getTime()));
								}catch(Exception e){
									logger.error(e);
								}
							
								arr.add(json);
						}	
					}	
							result.put("devices", arr);
						
					String resp = JsonUtil.objToJson(result);
					responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
				}else{
					status.setStatusDesc("No frames found");
	    			status.setStatusCode(HttpStatus.NO_CONTENT.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
    		 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /deviceInfo",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deviceInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deviceInfoHandler(@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /deviceInfo ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
		try{			
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				List<LoraFrame> frames=mqttFramesService.getFrameByDeviceId();
				
				if(frames!=null && !frames.isEmpty()){
				 LoraFrame res=frames.get(0);
				 	logger.info("res object ",res.getNodeName());
					JSONArray arr=null;
						arr=new JSONArray();
					JSONArray resultant=null;
						resultant=new JSONArray();
					JSONObject result=null;
						result=new JSONObject();
					int i=0;
					String node="";
						
					for(LoraFrame f : frames){
						List<LoraFrame> frmList=mqttFramesService.getFrameByDevId(f.getDeviceId(),f.getNodeName());
						if(frmList!=null && !frmList.isEmpty()){
							LoraFrame frm=frmList.get(0);
							if(res.getNodeName().equalsIgnoreCase(frm.getNodeName())){
								
								JSONObject json=null;
									json=new JSONObject();								
									json.put("id", frm.getId());
									json.put("led1", frm.getLed1());
									json.put("led2", frm.getLed2());
									json.put("led3", frm.getLed3());
									json.put("led4", frm.getLed4());
									json.put("humidity", frm.getHumidity());
									json.put("pressure", frm.getPressure());
									json.put("temperature", frm.getTemperature());
									json.put("nodeName", frm.getNodeName());
									//json.put("loraId", frm.getLoraId());
									json.put("deviceId", frm.getDeviceId());
									
									try{
										json.put("date", String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
											.parse(frm.getCreatedAt().toString()).getTime()));
									}catch(Exception e){
										logger.error(e);
									}
									
									//jsonArr.add(json);								
									arr.add(json);
							}else{
							 	if(i==0){
									JSONArray rArr=null;
											rArr=new JSONArray();
									JSONObject json=null;
										json=new JSONObject();								
										json.put("id", frm.getId());
										json.put("led1", frm.getLed1());
										json.put("led2", frm.getLed2());
										json.put("led3", frm.getLed3());
										json.put("led4", frm.getLed4());
										json.put("humidity", frm.getHumidity());
										json.put("pressure", frm.getPressure());
										json.put("temperature", frm.getTemperature());
										json.put("nodeName", frm.getNodeName());
										//json.put("loraId", frm.getLoraId());
										json.put("deviceId", frm.getDeviceId());
										
										try{
											json.put("date", String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
												.parse(frm.getCreatedAt().toString()).getTime()));
										}catch(Exception e){
											logger.error(e);
										}
										
										rArr.add(json);								
										arr.add(rArr);
										i++;
										node=frm.getNodeName();
							 	}else if(node.equalsIgnoreCase(frm.getNodeName())){
							 		
							 	}
							}
							
							resultant.add(arr);
						}	
					}	
							result.put("devices", resultant);
						
					String resp = JsonUtil.objToJson(result);
					responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
				}else{
					status.setStatusDesc("No frames found");
	    			status.setStatusCode(HttpStatus.NO_CONTENT.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
    		 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /deviceInfo",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}*/
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeviceInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDeviceValHandler(@RequestBody String received,@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /getDeviceInfo ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
				JSONObject obj=null;	

				try{		
						obj=new JSONObject();
						obj=(JSONObject)new JSONParser().parse(received);
				}catch(Exception e){
					return new ResponseEntity<String>("Empty received body /mobileLoginAuth", HttpStatus.BAD_REQUEST);
				}
				
		try{			

			if( obj.get("loraId").toString()!=null && !obj.get("loraId").toString().isEmpty() 
    				&& obj.get("deviceId").toString()!=null && !obj.get("deviceId").toString().isEmpty() ){
    					
    				logger.debug("loraId for /getDeviceInfo :",obj.get("loraId").toString());
    				logger.debug("deviceId for /getDeviceInfo :",obj.get("deviceId").toString());
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				List<LoraFrame> frames=mqttFramesService.getFramesByLoraIdAndDevId( obj.get("loraId").toString(),obj.get("deviceId").toString());
				JSONArray arr=null;
						arr=new JSONArray();
				if(frames!=null && !frames.isEmpty()){
					JSONObject result=null;
						result=new JSONObject();
					for(LoraFrame frm: frames){
						JSONObject json=null;
							json=new JSONObject();
							json.put("id", frm.getId());
							json.put("humidity", frm.getHumidity());
							json.put("pressure", frm.getPressure());
							json.put("temperature", frm.getTemperature());
														
							try{
								json.put("date", String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
									.parse(frm.getCreatedAt().toString()).getTime()));
							}catch(Exception e){
								logger.error(e);
							}
						
							arr.add(json);
					}	
							result.put("devices", arr);
						
					String resp = JsonUtil.objToJson(result);
					responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);
				}else{
					status.setStatusDesc("No frames found");
	    			status.setStatusCode(HttpStatus.NO_CONTENT.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NO_CONTENT);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
		}else{
			status.setStatusDesc("loraId or deviceId any or both null");
			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			String resp = JsonUtil.objToJson(status);
			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
		}	 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /getDeviceInfo",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDownlinkOnLED1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDownlinkOnLED1(@RequestBody String received,@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /setDownlinkOnLED1 ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
				JSONObject obj=null;	

				try{		
						obj=new JSONObject();
						obj=(JSONObject)new JSONParser().parse(received);
				}catch(Exception e){
					return new ResponseEntity<String>("Empty received body /setDownlinkOnLED1", HttpStatus.BAD_REQUEST);
				}
				
		try{			

			if( obj.get("devices").toString()!=null && !obj.get("devices").toString().isEmpty()){
    					
    				logger.debug("devices for /setDownlinkOnLED1 :",obj.get("devices").toString());
    			
			
			logger.debug("JWT TOken ",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				JSONArray arr=(JSONArray) obj.get("devices");
				
				if(arr!=null && arr.size()>0){
					for (int i = 0; i < arr.size(); i++) {
						logger.debug("INside for main loop");
						JSONArray jsonArr=(JSONArray) arr.get(i);	
						
							if(jsonArr!=null && jsonArr.size()>0){
								
								String devices="00000000";
								String command="0000";
								String ledadd="0001";
								
								
								for (int j = 0; j < jsonArr.size(); j++) {
									JSONObject jObj=(JSONObject) jsonArr.get(i);
									   logger.debug("/deviceId",jObj.get("deviceId"));
										
										
										if(!jObj.get("deviceId").toString().isEmpty() && jObj.get("deviceId").toString()!=null){
											
											if(jObj.get("deviceId").toString().equalsIgnoreCase("000")){
												devices=devices.substring(0, 7)+"1";
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("001")){
												 logger.debug("/inside deviceId as ",devices);
												devices=devices.substring(0, 6)+"1"+devices.substring(7);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("002")){
												devices=devices.substring(0, 5)+"1"+devices.substring(6);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("003")){
												devices=devices.substring(0, 4)+"1"+devices.substring(5);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("004")){
												devices=devices.substring(0, 3)+"1"+devices.substring(4);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("005")){
												devices=devices.substring(0, 2)+"1"+devices.substring(3);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("006")){
												devices=devices.substring(0, 1)+"1"+devices.substring(2);
											}else if(jObj.get("deviceId").toString().equalsIgnoreCase("007")){
												devices="1"+devices.substring(1);
											}
										}else{
											status.setStatusDesc("deviceId is null or empty");
							    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
											String resp = JsonUtil.objToJson(status);
							    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
										}
										
										logger.debug("Downlink as devices "+devices);
										
											if(!jObj.get("led1").toString().isEmpty() && jObj.get("led1").toString()!=null){
												if(jObj.get("led1").toString().equalsIgnoreCase("0")){
													command="0010";
												}else if(jObj.get("led1").toString().equalsIgnoreCase("1")){
													command="0001";
												}
												
											}else{
												status.setStatusDesc("led1 is null or empty");
								    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
												String resp = JsonUtil.objToJson(status);
								    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
											}
										
										logger.debug("Downlink as command "+command);
										
								}	
								
																
								String downlinkData=devices+command+ledadd;
								
								logger.debug("downlinkData "+downlinkData);
								
								Base64.encodeBase64(downlinkData.getBytes());
								
								JSONObject jsonObj=null;
				    				jsonObj=new JSONObject();
				    				jsonObj.put("confirmed",true);
				    				jsonObj.put("data",Base64.encodeBase64(downlinkData.getBytes()).toString());
				    				jsonObj.put("devEUI","4786e6ed00490048");
				    				jsonObj.put("fPort","");
				    				jsonObj.put("reference","BLE-NODE");
				    	
			    				
				    				String jsonData=jsonObj.toString(); 
			    			
										
										String url="https://139.59.84.50:8080/api/nodes/4786e6ed00490048/queue";
					    				logger.debug("URLConn",url);
					    				
					    				URL obj1 = new URL(url);
					    				HttpURLConnection con = (HttpURLConnection) obj1.openConnection();
					    				con.setDoOutput(true);
					    				con.setRequestMethod("POST");
					    				con.setRequestProperty("accept", "application/json");
					    				con.setRequestProperty("Content-Type", "application/json");
					    				con.setRequestProperty("Grpc-Metadata-Authorization",jwt);
					    				
					    				OutputStream os = con.getOutputStream();
					    		        os.write(jsonData.getBytes());
					    		        os.flush();
					    		        os.close();
					    		        
					    				int responseCode = con.getResponseCode();
					    					logger.debug("POST Response Code :: " + responseCode);
					    						logger.debug("POST Response message :: " + con.getResponseMessage());
					    				
					    				if(responseCode == HttpURLConnection.HTTP_OK) {
					    					status.setStatusDesc("downlink for LED1 sent to queue successfully");
							    			status.setStatusCode(HttpStatus.OK.toString());
											String resp = JsonUtil.objToJson(status);
							    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);	
					    				}else{
					    					status.setStatusDesc("downlink for LED1 failed");
							    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
											String resp = JsonUtil.objToJson(status);
							    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
					    				}
									
									
									
									
								
							}else{
								status.setStatusDesc("further devices json array is null/0");
				    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
								String resp = JsonUtil.objToJson(status);
				    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
							}
					
						
					}
				}else{
					status.setStatusDesc("devices of jsonarray is null/0");
	    			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
		}else{
			status.setStatusDesc("devices in request body is null");
			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			String resp = JsonUtil.objToJson(status);
			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
		}	 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /setDownlinkOnLED1",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDownlinkOnLED2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDownlinkOnLED2Handler(@RequestBody String received,@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /setDownlinkOnLED2 ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
				JSONObject obj=null;	

				try{		
						obj=new JSONObject();
						obj=(JSONObject)new JSONParser().parse(received);
				}catch(Exception e){
					return new ResponseEntity<String>("Empty received body /setDownlinkOnLED2", HttpStatus.BAD_REQUEST);
				}
				
		try{			

			if( obj.get("devices").toString()!=null && !obj.get("devices").toString().isEmpty()){
    					
    				logger.debug("devices for /setDownlinkOnLED2 :",obj.get("devices").toString());
    			
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				JSONArray arr=(JSONArray) obj.get("devices");
				
				if(arr!=null && arr.size()>0){
					for (int i = 0; i < arr.size(); i++) {
						JSONArray jsonArr=(JSONArray) arr.get(i);		
							if(jsonArr!=null && jsonArr.size()>0){
								for (int j = 0; j < jsonArr.size(); j++) {
									JSONObject jObj=(JSONObject) jsonArr.get(0);
									logger.debug("/nodeName",jObj.get("nodeName"));
									logger.debug("/deviceId",jObj.get("deviceId"));
									logger.debug("/led2",jObj.get("led2"));
									
									
									status.setStatusDesc("downlink for LED2 sent to queue successfully");
					    			status.setStatusCode(HttpStatus.OK.toString());
									String resp = JsonUtil.objToJson(status);
					    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);	
									
								}
							}else{
								status.setStatusDesc("further devices json array is null/0");
				    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
								String resp = JsonUtil.objToJson(status);
				    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
							}
					
						
					}
				}else{
					status.setStatusDesc("devices of jsonarray is null/0");
	    			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
		}else{
			status.setStatusDesc("devices in request body is null");
			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			String resp = JsonUtil.objToJson(status);
			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
		}	 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /setDownlinkOnLED2",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDownlinkOnLED3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDownlinkOnLED3Handler(@RequestBody String received,@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /setDownlinkOnLED3 ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
				JSONObject obj=null;	

				try{		
						obj=new JSONObject();
						obj=(JSONObject)new JSONParser().parse(received);
				}catch(Exception e){
					return new ResponseEntity<String>("Empty received body /setDownlinkOnLED3", HttpStatus.BAD_REQUEST);
				}
				
		try{			

			if( obj.get("devices").toString()!=null && !obj.get("devices").toString().isEmpty()){
    					
    				logger.debug("devices for /setDownlinkOnLED3 :",obj.get("devices").toString());
    			
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				JSONArray arr=(JSONArray) obj.get("devices");
				
				if(arr!=null && arr.size()>0){
					for (int i = 0; i < arr.size(); i++) {
						JSONArray jsonArr=(JSONArray) arr.get(i);		
							if(jsonArr!=null && jsonArr.size()>0){
								for (int j = 0; j < jsonArr.size(); j++) {
									JSONObject jObj=(JSONObject) jsonArr.get(0);
									logger.debug("/nodeName",jObj.get("nodeName"));
									logger.debug("/deviceId",jObj.get("deviceId"));
									logger.debug("/led3",jObj.get("led3"));
									
									
									status.setStatusDesc("downlink for LED3 sent to queue successfully");
					    			status.setStatusCode(HttpStatus.OK.toString());
									String resp = JsonUtil.objToJson(status);
					    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);	
									
								}
							}else{
								status.setStatusDesc("further devices json array is null/0");
				    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
								String resp = JsonUtil.objToJson(status);
				    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
							}
					
						
					}
				}else{
					status.setStatusDesc("devices of jsonarray is null/0");
	    			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
		}else{
			status.setStatusDesc("devices in request body is null");
			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			String resp = JsonUtil.objToJson(status);
			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
		}	 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /setDownlinkOnLED3",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDownlinkOnLED4", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setDownlinkOnLED4Handler(@RequestBody String received,@RequestHeader(value = AppConstants.HTTP_HEADER_JWT_TOKEN) String jwt){
		logger.info("Inside in /setDownlinkOnLED4 ");
		ResponseEntity<String> responseEntity = null;
		Status status=null;
				status=new Status();
				JSONObject obj=null;	

				try{		
						obj=new JSONObject();
						obj=(JSONObject)new JSONParser().parse(received);
				}catch(Exception e){
					return new ResponseEntity<String>("Empty received body /setDownlinkOnLED4", HttpStatus.BAD_REQUEST);
				}
				
		try{			

			if( obj.get("devices").toString()!=null && !obj.get("devices").toString().isEmpty()){
    					
    				logger.debug("devices for /setDownlinkOnLED4 :",obj.get("devices").toString());
    			
			
			logger.debug("JWT TOken",jwt);
			if( jwt!=null && !jwt.isEmpty()){    					
				JSONArray arr=(JSONArray) obj.get("devices");
				
				if(arr!=null && arr.size()>0){
					for (int i = 0; i < arr.size(); i++) {
						JSONArray jsonArr=(JSONArray) arr.get(i);		
							if(jsonArr!=null && jsonArr.size()>0){
								for (int j = 0; j < jsonArr.size(); j++) {
									JSONObject jObj=(JSONObject) jsonArr.get(0);
									logger.debug("/nodeName",jObj.get("nodeName"));
									logger.debug("/deviceId",jObj.get("deviceId"));
									logger.debug("/led4",jObj.get("led4"));
									
									
									status.setStatusDesc("downlink for LED4 sent to queue successfully");
					    			status.setStatusCode(HttpStatus.OK.toString());
									String resp = JsonUtil.objToJson(status);
					    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.OK);	
									
								}
							}else{
								status.setStatusDesc("further devices json array is null/0");
				    			status.setStatusCode(HttpStatus.BAD_REQUEST.toString());
								String resp = JsonUtil.objToJson(status);
				    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.BAD_REQUEST);
							}
					
						
					}
				}else{
					status.setStatusDesc("devices of jsonarray is null/0");
	    			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
					String resp = JsonUtil.objToJson(status);
	    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
				}
    		    					
    		}else{
    			status.setStatusDesc("Jwt token is empty");
    			status.setStatusCode(HttpStatus.NOT_ACCEPTABLE.toString());
				String resp = JsonUtil.objToJson(status);
    			responseEntity = new ResponseEntity<String>(resp,HttpStatus.NOT_ACCEPTABLE);
    		}
		}else{
			status.setStatusDesc("devices in request body is null");
			status.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			String resp = JsonUtil.objToJson(status);
			responseEntity = new ResponseEntity<String>(resp,HttpStatus.EXPECTATION_FAILED);
		}	 		  				   				
			
		}catch(Exception e){
			logger.error("IN contoller catch block /setDownlinkOnLED4",e);
			responseEntity = new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	
	

	
	
}
	