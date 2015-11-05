package com.assignments.koorong.gym_buddy_alpha_.NavDrawer;

/**
 * Created by Peter on 04/11/2015.
 */
public class NavDrawerItem {
    public String heading, subheading;
    public int icon;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public NavDrawerItem(int icon, String heading, String subheading){
        this.icon = icon;
        this.heading = heading;
        this.subheading = subheading;

    }
}
