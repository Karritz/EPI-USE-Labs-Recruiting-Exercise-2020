package com.epi_use.app;

public class Door {
    private String id;
    private String name;
    private String parent_area;
    private String status;

    public Door(String id, String name, String parent_area, String status) {
        this.id = id;
        this.name = name;
        this.parent_area = parent_area;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParent_area() {
        return parent_area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
