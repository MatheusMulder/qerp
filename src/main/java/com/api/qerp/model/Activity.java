package com.api.qerp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acitvity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "O campo nome não pode ficar vazio")
	private String name;

	@Column(nullable = false)
	@NotNull(message = "O campo descrição não pode ficar vazio")
	private String description;

	@Column
	private String videoLink;

	@Column
	private String pdfSerialized;

	@Column
	@NotNull(message = "O campo estado não pode ficar vazio")
	@Enumerated(EnumType.STRING)
	private State state;

	@Column
	@Enumerated(EnumType.STRING)
	private Types type;

	@Column
	@NotNull(message = "O campo data de liberação não pode ficar vazio")
	private Date releaseDate;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "activity_course_fk")
	private Course course;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "activity_user", joinColumns = @JoinColumn(name = "id_activity"), inverseJoinColumns = @JoinColumn(name = "id_user"))
	private List<User> users;

	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Activity(Long id, @NotNull(message = "O campo nome não pode ficar vazio") String name,
			@NotNull(message = "O campo descrição não pode ficar vazio") String description, String videoLink,
			String pdfSerialized, @NotNull(message = "O campo estado não pode ficar vazio") State state, Types type,
			@NotNull(message = "O campo data de liberação não pode ficar vazio") Date releaseDate, Course course) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.videoLink = videoLink;
		this.pdfSerialized = pdfSerialized;
		this.state = state;
		this.type = type;
		this.releaseDate = releaseDate;
		this.course = course;
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public String getPdfSerialized() {
		return pdfSerialized;
	}

	public void setPdfSerialized(String pdfSerialized) {
		this.pdfSerialized = pdfSerialized;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", description=" + description + ", videoLink=" + videoLink
				+ ", pdfSerialized=" + pdfSerialized + ", state=" + state + ", type=" + type + ", releaseDate="
				+ releaseDate + ", course=" + course + "]";
	}

}