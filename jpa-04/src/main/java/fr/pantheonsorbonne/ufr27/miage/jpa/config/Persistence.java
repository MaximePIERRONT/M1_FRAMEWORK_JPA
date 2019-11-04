package fr.pantheonsorbonne.ufr27.miage.jpa.config;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.h2.tools.Server;

public class Persistence {

	@PostConstruct
	public void launchH2WS() {

		try {
			Server server = Server.createWebServer("-webAllowOthers");

			server = server.start();

			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI("http://localhost:8082"));
			}
		} catch (SQLException | IOException | URISyntaxException e) {

			e.printStackTrace();
			System.exit(-1);
		}

	}

	private final EntityManagerFactory factory = javax.persistence.Persistence.createEntityManagerFactory("h2");

	@Produces
	public EntityManager getEM() {
		EntityManager em = factory.createEntityManager();
		return em;
	}

}
