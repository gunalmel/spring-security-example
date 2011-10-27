/**
 * 
 */
package edu.umich.med.michr.scheduling.helper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Hibernate User Type that will do the conversion both ways. Each enum requires
 * its own user type. Superclass that works with the PersistentEnum interface
 * and that will be subclassed by each enum that will be used in conjunction
 * with @Type annotation to map enum to db.
 * 
 * @author Melih Gunal
 * 
 */
public abstract class PersistentEnumUserType<T extends PersistentEnum>
		implements UserType {
	@Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }
 
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }
 
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }
 
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }
 
    @Override
    public boolean isMutable() {
        return false;
    }
 
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        int id = rs.getInt(names[0]);
        if(rs.wasNull()) {
            return null;
        }
        for(PersistentEnum value : returnedClass().getEnumConstants()) {
            if(id == value.getId()) {
                return value;
            }
        }
        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
    }
 
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.INTEGER);
        } else {
            st.setInt(index, ((PersistentEnum)value).getId());
        }
    }
 
    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
 
    @Override
    public abstract Class<T> returnedClass();
 
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER};
    }
	
}
