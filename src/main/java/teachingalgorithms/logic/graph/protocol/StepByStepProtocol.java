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

package teachingalgorithms.logic.graph.protocol;

import teachingalgorithms.logic.graph.protocol.step.Step;

import java.util.*;

/**
 * @author Jonathan Lechner
 */
public class StepByStepProtocol implements List<Step> {

    List<Step> steps;

    public StepByStepProtocol() {
        steps = new ArrayList<>();
    }

    @Override
    public int size() {
        return steps.size();
    }

    @Override
    public boolean isEmpty() {
        return steps.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return steps.contains(o);
    }

    @Override
    public Iterator iterator() {
        return steps.iterator();
    }

    @Override
    public Step[] toArray() {
        return (Step[]) steps.toArray();
    }

    @Override
    public boolean add(Step o) {
        return steps.add((Step) o);
    }

    @Override
    public boolean remove(Object o) {
        return steps.remove(o);
    }

    @Override
    public boolean addAll(Collection collection) {
        return steps.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection collection) {
        return steps.addAll(i, collection);
    }

    @Override
    public void clear() {
        steps.clear();
    }

    @Override
    public Step get(int i) {
        return steps.get(i);
    }

    @Override
    public Step set(int i, Step o) {
        return steps.set(i, o);
    }

    @Override
    public void add(int i, Step o) {
        steps.add(i, o);
    }

    @Override
    public Step remove(int i) {
        return steps.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return steps.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return steps.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return steps.listIterator();
    }

    @Override
    public ListIterator listIterator(int i) {
        return steps.listIterator(i);
    }

    @Override
    public StepByStepProtocol subList(int i, int i1) {
        StepByStepProtocol stepByStepProtocol = new StepByStepProtocol();
        stepByStepProtocol.addAll(steps.subList(i, i1));
        return stepByStepProtocol;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return steps.retainAll(collection);
    }

    @Override
    public boolean removeAll(Collection collection) {
        return steps.removeAll(collection);
    }

    @Override
    public boolean containsAll(Collection collection) {
        return steps.containsAll(collection);
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return steps.toArray(objects);
    }
}
