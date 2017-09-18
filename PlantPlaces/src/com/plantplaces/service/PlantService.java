package com.plantplaces.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.plantplaces.dao.IFileDAO;
import com.plantplaces.dao.IPhotoDAO;
import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dao.ISpecimenDAO;
import com.plantplaces.dto.Photo;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;

@Named
public class PlantService implements IPlantService {

	@Inject
	private IPlantDAO plantDAO;
	
	@Inject
	private ISpecimenDAO specimenDAO;
	
	@Inject
	private IFileDAO fileDAO;
	
	@Inject
	private IPhotoDAO photoDAO;
	
	private List<Plant> allPlants;
	
	public IPlantDAO getPlantDAO() {
		return plantDAO;
	}

	public void setPlantDAO(IPlantDAO plantDAO) {
		this.plantDAO = plantDAO;
	}
	
	public void save(Plant plant) throws Exception { 
		if (plant.getGenus() == null || plant.getGenus().isEmpty()) {
			throw new Exception ("Genus required");
		}
		plantDAO.save(plant);
	}

	@Override
	public List<Plant> filterPlants(String filter) {
		if (allPlants == null) {
			allPlants = plantDAO.fetchPlants();
		}

		// the collection we are returning.
		List<Plant> returnPlants = new ArrayList<Plant>();

		// filter the list.
		// interview all possible plants (allPlants), and move plants that contain the
		// filter text into our subset collection (returnPlants)
		for (Plant plant : allPlants) {
			if (plant.toString().contains(filter)) {
				// this plant matches our criteria, so add it to the collection that will be
				// returned from this method.
				returnPlants.add(plant);
			}
		}
		return returnPlants;
	}
	
	/**
	 * Return a list of plants that match the given search criteria
	 * @param plant a parameter that contains the search criteria
	 * @return a list of matching plants
	 */
	@Override
	public List<Plant> fetchPlants(Plant plant) { 		
		List<Plant> plants = plantDAO.fetchPlants(plant);
		return plants;
	}
	
	@Override
	public void save(Specimen specimen) throws Exception {
		specimenDAO.save(specimen);
	}

	@Override
	public void loadSpecimens(Plant plant) {
		List<Specimen> specimens = specimenDAO.fetchByPlantId(plant.getGuid());
		plant.setSpecimens(specimens);
	}

	@Override
	public void savePhoto(Photo photo, InputStream inputStream) throws Exception {
		File directory = new File("/Users/abc/Desktop/Workspace JEE/PlantPlaces/WebContent/resources/images");
		String uniqueImageName = getUniqieImageName();
		File file= new File(directory, uniqueImageName );
		fileDAO.save(inputStream, file);
		photo.setUri(uniqueImageName);
		photoDAO.save(photo);
	}

	private String getUniqieImageName() {
		String imagePrefix = "plantPlaces";
		String imageSuffix = ".jpg";
		String imageMiddle = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		imageMiddle = sdf.format(new Date());
		return imagePrefix + imageMiddle + imageSuffix;
	}	
	
	@Override
	public List<Photo> fetchPhotos(Specimen specimen) {
		return photoDAO.fetchPhotos(specimen);
	}
	
}
