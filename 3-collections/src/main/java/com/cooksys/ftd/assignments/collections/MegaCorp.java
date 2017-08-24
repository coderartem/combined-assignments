package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	 /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
	
	private Set<Capitalist> inputCollection = new HashSet<>();
	
    @Override
    public boolean add(Capitalist capitalist) {
    	
    	if(capitalist==null || (!capitalist.hasParent() && capitalist instanceof WageSlave) ){
    		return false;
    	}else{
    		add(capitalist.getParent());
    	}
    	return inputCollection.add(capitalist);
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	return inputCollection.contains(capitalist);
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	
    	Set<Capitalist> cloneOfInputCollection = new HashSet<>();
    	
    	for (Capitalist c : inputCollection){
    		cloneOfInputCollection.add(c);
    	}
    	
        return cloneOfInputCollection;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	
    	Set<FatCat> parents = new HashSet<>();
    	
    	for(Capitalist c : inputCollection){
    		if(c instanceof FatCat) 
    			parents.add((FatCat)c);
    	}    	
    	
    	return parents;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	
    	Set<Capitalist> children = new HashSet<>();
    
    	for(Capitalist c : inputCollection){
    		if(c.hasParent() && fatCat.equals(c.getParent())) 
    			children.add(c);
    	}
    	
    	return children;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	
        Map<FatCat,Set<Capitalist>> hierarchy = new HashMap<>();
        
        for (Capitalist c : inputCollection){
        	if(c instanceof FatCat)
        		hierarchy.put((FatCat)c, getChildren((FatCat)c));
        }
        
        return hierarchy;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    
    private List<FatCat> parentChain = new ArrayList<>();
    
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
        
    	if(!inputCollection.isEmpty() && capitalist!=null && capitalist.hasParent()){
    		parentChain.add(capitalist.getParent());
    		getParentChain(capitalist.getParent());
    	}
    	
    	return parentChain;
    	
    }
}
