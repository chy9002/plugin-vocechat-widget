package run.halo.vocechatwidget;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.SettingFetcher;
import run.halo.app.theme.DefaultTemplateEnum;
import run.halo.app.theme.dialect.TemplateHeadProcessor;
import run.halo.app.theme.router.strategy.ModelConst;

/**
 * @author chy9002
 */
@Component
public class VoceChatWidgetHeadProcessor implements TemplateHeadProcessor {

    private final SettingFetcher settingFetcher;

    public VoceChatWidgetHeadProcessor(SettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
                              IElementModelStructureHandler structureHandler) {
        return settingFetcher.fetch("basic", BasicConfig.class)
                .map(basicConfig -> {
                    final IModelFactory modelFactory = context.getModelFactory();
                    model.add(modelFactory.createText(voceChatWidgetScript(basicConfig)));
                    return Mono.empty();
                }).orElse(Mono.empty()).then();
    }

    private String voceChatWidgetScript(BasicConfig config) {
        String script = "";
        if(StringUtils.isNotBlank(config.getVocechat_url()) && StringUtils.isNotBlank(config.getVocechat_userid())){
            script = """
                <script 
                  data-host-id="%s" 
                  data-theme-color="%s"
                  data-close-width="48"
                  data-close-height="48"
                  data-open-width="%s" 
                  data-open-height="%s" 
                  src="%s/widget.js" 
                  async 
                />
                """.formatted(config.getVocechat_userid(),config.getVocechat_color(),config.getVocechat_width(),config.getVocechat_height(),config.getVocechat_url());
        }
        return script;
    }

    public boolean isContentTemplate(ITemplateContext context) {
        return DefaultTemplateEnum.POST.getValue().equals(context.getVariable(ModelConst.TEMPLATE_ID)) || DefaultTemplateEnum.SINGLE_PAGE.getValue().equals(context.getVariable(ModelConst.TEMPLATE_ID));
    }


    @Data
    public static class BasicConfig {
        String vocechat_userid;
        String vocechat_url;
        String vocechat_height;
        String vocechat_width;
        String vocechat_color;
    }
}
