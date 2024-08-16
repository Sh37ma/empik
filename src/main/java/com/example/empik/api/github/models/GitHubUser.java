package com.example.empik.api.github.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubUser {
    private int id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private String created_at;
    private int followers;
    private int public_repos;
}
