package com.barath.app;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class CassandraDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassandraDemoApplication.class, args);
	}

}

@Service
class UserService {
	
	public final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
	
	public List<User> getUsers(){
		return this.userRepository.findAll();
	}
	
	@PostConstruct
	public void init() {
		
		Arrays.asList(new User(1L, "barath"))
			.stream()
			.forEach(this::saveUser);
	}
}

interface UserRepository extends CassandraRepository<User, Long>{
	
}

@Table
class User { 
	
	@PrimaryKeyColumn( name= "userid",
			type= PrimaryKeyType.PARTITIONED)
	private Long userId; 
	
	@Column
	private String userName;

	public User(Long userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}
	
	
	
	
}