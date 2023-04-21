/**
 * Copyright (c) 2008-2023, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */
package edu.nps.moves.dis7.source.generator.pdus;

import java.io.*;
import java.util.*;

/**
 * Abstract superclass for all the concrete language generators such as Java, Python, C++, etc.
 *
 * @author Don McGregor, Mike Bailey and Don Brutzman
 */

public abstract class AbstractGenerator 
{
    /** Contains abstract descriptions of all the classes, key = name, value = object
    */
    protected Map<String, GeneratedClass> classDescriptions;
    
    /** Directory in which to write the autogenerated source code, relative to root of project */
    public String generatedSourceDirectoryName; // TODO rename generatedSourceDirectoryName

    /** properties for this programming language */
    protected Properties languageProperties;
    
    /**
     * Constructor
     * @param pClassDescriptions of interest
     * @param pLanguageProperties of interest
     */
    public AbstractGenerator(Map<String, GeneratedClass> pClassDescriptions, Properties pLanguageProperties)
    {
        classDescriptions = pClassDescriptions;
        languageProperties = pLanguageProperties;

        // Directory is set in the subclasses

        /*
        try
        {
            directory = (String)languageProperties.getProperty("directory");
        }
        catch(Exception e)
        {
            System.out.println("Missing language property, probably the directory in which the source code should be placed");
            System.out.println("add directory = aDir in the properties for the language");
            System.out.println(e);
        }
         
         */
    }
    
    /**
     * Overridden by the subclasses to generate the code specific to that language.
     */
    public abstract void writeClasses();
    
    /**
     * Create the directory in which to put the generated source code files
     * @param cleanFiles whether to clean out prior files, if any exist in that directory
     */
    protected void createGeneratedSourceDirectory(boolean cleanFiles)
    {
        System.out.println("creating subdirectory=" + this.getGeneratedSourceDirectoryName());
        boolean directoryExists = false;
        if (this.getGeneratedSourceDirectoryName() != null)
        {
            File dir = new File(this.getGeneratedSourceDirectoryName());
            if (dir.exists())
                directoryExists = true;
            else
            {
                System.out.println("Director canWrite()=" + dir.canWrite() + ", creating expected output directory (" + dir.getAbsolutePath() + ")...");
                directoryExists = dir.mkdirs(); // create directory plus intermediate path subdirectories
                System.out.println("File creation success: " + directoryExists);            
            }
            
            if (directoryExists)
            {
                if (cleanFiles)
                {
                    System.out.println("cleaning files in directory " + dir.getPath());
                    for (File someFile : dir.listFiles())
                         if (!someFile.isDirectory() && !someFile.getName().equals(".keep") && 
                             !someFile.getName().equals("README.md"))
                         {
                             System.out.println("... deleting " + someFile.getName());
                             someFile.delete();
                         }
                }
            }
            else System.exit(-1);
        }
        else
        {
            System.err.println ("Error: invalid invocation, current directory not found");
            System.exit(1);
        }
    }

    /**
     * Directory in which to write the autogenerated source code
     * @return the directory name
     */
    public String getGeneratedSourceDirectoryName()
    {
        return generatedSourceDirectoryName;
    }

    /**
     * Directory in which to write the autogenerated source code
     * @param newDirectoryName the directory to set
     */
    public void setGeneratedSourceDirectoryName(String newDirectoryName)
    {
        this.generatedSourceDirectoryName = newDirectoryName;
    }
    
    /** 
     * returns a string with the first letter capitalized. 
     * @param aString of interest
     * @return same string with first letter capitalized
     */
    public String initialCapital(String aString)
    {
        StringBuffer stb = new StringBuffer(aString);
        stb.setCharAt(0, Character.toUpperCase(aString.charAt(0)));
        
        return new String(stb);
    }
    /**
     * returns a string with the first letter lower case.
     * @param aString of interest
     * @return same string with first letter lower case
     */
    public String initialLower(String aString)
    {
        StringBuffer stb = new StringBuffer(aString);
        stb.setCharAt(0, Character.toLowerCase(aString.charAt(0)));

        return new String(stb);
    }
    
    /** This is ugly and brute force, but I don't see an easier way to do it.
     * Given a mask (like 0xf0) we want to know how many bits to shift an
     * integer when masking in a new value. 
     * @param anAttribute of interest
     * @param mask a character set for the mask value
     * @return number of bits to shift
     */
    protected int getBitsToShift(GeneratedClassAttribute anAttribute, String mask)
    {
        String fieldType = anAttribute.getType();
        
       int intMask = Integer.decode(mask);
       
       int maxBits = 0;
       if(fieldType.equalsIgnoreCase("unsigned byte") || fieldType.equalsIgnoreCase("byte"))
       {
           maxBits = 8;
       }
       else if(fieldType.equalsIgnoreCase("unsigned short") || fieldType.equalsIgnoreCase("short"))
       {
           maxBits = 16;
       }
       else if(fieldType.equalsIgnoreCase("unsigned int") || fieldType.equalsIgnoreCase("int"))
       {
           maxBits = 32;
       }
       else if(fieldType.equalsIgnoreCase("unsigned long") || fieldType.equalsIgnoreCase("long"))
       {
           maxBits = 64;
       }
       else if(fieldType.equalsIgnoreCase("float") ) // Bit flags in a float field? Shouldn't happen, but be careful
       {
           maxBits = 32;
       }
       else if(fieldType.equalsIgnoreCase("double") ) // ditto
       {
           maxBits = 64;
       }
       
       int count = 0, startBit = 0, endBit = 0;
       boolean started = false, ended = false;
       
       if(maxBits == 0)
           return 0;
       
       for(int idx = 0; idx < maxBits; idx++)
       {
           int result = intMask & 0x1;
           if(result == 1)
           {
               if(!started)
               {
                    started = true;
                    startBit = idx;
               }
           }
           
           if( (result == 0) && (started == true) )
           {
                 endBit = idx -1;
                 ended = true;
                 break;
           }
                       
           if( (started == true) && (result == 1) && (idx == maxBits-1))
           {
               endBit = idx;
               ended = true;
               break;
           }
           
          // Zero-fill the left-most slot when shifting. Otherwise a sign 
          // can result in a 1 in the leftmost slot
          intMask = intMask >>> 1;
           
       }    // end of loop through bits   
             
       return startBit;
        
    }

}

