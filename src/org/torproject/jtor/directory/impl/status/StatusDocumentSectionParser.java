package org.torproject.jtor.directory.impl.status;

import org.torproject.jtor.directory.impl.status.StatusDocumentParser.DocumentSection;
import org.torproject.jtor.directory.parsing.DocumentFieldParser;

public abstract class StatusDocumentSectionParser {
	
	protected final StatusDocumentImpl document;
	protected final DocumentFieldParser fieldParser;
	
	
	StatusDocumentSectionParser(DocumentFieldParser parser, StatusDocumentImpl document) {
		this.fieldParser = parser;
		this.document = document;
	}
	
	DocumentSection parseKeywordLine() {
		String keywordString = fieldParser.getCurrentKeyword();
		if(getNextStateKeyword() != null && getNextStateKeyword().equals(keywordString)) 
			return nextSection();
			
		final DocumentKeyword keyword = DocumentKeyword.findKeyword(keywordString, getSection());
		/*
		 * dirspec.txt (1.2)
		 * When interpreting a Document, software MUST ignore any KeywordLine that
		 * starts with a keyword it doesn't recognize;
		 */
		if(!keyword.equals(DocumentKeyword.UNKNOWN_KEYWORD))
			parseLine(keyword);
		
		return getSection();
	}
	
	abstract void parseLine(DocumentKeyword keyword);
	abstract String getNextStateKeyword();
	abstract DocumentSection getSection();
	abstract DocumentSection nextSection();
}