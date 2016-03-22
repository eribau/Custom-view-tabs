package org.jenkinsci.plugins.customviewtabs;

import org.junit.Rule;
import org.junit.Test;
import hudson.model.*;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.MockFolder;

import static org.junit.Assert.*;

/**
 * Created by mycronic-praktikant on 2016-03-18.
 *
 * */
public class CustomViewsTabBarTest {
    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void getFailureCount_should_return_0_with_zero_failed_builds() throws Exception {
        FreeStyleProject project = j.createFreeStyleProject();
        MockFolder folder = j.createFolder("testFolder");
        folder.add(project, "name");
        View view = folder.getPrimaryView();

        CustomViewsTabBar viewsTabBar = new CustomViewsTabBar();
        assertEquals(viewsTabBar.getFailureCount(view), 0);
    }
    @Test
    public void testGetFailureCount_should_return_1_with_one_failed_build() throws Exception {
        FreeStyleProject project = j.createFreeStyleProject();

        MockFolder folder = j.createFolder("testFolder");
        folder.add(project, "name");
        View view = folder.getPrimaryView();

        CustomViewsTabBar viewsTabBar = new CustomViewsTabBar();
        assertEquals(viewsTabBar.getFailureCount(view), 1);
    }
}