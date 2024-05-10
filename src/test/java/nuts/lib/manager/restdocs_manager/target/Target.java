package nuts.lib.manager.restdocs_manager.target;

import nuts.lib.manager.restdocs_manager.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.expression.DocsField;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public class Target {

    @RestDocsSnippet(fields = {
            @DocsField(name = "groupName", description = "groupName"),
            @DocsField(name = "groupAuthority.aquatic", description = "aquatic"),
            @DocsField(name = "groupAuthority.agricultural", description = "agricultural"),
            @DocsField(name = "groupAuthority.livestock", description = "livestock"),
    })
    public Object test;
}
