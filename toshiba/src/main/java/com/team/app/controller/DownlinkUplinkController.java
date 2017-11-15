package com.team.app.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.app.domain.DownlinkQueue;
import com.team.app.logger.AtLogger;
import com.team.app.service.MqttFramesService;

@Controller
public class DownlinkUplinkController {
private static final AtLogger logger = AtLogger.getLogger(DownlinkUplinkController.class);
	
		
	@Autowired
	private MqttFramesService mqttFramesService;
	
	@RequestMapping(value= {"/downlinkQueue"}, method=RequestMethod.GET)
    public String downlinkQueueHandler(Map<String,Object> map) {
		try{
			logger.debug("In /downlinkQueue");
			mqttFramesService.deleteDownlinkQuere();
		String url="https://139.59.14.31:8080/api/nodes/4786e6ed00490042/queue";
		logger.debug("URLConn",url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Grpc-Metadata-Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb3JhLWFwcC1zZXJ2ZXIiLCJhdWQiOiJsb3JhLWFwcC1zZXJ2ZXIiLCJuYmYiOjE1MDQ3NjQ4NzUsInN1YiI6InVzZXIiLCJ1c2VybmFtZSI6ImFkbWluIn0.l_XuzGUBJmDuKE-7rAEh0cr2HN9myv20MbrxwYBnoRo");
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
			JSONArray arr=(JSONArray) json.get("items");
			
			if(arr!=null && arr.size()>0){
				 for (int i = 0; i < arr.size(); i++) {
					JSONObject jsonObj = (JSONObject) arr.get(i);
					
					DownlinkQueue q=null;
						q=new DownlinkQueue();
						
						q.setId(jsonObj.get("id").toString());
						q.setDevEui(jsonObj.get("devEUI").toString());
						q.setReference(jsonObj.get("reference").toString());
						q.setConfirmed(jsonObj.get("confirmed").toString());
						q.setPending(jsonObj.get("pending").toString());
						q.setFport(jsonObj.get("fPort").toString());
						q.setData(jsonObj.get("data").toString());
						q.setCreatedAt(new Date(System.currentTimeMillis()));
						
						mqttFramesService.saveDownlink(q);
						
								
					
				 }
			}	
			
		List<DownlinkQueue>	downlinkQueueList=mqttFramesService.getDownlinkQueue();
			map.put("downlinkQueueList", downlinkQueueList);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "downlinkQueue";
		
		
	}
	
}
