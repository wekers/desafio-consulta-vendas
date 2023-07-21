package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;


	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate){

		// Data de hoje
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		// Campo 'max' vazio -> data de hoje ou data informada
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		// Campo 'min' vazio -> 'max' (data de hoje - 1Ano) ou data informada
		LocalDate min = minDate.equals("") ? max.minusYears(1L) : LocalDate.parse(minDate);

		/* --------------------------------------------------------------------------- */

		/* Em JPQL - descomentar as 2 linhas abaixo*/
		List<SaleSummaryDTO> result = repository.findSummaryJPQL(min, max);
		return result;

		/* Em SQL Nativo - descomentar as 2 linha abaixo*/
		//List<SummaryProjection> result = repository.findSummaryNativeSQL(min, max);
		//return result.stream().map(x -> new SaleSummaryDTO(x)).toList();

		/* --------------------------------------------------------------------------- */


	}

}
