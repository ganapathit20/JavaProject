package Collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapDemo {

	public static void main(String[] args) throws Exception {

		House house[] = new House[5];

		house[0]=new House("green",200000,"1000sqf");
		house[1]=new House("red",100000,"800sqf");
		house[2]=new House("white",120000,"1200sqf");
		house[3]=new House("yellow",400000,"1800sqf");
		house[4]=new House("orange",300000,"1500sqf");

		String color[] = new String[10];
		long price[] = new long[10];
		String sqf[] = new String[10];


		for(int i=0; i<house.length; i++) {
			color[i]=house[i].getColur();
			price[i]=house[i].getPrice();
			sqf[i]=house[i].getSqFit();

		}

		for(int i=0; i<color.length; i++) {
			System.out.print(color[i] +" ");
		}

		System.out.println();

		for(int i=0; i<price.length; i++) {
			System.out.print(price[i] +" ");
		}

		System.out.println();

		for(int i=0; i<sqf.length; i++) {
			System.out.print(sqf[i] +" ");
		}



		Map<String, House> mapHouse= new HashMap<String, House>();

		mapHouse.put("first", new House("green",20000,"1000sqf"));
		mapHouse.put("two", new House("red",10000,"800sqf"));
		mapHouse.put("three", new House("white",12000,"1200sqf"));
		mapHouse.put("four", new House("yellow",40000,"1800sqf"));
		mapHouse.put("five", new House("orange",30000,"1500sqf"));

		//Only print keys
		System.out.println(mapHouse.keySet());

		//Only print Values
		System.out.println(mapHouse.values());

		//iterator by for
		for(Map.Entry<String, House> entry : mapHouse.entrySet()) {

			System.out.println(entry.getValue().getColur());

		}
		
		//iterator 
		Iterator<Map.Entry<String, House>> itrHouse = mapHouse.entrySet().iterator();
		while(itrHouse.hasNext()) {
			Map.Entry<String, House> pair = itrHouse.next();
			System.out.println(pair.getValue().getPrice());
		}

	}

}

class House{

	private String colur;
	private long price;
	private String sqFit;

	public House(String name, long price, String sqFit) {
		this.colur = name;
		this.price = price;
		this.sqFit = sqFit;
	}

	public String getColur() {
		return colur;
	}

	public long getPrice() {
		return price;
	}

	public String getSqFit() {
		return sqFit;
	}



}
