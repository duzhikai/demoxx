package util.login.common.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuliangliang.1995
 */
public class YamlBinder {

    private static final Logger logger = LoggerFactory.getLogger(YamlBinder.class);

    public YamlBinder(Resource cfgResource) {
        this.cfgResource = cfgResource;
        this.binder = initBinder();
    }

    private final Resource cfgResource;

    private final Binder binder;

    private static final BindHandler BIND_HANDLER = new IgnoreTopLevelConverterNotFoundBindHandler();


    /**
     * bind properties to object .
     * @param clazz
     * @param prefix
     * @param <T>
     * @return
     */
    public <T> T bind(Class<T> clazz, String prefix) {
        Bindable<T> bindable = Bindable.of(ResolvableType.forClass(clazz));
        return binder.bind(prefix, bindable, BIND_HANDLER).get();
    }

    /**
     * init a {@link Binder}
     * @return
     */
    private Binder initBinder() {
        List<PropertySource<?>> sources = loadPropertySource();

        ConversionService conversionService = new DefaultFormattingConversionService();
        PropertySourcesPlaceholdersResolver placeholdersResolver = new PropertySourcesPlaceholdersResolver(sources);

        return new Binder(ConfigurationPropertySources.from(sources), placeholdersResolver, conversionService,
                null);
    }

    /**
     * load property sources
     * @return
     */
    private List<PropertySource<?>> loadPropertySource() {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        try {
            return loader.load(cfgResource.getFilename(), cfgResource);
        } catch (IOException e) {
            logger.error("Load resource {} fail .", cfgResource.getFilename(), e);
        }
        return new ArrayList<>(0);
    }
}
