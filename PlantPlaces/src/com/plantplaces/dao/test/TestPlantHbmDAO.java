package com.plantplaces.dao.test;

import java.util.List;

import org.junit.Test;

import com.plantplaces.dao.PlantHbmDAO;
import com.plantplaces.dto.Plant;

import junit.framework.TestCase;

public class TestPlantHbmDAO extends TestCase {

	private PlantHbmDAO plantHbmDAO;
	private List<Plant> plants;

	@Test
	public void testVerifyFetchByCommonName() {
		givenPlantDAOInstantiated();
		whenCommonNameIsRedbud();
		thenVerifyResults();
	}

	private void thenVerifyResults() {
		assertTrue(plants.size() > 0);
		for (Plant plant : plants) {
			assertEquals("przyklad",plant.getCommon());
		}
	}

	private void whenCommonNameIsRedbud() {
		Plant p = new Plant();
		p.setCommon("przyklad");
		plants = plantHbmDAO.fetchPlants(p);
		
	}

	private void givenPlantDAOInstantiated() {
		plantHbmDAO = new PlantHbmDAO();		
	}
	
}
