package cz.ptw.databasebrowser.view.rest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration of repository rest validation
 */
@Configuration
@RequiredArgsConstructor
public class RepositoryRestValidationConfiguration implements RepositoryRestConfigurer {

    private final LocalValidatorFactoryBean beanValidator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", beanValidator);
        v.addValidator("beforeSave", beanValidator);
    }
}