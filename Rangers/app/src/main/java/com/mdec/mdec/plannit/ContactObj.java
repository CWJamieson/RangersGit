package com.mdec.mdec.plannit;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris on 2/24/2017.
 */

public class ContactObj implements Serializable {
    private String name;
    private String planner;
    private String flag;
    private String color;

    public ContactObj(String name, String planner, String flag, String color)
    {
        this.name = name;
        this.planner = planner;
        this.flag = flag;
        this.color = color;

    }

    public String getName()
    {
        return name;
    }
    public String getPlanner()
    {
        return planner;
    }
    public String getFlag()
    {
        return flag;
    }
    public String getColor()
    {
        return color;
    }
    public void setName(String in)
    {
        this.name = in;
    }
    public void setPlanner(String in)
    {
        this.planner = in;
    }
    public void setFlag(String in)
    {
        this.flag = in;
    }
    public void setColor(String in)
    {
        this.color = in;
    }
}
