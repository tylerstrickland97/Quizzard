package quizzard.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import quizzard.app.models.DomainObject;

/**
 * Interface for all Service classes to extend. It gives basic operations that
 * all Service classes will use.
 *
 * @param <T>
 *            Type of entity that will be handled by this service
 * @param <K>
 *            Type of the key for this entity
 * @author Tyler Strickland
 *
 */
public abstract class Service <T extends DomainObject, K> {

    protected abstract JpaRepository<T, K> getRepository ();

    /**
     * Saves the provided object into the database. If the object already
     * exists, `save()` will perform an in-place update, overwriting the
     * existing record.
     *
     * @param obj
     *            The object to save into the database.
     */
    public void save ( final T obj ) {
        getRepository().saveAndFlush( obj );
    }

    /**
     * Returns all records of this type that exist in the database. If you want
     * more precise ways of retrieving an individual record (or collection of
     * records) see `findBy(Example)`
     *
     * @return All records stored in the database.
     */
    public List<T> findAll () {
        return getRepository().findAll();
    }

    /**
     * Deletes an object from the database. This will remove the object from the
     * database, but not from memory. Trying to save it again after deletion is
     * undefined behaviour. YMMV.
     *
     * @param obj
     *            The object to delete from the database.
     */
    public void delete ( final T obj ) {
        getRepository().delete( obj );
    }

    /**
     * Removes all records of a given type from the database. For example,
     * `UserService.deleteAll()` would delete all Users. Be very careful when
     * calling this.
     */
    public void deleteAll () {
        getRepository().deleteAll();
    }

    /**
     * Returns a count of the number of records of a given type stored in the
     * database. This is faster than, and should be preferred to,
     * `findAll().size()` if you don't care about the actual records themselves.
     *
     * @return The number of records in the DB.
     */
    public long count () {
        return getRepository().count();
    }

    /**
     * Checks to see if an object with the provided ID exists or not
     *
     * @param id
     *            Id to check for existence of
     * @return If the object was found
     */
    public boolean existsById ( final K id ) {
        return getRepository().existsById( id );
    }

    /**
     * Finds an object with the provided ID
     *
     * @param id
     *            ID of the object to find
     * @return The found object, null if none
     */
    public T findById ( final K id ) {
        if ( null == id ) {
            return null;
        }
        final Optional<T> res = getRepository().findById( id );
        if ( res.isPresent() ) {
            return res.get();
        }
        return null;
    }

}
