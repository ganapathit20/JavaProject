package io.calandtime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

public class CalendarAndDataTime {

	static public void  main(String ... strings ) throws Exception {

		Map<String, String> sysPro = System.getenv();

		Map<Object, Object> sysPro1 =	System.getProperties();

		System.out.println(System.getProperties());

		System.out.println(System.getenv("HOME"));

		//String java = System.getProperty("java.specification.version");

		System.out.println(System.currentTimeMillis());

		for(Entry<Object, Object> data : sysPro1.entrySet()) {

			//	System.out.println("Key: " + data.getKey() + " <===> Value: " +sysPro.values());

		}

		LocalDateTime time = LocalDateTime.now();
		System.out.println(time);

		LocalTime time1 = LocalTime.now();
		System.out.println(time1);

		LocalDate date1 = LocalDate.now();
		System.out.println(date1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
		String def = time.format(dtf);
		System.out.println(def);


		Calendar calendar = Calendar.getInstance();  

		System.out.println("At present Calendar's Day: " + calendar.get(Calendar.DATE)); 
		System.out.println("At present Calendar's Month: " + calendar.get(Calendar.MONTH));
		System.out.println("At present Calendar's Year: " + calendar.get(Calendar.YEAR)); 

		System.out.println("The current date is : " + calendar.getTime());  
		calendar.add(Calendar.DATE, -15);  
		System.out.println("15 days ago: " + calendar.getTime());  
		calendar.add(Calendar.MONTH, 4);  
		System.out.println("4 months later: " + calendar.getTime());  
		calendar.add(Calendar.YEAR, 2);  
		System.out.println("2 years later: " + calendar.getTime());  


		//Get Day of Week in Java

		Calendar calendar1 = Calendar.getInstance();
		System.out.println(calendar1.getTime().toString());
		int day = calendar1.get(Calendar.DAY_OF_WEEK);
		System.out.println("Day: " + day);
		int hour = calendar1.get(Calendar.HOUR_OF_DAY);
		System.out.println("Hour: " + hour);
		int minute = calendar1.get(Calendar.MINUTE);
		System.out.println("Minute: " + minute);


		//Timer Concept
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//System.out.println(new Date());
			}
		}, 0, 1000);//1000 -> 1sec once print if u change convent use of time


	}
}
