package nl.topicus.bitbucket.events;

import com.atlassian.bitbucket.event.pull.PullRequestCommentEvent;
import com.atlassian.bitbucket.event.pull.PullRequestEvent;
import com.atlassian.bitbucket.event.repository.RepositoryRefsChangedEvent;
import com.atlassian.bitbucket.repository.RefChange;
import com.atlassian.bitbucket.server.ApplicationPropertiesService;
import nl.topicus.bitbucket.model.Models;

import java.util.ArrayList;
import java.util.List;

public final class Events
{
    private Events()
    {
    }

    public static BitbucketPushEvent createPushEvent(RepositoryRefsChangedEvent event,
                                                     ApplicationPropertiesService appPropSvc)
    {
        BitbucketPushEvent pushEvent = new BitbucketPushEvent();
        pushEvent.setActor(Models.createActor(event.getUser()));
        pushEvent.setRepository(Models.createRepository(event.getRepository(), appPropSvc));
        List<BitbucketPushChange> changes = new ArrayList<>();
        for (RefChange change : event.getRefChanges())
        {
            changes.add(Models.createChange(change));
        }
        BitbucketPushDetail push = new BitbucketPushDetail();
        push.setChanges(changes);
        pushEvent.setPush(push);
        return pushEvent;
    }

    public static BitbucketServerPullRequestEvent createPullrequestEvent(PullRequestEvent event,
                                                                         ApplicationPropertiesService appPropSvc)
    {
        BitbucketServerPullRequestEvent pullRequestEvent = new BitbucketServerPullRequestEvent();
        pullRequestEvent.setActor(Models.createActor(event.getUser()));
        pullRequestEvent.setPullrequest(Models.createPullrequest(event.getPullRequest(), appPropSvc));
        pullRequestEvent.setRepository(Models.createRepository(event.getPullRequest().getToRef().getRepository(),
                                                               appPropSvc));
        return pullRequestEvent;
    }

    public static BitbucketServerPullRequestCommentEvent createPullRequestCommmentEvent(PullRequestCommentEvent event,
                                                                                   ApplicationPropertiesService appPropSvc)
    {
        BitbucketServerPullRequestCommentEvent commentEvent = new BitbucketServerPullRequestCommentEvent();
        commentEvent.setPullrequest(Models.createPullrequest(event.getPullRequest(), appPropSvc));
        commentEvent.setRepository(Models.createRepository(event.getPullRequest().getToRef().getRepository(), appPropSvc));
        commentEvent.setComment((event.getComment().getText()));
        return commentEvent;
    }
}
