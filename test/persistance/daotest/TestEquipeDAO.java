package persistance.daotest;

import business.entities.Cycliste;
import business.entities.Equipe;
import persistance.dao.CyclisteDAO;
import persistance.dao.EquipeDAO;
import persistance.pere.TU_Pere;

public class TestEquipeDAO extends TU_Pere {
	
	EquipeDAO equipeDao = new EquipeDAO();
	Equipe equipe = new Equipe(10, "Ant", 15000,"Cyan");
	CyclisteDAO cdao = new CyclisteDAO();
	Cycliste cycliste = new Cycliste();
	String selectList = "select count(id) from equipe";
	String updateName = "Poiscaille";
	String updateColor = "Cyan";

	public void testFindById() throws Exception {
		// Normal
		equipe = equipeDao.findById(2);
		assertEquals(equipeDao.findById(2).getName(), equipe.getName());
		assertEquals(equipeDao.findById(2).getCouleur(), equipe.getCouleur());

		// Id = 0
		assertNull(equipeDao.findById(0));

		// Id > list size
		assertNull(equipeDao.findById(150));
	}

	public void testCreate() throws Exception {
		// Normal
		equipeDao.create(equipe);
		assert (equipeDao.findById(equipeDao.findList().size())) != null;
		String equipeNameFromDB = equipeDao.findById(equipeDao.findList().size()).getName();
		String equipeColorFromDB = equipeDao.findById(equipeDao.findList().size()).getCouleur();
		
		assertEquals(equipe.getName(), equipeNameFromDB);
		assertEquals(equipe.getCouleur(), equipeColorFromDB);

		// Object null
		assertNull(equipeDao.create(null));
		
		equipe.setId(2);
		assertNull(equipeDao.create(equipe));
	}

	public void testFindList() throws Exception {
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(equipeDao.findList().size(), realNb);
	}

	public void testUpdateById() throws Exception {
		// Normal
		equipe = equipeDao.findById(2);
		equipe.setName(updateName);
		equipe.setCouleur(updateColor);
		equipeDao.updateById(equipe);
		assertEquals(equipe.getName(), equipeDao.findById(2).getName());
		assertEquals(equipeDao.findById(2).getCouleur(), equipe.getCouleur());

		// Object null
		assertNull(equipeDao.updateById(null));

		// Id > list size and not present in database
		equipe.setId(150);
		assertNull(equipeDao.updateById(equipe));

		// Id not present in database
		equipe.setId(0);
		assertNull(equipeDao.updateById(equipe));
	}

	public void testDeleteById() throws Exception {
		// Normal
		equipeDao.deleteById(2);
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(equipeDao.findList().size(), realNb);

		// Id > list size
		equipeDao.deleteById(15);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(equipeDao.findList().size(), realNb);

		// Id not present in database
		equipeDao.deleteById(0);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(equipeDao.findList().size(), realNb);
	}
}