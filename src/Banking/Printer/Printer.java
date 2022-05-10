package Banking.Printer;

import java.util.List;

public class Printer {
	
	public static void print(List<?> list) {
		if (list==null) return;
		list.forEach(t -> System.out.println(t));
	}
	
	public static void print(Object obj) {
		if (obj==null) return;
		System.out.println(obj.toString());
	}

}
