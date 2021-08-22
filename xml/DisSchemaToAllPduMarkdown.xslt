<?xml version="1.0" encoding="UTF-8"?>
<!--
    title       : DisSchemaToAllPduMarkdown.xslt
    created     : 21 August 2021
    creator     : Don Brutzman
    description : Example template stylesheet to process DIS schema and convert selected information to text
    reference   : DisPduSurvey.md
    reference   : https://www.w3.org/TR/xslt
    identifier  : TODO/DisSchemaToAllPduMarkdown.xslt
    license     : license.html
-->

<!-- TODO authors can edit this example to customize all transformation rules -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xs ="http://www.w3.org/2001/XMLSchema"
	            xmlns:fn ="http://www.w3.org/2005/xpath-functions">
	<!--  extension-element-prefixes="xs" -->
    <xsl:output method="text"/> <!-- output methods:  xml html text -->
    
    <!-- ======================================================= -->
    
    <xsl:template match="/"> <!-- process root of input document -->
        
        <xsl:message>
            <xsl:text>found </xsl:text>
            <xsl:value-of select="count(/xs:schema/xs:element[ends-with(@name,'Pdu')][not(ends-with(@name,'RPdu'))][not(@name = 'SEESPdu')][not(@name = 'IFFPdu')][not(@name = 'TSPIPdu')])"/>
            <xsl:text> Pdu elements</xsl:text>
        </xsl:message>
        <!-- process elements and comments
        <xsl:apply-templates select="/xs:schema/xs:element[ends-with(@name,'Pdu')]">
            <xsl:sort select="@name" order="ascending"/>
        </xsl:apply-templates> -->
        <xsl:text># OpenDIS version 7 PDU Families</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        
        <xsl:for-each select="/xs:schema/xs:complexType[ends-with(@name,'PduType')][not(@name = 'PduType')]">
            <xsl:sort select="@name" order="ascending"/>
            
            <xsl:variable name="complexTypeName" select="@name"/>
            <xsl:variable name="pdusWithMatchingComplexType" 
                        select="/xs:schema/xs:element[ends-with(@name,'Pdu')][not(ends-with(@name,'RPdu'))][not(@name = 'SEESPdu')][not(@name = 'IFFPdu')][not(@name = 'TSPIPdu')]
                                //xs:extension[@base = $complexTypeName]"/>
            
            <xsl:text>## </xsl:text>
            <xsl:value-of select="$complexTypeName"/>
            <xsl:text> includes </xsl:text>
            <xsl:value-of select="count($pdusWithMatchingComplexType)"/>
            <xsl:text> PDUs.</xsl:text>
            <xsl:text>&#10;</xsl:text>
            
            <xsl:for-each select="$pdusWithMatchingComplexType">
                <xsl:sort select="ancestor::xs:element/@name" order="ascending"/>

                <xsl:variable name="ancestorElement" select="ancestor::xs:element"/>
                <xsl:value-of select="position()"/>
                <xsl:text>. </xsl:text>
                <xsl:value-of select="$ancestorElement/@name"/>
                <xsl:text>&#10;</xsl:text>
            </xsl:for-each>
            <xsl:text>&#10;</xsl:text>
            
        </xsl:for-each>
            
    </xsl:template>

    <!-- ===================================================== -->
    
    <xsl:template match="*"> <!-- rule to process each element -->
        
        <xsl:message>
            <xsl:value-of select="local-name()"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="@name"/>
        </xsl:message>
        <!-- common initial processing for each element -->
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text>
        <xsl:value-of select="@name"/>
        
        <xsl:apply-templates select="@*"/> <!-- process attributes for this element -->
        
        <xsl:apply-templates select="xs:element"/> <!-- recurse on child elements -->
        
        <!-- common final processing for each element -->
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text><!-- end element -->
        <xsl:text>&#10;</xsl:text>
        
    </xsl:template>

    <!-- ===================================================== -->
    
    <xsl:template match="@*"> <!-- rule to process each attribute -->
        
        <!-- common processing for each attribute -->
        <xsl:text> </xsl:text>
        <xsl:value-of select="local-name()"/>
        <xsl:text>='</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>'</xsl:text>
        
    </xsl:template>

    <!-- ===================================================== -->
    
    <xsl:template match="comment()"> <!-- rule to process each comment -->
    
        <xsl:text disable-output-escaping="yes">&lt;!--</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text disable-output-escaping="yes">--&gt;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        
    </xsl:template>

</xsl:stylesheet>