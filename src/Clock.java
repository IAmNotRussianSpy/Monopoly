import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock extends Thread {
    private String time;
    private int h=0;
    private int m=0;
    private int s=0;
    public Observer observer;

    public String getTime(){return time;}

    public void subscribe(Observer o){observer=o;}

    public void run(){
        while(true){
            try{
                sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            s++;
            if(s==60) {
                s=0;
                m++;
            }
            if(m==60) {
                m=0;
                h++;
            }
            //Calendar cal = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            //time=sdf.format(cal.getTime());
            time=""+h +":"+ m +":"+ s;
            observer.inform();
        }
    }
}
