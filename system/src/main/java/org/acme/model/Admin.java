package org.acme.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Admin extends PanacheEntityBase {
    
    //gera ID
	@Id
	@SequenceGenerator(
		name = "adminSeq",
		sequenceName = "admin_id_seq",	
		allocationSize = 1,
		initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
	public Long id;

    private String username;
    private String password;
	private String email;


    //construtores
    public Admin(){}
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
	    this.email = email;
    }

    // lista os objetos Admin da classe
    public static List<Admin> findByCredentials(String username, String password) {
        List<Admin> listUsername = find("userUsername", username).list();
        List<Admin> listPassword = find("userPassword", password).list();
        listUsername.retainAll(listPassword);
        System.out.println(listUsername);
        return listUsername;
    }


}
