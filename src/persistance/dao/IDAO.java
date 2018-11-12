package persistance.dao;

import java.util.List;

import persistance.exceptions.DAOException;

public interface IDAO<T> {
	/**
	 * <b>CREER UNE ENTITE 'T' DANS LES DONNEES PERSISTANTES</b>
	 * 
	 * @param pT
	 *            L'entité 'T' à créer.
	 * @return L'entité 'T' créée.
	 * 
	 * @throws EntityAlreadyExistsException
	 *             Situation : L'objet existe déjà.
	 * @throws DAOException
	 *             Situation : Erreur technique dans le DAO.
	 * @throws Exception 
	 */
	public abstract T create(T pT) throws DAOException, Exception;

	/**
	 * <b>RECHERCHER UNE ENTITE 'T' DANS LES DONNEES PERSISTANTES</b>
	 * 
	 * @param pId
	 *            L'identifiant 'technique' de l'entité 'T' à rechercher.
	 * @return L'entité 'T' trouvée.
	 * 
	 * @throws EntityNotFoundException
	 *             Situation : L'objet n'a pas été trouvé.
	 * @throws DAOException
	 *             Situation : Erreur technique dans le DAO.
	 * @throws Exception 
	 */
	public abstract T findById(long pId) throws DAOException, Exception;

	/**
	 * <b>RECHERCHER TOUTES LES ENTITES 'T' DANS LES DONNEES PERSISTANTES</b>
	 * 
	 * @return La liste des entités 'T' trouvées.
	 * 
	 * @throws EntityNotFoundException
	 *             Situation : Les objets n'ont pas été trouvés.
	 * @throws DAOException
	 *             Situation : Erreur technique dans le DAO.
	 * @throws Exception 
	 */
	public abstract List<T> findList() throws DAOException, Exception;

	/**
	 * <b>MODIFIER UNE ENTITE 'T' DANS LES DONNEES PERSISTANTES</b> <br>
	 * <b>(01.)Rechercher l'entité 'T' (par son identifiant)</b> <br>
	 * <b>(02.)Modifier l'entité 'T' (Avec les attributs de l'entité 'T'
	 * fournie)</b>
	 * 
	 * @param pT
	 *            L'entité 'T' à modifier.
	 * @return L'entité 'T' modifiée.
	 * 
	 * @throws EntityNotFoundException
	 *             Situation : L'objet n'a pas été trouvé.
	 * @throws DAOException
	 *             Situation : Erreur technique dans le DAO.
	 * @throws Exception 
	 */
	public abstract T updateById(T pT) throws DAOException, Exception;

	/**
	 * <b>SUPPRIMER UNE ENTITE 'T' DANS LES DONNEES PERSISTANTES</b> <br>
	 * <b>(01.)Rechercher l'entité 'T' (par son identifiant)</b> <br>
	 * <b>(02.)Supprimer l'entité 'T'</b>
	 * 
	 * @param pId
	 *            L'identifiant de l'entité 'T' à supprimer.
	 * 
	 * @throws EntityNotFoundException
	 *             Situation : L'objet n'a pas été trouvé.
	 * @throws DAOException
	 *             Situation : Erreur technique dans le DAO.
	 * @throws Exception 
	 */
	public abstract void deleteById(long pId) throws DAOException, Exception;
}