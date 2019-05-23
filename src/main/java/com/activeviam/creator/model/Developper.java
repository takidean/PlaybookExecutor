package com.activeviam.creator.model;

import javax.persistence.*;

@Entity
public class Developper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String githubId;
    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


	public String getGithubId() {
		return githubId;
	}

	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}
}
