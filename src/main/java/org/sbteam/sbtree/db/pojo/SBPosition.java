package org.sbteam.sbtree.db.pojo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SBPosition implements Serializable {

    private List<Integer> years = new LinkedList<>();

    private String name;

    public SBPosition() {}

    public SBPosition(List<Integer> years, String name) {
        this.years = years;
        this.name = name;
    }
    
    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public String getName() {
        return name;
    }

    public void setPosition(String name) {
        this.name = name;
    }
}