package persistance.daotest;

import business.entities.Cycliste;
import business.entities.Equipe;
import persistance.dao.CyclisteDAO;
import persistance.dao.EquipeDAO;
import persistance.pere.TU_Pere;

public class TestCyclisteDAO extends TU_Pere {
	CyclisteDAO cdao = new CyclisteDAO();
	Cycliste c = new Cycliste();
	EquipeDAO edao = new EquipeDAO();
	Equipe e = new Equipe();
	private String selectList = "select count(id) from cycliste";
	String updateName = "Merx";

	public void testFindById() throws Exception {
		// Normal
		c = cdao.findById(2);
		assertEquals(cdao.findById(2).getName(), c.getName());

		// Id not present in database
		assertNull(cdao.findById(0));

		// Id > list size
		assertNull(cdao.findById(150));
	}

	public void testCreate() throws Exception {
		// Normal
		c.setName("Birinque");
		c.setE(edao.findById(2));
		c.setNbVelos(6);
		cdao.create(c);
		assert (cdao.findById(cdao.findList().size())) != null;
		assertEquals(c.getName(), cdao.findById(cdao.findList().size()).getName());

		// Object null
		assertNull(cdao.create(null));

		// Object with same id as present object in database
		c.setId(2);
		assertNull(cdao.create(c));
	}

	public void testFindList() throws Exception {
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(cdao.findList().size(), realNb);
	}

	public void testUpdateById() throws Exception {
		// Normal
		c = cdao.findById(2);
		c.setName(updateName);
		cdao.updateById(c);
		assertEquals(c.getName(), cdao.findById(2).getName());

		// Object null
		assertNull(cdao.updateById(null));

		// Object with id > list size
		c.setId(150);
		assertNull(cdao.updateById(c));

		// Id not present in database
		c.setId(0);
		assertNull(cdao.updateById(c));
	}

	public void testDeleteById() throws Exception {
		// Normal
		cdao.deleteById(2);
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(cdao.findList().size(), realNb);

		// Object with id > list size
		cdao.deleteById(15);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(cdao.findList().size(), realNb);

		// Id not present in database
		cdao.deleteById(0);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(cdao.findList().size(), realNb);
	}
}