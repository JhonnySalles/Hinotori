package Servidor.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class DaoFactory {

	public DaoFactory() { }
	
	private static final String PERSISTENCE_UNIT_NAME = "projeto";
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory entityManagerFactorInstance() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return emf;
	}
	
}
