package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Manage Hibernation session. */
public class SessionManager {
	private static final SessionFactory sf =
			new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	private SessionManager() {}

	/**
	* Retrieves the session built from the factory. YOU MUST CLOSE THE SESSION AFTER USE!
	*
	* @return Hibernate Session object.
	*/
	public static Session getSession() {
		return sf.openSession();
	}

	// TODO: add switch for embedded and server databases
}
