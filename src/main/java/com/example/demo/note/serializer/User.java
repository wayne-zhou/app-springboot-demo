package com.example.demo.note.serializer;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -4142038659584068924L;
	
	private transient String name; //使用transient修饰的不会被序列化
	
	private Integer sex;
	
	private Integer age;
	
	private Integer cardId;
	
	public User(){}

	public User(String name, Integer sex, Integer age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
}
