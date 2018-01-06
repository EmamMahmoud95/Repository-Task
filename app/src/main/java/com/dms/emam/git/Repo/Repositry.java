package com.dms.emam.git.Repo;

/**
 * Created by emam_ on 1/4/2018.
 */

public class Repositry {
    String repoName;
    String description;
    String usernameOfOwner;
    boolean fork;
    String repositoryHtml_url;
    String ownerHtml_url;

    public Repositry() {
    }

    public Repositry(String repoName, String description, String usernameOfOwner, boolean fork, String repositoryHtml_url, String ownerHtml_url) {
        this.repoName = repoName;
        this.description = description;
        this.usernameOfOwner = usernameOfOwner;
        this.fork = fork;
        this.repositoryHtml_url = repositoryHtml_url;
        this.ownerHtml_url = ownerHtml_url;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsernameOfOwner() {
        return usernameOfOwner;
    }

    public void setUsernameOfOwner(String usernameOfOwner) {
        this.usernameOfOwner = usernameOfOwner;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getRepositoryHtml_url() {
        return repositoryHtml_url;
    }

    public void setRepositoryHtml_url(String repositoryHtml_url) {
        this.repositoryHtml_url = repositoryHtml_url;
    }

    public String getOwnerHtml_url() {
        return ownerHtml_url;
    }

    public void setOwnerHtml_url(String ownerHtml_url) {
        this.ownerHtml_url = ownerHtml_url;
    }


}
