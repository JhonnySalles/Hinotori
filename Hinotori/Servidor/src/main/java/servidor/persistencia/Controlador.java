package servidor.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controlador {
private EntityManagerFactory emf;

	private static Persistence persistence;

	public static Persistence getInstance() {
		if (persistence == null) {
			persistence = new Persistence();
		}

		return persistence;
	}

	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	public Controlador() {
		emf = javax.persistence.Persistence.createEntityManagerFactory("usuarios");
	}

	public static void closeAll() {
		if(persistence != null){
			((EntityManager) persistence).close();
		}
	}

	@SuppressWarnings("unused")
	private void close() {
		emf.close();
	}
}
