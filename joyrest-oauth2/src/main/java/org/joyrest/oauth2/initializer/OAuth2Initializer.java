/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.oauth2.initializer;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.joyrest.context.initializer.BeanFactory;
import org.joyrest.context.initializer.InitContext;
import org.joyrest.context.initializer.Initializer;
import org.joyrest.oauth2.endpoint.AuthorizationEndpoint;
import org.joyrest.oauth2.endpoint.TokenEndpoint;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.ClassUtils;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

public class OAuth2Initializer implements Initializer {

    @Override
    @SuppressWarnings("unchecked")
    public void init(InitContext context, BeanFactory beanFactory) {
        AuthorizationServerConfiguration authServerConfig = beanFactory.get(AuthorizationServerConfiguration.class);
        TokenStore tokenStore = authServerConfig.getTokenStore();
        UserDetailsService userService = authServerConfig.getUserDetailsService();
        ClientDetailsService clientService = authServerConfig.getClientDetailsService();
        DataSource dataSource = authServerConfig.getDataSource();

        PreAuthenticatedAuthenticationProvider preProvider = new PreAuthenticatedAuthenticationProvider();
        preProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper(userService));

        DaoAuthenticationProvider clientAuthProvider = new DaoAuthenticationProvider();
        clientAuthProvider.setUserDetailsService(new ClientDetailsUserDetailsService(clientService));

        DaoAuthenticationProvider userAuthProvider = new DaoAuthenticationProvider();
        userAuthProvider.setUserDetailsService(userService);

        ProviderManager clientManager = new ProviderManager(singletonList(clientAuthProvider));
        ProviderManager userManager = new ProviderManager(asList(userAuthProvider, preProvider));

        AuthorizationCodeServices authServices = new InMemoryAuthorizationCodeServices();
        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientService);

        DefaultTokenServices tokenServices = tokenServices(clientService, userManager, tokenStore, dataSource);
        TokenGranter tokenGranter = compositeTokenGranter(clientService, userManager, tokenServices, requestFactory,
            authServices);

        TokenEndpoint tokenEndpoint = new TokenEndpoint(clientManager, clientService, tokenGranter);

        TokenStoreUserApprovalHandler userApprovalHandler = new TokenStoreUserApprovalHandler();
        userApprovalHandler.setClientDetailsService(clientService);
        userApprovalHandler.setRequestFactory(requestFactory);
        userApprovalHandler.setTokenStore(tokenStore);

        AuthorizationEndpoint authorizationEndpoint = new AuthorizationEndpoint(
            authServices, clientService, tokenGranter, userApprovalHandler, requestFactory);

        context.addControllerConfiguration(tokenEndpoint);
        context.addControllerConfiguration(authorizationEndpoint);
    }

    private TokenGranter compositeTokenGranter(final ClientDetailsService clientService,
                                               final AuthenticationManager manager,
                                               final DefaultTokenServices tokenServices,
                                               final OAuth2RequestFactory requestFactory,
                                               final AuthorizationCodeServices authorizationCodeServices) {

        List<TokenGranter> granters = new ArrayList<>();
        granters.add(new ClientCredentialsTokenGranter(tokenServices, clientService, requestFactory));
        granters.add(new ImplicitTokenGranter(tokenServices, clientService, requestFactory));
        granters.add(new ResourceOwnerPasswordTokenGranter(manager, tokenServices, clientService, requestFactory));
        granters.add(new RefreshTokenGranter(tokenServices, clientService, requestFactory));
        granters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientService, requestFactory));
        return new CompositeTokenGranter(granters);
    }

    private DefaultTokenServices tokenServices(final ClientDetailsService clientService,
                                               final AuthenticationManager manager,
                                               final TokenStore tokenStore,
                                               final DataSource dataSource) {

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientService);
        tokenServices.setAuthenticationManager(manager);
        tokenServices.setTokenStore(tokenStore);

        if (isNull(dataSource)) {
            return tokenServices;
        } else {
            return txProxiedTokenServices(tokenServices, dataSource);
        }
    }

    private DefaultTokenServices txProxiedTokenServices(DefaultTokenServices tokenServices, DataSource dataSource) {
        AnnotationTransactionAttributeSource attrSource = new AnnotationTransactionAttributeSource();
        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        TransactionInterceptor txInterceptor = transactionInterceptor(attrSource, txManager);
        BeanFactoryTransactionAttributeSourceAdvisor txAdvisor = transactionAdvisor(attrSource, txInterceptor);
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

        ProxyFactory proxyFactory = new ProxyFactory(tokenServices);
        proxyFactory.addAdvice(txInterceptor);
        proxyFactory.addAdvisor(txAdvisor);
        proxyFactory.setInterfaces(
            ClassUtils.getAllInterfacesForClass(
                new SingletonTargetSource(tokenServices).getTargetClass(), classLoader));

        return (DefaultTokenServices) proxyFactory.getProxy(classLoader);
    }

    private BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor(
        AnnotationTransactionAttributeSource source, TransactionInterceptor interceptor) {
        BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
        advisor.setTransactionAttributeSource(source);
        advisor.setAdvice(interceptor);
        return advisor;
    }

    private TransactionInterceptor transactionInterceptor(
        AnnotationTransactionAttributeSource source, PlatformTransactionManager txManager) {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionAttributeSource(source);
        interceptor.setTransactionManager(txManager);
        return interceptor;
    }

}
