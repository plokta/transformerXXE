<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>

    <xsl:template match="/">
        <xsl:variable name="test" select="document('https://gist.githubusercontent.com/plokta/0b7e01d08b3dde10f45692a06fa8f836/raw/3de10d58548f27b852e50c815aa7b1b303755239/extStylesheet.xsl')"/>
        <RESULT><xsl:value-of select='$test'/></RESULT>
    </xsl:template>

</xsl:stylesheet>
