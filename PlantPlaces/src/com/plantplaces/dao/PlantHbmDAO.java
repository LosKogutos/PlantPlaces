package com.plantplaces.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.plantplaces.dto.Plant;

public class PlantHbmDAO extends PlantPlacesHbmDAO<Plant> implements IPlantDAO {

	@Override
	public List<Plant> fetchPlants() {
		// search for plant
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//create a query
		Query query = session.createQuery("from Plant");

		List list = query.list();
		List<Plant> plants = Collections.checkedList(list, Plant.class);
		
		return plants;
	}
	
	@Override
	public List<Plant> fetchPlants(Plant plant) {
		// search for plant
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//create a query
		Query query = session.createQuery("from Plant where common like :common");
		query.setParameter("common", "%" + plant.getCommon() + "%");
		//query.setProperties(plant);
		List list = query.list();
		List<Plant> plants = Collections.checkedList(list, Plant.class);
		
		return plants;
	}

	@Override
	public void insert(Session session, Plant plant) throws Exception {		
		session.save(plant);
	}

	@Override
	public void update(Plant plant) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Plant plant) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Plant plant) throws Exception {
		// TODO Auto-generated method stub

	}

}
