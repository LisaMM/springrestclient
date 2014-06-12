package be.vdab.main;

import java.util.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.vdab.restclient.FiliaalClient;

public class MAIN {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/restClient.xml");
		FiliaalClient filiaalClient = context.getBean(FiliaalClient.class);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nummer filiaal:");
		long id = scanner.nextLong();
		try {
			if (filiaalClient.kanVerwijderdWorden(id)) {
				System.out.println("Filiaal kan verwijderd worden");
			} else {
				System.out.println("Filiaal kan niet verwijderd worden");
			}
		} catch (IllegalArgumentException ex) {
			System.out.println("Filiaal met dit id bestaat niet");
		} catch (Throwable ex) {
			System.out.println(ex.getMessage());
		}
	}
}
