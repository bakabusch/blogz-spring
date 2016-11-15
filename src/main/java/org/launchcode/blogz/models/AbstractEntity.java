package org.launchcode.blogz.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEntity {

	private int uid;
	
	@Id // primary key -- responds to a column in DB
    @GeneratedValue // will create value for us
    @NotNull
    @Column(name = "uid", unique = true)
	public int getUid() {
		return this.uid;
	}
	
	protected void setUid(int uid) {
        this.uid = uid;
    }
	
}
