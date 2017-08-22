package nl.topicus.bitbucket.events;

import nl.topicus.bitbucket.model.repository.BitbucketServerRepository;

public class BuildStatusEvent
{
    private String commit;
    private String status;
    private String url;
    private BitbucketServerRepository repository;

    public String getCommit()
    {
        return commit;
    }

    public void setCommit(String commit)
    {
        this.commit = commit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BitbucketServerRepository getRepository() {
        return repository;
    }

    public void setRepository(BitbucketServerRepository repository) {
        this.repository = repository;
    }
}
