package com.mdec.mdec.plannit;

/**
 * Created by Bradley on 2017-02-06.
 */

public class Contact
{
    private String name;
    private String data;
    private String flag;
    private String color;
    private char pref;

    public Contact(String name, String flag, String data, String color, char pref)
    {
        this.name = name;
        this.flag = flag;
        this.data = data;
        this.color = color;
        this.pref = pref;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public char getPref()
    {
        return pref;
    }

    public void setPref(char pref)
    {
        this.pref = pref;
    }

    @Override
    public String toString()
    {
        return name + "~" + data + "~" + flag + "~" + color;
    }
}
