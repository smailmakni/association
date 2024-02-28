package tn.dksoft.association.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "t_documents", uniqueConstraints = { @UniqueConstraint(columnNames = "nom") })
//@Table(name = "t_documents")
@EntityListeners(AuditingEntityListener.class)

public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;

	private String nom;

	private String url;

	public Document(String nom, String url) {
		this.nom = nom;
		this.url = url;
	}

	@ManyToOne
	@JoinColumn(name = "equipment_id")
	private Equipment equipment;

	@OneToOne(mappedBy = "document")
	private Attribution attribution;

	@ManyToOne
	@JoinColumn(name = "intervention_id")
	private Intervention intervention;

}
