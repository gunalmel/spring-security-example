package edu.umich.med.michr.scheduling.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import edu.umich.med.michr.scheduling.domain.AbstractUser;
import edu.umich.med.michr.scheduling.domain.User;

/**
 * Data access object (User Repository) to manipulate/view user account records
 * in the database. All get operations assume order by user name asc. by
 * default. 
 * The number of users returned by queries may be multiplied by role
 * name in the case of many to many user-role relationship due to mapping of
 * roles through ElementsCollection annotation (implies one-to-many
 * relationship) that is why ditinct transformation is used where that can happen.
 * 
 * @author Melih Gunal
 * 
 */
@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends AbstractBaseDao<User, Long> {

	private static final Logger logger = getLogger();
	@Inject
	@Named("passwordGracePeriod")
	private Integer pwdGracePeriod;
	@Inject
	@Named("inactivityGracePeriod")
	private Integer accInactivityGracePeriod;
	private DateTime currentDateTime = new DateTime();

	public UserDao() {
		super(User.class);
	}

	/**
	 * Gets the user using user name. User name should be marked with NaturalId
	 * for hibernate caching optimization when this query is used. User name is
	 * the actual primary key but still id is used as surrogate primary key.
	 * 
	 * @param un
	 *            User name as string
	 * @return User having the specified user name.
	 */
	public User getByUserName(String un) {
		return (User) getCriteria().add(
				Restrictions.naturalId().set(AbstractUser.USER_NAME, un))
				.uniqueResult();
	}

	/**
	 * Returns the list of all user accounts which are approved/enabled.
	 * 
	 * @return List of all enabled user accounts.
	 */
	public List<User> getEnabled() {
		return getCriteria().add(Restrictions.eq(User.ENABLED, true))
				.addOrder(Order.asc(User.USER_NAME))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	/**
	 * Returns the list of all user accounts which are not-approved/disabled.
	 * 
	 * @return List of all disabled user accounts.
	 */
	public List<User> getDisabled() {
		return getCriteria().add(Restrictions.eq(User.ENABLED, false))
				.addOrder(Order.asc(User.USER_NAME))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	/**
	 * Gets the list of user accounts that are locked as result of multiple
	 * failed login attempts.
	 * 
	 * @return List of locked user accounts
	 */
	public List<User> getLocked() {
		return getCriteria().add(Restrictions.eq(User.LOCKED, true))
				.addOrder(Order.asc(User.USER_NAME))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	/**
	 * Gets the list of expired user accounts that have become disabled because
	 * of long time inactivity.
	 * 
	 * @return List of user accounts that are expired due to activity.
	 */
	public List<User> getAccountExpired() {
		DateTime accExpireDate = currentDateTime
				.minusDays(accInactivityGracePeriod);
		logger.debug("Account expiration date is calculated using grace period: "
				+ accInactivityGracePeriod + " as: " + accExpireDate.toString());
		return getCriteria()
				.add(Restrictions.le(User.LAST_LOGIN_DATE,
						accExpireDate.toDate()))
				.addOrder(Order.asc(User.USER_NAME))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}

	/**
	 * Gets the list of user accounts whose password has expired to trigger
	 * password reset operation.
	 * 
	 * @return List of user accounts whose passwords need reset.
	 */
	public List<User> getPasswordExpired() {
		DateTime pwdExpireDate = currentDateTime.minusDays(pwdGracePeriod);
		logger.debug("Password expiration date is calculated using grace period: "
				+ pwdGracePeriod + " as: " + pwdExpireDate.toString());
		return getCriteria()
				.add(Restrictions.le(User.LAST_PWD_CHANGE_DATE,
						pwdExpireDate.toDate()))
				.addOrder(Order.asc(User.USER_NAME))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}
}
