package nl.topicus.bitbucket.events;

public class BuildStatusEvent
{
    private String commit;
    private String status;

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
}
