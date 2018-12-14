package com.api.qerp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull(message = "O campo nome não pode ficar vazio.")
	private String name;

	@Column
	@NotNull(message = "O campo descrição não pode ficar vazio.")
	private String description;

	@Column
	private Date releaseDate;

	@Column
	@NotNull(message = "O campo preço não pode ficar vazio.")
	private float price;

	@Column
	@NotNull(message = "O campo hora não pode ficar vazio.")
	private float hours;

	@Column
	@NotNull(message = "O campo dificuldade não pode ficar vazio.")
	@Enumerated(EnumType.STRING)
	private Dificulty dificulty;

	@Column
	@NotNull(message = "O campo status não pode ficar vazio.")
	@Enumerated(EnumType.STRING)
	private State state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_category_fk", nullable = true)
	private Category category;

	/*
	 * @ManyToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "course_group_fk") private Group group;
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<Activity> activiy;

	/*
	 * @OneToMany(mappedBy = "course", fetch = FetchType.EAGER) private
	 * Set<Certification> certification;
	 * 
	 * @OneToMany(mappedBy = "course", fetch = FetchType.EAGER) private Set<Slip>
	 * slip;
	 */

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "course_user", joinColumns = @JoinColumn(name = "id_course"), inverseJoinColumns = @JoinColumn(name = "id_user"))
	private List<User> users;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(Long id, @NotNull(message = "O campo nome não pode ficar vazio.") String name,
			@NotNull(message = "O campo descrição não pode ficar vazio.") String description, Date releaseDate,
			@NotNull(message = "O campo preço não pode ficar vazio.") float price,
			@NotNull(message = "O campo hora não pode ficar vazio.") float hours,
			@NotNull(message = "O campo dificuldade não pode ficar vazio.") Dificulty dificulty,
			@NotNull(message = "O campo status não pode ficar vazio.") State state, Category category,
			List<Activity> activiy, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.price = price;
		this.hours = hours;
		this.dificulty = dificulty;
		this.state = state;
		this.category = category;
		this.activiy = activiy;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public Dificulty getDificulty() {
		return dificulty;
	}

	public void setDificulty(Dificulty dificulty) {
		this.dificulty = dificulty;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Activity> getActiviy() {
		return activiy;
	}

	public void setActiviy(List<Activity> activiy) {
		this.activiy = activiy;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", releaseDate=" + releaseDate
				+ ", price=" + price + ", hours=" + hours + ", dificulty=" + dificulty + ", state=" + state
				+ ", category=" + category + ", activiy=" + activiy + ", users=" + users + "]";
	}

}
