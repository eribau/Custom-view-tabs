package org.jenkinsci.plugins.customviewtabs;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.TopLevelItem;
import hudson.model.View;
import hudson.util.ListBoxModel;
import hudson.views.ViewsTabBar;
import hudson.views.ViewsTabBarDescriptor;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;


public class CustomViewsTabBar extends ViewsTabBar {
   
    @DataBoundConstructor
    public CustomViewsTabBar() {
        super();
    }

    public String getTabLabel() {
        CustomViewsTabBarDescriptor descriptor = (CustomViewsTabBarDescriptor) super.getDescriptor();
        return descriptor.getLabelText();
    }
    public String getTabColour() {
        CustomViewsTabBarDescriptor descriptor = (CustomViewsTabBarDescriptor) super.getDescriptor();
        return descriptor.getTabColour();
    }
    public int getFailureCount(View v){

        int failed = 0;

        for(TopLevelItem item : v.getItems()) {
            for(Job j : item.getAllJobs()) {

                switch (j.getIconColor()) {
                    case RED:
                        failed++;
                        break;
                    case BLUE:
                    case YELLOW:
                    case DISABLED:
                    default:
                        break;
                }
            }
        }

        return failed;
    }

    @Extension
    public static final class CustomViewsTabBarDescriptor extends ViewsTabBarDescriptor {

        private String labelText = "initial";
        private String tabColour = "00ff00";

        public CustomViewsTabBarDescriptor() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "Custom Views TabBar";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

            labelText = formData.getString("labelText");
            tabColour = formData.getString("tabColour");

            save();
            return false;
        }

        public ListBoxModel doFillTabColourItems(){

            return new ListBoxModel(
                    new ListBoxModel.Option("Green", "00ff00", tabColour.equals("00ff00")),
                    new ListBoxModel.Option("Yellow", "ffff00", tabColour.equals("ffff00")),
                    new ListBoxModel.Option("Red", "ff0000", tabColour.equals("ff0000")));
        }

        public String getLabelText(){
            return labelText;
        }
        public String getTabColour() { return tabColour;}
    }
}
