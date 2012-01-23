package org.drugis.mtc.parameterization;

import java.util.Comparator;

import org.drugis.mtc.model.Treatment;

public class TreatmentComparator implements Comparator<Treatment> {
	@Override
	public int compare(Treatment o1, Treatment o2) {
		return o1.getId().compareTo(o2.getId());
	}
}
