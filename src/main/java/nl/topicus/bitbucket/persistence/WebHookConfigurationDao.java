package nl.topicus.bitbucket.persistence;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableMap;
import net.java.ao.Query;
import nl.topicus.bitbucket.events.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static nl.topicus.bitbucket.persistence.WebHookConfiguration.*;

@Component
public class WebHookConfigurationDao {
    private final ActiveObjects activeObjects;

    @Autowired
    public WebHookConfigurationDao(@ComponentImport ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    public WebHookConfiguration[] getWebHookConfigurations(Repository repo) {
        return activeObjects.find(WebHookConfiguration.class, Query.select()
                .where(COLUMN_REPO_ID + " = ?", repo.getId())
                .order(COLUMN_TITLE));
    }

    public WebHookConfiguration[] getEnabledWebHookConfigurations(Repository repo, EventType eventType) {
        return activeObjects.find(WebHookConfiguration.class, Query.select()
                .where(COLUMN_REPO_ID + " = ? AND " + COLUMN_ENABLED + " = ? AND " + eventType.getQueryColumn() + " = ?",
                        repo.getId(), true, true)
                .order(COLUMN_TITLE));
    }

    public WebHookConfiguration getWebHookConfiguration(String id) {
        return activeObjects.get(WebHookConfiguration.class, Integer.valueOf(id));
    }

    public int deleteWebhookConfigurations(Repository repo) {
        return activeObjects.deleteWithSQL(WebHookConfiguration.class, COLUMN_REPO_ID + " = ?", repo.getId());
    }

    public void deleteWebhookConfiguration(WebHookConfiguration webHookConfiguration) {
        activeObjects.delete(webHookConfiguration);
    }

    public WebHookConfiguration createOrUpdateWebHookConfiguration(Repository rep, String id, String title, String url, String committersToIgnore,
                                                                   boolean enabled) {
        return createOrUpdateWebHookConfiguration(rep, id, title, url, committersToIgnore, enabled,
                false, true, true, true, true,
                true, true, true, true, true,
                false, false
        );
    }

    public WebHookConfiguration createOrUpdateWebHookConfiguration(Repository rep, String id, String title, String url, String committersToIgnore,
                                                                   boolean enabled, boolean isTagCreated, boolean isBranchDeleted,
                                                                   boolean isBranchCreated, boolean isRepoPush, boolean isPrDeclined,
                                                                   boolean isPrRescoped, boolean isPrMerged, boolean isPrReopened,
                                                                   boolean isPrUpdated, boolean isPrCreated, boolean isPrCommented, boolean isBuildStatus) {
        WebHookConfiguration webHookConfiguration = id == null ? null : getWebHookConfiguration(id);
        if (webHookConfiguration == null || !webHookConfiguration.getRepositoryId().equals(rep.getId())) {
            webHookConfiguration = activeObjects.create(WebHookConfiguration.class, ImmutableMap.<String, Object>builder()
                    .put(COLUMN_BRANCH_CREATED, isBranchCreated)
                    .put(COLUMN_BRANCH_DELETED, isBranchDeleted)
                    .put(COLUMN_ENABLED, enabled)
                    .put(COLUMN_PR_CREATED, isPrCreated)
                    .put(COLUMN_PR_DECLINED, isPrDeclined)
                    .put(COLUMN_PR_MERGED, isPrMerged)
                    .put(COLUMN_PR_REOPENED, isPrReopened)
                    .put(COLUMN_PR_RESCOPED, isPrRescoped)
                    .put(COLUMN_PR_UPDATED, isPrUpdated)
                    .put(COLUMN_PR_COMMENTED, isPrCommented)
                    .put(COLUMN_REPO_ID, rep.getId())
                    .put(COLUMN_REPO_PUSH, isRepoPush)
                    .put(COLUMN_TAG_CREATED, isTagCreated)
                    .put(COLUMN_TITLE, title)
                    .put(COLUMN_URL, url)
                    .put(COLUMN_IGNORED_COMMITERS, committersToIgnore)
                    .put(COLUMN_BUILD_STATUS, isBuildStatus)
                    .build());
        } else {
            webHookConfiguration.setBranchCreated(isBranchCreated);
            webHookConfiguration.setBranchDeleted(isBranchDeleted);
            webHookConfiguration.setEnabled(enabled);
            webHookConfiguration.setPrCreated(isPrCreated);
            webHookConfiguration.setPrDeclined(isPrDeclined);
            webHookConfiguration.setPrMerged(isPrMerged);
            webHookConfiguration.setPrReopened(isPrReopened);
            webHookConfiguration.setPrRescoped(isPrRescoped);
            webHookConfiguration.setPrUpdated(isPrUpdated);
            webHookConfiguration.setPrCommented(isPrCommented);
            webHookConfiguration.setRepoPush(isRepoPush);
            webHookConfiguration.setTagCreated(isTagCreated);
            webHookConfiguration.setTitle(title);
            webHookConfiguration.setURL(url);
            webHookConfiguration.setCommittersToIgnore(committersToIgnore);
            webHookConfiguration.setBuildStatus(isBuildStatus);
            webHookConfiguration.save();
        }

        return webHookConfiguration;
    }
}
