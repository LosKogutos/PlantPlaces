package com.plantplaces.ui;

import java.io.InputStream;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.plantplaces.dto.Photo;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;
import com.plantplaces.service.IPlantService;
import com.plantplaces.service.PlantService;

@Named
@ManagedBean
@Scope("session")
public class SpecimenVO {

	private Plant plant;
	
	@Inject
	private Photo photo;

	@Inject
	private Specimen specimen;

	@Inject
	private IPlantService plantService;
	
	private List<Photo> photos;

	private UploadedFile file;

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
		loadSpecimens();
	}

	private void loadSpecimens() {
		plantService.loadSpecimens(plant);
	}

	public Specimen getSpecimen() {
		return specimen;
	}

	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
	}

	public String save() {
		// set the foreign key to plant ID before saving
		specimen.setPlantId(plant.getGuid());

		try {
			plantService.save(specimen);
			return "specimensaved";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
	}

	public void onRowSelect(SelectEvent event) {
		Specimen specimen = ((Specimen) event.getObject());
		setSpecimen(specimen);
		photos = plantService.fetchPhotos(specimen);
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		if (specimen.getId() == 0) {
			FacesMessage message = new FacesMessage("You have not yet selected a specimen. Please select one before saving the image.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else if (file != null) {
			try {
				InputStream inputStream = file.getInputstream();
				
				photo.setSpecimenId(specimen.getId());
				plantService.savePhoto(getPhoto(), inputStream);
				
				FacesMessage message = new FacesMessage("Successfull ", file.getFileName() + " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FacesMessage message = new FacesMessage("There was a problem, your file was not uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}

		}
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
