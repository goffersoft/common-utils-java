/**
 ** File: GenericStateMachine.java
 **
 ** Description : GenericStateMachine class - State Machine common code
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author peaswa000
 *
 */
public class GenericStateMachine<TState 
					extends Trigger<TState> >
{
	private static final Logger log = Logger.getLogger(GenericStateMachine.class);
	
	private volatile State<TState> currentState;
	private ArrayList<State<TState>> rootStates;
	private ArrayList<StateTransition<TState>> stateTransitions;
	protected GenericStateMachine(ArrayList<State<TState>> root,
				ArrayList<StateTransition<TState>> transitions)
	{
		rootStates = root;
		currentState = null;
		stateTransitions = transitions;
	}
	
	protected GenericStateMachine(GenericStateMachine<TState> s)
	{
		rootStates = s.rootStates;
		currentState = s.currentState;
		stateTransitions = s.stateTransitions;
	}
	
	public boolean start(State<TState> toState, Object object)
	{
		boolean retVal;
		
		synchronized(this)
		{
			currentState = null;
			retVal = changeStateInternal(toState, object);
		}
		return retVal;
	}
	
	public void stop()
	{
		currentState = null;
	}
	
	protected boolean changeStateInternal(State<TState> toState, Object object)
	{
		State<TState> prevState;
		State<TState> targetState = null;
	
		for(int i = 0; i < stateTransitions.size(); i++)
		{
			if(currentState == stateTransitions.get(i).getFromState())
			{
				targetState = stateTransitions.get(i).getNextState(toState);
				if(targetState != null)
				{
					if(currentState == targetState) {
						return true;
					}
					
					if(currentState != null) {
						currentState.getState().fireOnExit(this, currentState, toState, object);
					}
					
					prevState = currentState;
					currentState = targetState;
					
					if(currentState != null) {
						currentState.getState().fireOnEntry(this, currentState, prevState, object);
					}
					
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	public boolean changeState(State<TState> toState, Object object)
	{
		boolean retVal;
	
		synchronized(this)
		{
			retVal = changeStateInternal(toState, object);
		}
		return retVal;
	}
	
	public State<TState> getCurrentState()
	{
		State<TState> retVal;
		synchronized(this) {
			retVal = currentState;
		}
		return retVal;
	}
	
	public State<TState> getState(TState state)
	{
		if(rootStates == null) {
			return null;
		}
		for(int i = 0; i < rootStates.size(); i++) {
			if(rootStates.get(i).getState() == state) {
				return rootStates.get(i);
			}
		}
		return null;
	}
	
	public StateTransition<TState> getStateTransition() {
		StateTransition<TState> retVal = null;
		
		synchronized(this) {
			if(stateTransitions != null) {
			
				for(int i = 0; i < stateTransitions.size(); i++)
				{
					if(currentState.getState() == stateTransitions.get(i).getFromState().getState()) {
						retVal = stateTransitions.get(i);
						break;
					}
				}
			}
		}
		return retVal;
	}
	
	public StateTransition<TState> getStateTransition(State<TState> state) {
		if(stateTransitions == null) {
			return null;
		}
		
		for(int i = 0; i < stateTransitions.size(); i++)
		{
			if(state.getState() == stateTransitions.get(i).getFromState().getState()) {
				return stateTransitions.get(i);
			}
		}
		
		return null;
	}
	
	public StateTransition<TState> getStateTransition(TState state) {
		if(stateTransitions == null) {
			return null;
		}
		
		for(int i = 0; i < stateTransitions.size(); i++)
		{
			if(state == stateTransitions.get(i).getFromState().getState()) {
				return stateTransitions.get(i);
			}
		}
		
		return null;
	}
	
	public  ArrayList<State<TState>> getListOfValidToStates() {
		ArrayList<State<TState>> retVal = null;
		
		synchronized(this) {
			if(stateTransitions != null) {
				for(int i = 0; i < stateTransitions.size(); i++)
				{
					if(currentState.getState() == stateTransitions.get(i).getFromState().getState()) {
						retVal = stateTransitions.get(i).getToStates();
						break;
					}
				}
			}
		}
		return retVal;
	}
	
	public  ArrayList<State<TState>> getListOfValidToStates(State<TState> fromState) {
		if(stateTransitions == null) {
			return null;
		}
		
		for(int i = 0; i < stateTransitions.size(); i++)
		{
			if(fromState.getState() == stateTransitions.get(i).getFromState().getState()) {
				return stateTransitions.get(i).getToStates();
			}
		}
		
		return null;
	}
	
	public  ArrayList<State<TState>> getListOfValidToStates(TState fromState) {
		if(stateTransitions == null) {
			return null;
		}
		
		for(int i = 0; i < stateTransitions.size(); i++)
		{
			if(fromState == stateTransitions.get(i).getFromState().getState()) {
				return stateTransitions.get(i).getToStates();
			}
		}
		
		return null;
	}
	
	public boolean isValidStateTransition(State<TState> toState) {
		boolean retVal = false;
		
		synchronized(this) {
			if(stateTransitions == null) {
				return false;
			}
			
			for(int i = 0; i < stateTransitions.size(); i++)
			{
				if(currentState.getState() == stateTransitions.get(i).getFromState().getState()) {
					if(stateTransitions.get(i).getNextState(toState) == null) {
						retVal = false;
					} else {
						retVal = true;
					}
					break;
				}
			}
		}
		return retVal;
	}
	
	public boolean isValidStateTransition(TState toState) {
		boolean retVal = false;
		
		synchronized(this) {
			if(stateTransitions == null) {
				return false;
			}
			
			for(int i = 0; i < stateTransitions.size(); i++)
			{
				if(currentState.getState() == stateTransitions.get(i).getFromState().getState()) {
					if(stateTransitions.get(i).getNextState(toState) == null) {
						retVal = false;
					} else {
						retVal = true;
					}
					break;
				}
			}
		}
		return retVal;
	}
	
	@Override
	public Object clone()
	{
		return (Object) new GenericStateMachine<TState>(this);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		
		if(obj instanceof GenericStateMachine<?>)
		{
			GenericStateMachine<?> s = (GenericStateMachine<?>)obj;
			
			if(currentState == s.currentState &&
				(ArrayList<?>)rootStates == s.rootStates &&
				(ArrayList<?>)stateTransitions == s.stateTransitions)
			{
				return true;
			}
		}
		return false;
	}
}