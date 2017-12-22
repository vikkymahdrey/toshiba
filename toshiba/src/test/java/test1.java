import java.util.Date;

public class test1  {

public static void main(String[] args) {
    
    System.out.println("/*** In main ***/");
    
    Date dt=new Date();
    long date=new Date(System.currentTimeMillis()).getTime();
    System.out.println("Date:"+date);
    
    
}


}