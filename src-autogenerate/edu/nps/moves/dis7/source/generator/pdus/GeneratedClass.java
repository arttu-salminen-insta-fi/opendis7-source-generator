/**
 * Copyright (c) 2008-2021, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */
package edu.nps.moves.dis7.source.generator.pdus;

import java.util.*;

/**
 * Represents one generated class. A generated class has a series of attributes, the
 * order of which is significant. These attributes are used to create instance 
 * variables, getters and setters, and serialization code.
 *
 * @author DMcG
 */

public class GeneratedClass 
{
    /** A list of all the attributes (ivars) of one class */
    protected List<ClassAttribute> classAttributes = new ArrayList<>();
    
    /** A list of attribute names and initial values for those attributes. */
    protected List<InitialValue> initialValues = new ArrayList<>();
    
    /** comments for this generated class */
    private String comment;
    
    /** Name of generated class */
    protected String name;
    
    /** parent class */
    protected String parentClass;
    
    /** alias for class */
    protected String aliasFor;
    
    /** interfaces */
    protected String interfaces;
    
    /** Whether this is an XmlRootElement; used only with XML marshalling */
    protected boolean xmlRootElement = false;

    /** Special case for, e.g., enum collection/subclass */
    protected String specialCase;
    
    /** whether this class should be abstract */
    protected boolean abstractClass = false;
    
    /** Constructor creates and configures a new instance object */
    public GeneratedClass()
    {
        
    }
    
    /**
     * setter for parent class
     * @param pParentClass parent class
     */
    public void setParentClass(String pParentClass)
    {
        parentClass = pParentClass;
    }

    /**
     * get parent class
     * @return parent class
     */
    public String getParentClass()
    {
        return parentClass;
    }

    /**
     * get interfaces
     * @return interfaces
     */
    public String getInterfaces()
    {
        return interfaces;
    }
    
    /**
     * set interfaces
     * @param pInterfaces interfaces
     */
    public void setInterfaces(String pInterfaces)
    {
        interfaces = pInterfaces;
    }
    
    /**
     * get name
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * set name
     * @param pName new name
     */
    public void setName(String pName)
    {
        name = pName;
    }
    
    /** Add one ivar/attribute to the class
     * @param anAttribute of interest */
    public void addClassAttribute(ClassAttribute anAttribute)
    {
        classAttributes.add(anAttribute);
    }
    
    /** Return a list of all the attributes of the class
     * @return list of {@link ClassAttribute} values */
    public List<ClassAttribute> getClassAttributes()
    {
        return classAttributes;
    }
    
    /** Add one initial value to the class
     * @param anInitialValue of interest */
    public void addInitialValue(InitialValue anInitialValue)
    {
        initialValues.add(anInitialValue);
    }
    
    /** Return a list of all the initial values of the class
     * @return list of {@link InitialValue} settings */
    public List<InitialValue> getInitialValues()
    {
        return initialValues;
    }
    
    /** Set the comments associated with this class
     * @param comments of interest */
    public void setComment(String comments)
    {
        comment = comments;
    }
    
    
    
    /** get the comments associated with this class
     * @return comments associated with this class */
    public String getClassComments()
    {
        return comment;
    }
    
    /**
     * String representation for this object
     * @return  representation for this object
     */
    @Override
    public String toString()
    {
        String result = "Name: " + name + "\n" + "Comment: " + comment + "\n";
        
        for(int idx = 0; idx < classAttributes.size(); idx++)
        {
            ClassAttribute attribute = classAttributes.get(idx);
            String anAttribute = "  Name: " + attribute.getName() + " Comment: " + attribute.getComment() + 
                                 " Kind: " + attribute.getAttributeKind() + " Type:" + attribute.getType() + "\n";
            result = result + anAttribute;
        }
        return result;
    }

    /**
     * whether class is XML root element
     * @return whether class is XML root element
     */
    public boolean isXmlRootElement()
    {
        return xmlRootElement;
    }

    /**
     * set whether class is XML root element
     * @param isXmlRootElement whether class is XML root element
     */
    public void setXmlRootElement(boolean isXmlRootElement)
    {
        this.xmlRootElement = isXmlRootElement;
    }
    
    /**
     * whether object is special case
     * @param flag whether object is special case
     */
    public void setSpecialCase(String flag)
    {
        this.specialCase = flag;
    }
    
    /**
      whether object is special case
     * @return whether object is special case
     */
    public String getSpecialCase()
    {
        return specialCase;
    }

    /**
     * set whether object is abstract
     * @param flag true or false
     */
    public void setAbstract(String flag)
    {
        this.abstractClass = Boolean.parseBoolean(flag);
    }
    
    /**
     * whether object is abstract
     * @return whether object is abstract
     */
    public boolean isAbstract()
    {
      return abstractClass;
    }

    /**
     * set alias name
     * @param aliasFor alias name for this class
     */
    public void setAliasFor(String aliasFor)
    {
      this.aliasFor = aliasFor;
    }
    
    /**
     * get alias name
     * @return alias name
     */
    public String getAliasFor()
    {
      return aliasFor;
    }
}
