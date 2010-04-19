package org.drugis.mtc.yadas

import org.drugis.mtc._
import gov.lanl.yadas.ArgumentMaker

/**
 * ArgumentMaker for individual treatment success probabilities within studies.
 * p_i,k = ilogit(theta_i,k) ; theta_i,k = mu_i + delta_i,b(i),k
 */
class ThetaArgumentMaker[M <: Measurement](
		override val model: NetworkModel[M],
		override val sIdx: Int,
		override val dIdx: Int)
extends ArgumentMaker with ThetaMaker[M] {
	/**
	 * Calculate "the argument": an array of succes-probabilities, one for
	 * each study-arm.
	 * data[sIdx] should contain study baseline means, one per study
	 * data[dIdx] should contain relative effects, one for each non-baseline
	 */
	def getArgument(data: Array[Array[Double]]): Array[Double] = {
		Array.make(0, 0.0) ++ {
			for {d <- model.data} yield theta(d._1, d._2.treatment, data)
		}
	}
}

trait ThetaMaker[M <: Measurement] {
	protected val model: NetworkModel[M]
	protected val sIdx: Int
	protected val dIdx: Int

	private def relativeTreatmentIndex(s: Study[M], t: Treatment)
	: Int = {
		model.studyRelativeEffects(s).findIndexOf(x => x._2 == t)
	}

	private def treatmentIndex(s: Study[M], t: Treatment)
	: Int = {
		val base = model.relativeEffectIndex(s)
		if (model.studyBaseline(s) == t) -1
		else base + relativeTreatmentIndex(s, t)
	}

	protected def theta(s: Study[M], t: Treatment, data: Array[Array[Double]])
	: Double = {
		val baselineIdx = model.studyList.indexOf(s)
		val treatmentIdx = treatmentIndex(s, t)

		if (treatmentIdx >= 0) 
			data(sIdx)(baselineIdx) + data(dIdx)(treatmentIdx)
		else
			data(sIdx)(baselineIdx)
	}
}
