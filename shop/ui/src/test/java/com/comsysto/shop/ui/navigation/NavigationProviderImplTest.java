package com.comsysto.shop.ui.navigation;

import com.comsysto.shop.service.authentication.api.AuthenticationService;
import com.comsysto.shop.service.basket.api.Basket;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class NavigationProviderImplTest {

    private Navigation navigation;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Basket basket;

    @Before
    public void setup() {
        initMocks(this);
        when(authenticationService.isAuthorized()).thenReturn(true);
        when(authenticationService.isAuthorized(anyString())).thenReturn(true);
        ApplicationContextMock applicationContext = new ApplicationContextMock();
        applicationContext.putBean("authenticationService", authenticationService);
        applicationContext.putBean("basket", basket);
        NavigationProvider navigationProvider = new NavigationProviderImpl(applicationContext, authenticationService);
        navigation = navigationProvider.getNavigation();
    }

    @After
    public void tearDown() {
        NavigationProvider navigationProvider = new NavigationProviderImpl(new ApplicationContextMock(), authenticationService);
        navigationProvider.setClassPathToScan("com.comsysto.shop.ui.page");
    }

    @Test
    public void testGetNavigation() throws Exception {
        assertNotNull(navigation);
        assertEquals(3, navigation.getNavigationGroups().size());
    }

    @Test
    public void getMainNavigationGroup() {
        NavigationGroup mainGroup = navigation.getMainNavigationGroup();
        assertNotNull(mainGroup);
        assertEquals(6, mainGroup.getNavigationEntries().size());
        assertEquals("Home", mainGroup.getNavigationEntries().get(0).getName());
        assertEquals("Proceed to Checkout", mainGroup.getNavigationEntries().get(5).getName());
    }

    @Test
    public void testRealNavigation() {
        ApplicationContextMock applicationContext = new ApplicationContextMock();
        applicationContext.putBean("basket", mock(Basket.class));
        applicationContext.putBean("authenticationService", mock(AuthenticationService.class));
        NavigationProvider navigationProvider = new NavigationProviderImpl(applicationContext, authenticationService);
        Navigation realNavigation = navigationProvider.getNavigation();
        assertNotNull(realNavigation);
        assertTrue("Main Navigation must not empty", !realNavigation.getMainNavigationGroup().getNavigationEntries().isEmpty());
    }

    @NavigationItem(name = "page1", sortOrder = 2)
    private static class Page1 extends WebPage {
    }

    @NavigationItem(name = "page2", sortOrder = 1)
    private static class Page2 extends WebPage {
    }

    @NavigationItem(name = "page3", sortOrder = 1, group = "group-a")
    private static class Page3 extends WebPage {
    }

    @NavigationItem(name = "page4", sortOrder = 2, group = "group-a")
    private static class Page4 extends WebPage {
    }

    @NavigationItem(name = "page5", sortOrder = 1, group = "group-b")
    private static class Page5 extends WebPage {
    }
}
