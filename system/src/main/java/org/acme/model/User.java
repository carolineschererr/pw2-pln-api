package org.acme.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
public class User extends PanacheEntityBase {

	//gerando ID
	
    @Id
	@SequenceGenerator(
		name = "UserSeq",
		sequenceName = "user_id_seq",	
		allocationSize = 1,
		initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
	public Long id;
	
	//atributos
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
	
	@OneToMany (cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
    // um usuário pode ter vários pedidos de análise, mas uma análise é atrelada a apenas 1 usuário
    @JoinColumn(name="Analysis_User")
	@JsonBackReference
	private Set<Analysis> analysis;

	
	//contrutores
	public User() {}

	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String email, String password, String firstName, String lastName,
            Date birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }


    public User(String username, String email, String password, String firstName, String lastName, Date birthDate, Set<Analysis> analysis) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.analysis = analysis;
    }
    public Set<Analysis> getAnalysis() {
		return analysis;
	}
	public void setAnalysis(Set<Analysis> analysis) {
		this.analysis = analysis;
	}
	
	public void addAnalysis(Analysis analysis) {
		this.analysis.add(analysis);
	}

    // lista Users por username e password
    // retorna lista (não tem null)
    public static List<User> findByCredentials(String username, String password) {
	List<User> listUsername = find("userUsername", username).list();
	List<User> listPassword = find("userPassword", password).list();
	listUsername.retainAll(listPassword);
	System.out.println(listUsername);
	return listUsername;
}

}