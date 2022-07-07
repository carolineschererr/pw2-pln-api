package org.acme.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Analysis extends PanacheEntityBase {

	
	// gerando ID
	@Id
	@SequenceGenerator(
		name = "AnalysisSeq",
		sequenceName = "analysis_id_seq",	
		allocationSize = 1,
		initialValue = 3000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analysisSeq")
	public Long id;

    //Atributos
	private String text;
	private String searchWord;
	
    //muitas análises p/ 1 user
	@ManyToOne (cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonManagedReference
	private User user;
	
	//Contrutores
	public Analysis() {}
	
	public Analysis(String text, String searchWord, User user) {
        this.text = text;
        this.searchWord = searchWord;
        this.user = user;
    }
    
    
}
