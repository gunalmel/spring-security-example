package edu.umich.med.michr.scheduling.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Melih Gunal
 * Generic interface for data access objects that will access to a rdbms using hibernate
 * @param <TEntity> Type of the entity that is going to be handled by the data access object
 * @param <TPK> Type of primary key for the entity that is going to be handled by the Dao.
 */
public interface BaseDao<TEntity, TPK extends Serializable> {
	/**
	 * Retrieves all the entities of type TEntity
	 * @return List of all entities of type TEntity.
	 */
	List<TEntity> getAll();
	/**
	 * Retrieves the entity object by primary key value
	 * @param entityId id Primary key value of the object
	 * @return The persistent object identified by the id parameter of type TPK
	 */
	TEntity getById(Long entityId);
	/**
	 * Returns the list of objects whose property values will match the property value pairs in the parameter map object
	 * @param propertyMap The Map storing property, value pairs for which Hibernate criteria will be built. Use field names as keys of the map. 
	 * @return List of persistent objects whose properties identified by the keys of the map is set to the values of the same map.
	 */
	List<TEntity> getByPropertyValues(Map<String,Object> propertyMap);
	/**
	 * @param e The entity whose properties are set to the appropriate values to run an example query with Hibernate. The Hibernate query by example settings are implementation specific. (e.g.: whether 0s are ignored, like operator searches for anywhere or at the beginning etc.)  
	 * @return Returns a list of objects whose properties matching the values set in example entity. 
	 */
	List<TEntity> getByExample(TEntity e);
	/**
	 * Deletes the object from the database permanently
	 * @param e Object to be deleted. Primary key value of that parameter object is neede to be set.
	 */
	void hardDelete(TEntity e);
	/**
	 * Calls Hibernate session object's save method. Persists a new entity in the database. Attempts to create a new record in the db for the given entity so be careful about calling it with an detached entity for which there is already a record in the db.
	 * @param e Object entity that is to be saved into the database
	 */
	void save(TEntity e);
	/**
	 * Calls Hibernate session object's update method on the entity
	 * @param e Object to be updated. Primary key value of that parameter object is needed to be set if the object is detached.
	 */
	void update(TEntity e);
	}
