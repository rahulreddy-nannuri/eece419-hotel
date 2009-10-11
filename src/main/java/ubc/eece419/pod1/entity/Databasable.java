/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ubc.eece419.pod1.entity;

import java.io.Serializable;

/**
 *
 * @author yang
 */
public interface Databasable<T> extends Serializable{
    long getId();
    void setId(long id);
    String getName();
}
