package run.halo.vocechatwidget;

import org.pf4j.PluginWrapper;
import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;

/**
 * @author chy9002
 */
@Component
public class VoceChatWidgetPlugin extends BasePlugin {

    public VoceChatWidgetPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
