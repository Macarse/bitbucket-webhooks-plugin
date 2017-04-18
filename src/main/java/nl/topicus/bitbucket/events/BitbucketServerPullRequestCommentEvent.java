package nl.topicus.bitbucket.events;

import nl.topicus.bitbucket.model.pullrequest.BitbucketServerPullRequest;
import nl.topicus.bitbucket.model.repository.BitbucketServerRepository;

public class BitbucketServerPullRequestCommentEvent
{
    private BitbucketServerPullRequest pullrequest;
    private BitbucketServerRepository repository;
    private String comment;

    public BitbucketServerPullRequest getPullrequest()
    {
        return pullrequest;
    }

    public void setPullrequest(BitbucketServerPullRequest pullrequest)
    {
        this.pullrequest = pullrequest;
    }

    public BitbucketServerRepository getRepository()
    {
        return repository;
    }

    public void setRepository(BitbucketServerRepository repository)
    {
        this.repository = repository;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
