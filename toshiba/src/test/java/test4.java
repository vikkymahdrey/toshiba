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
		
		String v="1001100000010001";
			
		String value=Base64.encodeBase64String(v.getBytes());
		System.out.println("hiiiiii==="+value);
		
		String v1=v.substring(0,3);
		System.out.println("hiiiiii"+value);

        //System.out.println("hiiiiii"+da);
        //encoding  byte array into base 64
        //byte[] encoded = Base64.encodeBase64(orig.getBytes());     
        //byte[] data = SerializationUtils.serialize(yourObject);
        //System.out.println("Original String: " + orig );
       // System.out.println("Base64 Encoded String : " + new String(encoded));
      
        //decoding byte array into base64
        //byte[] decoded = Base64.decodeBase64(encoded);
		try{
        byte[] decoded=Base64.decodeBase64("DAECAwQ=");
        //System.out.println("decoded val : " +decoded[0]);
        
        //System.out.println("Base 64 Decoded  String : " + new String(decoded));
        for(Byte b: decoded){
        	System.out.println("decoded val : " + b);
        }
       // byte[] bb=new byte[decoded.length];
    	if(decoded!=null && decoded.length>0){
    		//System.out.println("decoded val 2: " +String.valueOf(decoded[3]));
    		//System.arraycopy(decoded, 10, bb, 4 , 4);
    		//System.out.println("decoded val 2: " +String.valueOf(decoded[3]));
    		
    		//String hex = "ff"
    		//int value = Integer.parseInt(hex, 16);
    		
    		//String hex = hexToString(decoded[10]);
    		  //BigInteger bi = new BigInteger(hex, 16);
    		//bi.intValue();
    		//(String.format("%.7f", f));
    		
    		//String hex = Integer.toHexString(decoded[10]);
    		// Integer.parseInt(hex);
    		
    		//System.out.println("Lora  " +hex);
    		int n=0;
    		try{
    			//n=Byte.valueOf(hex);
    			//System.out.println("Loraa  " +n);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		System.out.println("Lora1  " +n);
    		
    		System.out.println("decoded 10  " +decoded[10]);
    		System.out.println("decoded 11  " +decoded[11]);
    		System.out.println("decoded 12  " +decoded[12]);	
    		System.out.println("decoded 13  " +decoded[13]);
    		
    		/*ByteBuffer bb = ByteBuffer.wrap(d);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            System.out.println( bb.getInt());*/
    		
		 //int b=(int) Integer.toUnsignedLong((int)decoded[10]);
		// String b=String.format("%x", decoded[10]);
		 //int p1=Integer.valueOf(b);
		 //System.out.println("Pppppp  " +b);
		// n=Integer.parseInt(hex);
		//long h=(long) (decoded[10] & 0x00000000ffffffffL);
		// System.out.println("nnnn " +Integer.toHexString(decoded[10]));
		// System.out.println(b);
		 int w=112;
		// Integer.toBinaryString(w)
		 System.out.println("dfdfdfdf"+Integer.toBinaryString(w));
		 
		 String p=Integer.toBinaryString(w);
		 System.out.println("hiiddddd"+p);
		 int p1=decoded[10] & 0xFF;
    		//int c=decoded[11];
		 int c=0x5f;
    		System.out.println("p1"+p1);
    		System.out.println(c);
    		int d=decoded[12];
    		System.out.println(d);
    		int e=decoded[13] & 0xFF ;
    		 int a= (p1 | (c  << 8) | (d << 16) | (e << 24));
    		
    		
    		System.out.println(a);
    		
    	}
    	
    	//System.out.println("bb : " + bb[4]);
        
        //char character = '2';    
       // int ascii = (int) character;
       // String a= new String(decoded);
        //.out.println(String.format("0x%08X", a));
      //String str=String.format("0x%08X", a);
        //String str = String.format("0x", args)
     //System.out.println("String : " + str);
       /* 
        	System.out.println("Base 64 Decoded  String : " + Integer.toHexString(50));*/
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}


