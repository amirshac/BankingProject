package Banking.Printer;

import java.util.List;

public class Printer {
	
	public static void print(List<?> list) {
		list.forEach(t -> System.out.println(t));
	}

}
