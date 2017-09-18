package com.plantplaces.dao;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.Session;

import com.plantplaces.dto.Photo;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;

@Named
public class PhotoHbmDAO extends PlantPlacesHbmDAO<Photo> implements IPhotoDAO {

	/* (non-Javadoc)
	 * @see com.plantplaces.dao.IPhotoDAO#insert(org.hibernate.Session, com.plantplaces.dto.Photo)
	 */
	@Override
	public void insert(Session session, Photo dto) throws Exception {
		session.save(dto);
	}
	
	@Override
	public List<Photo> fetchPhotos(Specimen specimen) {
		// search for plant
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//create a query
		Query query = session.createQuery("from Photo where specimenId = :specimenId");
		query.setParameter("specimenId", specimen.getId());
		//query.setProperties(plant);
		List list = query.list();
		List<Photo> photos= list;
		
		return photos;
	}

}
