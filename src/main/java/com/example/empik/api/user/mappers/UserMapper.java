package com.example.empik.api.user.mappers;

import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.user.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class UserMapper {


    @Mapping(source = "avatar_url", target = "avatarUrl")
    @Mapping(source = "created_at", target = "createdAt")
    public abstract User map(GitHubUser gitHubUser);
}
