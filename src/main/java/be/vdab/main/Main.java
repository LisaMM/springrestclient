package be.vdab.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.vdab.rest.*;
import be.vdab.restclient.*;

public class Main {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/restClient.xml");
		FiliaalClient filiaalClient = context.getBean(FiliaalClient.class);
		try {
			FiliaalListREST filiaalListREST = filiaalClient.findAll();
			for (FiliaalListItemREST filiaalItem : filiaalListREST
					.getFilialen()) {
				System.out.println(filiaalItem.getId() + ":"
						+ filiaalItem.getNaam());
			}
		} catch (UserNamePasswordException ex) {
			System.out.println("Verkeerde gebruikersnaam/paswoord");
		} catch (ForbiddenException ex) {
			System.out.println("Je hebt niet de nodige rechten");
		} catch (Throwable ex) {
			System.out.println(ex.getMessage());
		}
	}
}
