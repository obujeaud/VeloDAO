package persistance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entities.Equipe;
import persistance.exceptions.DAOException;
import persistance.manager.JDBCManager;

public class EquipeDAO implements IDAO<Equipe> {
	private String createSQL = "INSERT INTO equipe (name, budget, couleur) values (?, ?, ?)";
	private String selectSolo = "SELECT * FROM equipe WHERE id = ?";
	private String update = "UPDATE equipe SET name = ?, budget = ?, couleur = ? WHERE id = ?";
	private String delete = "DELETE FROM equipe WHERE id = ?";
	private String deleteCyclisteInEquipe = "DELETE FROM cycliste WHERE equipe_id = ?";

	@Override
	public Equipe create(Equipe pT) throws Exception {
		boolean sameId = false;
		List<Equipe> le = findList();
		Connection con;

		// Check if object is null
		if (pT == null) {
			return null;
		}

		// For all teams present, check if pT's id isn't the same as other team
		for (Equipe e : le) {
			if (e.getId() == pT.getId()) {
				sameId = true;
			}
		}

		if (sameId) {
			return null;
		}

		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement(createSQL, PreparedStatement.RETURN_GENERATED_KEYS);
			prep.setString(1, pT.getName());
			prep.setInt(2, pT.getMoney());
			prep.setString(3, pT.getCouleur());
			prep.execute();
			ResultSet rs1 = prep.getGeneratedKeys();

			while (rs1.next()) {
				long id = rs1.getLong("GENERATED_KEY");
				System.out.println("new key for " + pT.getName() + " is " + id);
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
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
	public Equipe findById(long pId) throws Exception {
		Connection con;
		Equipe sp = null;

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
			sp = new Equipe(pId, s.getString(2), s.getInt(3), s.getString(4));
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
	public List<Equipe> findList() throws Exception {
		Connection con;
		List<Equipe> listSpecies = new ArrayList<Equipe>();

		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement("SELECT * FROM equipe");
			ResultSet s = prep.executeQuery();
			System.out.println("\nList of teams : \n");
			while (s.next()) {
				Equipe sp = new Equipe(s.getLong(1), s.getString(2), s.getInt(3), s.getString(4));
				listSpecies.add(sp);
				System.out.println(sp.toString());
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
		return listSpecies;
	}

	@Override
	public Equipe updateById(Equipe pT) throws Exception {
		// Check if the object isn't null and if its id is present in the table
		if (pT == null || pT.getId() > findList().size() || pT.getId() == 0) {
			return null;
		}

		Connection con;
		try {
			con = JDBCManager.getInstance().openConection();
			PreparedStatement prep = con.prepareStatement(update);
			prep.setString(1, pT.getName());
			prep.setLong(2, pT.getMoney());
			prep.setString(3, pT.getCouleur());
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
			PreparedStatement prepBefore = con.prepareStatement(deleteCyclisteInEquipe);
			prepBefore.setLong(1, pId);
			prepBefore.execute();
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