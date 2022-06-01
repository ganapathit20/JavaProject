package demo;

import java.time.LocalDate;
import java.util.Scanner;

public class BeforeAfterDays {

	public static void main(String[] args) {
		
		LocalDate localDate = LocalDate.now();
		
		System.out.println(localDate);

		try (Scanner input = new Scanner(System.in)) {
			
			String currentData = input.nextLine();
			
			//String ldate = localDate.toString();

			String temDate[] = currentData.split("-");

			String date = temDate[0];

			String month = temDate[1];

			String year = temDate[2];

			String yesterday = yesDate(Integer.parseInt(date), Integer.parseInt(month), Integer.parseInt(year));
			System.out.println("Yesterday Date: " + yesterday);

			System.out.println("Today Date: " + currentData);

			String tomorrow = tomoDate(Integer.parseInt(date), Integer.parseInt(month), Integer.parseInt(year));
			System.out.println("Tomorrow Date: " + tomorrow);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	public static String yesDate(int date, int month, int year) {
		
		// 1 2 1 2 1 2 1 2 1 2 1 2
		// j f m e may ju jy au sep oct nov dec
		// 31 28 31 30 31 30 31 30 31 30 31 30

		if (month == 1 && date == 1) {

			int endDate = 31;
			int endMoth = 12;
			int endYear = year - 1;

			return endDate + ":" + endMoth + ":" + endYear;

		} else if (year % 4 == 0 && month == 3 && date == 1) {// leaf year

			int leDay = 29;
			int leMonth = month - 1;

			return leDay + ":" + leMonth + ":" + year;

		} else if (month == 3 && date == 1) {

			int yDate = 28;
			int yMonth = month - 1;

			return yDate + ":" + yMonth + ":" + year;

		} else if (month % 2 == 1 && date == 1) {

			int yDate = 30;
			int yMonth = month - 1;

			return yDate + ":" + yMonth + ":" + year;

		} else if (month % 2 == 0 && date == 1) {

			int yDate = 31;
			int yMonth = month - 1;

			return yDate + ":" + yMonth + ":" + year;

		} else {

			return date - 1 + ":" + month + ":" + year;

		}

	}

	public static String tomoDate(int date, int month, int year) {
		
		// 1 2 1 2 1 2 1 2 1 2 1 2
		// j f m e may ju jy au sep oct nov dec
		// 31 28 31 30 31 30 31 30 31 30 31 30

		if (month == 12 && date == 31) {

			int nxtDate = 1;
			int nxtMoth = 1;
			int nxtYear = year + 1;

			return nxtDate + ":" + nxtMoth + ":" + nxtYear;

		} else if (year % 4 == 0 && month == 2 && date == 29) {// leaf year

			int leDay = 1;
			int leMonth = month + 1;

			return leDay + ":" + leMonth + ":" + year;

		} else if (month == 1 && date == 31 || month == 2 && date == 28) {

			int tDate = 1;
			int tMonth = month + 1;

			return tDate + ":" + tMonth + ":" + year;

		} else if (month % 2 == 1 && date == 31) {

			int tDate = 1;
			int tMonth = month + 1;

			return tDate + ":" + tMonth + ":" + year;

		} else if (month % 2 == 0 && date == 30) {

			int tDate = 1;
			int tMonth = month + 1;

			return tDate + ":" + tMonth + ":" + year;

		} else {

			return date + 1 + ":" + month + ":" + year;

		}

	}

}
