package nuts.lib.manager.restdocs_manager.target;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;
import nuts.lib.manager.restdocs_manager.domain.expression.child.ChildSection;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public class Target {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "groupName", description = "groupName"),
            @FieldDescription(name = "groupAuthority.aquatic", description = "aquatic"),
            @FieldDescription(name = "groupAuthority.agricultural", description = "agricultural"),
            @FieldDescription(name = "groupAuthority.livestock", description = "livestock", subSections = {
                    @ChildSection(name = "data[].adminName", description = "adminNameDes"),
                    @ChildSection(name = "data[].adminId", description = "adminIdDes")
            }),
    })
    static public RequestEX maps;
}
