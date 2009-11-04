package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Image extends AbstractEntity<Image> {
	private static final long serialVersionUID = 1L;

	private String name;

	private byte[] data;

	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
