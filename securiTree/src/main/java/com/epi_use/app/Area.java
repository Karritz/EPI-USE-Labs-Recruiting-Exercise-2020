package com.epi_use.app;

public class Area {
    private String id;
    private String name;
    private String parent_area_id;
    private String[] child_area_ids;
    private String[] access_rules;

    public Area(String id, String name, String parent_area_id) {
        this.id = id;
        this.name = name;
        this.parent_area_id = parent_area_id;
        this.child_area_ids = child_area_ids;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParent_area_id() {
        return parent_area_id;
    }

    public String[] getChild_area_ids() {
        return child_area_ids;
    }
    public String[] getAccess_rules() {
        return access_rules;
    }

    public void setAccess_rules(String[] access_rules) {
        this.access_rules = access_rules;
    }
}
