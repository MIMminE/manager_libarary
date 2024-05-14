package nuts.lib.manager.restdocs_manager.target;

import nuts.lib.manager.restdocs_manager.DocsHolder;
import nuts.lib.manager.restdocs_manager.DocsSnippet;
import nuts.lib.manager.restdocs_manager.docs_snippet.expression.ExpressionField;
import nuts.lib.manager.restdocs_manager.sub_section.expression.ExpressionSubSection;

import java.util.Map;

@DocsHolder(DocsHolder.RestDocsHolderType.request)
public class Target {

    @DocsSnippet(fields = {
            @ExpressionField(name = "groupName", description = "groupName"),
            @ExpressionField(name = "groupAuthority.aquatic", description = "aquatic"),
            @ExpressionField(name = "groupAuthority.agricultural", description = "agricultural"),
            @ExpressionField(name = "groupAuthority.livestock", description = "livestock", subSections = {
                    @ExpressionSubSection(name = "data[].adminName", description = "adminNameDes"),
                    @ExpressionSubSection(name = "data[].adminId", description = "adminIdDes")
            }),
    })
    static public RequestEX maps;
}
