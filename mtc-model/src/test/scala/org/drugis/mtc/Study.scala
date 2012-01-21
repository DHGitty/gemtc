/*
 * This file is part of drugis.org MTC.
 * MTC is distributed from http://drugis.org/mtc.
 * Copyright (C) 2009-2011 Gert van Valkenhoef.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.drugis.mtc

import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class StudyTest extends ShouldMatchersForJUnit {
	@Test def testEquals() {
		val a = new Treatment("A")
		val b = new Treatment("B")

		val meas = Map[Treatment, Measurement](
				(a, new DichotomousMeasurement(a, 10, 100)),
				(b, new DichotomousMeasurement(b, 10, 100))
			)

		new Study("1", meas) should be (new Study("1", meas))
	}

	@Test def testXML() {
		val a = new Treatment("A")
		val b = new Treatment("B")

		val meas = Map[Treatment, Measurement](
				(a, new DichotomousMeasurement(a, 10, 100)),
				(b, new DichotomousMeasurement(b, 10, 100))
			)

		val study = new Study("1", meas)

		study.toXML should be (
			<study id="1"><measurement treatment="A" responders="10" sample="100" /><measurement treatment="B" responders="10" sample="100" /></study>)
	}
}