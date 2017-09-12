import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class test4 {
	public static void main(String args[]) throws IOException {
        //String orig = "original String before base64 encoding in Java";
       // String da="MkUAAAAAAAAAAAAAAAAAAA==";
       //
       // JSONObject json=new JSONObject();
       // json.put("data", "MkUAAAAAAAAAAAAAAAAAAA==");
        //byte[] encoded = SerializationUtils.serialize((Serializable) json.get("data"));
        
        //byte[] encoded=json.get("json").getBytes();
        //byte[] b=da.getBytes(Charset.forName(da));

        //System.out.println("hiiiiii"+da);
        //encoding  byte array into base 64
        //byte[] encoded = Base64.encodeBase64(orig.getBytes());     
        //byte[] data = SerializationUtils.serialize(yourObject);
        //System.out.println("Original String: " + orig );
       // System.out.println("Base64 Encoded String : " + new String(encoded));
      
        //decoding byte array into base64
        //byte[] decoded = Base64.decodeBase64(encoded); 
        byte[] decoded=Base64.decodeBase64("ARgRAFBANXCQ");
        System.out.println("decoded val : " +decoded[0]);
        
        //System.out.println("Base 64 Decoded  String : " + new String(decoded));
        for(Byte b: decoded){
        	System.out.println("decoded val : " + b);
        }
        
    	if(decoded!=null && decoded.length>0){
    		System.out.println("decoded val 2: " +String.valueOf(decoded[3]));
    	}
        
        char character = '2';    
        int ascii = (int) character;
        String a= new String(decoded);
        //.out.println(String.format("0x%08X", a));
      //String str=String.format("0x%08X", a);
        //String str = String.format("0x", args)
     //System.out.println("String : " + str);
        try{
        	System.out.println("Base 64 Decoded  String : " + Integer.toHexString(50));
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}


