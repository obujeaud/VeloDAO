package persistance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entities.Cycliste;
import business.entities.Equipe;
import persistance.exceptions.DAOException;
import persistance.manager.JDBCManager;

public class CyclisteDAO implements IDAO<Cycliste> {
	private String createSQL = "INSERT INTO cycliste (name, equipe_id, nombre_velos) values (?, ?, ?)";
	private String selectSolo = "SELECT * FROM cycliste WHERE id = ?";
	private String update = "UPDATE cycliste SET name = ?, equipe_id = ?, nombre_velos = ? WHERE id = ?";
	private String delete = "DELETE FROM cycliste WHERE id = ?";
	private String deleteAP = "DELETE FROM cycliste WHERE id = ?";
	private Equipe equ = new Equipe();

	@Override
	public Cycliste create(Cycliste pT) throws Exception {
		boolean sameId = false;
		List<Cycliste> lc = findList();
		Connection con;

		// Check if object is null
		if (pT == null) {
			return null;
		}
		
		// For all cyclists present, check if pT's id isn't the same as other cyclist
		for (Cycliste c : lc) {
			if (c.getId() == pT.getId()) {
				sameId = true;
			}
		}
		
		if(sameId) {
			return null;
		}
		
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement(createSQL, PreparedStatement.RETURN_GENERATED_KEYS);
			EquipeDAO edao = new EquipeDAO();
			equ = edao.findById(pT.getE().getId());
			prep.setString(1, pT.getName());
			prep.setString(2, "" + equ.getId());
			prep.setLong(3, pT.getNbVelos());
			prep.execute();
			ResultSet rs1 = prep.getGeneratedKeys();

			while (rs1.next()) {
				long id = rs1.getLong("GENERATED_KEY");
				System.out.println("new key for " + pT.getName() + " is " + id);
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e);
		} finally {
			try {
				JDBCManager.getInstance().closeConnection();
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
		return pT;
	}

	@Override
	public Cycliste findById(long pId) throws Exception {
		Connection con;
		Cycliste sp = null;
		
		// Check if id is present in the table
		if (pId == 0 || pId > findList().size()) {
			return null;
		}
		
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement(selectSolo);
			prep.setLong(1, pId);
			ResultSet s = prep.executeQuery();
			s.next();
			EquipeDAO edao = new EquipeDAO();
			equ = edao.findById(s.getLong(3));
			sp = new Cycliste(pId, s.getString(2), equ, s.getInt(4));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e);
		} finally {
			try {
				JDBCManager.getInstance().closeConnection();
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
		return sp;
	}

	@Override
	public List<Cycliste> findList() throws Exception {
		Connection con;
		List<Cycliste> listCycliste = new ArrayList<Cycliste>();
		
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement("SELECT * FROM cycliste");
			ResultSet s = prep.executeQuery();
			System.out.println("\nList of cyclists with their team : \n");
			while (s.next()) {
				EquipeDAO edao = new EquipeDAO();
				equ = edao.findById(s.getLong(3));
				Cycliste c = new Cycliste(s.getLong(1), s.getString(2), equ, s.getInt(4));
				System.out.println(c.toString());
				listCycliste.add(c);
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e);
		} finally {
			try {
				JDBCManager.getInstance().closeConnection();
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
		return listCycliste;
	}

	@Override
	public Cycliste updateById(Cycliste pT) throws Exception {
		Connection con;
		
		// Check if the object isn't null and if its id is present in the table
		if (pT == null || pT.getId() > findList().size() || pT.getId() == 0) {
			return null;
		}
		
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement(update);
			EquipeDAO edao = new EquipeDAO();
			equ = edao.findById(pT.getE().getId());
			prep.setString(1, pT.getName());
			prep.setLong(2, equ.getId());
			prep.setLong(3, pT.getNbVelos());
			prep.setLong(4, pT.getId());
			prep.execute();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e);
		} finally {
			try {
				JDBCManager.getInstance().closeConnection();
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
		return pT;
	}

	@Override
	public void deleteById(long pId) throws Exception {
		Connection con;
		
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prepDelete = con.prepareStatement(deleteAP);
			prepDelete.setLong(1, pId);
			prepDelete.execute();

			PreparedStatement prep = con.prepareStatement(delete);
			prep.setLong(1, pId);
			prep.execute();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new DAOException(e);
		} finally {
			try {
				JDBCManager.getInstance().closeConnection();
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
	}
}