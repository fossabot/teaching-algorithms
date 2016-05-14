/*
 *
 *     Copyright (C) 2015-2016  Moritz Flöter
 *     Copyright (C) 2016  Jonathan Lechner
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teachingalgorithms.logic.sorting;

import teachingalgorithms.logic.sorting.step.SortingStep;

public abstract class SortingAlgorithm {

    public abstract void doAllSteps();

    public abstract boolean doStep();

    public abstract int getInputSize();

    public abstract int getProtocolSize();

    public abstract SortingStep getStep(int stepNumber);

    public abstract void reset();

    public abstract String protocol2LaTeX();

    public abstract String step2LaTeX(int i);

    public abstract boolean undoStep();

    public abstract String getName();

    public abstract int getStepLimit();


}