package com.qount.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;

import org.apache.log4j.Logger;
import org.eclipse.persistence.jaxb.BeanValidationMode;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;

public class MyApplication extends ResourceConfig {

	
	private static final Logger LOGGER = Logger.getLogger(MyApplication.class);
	
    public MyApplication() {
    	LOGGER.debug("registring packages");
        packages("io.swagger.jaxrs.listing","com.qount.controller");
        // Validation.
        LOGGER.debug("ValidationConfigurationContextResolver");
        register(ValidationConfigurationContextResolver.class);
        LOGGER.debug("CustomLoggingFilter");
        register(CustomLoggingFilter.class);
        // Providers - JSON.
        LOGGER.debug("MoxyJsonFeature");
        register(MoxyJsonFeature.class);
        LOGGER.debug("io.swagger.jaxrs.listing.ApiListingResource.class, io.swagger.jaxrs.listing.SwaggerSerializers.class");
        registerClasses(io.swagger.jaxrs.listing.ApiListingResource.class, io.swagger.jaxrs.listing.SwaggerSerializers.class);
        LOGGER.debug("MoxyJsonConfig");
        register(new MoxyJsonConfig().setFormattedOutput(true)
                // Turn off BV otherwise the entities on server would be validated by MOXy as well.
                .property(MarshallerProperties.BEAN_VALIDATION_MODE, BeanValidationMode.NONE)
                .resolver());
        LOGGER.debug(super.getClasses());
        LOGGER.debug("**********************************************************");
        LOGGER.debug("configuration complete");
        LOGGER.debug("**********************************************************");
    }
    

    /**
     * Custom configuration of validation. This configuration defines custom:
     * <ul>
     *     <li>ConstraintValidationFactory - so that validators are able to inject Jersey providers/resources.</li>
     *     <li>ParameterNameProvider - if method input parameters are invalid, this class returns actual parameter names
     *     instead of the default ones ({@code arg0, arg1, ..})</li>
     * </ul>
     */
    
    
    public static class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig> {

        @Context
        private ResourceContext resourceContext;

        @Override
        public ValidationConfig getContext(final Class<?> type) {
            return new ValidationConfig()
                    .constraintValidatorFactory(resourceContext.getResource(InjectingConstraintValidatorFactory.class))
                    .parameterNameProvider(new CustomParameterNameProvider());
        }

        /**
         * See ContactCardTest#testAddInvalidContact.
         */
        private class CustomParameterNameProvider implements ParameterNameProvider {

            private final ParameterNameProvider nameProvider;

            public CustomParameterNameProvider() {
                nameProvider = Validation.byDefaultProvider().configure().getDefaultParameterNameProvider();
            }

            @Override
            public List<String> getParameterNames(final Constructor<?> constructor) {
                return nameProvider.getParameterNames(constructor);
            }

            @Override
            public List<String> getParameterNames(final Method method) {
                return nameProvider.getParameterNames(method);
            }
        }
    }
}
