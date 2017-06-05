package com.igz.rssreader.resource;

import com.igz.rssreader.core.resource.RssParserException;
import com.igz.rssreader.domain.RssItem;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class RssParser {

	private static final String TAG_ITEM = "item";
	private static final String TAG_IMAGE = "enclosure";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_LINK = "link";
	private static final String TAG_PUBLISH_DATE = "pubDate";
	private static final String ATR_IMAGE_FORMAT = "image/jpeg";

	static List<RssItem> parseRss(InputStream inputStream, String pattern) throws RssParserException {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			return parseDocument(document, pattern);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new RssParserException(e);
		}
	}

	private static List<RssItem> parseDocument(Document document, String pattern) {
		ArrayList<RssItem> items = new ArrayList<>();
		NodeList itemNodes = document.getElementsByTagName(TAG_ITEM);
		for (int i = 0; i < itemNodes.getLength(); i++) {
			Node node = itemNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				RssItem rssItem = readItem((Element) node);
				if (pattern == null || rssItem.title.toLowerCase()
					.contains(pattern.toLowerCase())) {
					items.add(rssItem);
				}
			}
		}
		return items;
	}

	private static RssItem readItem(Element element) {
		String title = getValue(element, TAG_TITLE);
		String description = getValue(element, TAG_DESCRIPTION);
		String link = getValue(element, TAG_LINK);
		String publishDate = getValue(element, TAG_PUBLISH_DATE);
		String image = readLastImage(element.getElementsByTagName(TAG_IMAGE));
		return new RssItem(image, title, description, link, publishDate);
	}

	private static String getValue(Element element, String tag) {
		Node node = element.getElementsByTagName(tag)
			.item(0);
		return node == null ? "" : node.getTextContent();
	}

	private static String readLastImage(NodeList nodeList) {
		String img = null;
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nImg = nodeList.item(i);
				Node type = nImg.getAttributes()
					.getNamedItem("type");
				if ("image/jpeg".equals(type.getTextContent())) {
					img = nImg.getAttributes()
						.getNamedItem("url")
						.getTextContent();
				}
			}
		}
		return img;
	}

}
