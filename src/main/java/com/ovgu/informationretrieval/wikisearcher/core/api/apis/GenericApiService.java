package com.ovgu.informationretrieval.wikisearcher.core.api.apis;

import java.io.IOException;
import java.util.List;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;

/**
 * Servicio que deben de implementar el resto de servicios de este paquete.
 * 
 * @author Carlos
 *
 */
public interface GenericApiService {

	public List<WikiDocument> getWikiDocuments();

}
