<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>

 <xsl:variable name="aLower" select=
 "'abcdefghijklmnopqrstuvwxyz'"/>

 <xsl:variable name="aUpper" select=
 "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>

<xsl:template match="/POS_Output">
	<xsl:for-each select="S">
			<xsl:for-each select="W">
				<xsl:value-of select="substring(@id,3)"/>
				<xsl:text>&#x9;</xsl:text>
				<xsl:value-of select="."/>
				<xsl:text>&#x9;</xsl:text>
				<xsl:value-of select="@LEMMA"/>
				<xsl:text>&#x9;</xsl:text>
				
				<!-- Conditii pentri fiecare din Pos in parte -->
				<xsl:choose>
					
					<!-- Conditie pentru NOUN si ADJ -->
					<xsl:when test="@POS='NOUN' or @POS='ADJECTIVE'">
						
						<xsl:choose>
							<xsl:when test="@POS='NOUN'">
								<xsl:text>NOUN</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>ADJ</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
						

						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="concat('Case=', translate(substring(@Case, 1, 1), $aLower, $aUpper),
								substring(@Case, 2, 2))"/>
						
						<xsl:choose>
							<xsl:when test="@Definiteness='yes'">
								<xsl:text>|Definite=Def</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>|Definite=Ind</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
						
						<xsl:choose>
							<xsl:when test="@Gender='feminine'">
								<xsl:text>|Gender=Fem</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>|Gender=Masc</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
						
						<xsl:value-of select="concat('|Number=', translate(substring(@Number, 1, 1), $aLower, $aUpper),
								substring(@Number, 2, 3), '&#x9;')"/>
						
					</xsl:when>

					<!-- Conditie pentru verb -->
					<xsl:when test="@POS='VERB'">
						<xsl:choose>
							<xsl:when test="@Type='auxiliary'">
								<xsl:text>AUX</xsl:text>
								<xsl:text>&#x9;</xsl:text>
								<xsl:value-of select="@MSD"/>
								<xsl:text>&#x9;</xsl:text>
						
								<xsl:value-of select="concat('Mood=', translate(substring(@Mood, 1, 1), $aLower, $aUpper),
								substring(@Mood, 2, 2))"/>
								<xsl:value-of select="concat('|Number=', translate(substring(@Number, 1, 1), $aLower, $aUpper),
								substring(@Number, 2, 3))"/>
								<xsl:choose>
									<xsl:when test="@Person='third'">
										<xsl:text>|Person=3</xsl:text>
									</xsl:when>
									<xsl:when test="@Person='second'">
										<xsl:text>|Person=2</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>|Person=1</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:value-of select="concat('|Tense=', translate(substring(@Tense, 1, 1), $aLower, $aUpper),
										substring(@Tense, 2, 3))"/>
								<xsl:text>&#x9;</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@POS"/>
								<xsl:text>&#x9;</xsl:text>
								<xsl:value-of select="@MSD"/>
								<xsl:text>&#x9;</xsl:text>
								
								<xsl:value-of select="concat('VerbForm=', translate(substring(@Mood, 1, 1), $aLower, $aUpper),
										substring(@Mood, 2, 2))"/>
								<xsl:value-of select="concat('|Number=', translate(substring(@Number, 1, 1), $aLower, $aUpper),
										substring(@Number, 2, 3))"/>
								<xsl:choose>
									<xsl:when test="@Person='third'">
										<xsl:text>|Person=3</xsl:text>
									</xsl:when>
									<xsl:when test="@Person='second'">
										<xsl:text>|Person=2</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>|Person=1</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:value-of select="concat('|Tense=', translate(substring(@Tense, 1, 1), $aLower, $aUpper),
										substring(@Tense, 2, 3))"/>
								<xsl:text>&#x9;</xsl:text>
								
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<!-- Conditie pentru PRONUME -->
					<xsl:when test="@POS='PRONOUN'">
						<xsl:choose>
							<xsl:when test="@Type='personal'">
								<xsl:text>PRON</xsl:text>
								<xsl:text>&#x9;</xsl:text>
								<xsl:value-of select="@MSD"/>
								<xsl:text>&#x9;</xsl:text>

								<xsl:value-of select="concat('Case=', translate(substring(@Case, 1, 1), $aLower, $aUpper),
								substring(@Case, 2, 2))"/>
								<xsl:value-of select="concat('|Number=', translate(substring(@Number, 1, 1), $aLower, $aUpper),
								substring(@Number, 2, 3))"/>
								<xsl:choose>
									<xsl:when test="@Person='third'">
										<xsl:text>|Person=3</xsl:text>
									</xsl:when>
									<xsl:when test="@Person='second'">
										<xsl:text>|Person=2</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>|Person=1</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:text>|PronType=Prs</xsl:text>
								<xsl:text>&#x9;</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>PRON</xsl:text>
								<xsl:text>&#x9;</xsl:text>
								<xsl:value-of select="@MSD"/>
								<xsl:text>&#x9;</xsl:text>
								<xsl:value-of select="concat('Case=', translate(substring(@Case, 1, 1), $aLower, $aUpper),
								substring(@Case, 2, 2))"/>
								<xsl:choose>
									<xsl:when test="@Person='third'">
										<xsl:text>|Person=3</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:when>
									<xsl:when test="@Person='second'">
										<xsl:text>|Person=2</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>|Person=1</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:otherwise>
									
								</xsl:choose>		
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<!-- Conditie pentru DETERMINER -->
					<xsl:when test="@POS='DETERMINER' or @POS='ARTICLE'">
							
						<xsl:text>DET</xsl:text>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="concat('Case=', translate(substring(@Case, 1, 1), $aLower, $aUpper),
								substring(@Case, 2, 2))"/>
						
						<xsl:choose>
							<xsl:when test="@Gender='feminine'">
								<xsl:text>|Gender=Fem</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>|Gender=Masc</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
						
						<xsl:value-of select="concat('|Number=', translate(substring(@Number, 1, 1), $aLower, $aUpper),
								substring(@Number, 2, 3))"/>
						<xsl:choose>
									<xsl:when test="@Person='third'">
										<xsl:text>|Person=3</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:when>
									<xsl:when test="@Person='second'">
										<xsl:text>|Person=2</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>|Person=1</xsl:text>
										<xsl:text>&#x9;</xsl:text>
									</xsl:otherwise>
									
						</xsl:choose>
						
					</xsl:when>

					<!-- Conditie pentru Adverb -->
					<xsl:when test="@POS='ADVERB'">		
						<xsl:text>ADV</xsl:text>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:text>_&#x9;</xsl:text>
					</xsl:when>

					<!-- Conditie pentru prepozitie1 -->
					<xsl:when test="@POS='ADPOSITION'">
						<xsl:text>ADP</xsl:text>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:text>_&#x9;</xsl:text>
					</xsl:when>
		
					<!-- Conditie pentru CONJUNCTION -->
					<xsl:when test="@POS='CONJUNCTION'">
						<xsl:choose>
									<xsl:when test="@Type='coordinating'">
										<xsl:text>CCONJ</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>SCONJ</xsl:text>
									</xsl:otherwise>
									
						</xsl:choose>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:text>_&#x9;</xsl:text>
					</xsl:when>

					<!-- Conditie pentru NUmeral -->
					<xsl:when test="@POS='NUMERAL'">
						<xsl:text>NUM</xsl:text>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:text>_&#x9;</xsl:text>
					</xsl:when>
					
					


					<xsl:otherwise>
						<xsl:text>PUNCT</xsl:text>
						<xsl:text>&#x9;</xsl:text>
						<xsl:value-of select="@MSD"/>
						<xsl:text>&#x9;</xsl:text>
						<xsl:text>_&#x9;</xsl:text>
						
					</xsl:otherwise>
				</xsl:choose>
				
				<xsl:value-of select="@offset"/>
				<xsl:text>&#x9;</xsl:text>
				<xsl:text>_&#x9;_&#x9;_</xsl:text>
				<xsl:text>&#xA;</xsl:text>
				
				
			</xsl:for-each>
			<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
</xsl:template>
</xsl:stylesheet>