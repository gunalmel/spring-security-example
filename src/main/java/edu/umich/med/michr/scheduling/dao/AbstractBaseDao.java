package edu.umich.med.michr.scheduling.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.umich.med.michr.scheduling.helper.ReflectionHelper;

/**
 * @author Melih Gunal
 * Parent class for data access objects that will access to a rdbms using hibernate
 * @param <TEntity> Type of the entity that is going to be handled by the data access object
 * @param <TPK> Type of primary key for the entity that is going to be handled by the Dao.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseDao<TEntity, TPK extends Serializable> implements BaseDao<TEntity, TPK>{
	Logger logger = LoggerFactory.getLogger(AbstractBaseDao.class);

	private SessionFactory sessionFactory;
	private Class<? extends TEntity> entityClazz;
	
	// Default constructor is needed by spring container
	protected AbstractBaseDao(){}
	
	protected AbstractBaseDao(Class<? extends TEntity> entityClazz){
		this.setEntityClazz(entityClazz);
	}
	
	protected static Logger getLogger() {
		final Throwable t = new Throwable();
		// t.fillInStackTrace();
		return LoggerFactory.getLogger(t.getStackTrace()[1].getClassName());
	}
	
	public void setEntityClazz(Class<? extends TEntity> entityClazz) {
		logger.debug("Setting the class type of the object to be retrieved.");
		this.entityClazz = entityClazz;
	}
	protected Criteria getCriteria(){
		logger.debug("Creating the criteria object from Hibernate session for the entity type provided as generic argument.");
		return getSession().createCriteria(entityClazz);
	}
	protected Session getSession() {
		logger.debug("Current session object of Hibernate managed by Spring container is asked.");
		return sessionFactory.getCurrentSession();
	}
	@Inject @Named("sessionFactory")
	protected void setSessionFactory(SessionFactory sessionFactory) {
		logger.debug("Hibernate session factory is injected by Spring.");
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Retrieves all the distinct entities.
	 * @return All the persistent objects in the database specified by TEntity type argument
	 */
	@Transactional(readOnly=true)
	public List<TEntity> getAll(){
		logger.debug("Starting to execute the getAll method.");
		return getCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) //If eager fetching for child collection of parent is turned on then the parents will be duplicated in the object result set. Distinct result set transformer would prevent that.
				   .list();
	}
	/**
	 * Retrieves the entity object by primary key value
	 * @param id Primary key value of the object
	 * @return The persistent object of type TEntity identified by the identifier value of type TPK
	 */
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public TEntity getById(TPK id){
		logger.debug("Starting to execute the getById method.");
		// Use Hibernate load() method if exception is needed to be thrown when object is not found. get() method returns null when the object is not found in either cache or in db.
		// If the class is mapped with a proxy, load() method may return an uninitialized proxy instead of the object, get() never returns a proxy. However, load() may not throw exception when object does not exist until the proxy object is accessed.
		return (TEntity)getSession().get(entityClazz, id);
	}
	/**
	 * @param e The entity whose properties are set to the appropriate values to run an example query with Hibernate. Case is ignored, like operator is used to search the set value anywhere within in the target property, numeric fields that are set to 0 are ignored. 
	 * @return Returns a list of objects whose properties matching the values set in example entity. 
	 */
	@Transactional(readOnly=true)
	public List<TEntity> getByExample(TEntity e){
		logger.debug("Starting to execute the getByExample method.");
		Example exampleEntity = Example.create(e)
									.excludeZeroes()
									.ignoreCase()
									.enableLike(MatchMode.ANYWHERE);
		logger.debug("Created the example entity object for query by example API.");
		return getCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(exampleEntity).list();
	}

	public void save(TEntity e){
		logger.debug("save is called.");
		getSession().save(e);
	}
	/**
	 * Deletes the object from the database permanently
	 * @param e Object to be deleted. Primary key value of that parameter object is neede to be set.
	 */
	public void hardDelete(TEntity e){
		logger.debug("hardDelete is called");
		delete(e,true);
	}
	
	/**
	 * Calls Hibernate session objects update method on the entity
	 * @param e Object to be updated. Primary key value of that parameter object is needed to be set if the object is detached.
	 */
	public void update(TEntity e){
		logger.debug("update is called.");
		getSession().update(e);
	}
	
	/**
	 * Returns the list of objects whose property values will match the property value pairs in the parameter map object
	 * If object type property values are set instead of simple value properties then query would fail.
	 * @param propertyMap The Map storing property, value pairs for which Hibernate criteria will be built. 
	 * @return List of entity objects whose properties specified by the keys of the map argument is matching the values specified by the corresponding values of the map.
	 */
	@Transactional(readOnly=true)
	public List<TEntity> getByPropertyValues(Map<String,Object> propertyMap){
		logger.debug("getByPropertyValues is called.");
		return getCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.allEq(propertyMap)).list();
	}
	/**
	 * @param e Entity object that is either going to be soft deleted by updating deleted flag or hard deleted from the db. When a soft delete is triggered only the deleted flag is updated no other properties of the object is persisted. 
	 * @param isHard If set to true the entity object persistence would be removed from the database. Otherwise, deleted flag will be updated to true.
	 * @throws Exception 
	 */
	private void delete(TEntity e, boolean isHard) {
		logger.debug("Hard delete flag is set to: "+isHard+" Private delete method making the decision between hard or soft delete is called.");
		Session s = getSession();
		if(isHard){
			TEntity toBeDeleted = e;
			s.delete(toBeDeleted);
		}
		else{
			ReflectionHelper.<TEntity>setProperty(e,"deleted",true);
			s.update(e);
		}
	}
	protected static Example getExample(Object e){
		return Example.create(e).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE);
	}
}
