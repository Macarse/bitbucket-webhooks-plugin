package nl.topicus.bitbucket.events;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;
import static nl.topicus.bitbucket.persistence.WebHookConfiguration.*;

public enum EventType {

    PULL_REQUEST_CREATED("pullrequest:created", COLUMN_PR_CREATED),
    PULL_REQUEST_UPDATED("pullrequest:updated", COLUMN_PR_UPDATED),
    PULL_REQUEST_RESCOPED("pullrequest:updated", COLUMN_PR_RESCOPED),
    PULL_REQUEST_REOPENED("pullrequest:updated", COLUMN_PR_REOPENED),
    PULL_REQUEST_MERGED("pullrequest:fulfilled", COLUMN_PR_MERGED),
    PULL_REQUEST_DECLINED("pullrequest:rejected", COLUMN_PR_DECLINED),
    PULL_REQUEST_COMMENT("pullrequest:comment", COLUMN_PR_COMMENTED),
    BUILD_STATUS("build:status", COLUMN_BUILD_STATUS),
    REPO_PUSH("repo:push", COLUMN_REPO_PUSH),
    TAG_CREATED("repo:push", COLUMN_TAG_CREATED),
    BRANCH_CREATED("repo:push", COLUMN_BRANCH_CREATED),
    BRANCH_DELETED("repo:push", COLUMN_BRANCH_DELETED);

    private final String headerValue;
    private final String queryColumn;

    EventType(String headerValue, String queryColumn) {
        this.headerValue = requireNonNull(headerValue, "headerValue");
        this.queryColumn = requireNonNull(queryColumn, "queryColumn");
    }

    @Nonnull
    public String getHeaderValue() {
        return headerValue;
    }

    @Nonnull
    public String getQueryColumn() {
        return queryColumn;
    }
}
