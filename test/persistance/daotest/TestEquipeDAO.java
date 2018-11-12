package persistance.daotest;

import business.entities.Cycliste;
import business.entities.Equipe;
import persistance.dao.CyclisteDAO;
import persistance.dao.EquipeDAO;
import persistance.pere.TU_Pere;

public class TestEquipeDAO extends TU_Pere {
	EquipeDAO edao = new EquipeDAO();
	Equipe s = new Equipe(10, "Ant", 15000);
	CyclisteDAO cdao = new CyclisteDAO();
	Cycliste c = new Cycliste();
	String selectList = "select count(id) from equipe";
	String updateName = "Poiscaille";

	public void testFindById() throws Exception {
		// Normal
		s = edao.findById(2);
		assertEquals(edao.findById(2).getName(), s.getName());

		// Id = 0
		assertNull(edao.findById(0));

		// Id > list size
		assertNull(edao.findById(150));
	}

	public void testCreate() throws Exception {
		// Normal
		edao.create(s);
		assert (edao.findById(edao.findList().size())) != null;
		assertEquals(s.getName(), edao.findById(edao.findList().size()).getName());

		// Object null
		assertNull(edao.create(null));
		
		s.setId(2);
		assertNull(edao.create(s));
	}

	public void testFindList() throws Exception {
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(edao.findList().size(), realNb);
	}

	public void testUpdateById() throws Exception {
		// Normal
		s = edao.findById(2);
		s.setName(updateName);
		edao.updateById(s);
		assertEquals(s.getName(), edao.findById(2).getName());

		// Object null
		assertNull(edao.updateById(null));

		// Id > list size and not present in database
		s.setId(150);
		assertNull(edao.updateById(s));

		// Id not present in database
		s.setId(0);
		assertNull(edao.updateById(s));
	}

	public void testDeleteById() throws Exception {
		// Normal
		edao.deleteById(2);
		int realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(edao.findList().size(), realNb);

		// Id > list size
		edao.deleteById(15);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(edao.findList().size(), realNb);

		// Id not present in database
		edao.deleteById(0);
		realNb = getInserter().select(selectList).getDataAsInt();
		assertEquals(edao.findList().size(), realNb);
	}
}