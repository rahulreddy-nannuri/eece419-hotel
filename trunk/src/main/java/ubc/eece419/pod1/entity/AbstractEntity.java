/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ubc.eece419.pod1.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author yang
 */
@Entity
public abstract class AbstractEntity<T> implements Databasable<T> {

    @Id
    @GeneratedValue
    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

   @Override
    public String getName() {
        return this.getClass().getSimpleName().toLowerCase();
    }
}


