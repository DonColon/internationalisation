package com.dardan.rrafshi.internationalisation.alphanumeric;

import java.text.BreakIterator;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.dardan.rrafshi.internationalisation.Localisable;


public final class TextProcessor implements Localisable
{
	private BreakIterator sentenceIterator;
	private BreakIterator wordIterator;
	private BreakIterator lineIterator;

	private Collator collator;
	private Locale currentLocale;


	public TextProcessor(final Locale locale)
	{
		this.activateLocale(locale);
	}

	public TextProcessor()
	{
		this(Locale.getDefault());
	}


	public static List<String> extract(final BreakIterator textIterator, final String text)
	{
		final List<String> segments = new ArrayList<>();
		textIterator.setText(text);

		int boundaryIndex = textIterator.first();
		int previousIndex = 0;

		while(boundaryIndex != BreakIterator.DONE) {
			final String segment = text.substring(previousIndex, boundaryIndex);
			segments.add(segment);

			previousIndex = boundaryIndex;
			boundaryIndex = textIterator.next();
		}
		return segments;
	}


	public List<String> extractSentences(final String text)
	{
		return TextProcessor.extract(this.sentenceIterator, text);
	}

	public List<String> extractLines(final String text)
	{
		return TextProcessor.extract(this.lineIterator, text);
	}

	public List<String> extractWords(final String text)
	{
		return TextProcessor.extract(this.wordIterator, text);
	}

	public int countSentences(final String text)
	{
		final List<String> sentences = this.extractSentences(text);
		return sentences.size();
	}

	public int countLines(final String text)
	{
		final List<String> lines = this.extractLines(text);
		return lines.size();
	}

	public int countWords(final String text)
	{
		final List<String> words = this.extractWords(text);
		return words.size();
	}

	public List<String> sort(final List<String> list)
	{
		final List<String> sortedList = new ArrayList<>(list);
		Collections.sort(sortedList, this.collator);
		return sortedList;
	}

	@Override
	public boolean activateLocale(final Locale locale)
	{
		if(locale != null) {
			this.currentLocale = locale;

			this.sentenceIterator = BreakIterator.getSentenceInstance(locale);
			this.wordIterator = BreakIterator.getWordInstance(locale);
			this.lineIterator = BreakIterator.getLineInstance(locale);
			this.collator = Collator.getInstance(locale);

			return true;
		}
		return false;
	}

	@Override
	public Locale currentLocale()
	{
		return this.currentLocale;
	}

}
